package io._3650.lunchbox.datagen;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.registry.ModBlockTags;
import io._3650.lunchbox.registry.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DataBlockTags extends BlockTagsProvider {
	
	public DataBlockTags(DataGenerator generator, ExistingFileHelper helper) {
		super(generator, Lunchbox.MOD_ID, helper);
	}
	
	@Override
	protected void addTags() {
		tag(ModBlockTags.LUNCHBOX)
			.add(ModBlocks.LUNCHBOX.get())
			.add(ModBlocks.WHITE_LUNCHBOX.get())
			.add(ModBlocks.ORANGE_LUNCHBOX.get())
			.add(ModBlocks.MAGENTA_LUNCHBOX.get())
			.add(ModBlocks.LIGHT_BLUE_LUNCHBOX.get())
			.add(ModBlocks.YELLOW_LUNCHBOX.get())
			.add(ModBlocks.LIME_LUNCHBOX.get())
			.add(ModBlocks.PINK_LUNCHBOX.get())
			.add(ModBlocks.GRAY_LUNCHBOX.get())
			.add(ModBlocks.LIGHT_GRAY_LUNCHBOX.get())
			.add(ModBlocks.CYAN_LUNCHBOX.get())
			.add(ModBlocks.PURPLE_LUNCHBOX.get())
			.add(ModBlocks.BLUE_LUNCHBOX.get())
			.add(ModBlocks.BROWN_LUNCHBOX.get())
			.add(ModBlocks.GREEN_LUNCHBOX.get())
			.add(ModBlocks.RED_LUNCHBOX.get())
			.add(ModBlocks.BLACK_LUNCHBOX.get());
	}
	
	@Override
	public String getName() {
		return "Lunchbox Tags";
	}
	
}