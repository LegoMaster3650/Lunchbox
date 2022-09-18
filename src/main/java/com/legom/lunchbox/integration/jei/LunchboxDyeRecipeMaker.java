package com.legom.lunchbox.integration.jei;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.items.LunchboxItem;
import com.legom.lunchbox.registry.ModItemTags;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class LunchboxDyeRecipeMaker {
	
	private static final String group = "lunchbox.color";
	
	public static List<CraftingRecipe> getRecipes() {
		Ingredient lunchboxBase = Ingredient.of(ModItemTags.LUNCHBOX);
		return Arrays.stream(DyeColor.values())
				.map(color -> createRecipe(color, lunchboxBase))
				.toList();
	}
	
	private static CraftingRecipe createRecipe(DyeColor color, Ingredient baseLunchbox) {
		Ingredient colorIn = Ingredient.fromValues(Stream.of(new Ingredient.ItemValue(new ItemStack(DyeItem.byColor(color))), new Ingredient.TagValue(color.getTag())));
		
		NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, baseLunchbox, colorIn);
		
		ItemStack output = new ItemStack(LunchboxItem.byColor(color));
		ResourceLocation id = new ResourceLocation(Lunchbox.MOD_ID, group + "." + output.getDescriptionId());
		return new ShapelessRecipe(id, group, output, inputs);
	}
	
}