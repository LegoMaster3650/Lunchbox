package com.legom.lunchbox.client;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.items.LunchboxItem;
import com.legom.lunchbox.registry.ModContainers;
import com.legom.lunchbox.registry.ModItems;
import com.legom.lunchbox.screens.LunchboxPlacedScreen;
import com.legom.lunchbox.screens.LunchboxSelectorScreen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LunchboxClient {
	
	public LunchboxClient() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::clientSetup);
	}
	
	public void clientSetup(final FMLClientSetupEvent event) {
		Lunchbox.LOGGER.info("Lunchbox client setup");
		event.enqueueWork(() ->
		{
			//Register open property
			ResourceLocation openPropertyLoc = new ResourceLocation(Lunchbox.MOD_ID, "open");
			ItemProperties.register(ModItems.LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.WHITE_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.ORANGE_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.MAGENTA_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.LIGHT_BLUE_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.YELLOW_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.LIME_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.PINK_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.GRAY_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.LIGHT_GRAY_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.CYAN_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.PURPLE_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.BLUE_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.BROWN_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.GREEN_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.RED_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			ItemProperties.register(ModItems.BLACK_LUNCHBOX.get(), openPropertyLoc, LunchboxClient::openFunc);
			//Register screens
			MenuScreens.register(ModContainers.LUNCHBOX_PLACED.get(), LunchboxPlacedScreen::new);
			MenuScreens.register(ModContainers.LUNCHBOX_SELECTOR.get(), LunchboxSelectorScreen::new);
		});
	}
	
	public static float openFunc(ItemStack stack, ClientLevel level, LivingEntity living, int seed) {
		return stack == null || living == null ? 0.0F : openFunc(stack, level, living);
	}
	
	public static float openFunc(ItemStack stack, ClientLevel level, LivingEntity living) {
		return stack.getItem() instanceof LunchboxItem && ((living.isUsingItem() && living.getUseItem() == stack) || (Minecraft.getInstance().screen instanceof LunchboxSelectorScreen && (living.getMainHandItem() == stack || living.getOffhandItem() == stack))) ? 1.0F : 0.0F;
	}
	
	public static void playClientSideSound(SoundEvent sound) {
		Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(sound, 1));
	}
	
}