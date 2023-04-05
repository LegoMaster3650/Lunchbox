package io._3650.lunchbox.menus;

import io._3650.lunchbox.items.LunchboxItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LunchboxPlacedSlot extends Slot {

	public LunchboxPlacedSlot(Container container, int index, int xPosition, int yPosition) {
		super(container, index, xPosition, yPosition);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return LunchboxItem.checkItemValid(stack) && super.mayPlace(stack);
	}
	
}