package com.legom.lunchbox.registry;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.items.LunchboxItem;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Lunchbox.MOD_ID);
	
	public static final RegistryObject<Item> LUNCHBOX = ITEMS.register("lunchbox",
			() -> new LunchboxItem(ModBlocks.LUNCHBOX.get(), new Item.Properties().tab(ModCreativeModeTab.LUNCHBOX_TAB).stacksTo(1)));
	
}