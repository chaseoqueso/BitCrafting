package com.chaseoqueso.bitcrafting.container;

import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import com.chaseoqueso.bitcrafting.slots.BitSlot;
import com.chaseoqueso.bitcrafting.slots.ForgeSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ContainerBitForge extends Container {
	
	protected TileEntityBitForge tileForge;
    public IInventory craftResult = new InventoryCraftResult();
    private World world;
	
	public ContainerBitForge(InventoryPlayer player, TileEntityBitForge tileentity)
	{
		this.tileForge = tileentity;
		this.tileForge.setEventHandler(this);
		world = player.player.worldObj;
		
		int xAmount = 16, yAmount = 16, xStart = -35, yStart = -37;
		for(int y = 0; y < yAmount; y++)
		{
			for(int x = 0; x < xAmount; x++)
			{
				this.addSlotToContainer(new BitSlot(tileentity, x + y*16, xStart + x*10, yStart + y*10));
			}
		}
		this.addSlotToContainer(new ForgeSlot(player.player, tileentity, craftResult, 0, 169, 39));
		
		int i;
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(player, j + i*9 + 9, 8 + j*18, 128 + i*18));
			}
		}
		
		for(i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i*18, 184));
		}
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
		super.onCraftMatrixChanged(p_75130_1_);
		if (p_75130_1_ == this.tileForge)
        {
			if(tileForge.canForge())
				this.craftResult.setInventorySlotContents(0, tileForge.forgeItem().copy());
			else
				this.craftResult.setInventorySlotContents(0, null);
        }
	}
	
	public void setOutputName(String name)
	{
		ItemStack itemstack = this.craftResult.getStackInSlot(0);
		if (StringUtils.isBlank(name))
        {
            if (itemstack.hasDisplayName())
                itemstack.func_135074_t();
        }
        else if (!name.equals(itemstack.getDisplayName()))
        {
            itemstack.setStackDisplayName(name);
        }
	}
	
	public boolean canInteractWith(EntityPlayer player) {
		return this.tileForge.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(par2 < 257)
			{
				if(!this.mergeItemStack(itemstack1, 257, 293, false))
					return null;
				slot.onSlotChange(itemstack1, itemstack);
			} else if(par2 >= 257 && par2 < 284 && (!this.mergeItemStack(itemstack1, 284, 293, false))) {
					return null;
			} else if(par2 >= 284 && par2 < 293 && (!this.mergeItemStack(itemstack1, 257, 284, false))) {
					return null;
			}
			if(itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
			if(itemstack1.stackSize == itemstack.stackSize)
				return null;
			slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}
}
