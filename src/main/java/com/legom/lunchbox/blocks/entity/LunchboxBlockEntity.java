package com.legom.lunchbox.blocks.entity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.legom.lunchbox.blocks.LunchboxBlock;
import com.legom.lunchbox.items.LunchboxItem;
import com.legom.lunchbox.menus.LunchboxPlacedMenu;
import com.legom.lunchbox.registry.Reference;
import com.legom.lunchbox.registry.ModBlockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LunchboxBlockEntity extends BlockEntity implements MenuProvider {
	
	private final ItemStackHandler itemHandler = new ItemStackHandler(Reference.lunchboxRows * 9) {
		@Override
		protected void onContentsChanged(int slot) {
			setChanged();
		}
		
		@Override
		public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
			return LunchboxItem.checkItemValid(stack) && super.isItemValid(slot, stack);
		}
	};
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> this.itemHandler);
	
	private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {

		@Override
		protected void onOpen(Level level, BlockPos pos, BlockState state) {
			level.playSound(null, pos, Reference.LUNCHBOX_OPEN, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
			LunchboxBlockEntity.this.level.setBlock(getBlockPos(), state.setValue(LunchboxBlock.OPEN, Boolean.valueOf(true)), level.isClientSide ? Block.UPDATE_ALL_IMMEDIATE : Block.UPDATE_ALL);
		}

		@Override
		protected void onClose(Level level, BlockPos pos, BlockState state) {
			level.playSound(null, pos, Reference.LUNCHBOX_CLOSE, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
			LunchboxBlockEntity.this.level.setBlock(getBlockPos(), state.setValue(LunchboxBlock.OPEN, Boolean.valueOf(false)), level.isClientSide ? Block.UPDATE_ALL_IMMEDIATE : Block.UPDATE_ALL);
		}

		@Override
		protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int p_155466_, int p_155467_) {
		}

		@Override
		protected boolean isOwnContainer(Player playerIn) {
			if (playerIn.containerMenu instanceof LunchboxPlacedMenu) {
				LunchboxBlockEntity testBE = ((LunchboxPlacedMenu)playerIn.containerMenu).getBlockEntity();
				return testBE == LunchboxBlockEntity.this;
			} else {
				return false;
			}
		}
		
	};
	
	private int invRows;
	private Component customName;
	
	private int targetSlotSave = 0;
	
	public LunchboxBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.LUNCHBOX.get(), pos, state);
		this.invRows = Reference.lunchboxRows;
	}
	
	public void loadAllData(ItemStack stack) {
		if (stack.getItem() instanceof LunchboxItem) {
			this.invRows = LunchboxItem.getInventoryRows(stack);
			this.targetSlotSave = LunchboxItem.getTargetFoodSlot(stack);
			stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				itemHandler.deserializeNBT(((ItemStackHandler)h).serializeNBT());
			});
			if (stack.hasCustomHoverName()) {
				setCustomName(stack.getHoverName());
			}
		}
	}
	
	public ItemStack makeItem(Item item) {
		return this.saveAllData(new ItemStack(item, 1));
	}
	
	public ItemStack saveAllData(ItemStack stack) {
		LunchboxItem.getItemHandler(stack).deserializeNBT(itemHandler.serializeNBT());
		LunchboxItem.setTargetFoodSlot(stack, this.targetSlotSave);
		if (this.customName != null) {
			stack.setHoverName(this.customName);
		}
		return stack;
	}
	
	public void setInventory(CompoundTag tag) {
		itemHandler.deserializeNBT(tag);
	}
	
	public void setCustomName(Component name) {
		this.customName = name;
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag) {
		tag.put("Inventory", itemHandler.serializeNBT());
		if (this.customName != null) tag.putString("CustomName", Component.Serializer.toJson(this.customName));
		tag.putInt("TargetSlot", this.targetSlotSave);
		super.saveAdditional(tag);
	}
	
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("CustomName", CompoundTag.TAG_STRING)) setCustomName(Component.Serializer.fromJson(tag.getString("CustomName")));
		if (tag.contains("TargetSlot", CompoundTag.TAG_INT)) this.targetSlotSave = tag.getInt("TargetSlot");
		setInventory(tag.getCompound("Inventory"));
	}
	
	public int getInvRows() {
		return this.invRows;
	}
	
	@Override
	public Component getDisplayName() {
		return this.customName != null ? this.customName : new TranslatableComponent("block.lunchbox.lunchbox");
	}
	
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv, Player pPlayer) {
		return new LunchboxPlacedMenu(windowId, playerInv, this);
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		handler.invalidate();
	}
	
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		return super.getCapability(cap, side);
	}
	
	public void startOpen(Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.openersCounter.incrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
		}
	}
	
	public void stopOpen(Player player) {
		if (!this.remove && !player.isSpectator()) {
			this.openersCounter.decrementOpeners(player, getLevel(), getBlockPos(), getBlockState());
		}
	}
	
}