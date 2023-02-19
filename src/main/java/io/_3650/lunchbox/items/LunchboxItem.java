package io._3650.lunchbox.items;

import java.util.List;

import javax.annotation.Nullable;

import io._3650.lunchbox.menus.LunchboxSelectorMenu;
import io._3650.lunchbox.registry.ModItemTags;
import io._3650.lunchbox.registry.ModItems;
import io._3650.lunchbox.registry.Reference;
import io._3650.lunchbox.registry.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

public class LunchboxItem extends BlockItem {
	
	private static String NBT_TARGETSLOT = "TargetSlot";
	
	private DyeColor color;
	
	public LunchboxItem(Block block, Properties properties, @Nullable DyeColor color) {
		super(block, properties);
		this.color = color;
	}
	
	@Nullable
	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
		if (stack.getItem() instanceof LunchboxItem) {
			return new LunchboxItemCapabilityProvider();
		}
		return null;
	}
	
	@Override
	public int getUseDuration(ItemStack stack) {
		int useDuration = getTargetFood(stack).getUseDuration();
		return useDuration > 0 ? useDuration : 32;
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return getTargetFood(stack).getUseAnimation();
	}
	
	@Override
	public SoundEvent getEatingSound() {
		return SoundEvents.GENERIC_EAT;
	}
	
	@Override
	public SoundEvent getDrinkingSound() {
		return SoundEvents.GENERIC_DRINK;
	}
	
	@Override
	public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
		if (stack.getItem() instanceof LunchboxItem) return getTargetFood(stack).getFoodProperties(entity);
		return stack.getFoodProperties(entity);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!(stack.getItem() instanceof LunchboxItem)) return InteractionResultHolder.pass(stack);
		if (!level.isClientSide && player.isCrouching() && !(player.containerMenu instanceof LunchboxSelectorMenu)) {
			int playerSelSlot = player.getInventory().selected;
			int openInvRows = getInventoryRows(stack);
			NetworkHooks.openGui((ServerPlayer) player, new SimpleMenuProvider((windowId, playerInv, playerEntity) -> new LunchboxSelectorMenu(windowId, playerInv, playerSelSlot, openInvRows, hand, this.color), stack.getHoverName()), (buffer -> buffer.writeInt(playerSelSlot).writeInt(openInvRows).writeInt(hand.ordinal()).writeInt(color == null ? -1 : color.ordinal())));
		} else if (!player.isCrouching()) {
			ItemStack targetFood = getTargetFood(stack);
			FoodProperties targetFoodProperties = targetFood.getFoodProperties(player);
			if (!targetFood.isEmpty() && checkItemValid(targetFood) && !player.getCooldowns().isOnCooldown(targetFood.getItem()) && player.canEat((targetFood.isEdible() && targetFoodProperties != null && targetFoodProperties.canAlwaysEat()) || targetFood.is(ModItemTags.FORCE_ALWAYS_EAT))) {
				player.startUsingItem(hand);
				return InteractionResultHolder.consume(stack);
			} else {
				return InteractionResultHolder.fail(stack);
			}
		}
		return InteractionResultHolder.pass(stack);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		InteractionResult placeResult = this.place(new BlockPlaceContext(context));
		if (placeResult.consumesAction()) {
			return placeResult;
		} else {
			InteractionResult useResult = this.use(context.getLevel(), context.getPlayer(), context.getHand()).getResult();
			return useResult == InteractionResult.CONSUME ? InteractionResult.CONSUME_PARTIAL : useResult;
		}
	}
	
	@Override
	public InteractionResult place(BlockPlaceContext context) {
		return context.getPlayer().isCrouching() ? super.place(context) : InteractionResult.FAIL;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity living) {
		if (!(stack.getItem() instanceof LunchboxItem)) return stack;
		if (!(living instanceof Player player)) return stack;
		ItemStackHandler handler = getItemHandler(stack);
		int targetFoodSlot = getTargetFoodSlot(stack);
		ItemStack targetFood = getTargetFood(stack);
		Item targetFoodItem = targetFood.getItem();
		
		if (!stack.isEmpty()) {
			ItemStack result = ForgeEventFactory.onItemUseFinish(living, targetFood.copy(), living.getUseItemRemainingTicks(), targetFood.finishUsingItem(level, living));
			if (result != stack) handler.setStackInSlot(targetFoodSlot, result);
			boolean resultValid = checkItemValid(result);
			boolean resultEmpty = result.isEmpty();
			if (!resultValid && !result.isEmpty()) {
				handler.setStackInSlot(targetFoodSlot, ItemStack.EMPTY);
				if (!player.getInventory().add(result)) {
					player.drop(stack, false);
				}
			}
			if (!resultValid || resultEmpty) {
				//probably doesnt need to be this complicated
				boolean matchingFoodSlotFound = false;
				int matchingFoodSlot = 0;
				boolean nonEmptySlotFound = false;
				int firstNonEmptySlot = 0;
				for (int i = targetFoodSlot; i < handler.getSlots(); i++) {
					ItemStack checkItem = handler.getStackInSlot(i);
					if (checkItem.getItem() == targetFoodItem) {
						matchingFoodSlot = i;
						matchingFoodSlotFound = true;
						break;
					} else if (!nonEmptySlotFound && !checkItem.isEmpty()) {
						firstNonEmptySlot = i;
						nonEmptySlotFound = true;
					}
				}
				if (!matchingFoodSlotFound) {
					for (int i = 0; i < targetFoodSlot; i++) {
						ItemStack checkItem = handler.getStackInSlot(i);
						if (checkItem.getItem() == targetFoodItem) {
							matchingFoodSlot = i;
							matchingFoodSlotFound = true;
							break;
						} else if (!nonEmptySlotFound && !checkItem.isEmpty()) {
							firstNonEmptySlot = i;
							nonEmptySlotFound = true;
						}
					}
				}
					
				if (matchingFoodSlotFound) setTargetFoodSlot(stack, matchingFoodSlot);
				else if (nonEmptySlotFound) setTargetFoodSlot(stack, firstNonEmptySlot);
			}
		}
		
		living.gameEvent(GameEvent.EAT, living.eyeBlockPosition());
		return stack;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if (this.color != null && Config.CLIENT.enableLunchboxTooltips.get()) tooltip.add(new TranslatableComponent("tooltip.lunchbox." + this.color.toString()).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
		super.appendHoverText(stack, level, tooltip, flag);
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public static int getInventoryRows(ItemStack stack) {
		return Reference.lunchboxRows;
	}
	
	@Nullable
	public static ItemStackHandler getItemHandler(ItemStack stack) {
		LazyOptional<IItemHandler> handlerOptional = stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
		return handlerOptional.isPresent() && handlerOptional.resolve().get() instanceof ItemStackHandler handler ? handler : null;
	}
	
	public static ItemStack getTargetFood(ItemStack stack) {
		ItemStackHandler handler = getItemHandler(stack);
		return handler.getStackInSlot(getTargetFoodSlot(stack));
	}
	
	public static int getTargetFoodSlot(ItemStack stack) {
		CompoundTag tag = stack.getOrCreateTag();
		if (tag.contains(NBT_TARGETSLOT, CompoundTag.TAG_INT)) {
			return tag.getInt(NBT_TARGETSLOT);
		} else {
			tag.putInt(NBT_TARGETSLOT, 0);
			stack.setTag(tag);
			return 0;
		}
	}
	
	public static ItemStack setTargetFoodSlot(ItemStack stack, int slotNum) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putInt(NBT_TARGETSLOT, slotNum);
		stack.setTag(tag);
		
		return stack;
	}
	
	public static boolean checkItemValid(ItemStack stack) {
		return (stack.isEdible() || stack.is(ModItemTags.LUNCHBOX_WHITELIST)) && !stack.is(ModItemTags.LUNCHBOX_BLACKLIST) && !(stack.getItem() instanceof LunchboxItem);
	}
	
	public static Item byColor(DyeColor color) {
		if (color == null) return ModItems.LUNCHBOX.get();
		else switch(color) {
		case WHITE:
			return ModItems.WHITE_LUNCHBOX.get();
		case ORANGE:
			return ModItems.ORANGE_LUNCHBOX.get();
		case MAGENTA:
			return ModItems.MAGENTA_LUNCHBOX.get();
		case LIGHT_BLUE:
			return ModItems.LIGHT_BLUE_LUNCHBOX.get();
		case YELLOW:
			return ModItems.YELLOW_LUNCHBOX.get();
		case LIME:
			return ModItems.LIME_LUNCHBOX.get();
		case PINK:
			return ModItems.PINK_LUNCHBOX.get();
		case GRAY:
			return ModItems.GRAY_LUNCHBOX.get();
		case LIGHT_GRAY:
			return ModItems.LIGHT_GRAY_LUNCHBOX.get();
		case CYAN:
			return ModItems.CYAN_LUNCHBOX.get();
		case PURPLE:
			return ModItems.PURPLE_LUNCHBOX.get();
		case BLUE:
			return ModItems.BLUE_LUNCHBOX.get();
		case BROWN:
			return ModItems.BROWN_LUNCHBOX.get();
		case GREEN:
			return ModItems.GREEN_LUNCHBOX.get();
		case RED:
		default:
			return ModItems.RED_LUNCHBOX.get();
		case BLACK:
			return ModItems.BLACK_LUNCHBOX.get();
		}
	}
	
	public static DyeColor deserialzeDyeColor(int colId) {
		if (colId == -1) return null;
		else return DyeColor.values()[colId];
	}
	
	public static String getDyePrefix(@Nullable DyeColor color) {
		if (color == null) return "";
		else return color.toString() + "_";
	}
	
}