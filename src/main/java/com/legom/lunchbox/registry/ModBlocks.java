package com.legom.lunchbox.registry;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.blocks.LunchboxBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	
	public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lunchbox.MOD_ID);
	
	public static final RegistryObject<Block> LUNCHBOX = BLOCKS.register("lunchbox",
			() -> new LunchboxBlock(Properties.of(Material.METAL)
					.sound(SoundType.METAL)
					.instabreak()
					.noOcclusion()));
	
}