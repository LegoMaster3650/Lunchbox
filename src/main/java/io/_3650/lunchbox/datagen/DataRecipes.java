package io._3650.lunchbox.datagen;

import java.util.function.Consumer;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.registry.ModItems;
import io._3650.lunchbox.registry.ModRecipes;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class DataRecipes extends RecipeProvider {

	public DataRecipes(PackOutput output) {
		super(output);
	}
	
	@Override
	protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
		ShapedRecipeBuilder.shaped(RecipeCategory.FOOD, ModItems.LUNCHBOX.get())
			.pattern(" # ")
			.pattern("#-#")
			.pattern("###")
			.define('#', Tags.Items.INGOTS_IRON)
			.define('-', Tags.Items.NUGGETS_GOLD)
			.unlockedBy("iron", InventoryChangeTrigger.TriggerInstance.hasItems(Items.IRON_INGOT))
			.unlockedBy("gold", InventoryChangeTrigger.TriggerInstance.hasItems(Items.GOLD_NUGGET))
			.save(consumer);
		
		SpecialRecipeBuilder.special(ModRecipes.LUNCHBOX_DYE.get()).save(consumer, Lunchbox.MOD_ID + ":lunchbox_dye");
	}
	
}