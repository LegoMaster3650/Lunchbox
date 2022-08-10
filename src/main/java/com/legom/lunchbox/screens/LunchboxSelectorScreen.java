package com.legom.lunchbox.screens;

import com.legom.lunchbox.Lunchbox;
import com.legom.lunchbox.menus.LunchboxSelectorMenu;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;

public class LunchboxSelectorScreen extends AbstractContainerScreen<LunchboxSelectorMenu> {
	
	private final int boxRows;
	
	private final ResourceLocation BG_TEXTURE = new ResourceLocation(Lunchbox.MOD_ID, "textures/gui/lunchbox_selector.png");
	
	public LunchboxSelectorScreen(LunchboxSelectorMenu container, Inventory playerInv, Component name) {
		super(container, playerInv, name);
		this.boxRows = container.getBoxRows();
		this.imageWidth = 176;
		this.imageHeight = 24 + boxRows * 18;
		
	}
	
	@Override
	public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTick) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTick);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	public void renderBg(PoseStack matrixStack, float partialTick, int mouseX, int mouseY) {
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, BG_TEXTURE);
		int relX = (this.width - this.imageWidth) / 2;
		int relY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.boxRows * 18 + 17);
		this.blit(matrixStack, relX, relY + this.boxRows * 18 + 17, 0, 126, this.imageWidth, 7);
		//Render selected slot
		int targetSlot = this.menu.getTargetSlot();
		int targetSlotX = targetSlot % 9;
		int targetSlotY = Math.round(targetSlot / 9);
		this.blit(matrixStack, relX + targetSlotX * 18 + 7, relY + targetSlotY * 18 + 17, 176, 0, 18, 18);
	}
	
	@Override
	protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
		this.font.draw(matrixStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
	}
	
	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		for(int i = 0; i < 9; ++i) {
            if (this.minecraft.options.keyHotbarSlots[i].isActiveAndMatches(InputConstants.getKey(keyCode, scanCode))) {
            	this.slotClicked(this.menu.slots.get(i), i, 1, ClickType.PICKUP);
            	this.onClose();
            	return true;
            }
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}
	
}