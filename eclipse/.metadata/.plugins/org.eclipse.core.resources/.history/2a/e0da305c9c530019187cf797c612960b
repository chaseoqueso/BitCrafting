package com.chaseoqueso.bitcrafting.gui;

import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.alt_vanilla.BitGuiContainer;
import com.chaseoqueso.bitcrafting.container.ContainerBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBitChest extends BitGuiContainer {
	
	public static final ResourceLocation chestGuiTextures = new ResourceLocation("bcm", "textures/gui/BitChest.png");
	private TileEntityBitChest tileForge;
    protected int xSize = 176;
    protected int ySize = 243;
	
	public GUIBitChest(InventoryPlayer invPlayer, TileEntityBitChest tileentity)
	{
		super(new ContainerBitChest(invPlayer, tileentity));
		this.tileForge = tileentity;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par, int par2)
	{
		String string = this.tileForge.hasCustomInventoryName() ? this.tileForge.getInventoryName() : I18n.format(this.tileForge.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize/2 - this.fontRendererObj.getStringWidth(string)/2, -32, 4210752);
	}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(chestGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 1) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}
