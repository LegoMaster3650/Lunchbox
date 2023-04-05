package io._3650.lunchbox.blocks.entity;

import javax.annotation.Nullable;

import io._3650.lunchbox.blocks.LunchboxBlock;
import io._3650.lunchbox.items.LunchboxItem;
import io._3650.lunchbox.menus.LunchboxPlacedMenu;
import io._3650.lunchbox.registry.ModBlockEntities;
import io._3650.lunchbox.registry.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class LunchboxBlockEntity extends RandomizableContainerBlockEntity {
	
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
	
	private int invRows = Reference.lunchboxRows;
	private NonNullList<ItemStack> items = NonNullList.withSize(invRows * 9, ItemStack.EMPTY);
	
	private final DyeColor color;
	
	private int targetSlotSave = 0;
	
	public LunchboxBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlockEntities.LUNCHBOX.get(), pos, state);
		this.color = LunchboxBlock.getColorFromBlock(state.getBlock());
	}
	
	public LunchboxBlockEntity(BlockPos pos, BlockState state, @Nullable DyeColor color) {
		super(ModBlockEntities.LUNCHBOX.get(), pos, state);
		this.color = color;
	}
	
	public void loadAllData(ItemStack stack) {
		if (stack.getItem() instanceof LunchboxItem) {
			this.invRows = LunchboxItem.getInventoryRows(stack);
			this.readInventory(stack.getOrCreateTag());
			this.targetSlotSave = LunchboxItem.getTargetFoodSlot(stack);
			if (stack.hasCustomHoverName()) this.setCustomName(stack.getHoverName());
		}
	}
	
	public ItemStack makeItem(Item item) {
		return this.saveAllData(new ItemStack(item, 1));
	}
	
	public ItemStack saveAllData(ItemStack stack) {
		this.writeInventory(stack.getOrCreateTag());
		LunchboxItem.setTargetFoodSlot(stack, this.targetSlotSave);
		if (this.hasCustomName()) stack.setHoverName(this.getCustomName());
		return stack;
	}
	
	public void writeInventory(CompoundTag tag) {
		ContainerHelper.saveAllItems(tag, this.items);
	}
	
	public void readInventory(CompoundTag tag) {
		this.items = NonNullList.withSize(invRows * 9, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(tag, this.items);
	}
	
	@Override
	protected void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		if (!this.trySaveLootTable(tag)) {
			this.writeInventory(tag);
		}
		tag.putInt(LunchboxItem.NBT_TARGETSLOT, this.targetSlotSave);
	}
	
	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (!this.tryLoadLootTable(tag)) {
			this.readInventory(tag);
		}
		this.targetSlotSave = tag.getInt(LunchboxItem.NBT_TARGETSLOT);
	}
	
	@Override
	public AbstractContainerMenu createMenu(int windowId, Inventory playerInv) {
		return new LunchboxPlacedMenu(windowId, playerInv, this);
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
	
	public void recheckOpen() {
		if (!this.remove) {
			this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
		}
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	public int getInvRows() {
		return this.invRows;
	}

	@Override
	public int getContainerSize() {
		return this.invRows * 9;
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.lunchbox." + LunchboxItem.getDyePrefix(this.color) + "lunchbox");
	}
	
}