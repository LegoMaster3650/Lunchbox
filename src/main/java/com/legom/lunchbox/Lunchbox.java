package com.legom.lunchbox;

import com.legom.lunchbox.client.LunchboxClient;
import com.legom.lunchbox.registry.ModBlockEntities;
import com.legom.lunchbox.registry.ModBlocks;
import com.legom.lunchbox.registry.ModContainers;
import com.legom.lunchbox.registry.ModItems;
import com.legom.lunchbox.registry.ModRecipes;
import com.legom.lunchbox.registry.config.Config;
import com.mojang.logging.LogUtils;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
		ModContainers.CONTAINERS.register(bus);
		ModRecipes.SERIALIZERS.register(bus);
		
		bus.addListener(this::setup);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> LunchboxClient::new);
		
		ModLoadingContext.get().registerConfig(Type.CLIENT, Config.CLIENT_SPEC, "lunchbox-client.toml");
		ModLoadingContext.get().registerConfig(Type.COMMON, Config.COMMON_SPEC, "lunchbox-common.toml");
		ModLoadingContext.get().registerConfig(Type.SERVER, Config.SERVER_SPEC, "lunchbox-server.toml");
		
		MinecraftForge.EVENT_BUS.register(this);
	}

	private void setup(final FMLCommonSetupEvent event) {
		LOGGER.info("Preparing lunchboxes...");
	}
	
	public static void playClientSideSound(Level level, SoundEvent sound) {
		if (level.isClientSide) DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> LunchboxClient.playClientSideSound(sound));
	}
	
}
