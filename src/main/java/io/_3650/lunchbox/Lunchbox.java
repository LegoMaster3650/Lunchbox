package io._3650.lunchbox;

import com.mojang.logging.LogUtils;

import io._3650.lunchbox.client.LunchboxClient;
import io._3650.lunchbox.registry.ModBlockEntities;
import io._3650.lunchbox.registry.ModBlocks;
import io._3650.lunchbox.registry.ModMenus;
import io._3650.lunchbox.registry.ModItems;
import io._3650.lunchbox.registry.ModRecipes;
import io._3650.lunchbox.registry.config.Config;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Lunchbox.MOD_ID)
public class Lunchbox
{
	public static final String MOD_ID = "lunchbox";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Lunchbox() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ModItems.ITEMS.register(bus);
		ModBlocks.BLOCKS.register(bus);
		ModBlockEntities.BLOCK_ENTITIES.register(bus);
		ModMenus.MENUS.register(bus);
		ModRecipes.RECIPES.register(bus);
		
		bus.addListener(this::registerCreativeTabs);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> LunchboxClient::new);
		
		ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, "lunchbox-client.toml");
		ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_SPEC, "lunchbox-common.toml");
		ModLoadingContext.get().registerConfig(Type.SERVER, Config.SERVER_SPEC, "lunchbox-server.toml");
		
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void registerCreativeTabs(CreativeModeTabEvent.BuildContents event) {
		if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(ModItems.LUNCHBOX.get());
			event.accept(ModItems.WHITE_LUNCHBOX.get());
			event.accept(ModItems.LIGHT_GRAY_LUNCHBOX.get());
			event.accept(ModItems.GRAY_LUNCHBOX.get());
			event.accept(ModItems.BLACK_LUNCHBOX.get());
			event.accept(ModItems.BROWN_LUNCHBOX.get());
			event.accept(ModItems.RED_LUNCHBOX.get());
			event.accept(ModItems.ORANGE_LUNCHBOX.get());
			event.accept(ModItems.YELLOW_LUNCHBOX.get());
			event.accept(ModItems.LIME_LUNCHBOX.get());
			event.accept(ModItems.GREEN_LUNCHBOX.get());
			event.accept(ModItems.CYAN_LUNCHBOX.get());
			event.accept(ModItems.LIGHT_BLUE_LUNCHBOX.get());
			event.accept(ModItems.BLUE_LUNCHBOX.get());
			event.accept(ModItems.PURPLE_LUNCHBOX.get());
			event.accept(ModItems.MAGENTA_LUNCHBOX.get());
			event.accept(ModItems.PINK_LUNCHBOX.get());
		}
	}
	
	public static void playClientSideSound(Level level, SoundEvent sound) {
		if (level.isClientSide) DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> LunchboxClient.playClientSideSound(sound));
	}
	
}
