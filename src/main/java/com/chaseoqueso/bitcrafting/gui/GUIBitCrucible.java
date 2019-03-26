package com.chaseoqueso.bitcrafting.gui;

import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.alt_vanilla.BitGuiContainer;
import com.chaseoqueso.bitcrafting.container.ContainerBitCrucible;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class GUIBitCrucible extends BitGuiContainer {
	
	public static final ResourceLocation crucibleGuiTextures = new ResourceLocation("bcm", "textures/gui/crucible.png");
	private TileEntityBitCrucible tileCrucible;
	/** The X size of the inventory window in pixels. */
    protected int xSize = 176;
    /** The Y size of the inventory window in pixels. */
    protected int ySize = 178;
	
	public GUIBitCrucible(InventoryPlayer invPlayer, TileEntityBitCrucible tileentity)
	{
		super(new ContainerBitCrucible(invPlayer, tileentity));
		this.tileCrucible = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int par, int par2)
	{
		String string = this.tileCrucible.hasCustomInventoryName() ? this.tileCrucible.getInventoryName() : I18n.format(this.tileCrucible.getInventoryName(), new Object[0]);
		this.fontRendererObj.drawString(string, this.xSize/2 - this.fontRendererObj.getStringWidth(string)/2, -1, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 98, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(crucibleGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);

        if (this.tileCrucible.isBurning())
        {
            int i1 = this.tileCrucible.getBurnTimeRemainingScaled(13);
            this.drawTexturedModalRect(k + 16, l + 57 - i1, 176, 12 - i1, 14, i1 + 1);
            i1 = this.tileCrucible.getCookProgressScaled(24);
            this.drawTexturedModalRect(k + 38, l + 43, 176, 14, i1 + 1, 16);
        }
	}
}
