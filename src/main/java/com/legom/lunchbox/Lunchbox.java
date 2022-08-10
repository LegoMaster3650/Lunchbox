package com.legom.lunchbox;

import com.legom.lunchbox.items.LunchboxItem;
import com.legom.lunchbox.registry.ModBlockEntities;
import com.legom.lunchbox.registry.ModBlocks;
import com.legom.lunchbox.registry.ModContainers;
import com.legom.lunchbox.registry.ModItems;
import com.legom.lunchbox.registry.config.Config;
import com.legom.lunchbox.screens.LunchboxPlacedScreen;
import com.legom.lunchbox.screens.LunchboxSelectorScreen;
import com.mojang.logging.LogUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Lunchbox.MOD_ID)
public class Lunchbox
{
	public static final String MOD_ID = "lunchbox";
	public static final Logger LOGGER = LogUtils.getLogger();

	public Lunchbox()
	{
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		ModItems.ITEMS.register(bus);
		ModBlocks.BLOCKS.register(bus);
		ModBlockEntities.BLOCK_ENTITIES.register(bus);
		ModContainers.CONTAINERS.register(bus);
		
		bus.addListener(this::setup);
		
		bus.addListener(this::clientSetup);
		
		ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, "lunchbox-client.toml");
		ModLoadingContext.get().registerConfig(Type.SERVER, Config.SERVER_SPEC, "lunchbox-server.toml");
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Preparing lunch...");
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
		LOGGER.info("Lunchbox client setup");
		event.enqueueWork(() ->
		{
			ItemProperties.register(ModItems.LUNCHBOX.get(),
				new ResourceLocation(Lunchbox.MOD_ID, "open"), (stack, level, living, id) -> {
					return living != null && stack.getItem() instanceof LunchboxItem && ((living.isUsingItem() && living.getUseItem() == stack) || (Minecraft.getInstance().screen instanceof LunchboxSelectorScreen && (living.getMainHandItem() == stack || living.getOffhandItem() == stack))) ? 1.0F : 0.0F;
				});
			MenuScreens.register(ModContainers.LUNCHBOX_PLACED.get(), LunchboxPlacedScreen::new);
			MenuScreens.register(ModContainers.LUNCHBOX_SELECTOR.get(), LunchboxSelectorScreen::new);
		});
	}
	
	public static void playClientSideSound(Level level, SoundEvent sound) {
		if (level.isClientSide) Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1));
	}
	
	
}
