package com.legom.lunchbox.datagen;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.registry.ModItemTags;
import com.legom.lunchbox.registry.ModItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

public class DataItemTags extends ItemTagsProvider {
	
	public DataItemTags(DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
		super(generator, blockTags, Lunchbox.MOD_ID, helper);
	}
	
	@Override
	protected void addTags() {
		tag(ModItemTags.LUNCHBOX)
			.add(ModItems.LUNCHBOX.get());
		
		tag(ModItemTags.LUNCHBOX_BLACKLIST)
			.add(ModItems.LUNCHBOX.get());
		
		tag(ModItemTags.LUNCHBOX_WHITELIST)
			.add(Items.POTION);
		
		tag(ModItemTags.FORCE_ALWAYS_EAT)
			.add(Items.HONEY_BOTTLE)
			.add(Items.POTION);
	}
	
	@Override
	public String getName() {
		return "Lunchbox Tags";
	}
	
}