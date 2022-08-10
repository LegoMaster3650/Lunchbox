package com.legom.lunchbox.event;

import org.lwjgl.opengl.GL11;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.items.LunchboxItem;
import com.legom.lunchbox.registry.config.Config;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Lunchbox.MOD_ID, value = Dist.CLIENT)
public class ClientEventSubscriber {
	
	private static ResourceLocation WIDGETS = new ResourceLocation(Lunchbox.MOD_ID, "textures/gui/widgets.png");
	
	@SubscribeEvent
	public static void hudPost(final RenderGameOverlayEvent.Post event) {
		if (!Config.CLIENT.lunchboxItemPreview.get()) return;
		
		if (event.getType() == ElementType.ALL) {
			Minecraft mc = Minecraft.getInstance();
			Player player = mc.player;
			PoseStack matrixStack = event.getMatrixStack();
			
			ItemRenderer itemRenderer = mc.getItemRenderer();
			Window window = event.getWindow();
			
			ItemStack mainhand = player.getMainHandItem();
			ItemStack offhand = player.getOffhandItem();
			
			boolean renderMainHand = mainhand.getItem() instanceof LunchboxItem && Config.CLIENT.itemPreviewMainhand.get();
			boolean renderOffhand = offhand.getItem() instanceof LunchboxItem && Config.CLIENT.itemPreviewOffhand.get();
			
			ItemStack mainhandTarget = ItemStack.EMPTY;
			ItemStack offhandTarget = ItemStack.EMPTY;
			
			if (renderMainHand) {
				mainhandTarget = LunchboxItem.getTargetFood(mainhand);
				renderMainHand = !mainhandTarget.isEmpty();
			}
			if (renderOffhand) {
				offhandTarget = LunchboxItem.getTargetFood(offhand);
				renderOffhand = !offhandTarget.isEmpty();
			}
			
			int width = window.getGuiScaledWidth() / 2;
			
			RenderSystem.enableBlend();
			RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			
			RenderSystem.setShaderTexture(0, WIDGETS);
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.5F);
			
			int y = window.getGuiScaledHeight() - 42 - Config.CLIENT.itemPreviewYOffset.get();
			
			int xMH = 0;
			int xOH = 0;
			int yMH = 0;
			
			//Render widgets
			if (renderMainHand) {
				int mhDisplayMode = Config.CLIENT.mainhandPreviewMode.get();
				xMH = mhDisplayMode < 1 ? width - 8 : mhDisplayMode < 2 ? getOffhandPos(width, player.getMainArm()) : width - 88 + (player.getInventory().selected * 20);
				yMH = mhDisplayMode < 1 ? y - Config.CLIENT.centerPreviewYOffset.get() : mhDisplayMode < 2 ? y : mhDisplayMode < 3 ? y - Config.CLIENT.followPreviewYOffset.get() : y + 23;
				if (mhDisplayMode < 3) mc.gui.blit(matrixStack, xMH - 3, yMH - 3, 0, 0, 22, 22);
			}
			
			if (renderOffhand) {
				xOH = getOffhandPos(width, player.getMainArm().getOpposite());
				mc.gui.blit(matrixStack, xOH - 3, y - 3, 0, 0, 22, 22);
			}
			
			//Render items
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			if (renderMainHand) {
				itemRenderer.renderAndDecorateItem(mainhandTarget, xMH, yMH);
				itemRenderer.renderGuiItemDecorations(mc.font, mainhandTarget, xMH, yMH);
			}
			
			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			if (renderOffhand) {
				itemRenderer.renderAndDecorateItem(offhandTarget, xOH, y);
				itemRenderer.renderGuiItemDecorations(mc.font, offhandTarget, xOH, y);
			}
		}
	}
	
	private static int getOffhandPos(int width, HumanoidArm arm) {
		int configOffset = Config.CLIENT.offhandPreviewXOffset.get();
		return arm == HumanoidArm.LEFT ? width - 91 - 26 - configOffset : width + 91 + 10 + configOffset;
	}
	
}