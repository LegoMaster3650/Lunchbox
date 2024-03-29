package io._3650.lunchbox.integration.jei;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.items.LunchboxItem;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraftforge.registries.ForgeRegistries;

public class LunchboxDyeRecipeMaker {
	
	private static final String group = "lunchbox.color";
	private static final DyeColor[] VANILLA_DYES = {
			DyeColor.WHITE,
			DyeColor.LIGHT_GRAY,
			DyeColor.GRAY,
			DyeColor.BLACK,
			DyeColor.BROWN,
			DyeColor.RED,
			DyeColor.ORANGE,
			DyeColor.YELLOW,
			DyeColor.LIME,
			DyeColor.GREEN,
			DyeColor.CYAN,
			DyeColor.LIGHT_BLUE,
			DyeColor.BLUE,
			DyeColor.PURPLE,
			DyeColor.MAGENTA,
			DyeColor.PINK};
	
	public static List<CraftingRecipe> getRecipes() {
		return Arrays.stream(VANILLA_DYES)
				.map(color -> createRecipe(color))
				.toList();
	}
	
	private static CraftingRecipe createRecipe(DyeColor color) {
		Ingredient lunchboxes = Ingredient.of(Stream.concat(Stream.of(LunchboxItem.byColor(null)), Arrays.stream(DyeColor.values()).filter(col -> col != color).map(col -> LunchboxItem.byColor(col))).map(ItemStack::new));
//		Ingredient colorIn = Ingredient.fromValues(Stream.of(new Ingredient.ItemValue(new ItemStack(DyeItem.byColor(color))), new Ingredient.TagValue(color.getTag())));
		Ingredient colorIn = Ingredient.of(color.getTag());
		
		NonNullList<Ingredient> inputs = NonNullList.of(Ingredient.EMPTY, lunchboxes, colorIn);
		
		Item item = LunchboxItem.byColor(color);
		ItemStack output = new ItemStack(item);
		ResourceLocation id = new ResourceLocation(Lunchbox.MOD_ID, ForgeRegistries.ITEMS.getKey(item).getPath());
		return new ShapelessRecipe(id, group, CraftingBookCategory.EQUIPMENT, output, inputs);
	}
	
}