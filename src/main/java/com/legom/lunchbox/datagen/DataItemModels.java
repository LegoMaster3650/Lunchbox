package com.legom.lunchbox.datagen;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.registry.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DataItemModels extends ItemModelProvider {

	public DataItemModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, Lunchbox.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		registerLunchbox(ModItems.LUNCHBOX.get(), "item/lunchbox");
		registerLunchbox(ModItems.WHITE_LUNCHBOX.get(), "item/white_lunchbox");
		registerLunchbox(ModItems.ORANGE_LUNCHBOX.get(), "item/orange_lunchbox");
		registerLunchbox(ModItems.MAGENTA_LUNCHBOX.get(), "item/magenta_lunchbox");
		registerLunchbox(ModItems.LIGHT_BLUE_LUNCHBOX.get(), "item/light_blue_lunchbox");
		registerLunchbox(ModItems.YELLOW_LUNCHBOX.get(), "item/yellow_lunchbox");
		registerLunchbox(ModItems.LIME_LUNCHBOX.get(), "item/lime_lunchbox");
		registerLunchbox(ModItems.PINK_LUNCHBOX.get(), "item/pink_lunchbox");
		registerLunchbox(ModItems.GRAY_LUNCHBOX.get(), "item/gray_lunchbox");
		registerLunchbox(ModItems.LIGHT_GRAY_LUNCHBOX.get(), "item/light_gray_lunchbox");
		registerLunchbox(ModItems.CYAN_LUNCHBOX.get(), "item/cyan_lunchbox");
		registerLunchbox(ModItems.PURPLE_LUNCHBOX.get(), "item/purple_lunchbox");
		registerLunchbox(ModItems.BLUE_LUNCHBOX.get(), "item/blue_lunchbox");
		registerLunchbox(ModItems.BROWN_LUNCHBOX.get(), "item/brown_lunchbox");
		registerLunchbox(ModItems.GREEN_LUNCHBOX.get(), "item/green_lunchbox");
		registerLunchbox(ModItems.RED_LUNCHBOX.get(), "item/red_lunchbox");
		registerLunchbox(ModItems.BLACK_LUNCHBOX.get(), "item/black_lunchbox");
	}
	
	private void registerLunchbox(Item item, String path) {
		ModelFile openFile = singleTexture(item.getRegistryName().getPath()+ "_open", mcLoc("item/generated"), "layer0", modLoc(path + "_open"));
		withExistingParent(item.getRegistryName().getPath(), mcLoc("item/generated")).texture("layer0", modLoc(path)).override().predicate(modLoc("open"), 1.0f).model(openFile);
	}
	
}