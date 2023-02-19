package io._3650.lunchbox.datagen;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.registry.ModItemTags;
import io._3650.lunchbox.registry.ModItems;
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
			.add(ModItems.LUNCHBOX.get())
			.add(ModItems.WHITE_LUNCHBOX.get())
			.add(ModItems.ORANGE_LUNCHBOX.get())
			.add(ModItems.MAGENTA_LUNCHBOX.get())
			.add(ModItems.LIGHT_BLUE_LUNCHBOX.get())
			.add(ModItems.YELLOW_LUNCHBOX.get())
			.add(ModItems.LIME_LUNCHBOX.get())
			.add(ModItems.PINK_LUNCHBOX.get())
			.add(ModItems.GRAY_LUNCHBOX.get())
			.add(ModItems.LIGHT_GRAY_LUNCHBOX.get())
			.add(ModItems.CYAN_LUNCHBOX.get())
			.add(ModItems.PURPLE_LUNCHBOX.get())
			.add(ModItems.BLUE_LUNCHBOX.get())
			.add(ModItems.BROWN_LUNCHBOX.get())
			.add(ModItems.GREEN_LUNCHBOX.get())
			.add(ModItems.RED_LUNCHBOX.get())
			.add(ModItems.BLACK_LUNCHBOX.get());
		
		
		tag(ModItemTags.LUNCHBOX_BLACKLIST)
			.add(ModItems.LUNCHBOX.get())
			.add(ModItems.WHITE_LUNCHBOX.get())
			.add(ModItems.ORANGE_LUNCHBOX.get())
			.add(ModItems.MAGENTA_LUNCHBOX.get())
			.add(ModItems.LIGHT_BLUE_LUNCHBOX.get())
			.add(ModItems.YELLOW_LUNCHBOX.get())
			.add(ModItems.LIME_LUNCHBOX.get())
			.add(ModItems.PINK_LUNCHBOX.get())
			.add(ModItems.GRAY_LUNCHBOX.get())
			.add(ModItems.LIGHT_GRAY_LUNCHBOX.get())
			.add(ModItems.CYAN_LUNCHBOX.get())
			.add(ModItems.PURPLE_LUNCHBOX.get())
			.add(ModItems.BLUE_LUNCHBOX.get())
			.add(ModItems.BROWN_LUNCHBOX.get())
			.add(ModItems.GREEN_LUNCHBOX.get())
			.add(ModItems.RED_LUNCHBOX.get())
			.add(ModItems.BLACK_LUNCHBOX.get());
		
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