package io._3650.lunchbox.menus;

import javax.annotation.Nonnull;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LunchboxSelectorSlot extends Slot {

	public LunchboxSelectorSlot(Container container, int index, int xPosition, int yPosition) {
		super(container, index, xPosition, yPosition);
	}
	
	@Override
    public boolean mayPlace(@Nonnull ItemStack stack) {
		return false;
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