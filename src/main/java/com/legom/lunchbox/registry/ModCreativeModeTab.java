package com.legom.lunchbox.registry;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
	
	public static final CreativeModeTab LUNCHBOX_TAB = new CreativeModeTab("lunchbox") {
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModItems.LUNCHBOX.get());
		}
	};
	
}