package io._3650.lunchbox.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.core.NonNullList;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

@Mixin(SimpleContainer.class)
public interface SimpleContainerAccessor {
	
	@Accessor("items")
	public NonNullList<ItemStack> getItems();
	
}