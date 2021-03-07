package com.chaseoqueso.bitcrafting.gui;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.alt_vanilla.BitGuiContainer;
import com.chaseoqueso.bitcrafting.container.ContainerBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIBitChest extends BitGuiContainer {
	
	public static final ResourceLocation chestGuiTextures = new ResourceLocation(BitCraftingMod.MODID, "textures/gui/bitchest.png");
	private InventoryPlayer playerInventory;
	private TileEntityBitChest tileChest;
    protected int xSize = 176;
    protected int ySize = 243;
	
	public GUIBitChest(InventoryPlayer invPlayer, TileEntityBitChest tileentity, EntityPlayer player)
	{
		super(new ContainerBitChest(invPlayer, tileentity, player));
		this.playerInventory = invPlayer;
		this.tileChest = tileentity;
	}


	@Override protected void drawGuiContainerForegroundLayer(int par, int par2)
	{
		/*  No space on gui for a name, this string overlaps the inventory slots
		String string = this.tileChest.hasCustomName() ? this.tileChest.getName() : I18n.format(this.tileChest.getName(), new Object[0]);
		this.fontRenderer.drawString(string, this.xSize/2 - this.fontRenderer.getStringWidth(string)/2, -32, 4210752);
		 */
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(chestGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize + 1) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
	}

}
