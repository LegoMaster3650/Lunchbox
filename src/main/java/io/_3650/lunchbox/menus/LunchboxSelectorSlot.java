package io._3650.lunchbox.menus;

import javax.annotation.Nonnull;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LunchboxSelectorSlot extends SlotItemHandler {

	public LunchboxSelectorSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
		return false;
	}
	
	@Override
	@Nonnull
	public ItemStack getItem() {
		return super.getItem().copy();
	}
	
	@Override
    public void set(@Nonnull ItemStack stack) {
		//do nothing
	}
	
	@Override
    public boolean mayPickup(Player playerIn) {
		return false;
	}
	
	@Override
    @Nonnull
    public ItemStack remove(int amount) {
		return getItem();
	}
	
}