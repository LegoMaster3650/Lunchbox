package com.legom.lunchbox.registry;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.blocks.entity.LunchboxBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Lunchbox.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<LunchboxBlockEntity>> LUNCHBOX = BLOCK_ENTITIES.register("lunchbox", () ->
		BlockEntityType.Builder.of(LunchboxBlockEntity::new,
			ModBlocks.LUNCHBOX.get()
		).build(null));
	
}