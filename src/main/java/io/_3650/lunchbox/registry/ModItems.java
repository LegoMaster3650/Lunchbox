package io._3650.lunchbox.registry;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.items.LunchboxItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lunchbox.MOD_ID);
	
	//Lunchboxes                           //Also the fact that these lines lined up perfectly made this so much easier
	public static final RegistryObject<Item> LUNCHBOX = ITEMS.register("lunchbox",
			() -> new LunchboxItem(ModBlocks.LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), null));
	public static final RegistryObject<Item> WHITE_LUNCHBOX = ITEMS.register("white_lunchbox",
			() -> new LunchboxItem(ModBlocks.WHITE_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.WHITE));
	public static final RegistryObject<Item> ORANGE_LUNCHBOX = ITEMS.register("orange_lunchbox",
			() -> new LunchboxItem(ModBlocks.ORANGE_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.ORANGE));
	public static final RegistryObject<Item> MAGENTA_LUNCHBOX = ITEMS.register("magenta_lunchbox",
			() -> new LunchboxItem(ModBlocks.MAGENTA_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.MAGENTA));
	public static final RegistryObject<Item> LIGHT_BLUE_LUNCHBOX = ITEMS.register("light_blue_lunchbox",
			() -> new LunchboxItem(ModBlocks.LIGHT_BLUE_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.LIGHT_BLUE));
	public static final RegistryObject<Item> YELLOW_LUNCHBOX = ITEMS.register("yellow_lunchbox",
			() -> new LunchboxItem(ModBlocks.YELLOW_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.YELLOW));
	public static final RegistryObject<Item> LIME_LUNCHBOX = ITEMS.register("lime_lunchbox",
			() -> new LunchboxItem(ModBlocks.LIME_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.LIME));
	public static final RegistryObject<Item> PINK_LUNCHBOX = ITEMS.register("pink_lunchbox",
			() -> new LunchboxItem(ModBlocks.PINK_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.PINK));
	public static final RegistryObject<Item> GRAY_LUNCHBOX = ITEMS.register("gray_lunchbox",
			() -> new LunchboxItem(ModBlocks.GRAY_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.GRAY));
	public static final RegistryObject<Item> LIGHT_GRAY_LUNCHBOX = ITEMS.register("light_gray_lunchbox",
			() -> new LunchboxItem(ModBlocks.LIGHT_GRAY_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.LIGHT_GRAY));
	public static final RegistryObject<Item> CYAN_LUNCHBOX = ITEMS.register("cyan_lunchbox",
			() -> new LunchboxItem(ModBlocks.CYAN_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.CYAN));
	public static final RegistryObject<Item> PURPLE_LUNCHBOX = ITEMS.register("purple_lunchbox",
			() -> new LunchboxItem(ModBlocks.PURPLE_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.PURPLE));
	public static final RegistryObject<Item> BLUE_LUNCHBOX = ITEMS.register("blue_lunchbox",
			() -> new LunchboxItem(ModBlocks.BLUE_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.BLUE));
	public static final RegistryObject<Item> BROWN_LUNCHBOX = ITEMS.register("brown_lunchbox",
			() -> new LunchboxItem(ModBlocks.BROWN_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.BROWN));
	public static final RegistryObject<Item> GREEN_LUNCHBOX = ITEMS.register("green_lunchbox",
			() -> new LunchboxItem(ModBlocks.GREEN_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.GREEN));
	public static final RegistryObject<Item> RED_LUNCHBOX = ITEMS.register("red_lunchbox",
			() -> new LunchboxItem(ModBlocks.RED_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.RED));
	public static final RegistryObject<Item> BLACK_LUNCHBOX = ITEMS.register("black_lunchbox",
			() -> new LunchboxItem(ModBlocks.BLACK_LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1), DyeColor.BLACK));
	
}