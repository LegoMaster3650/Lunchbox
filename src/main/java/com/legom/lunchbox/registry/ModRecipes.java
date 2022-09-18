package com.legom.lunchbox.registry;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.recipes.LunchboxDyeRecipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
	
	public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Lunchbox.MOD_ID);
	
	public static final RegistryObject<SimpleRecipeSerializer<?>> LUNCHBOX_DYE = SERIALIZERS.register("lunchbox_dye", () -> new SimpleRecipeSerializer<>(LunchboxDyeRecipe::new));
	
}