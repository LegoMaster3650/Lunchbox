package io._3650.lunchbox.registry;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.menus.LunchboxPlacedMenu;
import io._3650.lunchbox.menus.LunchboxSelectorMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenus {
	
	public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Lunchbox.MOD_ID);
	
	public static final RegistryObject<MenuType<LunchboxPlacedMenu>> LUNCHBOX_PLACED = MENUS.register("lunchbox_placed", () -> IForgeMenuType.create(LunchboxPlacedMenu::new));
	
	public static final RegistryObject<MenuType<LunchboxSelectorMenu>> LUNCHBOX_SELECTOR = MENUS.register("lunchbox_selector", () -> IForgeMenuType.create(LunchboxSelectorMenu::new));
	
}