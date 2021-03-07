package com.chaseoqueso.bitcrafting.gui;

import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.container.ContainerBitFusionTable;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBitFusionTable extends GuiContainer {
	
	public static final ResourceLocation fusionTableGuiTextures = new ResourceLocation("bcm", "textures/gui/fusiontable.png");
	private TileEntityBitFusionTable tileFusionTable;
	/** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /** The Y size of the inventory window in pixels. */
    protected int ySize = 178;
	
	public GUIBitFusionTable(InventoryPlayer invPlayer, TileEntityBitFusionTable tileentity)
	{
		super(new ContainerBitFusionTable(invPlayer, tileentity));
		this.tileFusionTable = tileentity;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par, int par2)
	{
		String string = this.tileFusionTable.hasCustomInventoryName() ? this.tileFusionTable.getInventoryName() : I18n.format(this.tileFusionTable.getInventoryName(), new Object[0]);
		this.fontRenderer.drawString(string, this.xSize/2 - this.fontRenderer.getStringWidth(string)/2, -1, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 98, 4210752);
		string = "Fusion Cost: " + ((ContainerBitFusionTable)this.inventorySlots).xpCost;
        this.fontRenderer.drawStringWithShadow(string, this.xSize/2 - this.fontRenderer.getStringWidth(string)/2, this.ySize - 108, 8453920);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(fusionTableGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}