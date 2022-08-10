package com.legom.lunchbox.registry;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.menus.LunchboxPlacedMenu;
import com.legom.lunchbox.menus.LunchboxSelectorMenu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
	
	public static DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Lunchbox.MOD_ID);
	
	public static RegistryObject<MenuType<LunchboxPlacedMenu>> LUNCHBOX_PLACED = CONTAINERS.register("lunchbox_placed",
			() -> IForgeMenuType.create(LunchboxPlacedMenu::new));
	
	public static RegistryObject<MenuType<LunchboxSelectorMenu>> LUNCHBOX_SELECTOR = CONTAINERS.register("lunchbox_selector",
			() -> IForgeMenuType.create(LunchboxSelectorMenu::new));
	
}