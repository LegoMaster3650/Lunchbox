package com.legom.lunchbox.items;

import javax.annotation.Nullable;

import com.legom.lunchbox.menus.LunchboxSelectorMenu;
import com.legom.lunchbox.registry.ModItemTags;
import com.legom.lunchbox.registry.Reference;

import net.minecraft.nbt.CompoundTag;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

public class LunchboxItem extends BlockItem {
	
	private static String NBT_TARGETSLOT = "TargetSlot";
	
	public LunchboxItem(Block block, Properties properties) {
		super(block, properties);
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
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!(stack.getItem() instanceof LunchboxItem)) return InteractionResultHolder.pass(stack);
		if (!level.isClientSide && player.isCrouching() && !(player.containerMenu instanceof LunchboxSelectorMenu)) {
			int playerSelSlot = player.getInventory().selected;
			int openInvRows = getInventoryRows(stack);
			NetworkHooks.openGui((ServerPlayer) player, new SimpleMenuProvider((windowId, playerInv, playerEntity) -> new LunchboxSelectorMenu(windowId, playerInv, playerSelSlot, openInvRows, hand), stack.getHoverName()), (buffer -> buffer.writeInt(playerSelSlot).writeInt(openInvRows).writeInt(hand.ordinal())));
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
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		if (!(stack.getItem() instanceof LunchboxItem)) return stack;
		if (!(entity instanceof Player)) return stack;
		Player player = (Player) entity;
		ItemStackHandler handler = getItemHandler(stack);
		int targetFoodSlot = getTargetFoodSlot(stack);
		ItemStack targetFood = getTargetFood(stack);
		Item targetFoodItem = targetFood.getItem();
		
		if (!stack.isEmpty()) {
			ItemStack result = targetFood.finishUsingItem(level, entity);
			boolean resultValid = checkItemValid(result);
			boolean resultEmpty = result.isEmpty();
			if (!resultValid && !result.isEmpty()) {
				handler.setStackInSlot(targetFoodSlot, ItemStack.EMPTY);
				if (!player.getInventory().add(result)) {
					player.drop(stack, false);
				}
			}
			if (!resultValid || resultEmpty) {
				//why is this literally the most complex code block in the entire mod
				//it just makes it auto-select a new slot for you when you fully deplete one
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
		
		entity.gameEvent(GameEvent.EAT, entity.eyeBlockPosition());
		return stack;
	}
	
	public static int getInventoryRows(ItemStack stack) {
		return Reference.lunchboxRows;
	}
	
	@Nullable
	public static ItemStackHandler getItemHandler(ItemStack stack) {
		if (stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).isPresent()) {
			return (ItemStackHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve().get();
		}
		return null;
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
	
}