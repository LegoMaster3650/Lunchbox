package com.legom.lunchbox.datagen;

import java.util.function.Consumer;

import com.legom.lunchbox.registry.ModItems;

import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class DataRecipes extends RecipeProvider {

	public DataRecipes(DataGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(ModItems.LUNCHBOX.get())
			.pattern(" # ")
			.pattern("#-#")
			.pattern("###")
			.define('#', Tags.Items.INGOTS_IRON)
			.define('-', Tags.Items.NUGGETS_GOLD)
			.unlockedBy("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
			.unlockedBy("gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
			.save(consumer);
	}
	
}