package com.legom.lunchbox.datagen;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.registry.ModBlockTags;
import com.legom.lunchbox.registry.ModBlocks;

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
			.add(ModBlocks.LUNCHBOX.get());
	}
	
	@Override
	public String getName() {
		return "Lunchbox Tags";
	}
	
}