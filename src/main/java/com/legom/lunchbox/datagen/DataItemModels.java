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
	}
	
	private void registerLunchbox(Item item, String path) {
		ModelFile openFile = singleTexture(item.getRegistryName().getPath().concat("_open"), mcLoc("item/generated"), "layer0", modLoc(path.concat("_open")));
		withExistingParent(item.getRegistryName().getPath(), mcLoc("item/generated")).texture("layer0", modLoc(path)).override().predicate(modLoc("open"), 1.0f).model(openFile);
	}
	
}