package io._3650.lunchbox.menus;

import io._3650.lunchbox.blocks.entity.LunchboxBlockEntity;
import io._3650.lunchbox.registry.ModContainers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.CapabilityItemHandler;

public class LunchboxPlacedMenu extends AbstractContainerMenu {
	
	private final LunchboxBlockEntity blockEntity;
	private final Level level;
	private final int boxRows;
	
	private final DyeColor color;
	
	public LunchboxPlacedMenu(int windowId, Inventory playerInv, FriendlyByteBuf data) {
		this(windowId, playerInv, validateBlockEntity(playerInv.player.level.getBlockEntity(data.readBlockPos())));
	}
	
	public LunchboxPlacedMenu(int windowId, Inventory playerInv, LunchboxBlockEntity blockEntity) {
		super(ModContainers.LUNCHBOX_PLACED.get(), windowId);
		this.blockEntity = blockEntity;
		this.level = playerInv.player.level;
		this.boxRows = blockEntity.getInvRows();
		this.color = blockEntity.getColor();
		
		blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(handler -> {
			for (int i = 0; i < boxRows; i++) {
				for (int j = 0; j < 9; j++) {
					this.addSlot(new LunchboxPlacedSlot(handler, i * 9 + j, 18 * j + 8, 18 * i + 18));
				}
			}
		});
		
		this.addPlayerInventory(playerInv);
	}

	@Override
	public boolean stillValid(Player player) {
		return super.stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), player, blockEntity.getBlockState().getBlock());
	}
	
	@Override
	public void removed(Player player) {
		super.removed(player);
		blockEntity.stopOpen(player);
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		
		if (slot != null && slot.hasItem()) {
			int lunchboxSlots = this.boxRows * 9;
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < lunchboxSlots) {
				if (!this.moveItemStackTo(itemstack1, lunchboxSlots, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemstack1, 0, lunchboxSlots, false)) {
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		return itemstack;
	}
	
	private int addSlotRange(Inventory handler, int index, int x, int y, int slots, int dx) {
		for (int i = 0; i < slots; i++) {
			addSlot(new Slot(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}
	
	private int addSlotBox(Inventory handler, int index, int x, int y, int xSlots, int dx, int ySlots, int dy) {
		for (int j = 0; j < ySlots; j++) {
			index = addSlotRange(handler, index, x, y, xSlots, dx);
			y += dy;
		}
		return index;
	}
	
	private void addPlayerInventory(Inventory playerInv) {
		//Player Inventory
		addSlotBox(playerInv, 9, 8, boxRows * 18 + 31, 9, 18, 3, 18);
		
		//Hotbar
		addSlotRange(playerInv, 0, 8, boxRows * 18 + 89, 9, 18);
	}
	
	public int getBoxRows() {
		return boxRows;
	}
	
	public LunchboxBlockEntity getBlockEntity() {
		return this.blockEntity;
	}
	
	public DyeColor getColor() {
		return this.color;
	}
	
	private static LunchboxBlockEntity validateBlockEntity(BlockEntity blockEntity) {
		if (blockEntity instanceof LunchboxBlockEntity) {
			return (LunchboxBlockEntity) blockEntity;
		}
		throw new IllegalStateException("Block entity expected to be LunchboxBlockEntity! " + blockEntity);
	}
	
}