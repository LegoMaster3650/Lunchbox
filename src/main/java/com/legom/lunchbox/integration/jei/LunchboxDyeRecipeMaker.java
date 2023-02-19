package com.legom.lunchbox.integration.jei;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.items.LunchboxItem;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class LunchboxDyeRecipeMaker {
	
	private static final String group = "lunchbox.color";
	
	public static List<CraftingRecipe> getRecipes() {
		return Arrays.stream(DyeColor.values())
				.map(color -> createRecipe(color))
				.toList();
	}
	
	private static CraftingRecipe createRecipe(DyeColor color) {
		Ingredient lunchboxes = Ingredient.of(Stream.concat(Stream.of(LunchboxItem.byColor(null)), Arrays.stream(DyeColor.values()).filter(col -> col != color).map(col -> LunchboxItem.byColor(col))).map(ItemStack::new));
//		Ingredient colorIn = Ingredient.fromValues(Stream.of(new Ingredient.ItemValue(new ItemStack(DyeItem.byColor(color))), new Ingredient.TagValue(color.getTag())));
		Ingredient colorIn = Ingredient.of(color.getTag());
		
		NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, lunchboxes, colorIn);
		
		ItemStack output = new ItemStack(LunchboxItem.byColor(color));
		ResourceLocation id = new ResourceLocation(Lunchbox.MOD_ID, group + "." + output.getDescriptionId());
		return new ShapelessRecipe(id, group, output, inputs);
	}
	
}