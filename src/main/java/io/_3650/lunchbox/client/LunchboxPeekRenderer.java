package io._3650.lunchbox.client;

import io._3650.lunchbox.items.LunchboxItem;
import io._3650.peeklib.api.client.simple.ItemPeekRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class LunchboxPeekRenderer extends ItemPeekRenderer {
	
	public static final LunchboxPeekRenderer INSTANCE = new LunchboxPeekRenderer();
	
	@Override
	public ItemStack getItem(ForgeGui gui, ItemStack stack, InteractionHand hand) {
		return LunchboxItem.getTargetFood(stack);
	}
	
}