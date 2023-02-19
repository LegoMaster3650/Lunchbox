package io._3650.lunchbox.registry;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.blocks.entity.LunchboxBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
	
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Lunchbox.MOD_ID);
	
	public static final RegistryObject<BlockEntityType<LunchboxBlockEntity>> LUNCHBOX = BLOCK_ENTITIES.register("lunchbox", () ->
		BlockEntityType.Builder.of(LunchboxBlockEntity::new,
			ModBlocks.LUNCHBOX.get(),
			ModBlocks.WHITE_LUNCHBOX.get(),
			ModBlocks.ORANGE_LUNCHBOX.get(),
			ModBlocks.MAGENTA_LUNCHBOX.get(),
			ModBlocks.LIGHT_BLUE_LUNCHBOX.get(),
			ModBlocks.YELLOW_LUNCHBOX.get(),
			ModBlocks.LIME_LUNCHBOX.get(),
			ModBlocks.PINK_LUNCHBOX.get(),
			ModBlocks.GRAY_LUNCHBOX.get(),
			ModBlocks.LIGHT_GRAY_LUNCHBOX.get(),
			ModBlocks.CYAN_LUNCHBOX.get(),
			ModBlocks.PURPLE_LUNCHBOX.get(),
			ModBlocks.BLUE_LUNCHBOX.get(),
			ModBlocks.BROWN_LUNCHBOX.get(),
			ModBlocks.GREEN_LUNCHBOX.get(),
			ModBlocks.RED_LUNCHBOX.get(),
			ModBlocks.BLACK_LUNCHBOX.get()
		).build(null));
	
}