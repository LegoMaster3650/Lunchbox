package com.legom.lunchbox.integration.jei;

import com.legom.lunchbox.Lunchbox;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class LunchboxJEIPlugin implements IModPlugin {

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(Lunchbox.MOD_ID, "lunchbox");
	}
	
	@Override
	public void registerRecipes(IRecipeRegistration registration) {
//		registration.addRecipes(RecipeTypes.CRAFTING, LunchboxDyeRecipeMaker.getRecipes());
	}
	
}
