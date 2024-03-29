package io._3650.lunchbox.registry;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.blocks.LunchboxBlock;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Lunchbox.MOD_ID);
	
	//Lunchboxes
	public static final RegistryObject<Block> LUNCHBOX = BLOCKS.register("lunchbox", () -> new LunchboxBlock(null));
	public static final RegistryObject<Block> WHITE_LUNCHBOX = BLOCKS.register("white_lunchbox", () -> new LunchboxBlock(DyeColor.WHITE));
	public static final RegistryObject<Block> LIGHT_GRAY_LUNCHBOX = BLOCKS.register("light_gray_lunchbox", () -> new LunchboxBlock(DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Block> GRAY_LUNCHBOX = BLOCKS.register("gray_lunchbox", () -> new LunchboxBlock(DyeColor.GRAY));
	public static final RegistryObject<Block> BLACK_LUNCHBOX = BLOCKS.register("black_lunchbox", () -> new LunchboxBlock(DyeColor.BLACK));
	public static final RegistryObject<Block> BROWN_LUNCHBOX = BLOCKS.register("brown_lunchbox", () -> new LunchboxBlock(DyeColor.BROWN));
	public static final RegistryObject<Block> RED_LUNCHBOX = BLOCKS.register("red_lunchbox", () -> new LunchboxBlock(DyeColor.RED));
	public static final RegistryObject<Block> ORANGE_LUNCHBOX = BLOCKS.register("orange_lunchbox", () -> new LunchboxBlock(DyeColor.ORANGE));
	public static final RegistryObject<Block> YELLOW_LUNCHBOX = BLOCKS.register("yellow_lunchbox", () -> new LunchboxBlock(DyeColor.YELLOW));
	public static final RegistryObject<Block> LIME_LUNCHBOX = BLOCKS.register("lime_lunchbox", () -> new LunchboxBlock(DyeColor.LIME));
	public static final RegistryObject<Block> GREEN_LUNCHBOX = BLOCKS.register("green_lunchbox", () -> new LunchboxBlock(DyeColor.GREEN));
	public static final RegistryObject<Block> CYAN_LUNCHBOX = BLOCKS.register("cyan_lunchbox", () -> new LunchboxBlock(DyeColor.CYAN));
	public static final RegistryObject<Block> LIGHT_BLUE_LUNCHBOX = BLOCKS.register("light_blue_lunchbox", () -> new LunchboxBlock(DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Block> BLUE_LUNCHBOX = BLOCKS.register("blue_lunchbox", () -> new LunchboxBlock(DyeColor.BLUE));
	public static final RegistryObject<Block> PURPLE_LUNCHBOX = BLOCKS.register("purple_lunchbox", () -> new LunchboxBlock(DyeColor.PURPLE));
	public static final RegistryObject<Block> MAGENTA_LUNCHBOX = BLOCKS.register("magenta_lunchbox", () -> new LunchboxBlock(DyeColor.MAGENTA));
	public static final RegistryObject<Block> PINK_LUNCHBOX = BLOCKS.register("pink_lunchbox", () -> new LunchboxBlock(DyeColor.PINK));
	
}