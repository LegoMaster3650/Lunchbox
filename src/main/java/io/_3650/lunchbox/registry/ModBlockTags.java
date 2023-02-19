package io._3650.lunchbox.registry;

import io._3650.lunchbox.Lunchbox;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModBlockTags {
	
	public static final TagKey<Block> LUNCHBOX = BlockTags.create(new ResourceLocation(Lunchbox.MOD_ID, "placed_lunchboxes"));
	
}