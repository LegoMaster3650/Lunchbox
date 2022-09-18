package com.legom.lunchbox.screens;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.items.LunchboxItem;
import com.legom.lunchbox.menus.LunchboxPlacedMenu;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class LunchboxPlacedScreen extends AbstractContainerScreen<LunchboxPlacedMenu> {
	
	private final int boxRows;
	
	private final ResourceLocation BG_TEXTURE;
	
	public LunchboxPlacedScreen(LunchboxPlacedMenu container, Inventory playerInv, Component name) {
		super(container, playerInv, name);
		this.boxRows = container.getBoxRows();
		this.imageHeight = 114 + this.boxRows * 18;
		this.inventoryLabelY = this.imageHeight - 94;
		this.BG_TEXTURE = new ResourceLocation(Lunchbox.MOD_ID, "textures/gui/" + LunchboxItem.getDyePrefix(container.getColor()) + "lunchbox.png");
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTick);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void renderBg(PoseStack matrixStack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BG_TEXTURE);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.boxRows * 18 + 17);
		this.blit(matrixStack, relX, relY + this.boxRows * 18 + 17, 0, 126, this.imageWidth, 96);
	}
	
}