package io._3650.lunchbox.datagen;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class DataItemModels extends ItemModelProvider {

	public DataItemModels(PackOutput output, ExistingFileHelper existingFileHelper) {
		super(output, Lunchbox.MOD_ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {
		registerLunchbox(ModItems.LUNCHBOX, "item/lunchbox");
		registerLunchbox(ModItems.WHITE_LUNCHBOX, "item/white_lunchbox");
		registerLunchbox(ModItems.LIGHT_GRAY_LUNCHBOX, "item/light_gray_lunchbox");
		registerLunchbox(ModItems.GRAY_LUNCHBOX, "item/gray_lunchbox");
		registerLunchbox(ModItems.BLACK_LUNCHBOX, "item/black_lunchbox");
		registerLunchbox(ModItems.BROWN_LUNCHBOX, "item/brown_lunchbox");
		registerLunchbox(ModItems.RED_LUNCHBOX, "item/red_lunchbox");
		registerLunchbox(ModItems.ORANGE_LUNCHBOX, "item/orange_lunchbox");
		registerLunchbox(ModItems.YELLOW_LUNCHBOX, "item/yellow_lunchbox");
		registerLunchbox(ModItems.LIME_LUNCHBOX, "item/lime_lunchbox");
		registerLunchbox(ModItems.GREEN_LUNCHBOX, "item/green_lunchbox");
		registerLunchbox(ModItems.CYAN_LUNCHBOX, "item/cyan_lunchbox");
		registerLunchbox(ModItems.BLUE_LUNCHBOX, "item/blue_lunchbox");
		registerLunchbox(ModItems.LIGHT_BLUE_LUNCHBOX, "item/light_blue_lunchbox");
		registerLunchbox(ModItems.PURPLE_LUNCHBOX, "item/purple_lunchbox");
		registerLunchbox(ModItems.MAGENTA_LUNCHBOX, "item/magenta_lunchbox");
		registerLunchbox(ModItems.PINK_LUNCHBOX, "item/pink_lunchbox");
	}
	
	@SuppressWarnings("unused") //just in case there's future use
	private void registerLunchbox(Item item, String path) {
		registerLunchbox(ForgeRegistries.ITEMS.getKey(item), path);
	}
	
	private void registerLunchbox(RegistryObject<Item> itemReg, String path) {
		registerLunchbox(itemReg.getId(), path);
	}
	
	private void registerLunchbox(ResourceLocation id, String path) {
		ModelFile openFile = singleTexture(id.getPath()+ "_open", mcLoc("item/generated"), "layer0", modLoc(path + "_open"));
		withExistingParent(id.getPath(), mcLoc("item/generated")).texture("layer0", modLoc(path)).override().predicate(modLoc("open"), 1.0f).model(openFile);
	}
	
}