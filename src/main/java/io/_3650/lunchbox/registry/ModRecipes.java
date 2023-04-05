package io._3650.lunchbox.registry;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.recipes.LunchboxDyeRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
	
	public static final DeferredRegister<RecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Lunchbox.MOD_ID);
	
	public static final RegistryObject<SimpleCraftingRecipeSerializer<LunchboxDyeRecipe>> LUNCHBOX_DYE = RECIPES.register("lunchbox_dye", () -> new SimpleCraftingRecipeSerializer<>(LunchboxDyeRecipe::new));
	
}