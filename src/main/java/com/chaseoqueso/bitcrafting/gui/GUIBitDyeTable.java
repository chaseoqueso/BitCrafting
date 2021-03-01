package com.chaseoqueso.bitcrafting.gui;

import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.container.ContainerBitDyeTable;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitDyeTable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBitDyeTable extends GuiContainer {
	
	public static final ResourceLocation dyeTableGuiTextures = new ResourceLocation("bcm", "textures/gui/DyeTable.png");
	private TileEntityBitDyeTable tileDyeTable;
	/** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /** The Y size of the inventory window in pixels. */
    protected int ySize = 178;
	
	public GUIBitDyeTable(InventoryPlayer invPlayer, TileEntityBitDyeTable tileentity)
	{
		super(new ContainerBitDyeTable(invPlayer, tileentity));
		this.tileDyeTable = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par, int par2)
	{
		String string = this.tileDyeTable.hasCustomName() ? this.tileDyeTable.getName() : I18n.format(this.tileDyeTable.getName(), new Object[0]);
		this.fontRenderer.drawString(string, this.xSize/2 - this.fontRenderer.getStringWidth(string)/2, -1, 4210752);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 98, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(dyeTableGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}
}
