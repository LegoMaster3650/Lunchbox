package io._3650.lunchbox.client;

import io._3650.lunchbox.Lunchbox;
import io._3650.lunchbox.items.LunchboxItem;
import io._3650.lunchbox.registry.ModMenus;
import io._3650.lunchbox.registry.ModItems;
import io._3650.lunchbox.screens.LunchboxPlacedScreen;
import io._3650.lunchbox.screens.LunchboxSelectorScreen;
import io._3650.peeklib.api.client.PeekLibApi;
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
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class LunchboxClient {
	
	public LunchboxClient() {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		
		bus.addListener(this::clientSetup);
		bus.addListener(this::enqueueIMC);
	}
	
	private void clientSetup(final FMLClientSetupEvent event) {
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
			MenuScreens.register(ModMenus.LUNCHBOX_PLACED.get(), LunchboxPlacedScreen::new);
			MenuScreens.register(ModMenus.LUNCHBOX_SELECTOR.get(), LunchboxSelectorScreen::new);
		});
	}
	
	private void enqueueIMC(InterModEnqueueEvent event) {
		PeekLibApi.registerRenderer(ModItems.LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.WHITE_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.ORANGE_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.MAGENTA_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.LIGHT_BLUE_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.YELLOW_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.LIME_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.PINK_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.GRAY_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.LIGHT_GRAY_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.CYAN_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.PURPLE_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.BLUE_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.BROWN_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.GREEN_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.RED_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
		PeekLibApi.registerRenderer(ModItems.BLACK_LUNCHBOX.get(), LunchboxPeekRenderer.INSTANCE);
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