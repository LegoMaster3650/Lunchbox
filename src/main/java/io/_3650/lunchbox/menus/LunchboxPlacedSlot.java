package io._3650.lunchbox.menus;

import io._3650.lunchbox.items.LunchboxItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class LunchboxPlacedSlot extends SlotItemHandler {

	public LunchboxPlacedSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
		super(itemHandler, index, xPosition, yPosition);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return LunchboxItem.checkItemValid(stack) && super.mayPlace(stack);
	}
	
}