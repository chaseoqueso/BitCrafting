package com.chaseoqueso.bitcrafting.gui;

import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.container.ContainerBitForge;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBitForge extends BitGuiContainer {
	public static final ResourceLocation forgeGuiTextures = new ResourceLocation("bcm", "textures/gui/bitforge.png");
	private TileEntityBitForge tileForge;
	protected int xSize = 256;
	protected int ySize = 243;

	public GUIBitForge(InventoryPlayer invPlayer, TileEntityBitForge tileentity) {
		super(new ContainerBitForge(invPlayer, tileentity));
		this.tileForge = tileentity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par, int par2) {
		String string = this.tileForge.hasCustomInventoryName() ? this.tileForge.getInventoryName()
				: I18n.format(this.tileForge.getInventoryName(), new Object[0]);
		this.fontRenderer.drawString(string, this.xSize / 2 + 43 - this.fontRenderer.getStringWidth(string) / 2,
				-32, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(forgeGuiTextures);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize + 1) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}
