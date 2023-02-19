package io._3650.lunchbox.recipes;

import io._3650.lunchbox.items.LunchboxItem;
import io._3650.lunchbox.registry.ModRecipes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemStackHandler;

public class LunchboxDyeRecipe extends CustomRecipe {

	public LunchboxDyeRecipe(ResourceLocation id) {
		super(id);
	}

	@Override
	public boolean matches(CraftingContainer container, Level level) {
		ItemStack boxMatch = ItemStack.EMPTY;
		int dyes = 0;
		
		for(int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack = container.getItem(i);
			if (!itemstack.isEmpty()) {
				if (itemstack.getItem() instanceof LunchboxItem) {
					if (!boxMatch.isEmpty()) return false;
					boxMatch = itemstack;
				} else {
					DyeColor color = DyeColor.getColor(itemstack);
					if (color == null) return false;
					else if (!boxMatch.isEmpty() && ((LunchboxItem)boxMatch.getItem()).getColor() == color) return false;
					else dyes++;
				}
				
			}
			
			if (dyes > 1) return false;
		}
		return !boxMatch.isEmpty() && dyes == 1;
	}

	@Override
	public ItemStack assemble(CraftingContainer container) {
		ItemStack itemstack = ItemStack.EMPTY;
		DyeColor color = DyeColor.WHITE;
		
		for (int i = 0; i < container.getContainerSize(); ++i) {
			ItemStack itemstack1 = container.getItem(i);
			if (!itemstack1.isEmpty()) {
				if (itemstack1.getItem() instanceof LunchboxItem) {
					itemstack = itemstack1;
				} else {
					DyeColor color1 = DyeColor.getColor(itemstack1);
					if (color1 != null) color = color1;
				}
			}
		}
		
		ItemStack result = new ItemStack(LunchboxItem.byColor(color));
		if (itemstack.hasTag()) {
			result.setTag(itemstack.getTag());
		}
		
		ItemStackHandler stackHandler = LunchboxItem.getItemHandler(itemstack);
		ItemStackHandler resultHandler = LunchboxItem.getItemHandler(result);
		if (stackHandler != null && resultHandler != null) {
			resultHandler.deserializeNBT(stackHandler.serializeNBT());
		}
		
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.LUNCHBOX_DYE.get();
	}
	
}