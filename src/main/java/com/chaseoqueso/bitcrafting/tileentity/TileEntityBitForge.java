package com.chaseoqueso.bitcrafting.tileentity;

import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.container.ContainerBitForge;
import com.chaseoqueso.bitcrafting.items.ItemBitSword;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import java.util.Arrays;

public class TileEntityBitForge extends TileEntity implements IInventory {
	
	private ItemStack[] forgeItemStacks = new ItemStack[256];
	private String forgeName = "Bit Forge";
	protected ContainerBitForge eventhandler;
	
	public void setForgeName(String displayName) {
		this.forgeName = displayName;
	}
	
	public void setEventHandler(Container handler)
	{
		this.eventhandler = (ContainerBitForge) handler;
	}

	public int getSizeInventory() {
		return this.forgeItemStacks.length;
	}

	public ItemStack getStackInSlot(int index) {
		return this.forgeItemStacks[index];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if(this.forgeItemStacks[par1] != null)
		{
			ItemStack itemstack;
			if(this.forgeItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.forgeItemStacks[par1];
				this.forgeItemStacks[par1] = null;
                this.eventhandler.onCraftMatrixChanged(this);
				return itemstack;
			} else {
				itemstack = this.forgeItemStacks[par1].splitStack(par2);
				if(this.forgeItemStacks[par1].stackSize == 0)
				{
					this.forgeItemStacks[par1] = null;
				}
                this.eventhandler.onCraftMatrixChanged(this);
				return itemstack;
			}
		}
		return null;
	}

	public ItemStack decrStackSizeNoUpdate(int par1, int par2) {
		if(this.forgeItemStacks[par1] != null)
		{
			ItemStack itemstack;
			if(this.forgeItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.forgeItemStacks[par1];
				this.forgeItemStacks[par1] = null;
				return itemstack;
			} else {
				itemstack = this.forgeItemStacks[par1].splitStack(par2);
				if(this.forgeItemStacks[par1].stackSize == 0)
				{
					this.forgeItemStacks[par1] = null;
				}
				return itemstack;
			}
		}
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.forgeItemStacks[slot] != null)
		{
			ItemStack itemstack = this.forgeItemStacks[slot];
			this.forgeItemStacks[slot] = null;
			return itemstack;
		}
		return null;
	}

	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.forgeItemStacks[slot] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
        this.eventhandler.onCraftMatrixChanged(this);
	}

	public void setInventorySlotContentsNoUpdate(int slot, ItemStack itemstack) {
		this.forgeItemStacks[slot] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
		{
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.forgeName : "Bit Forge";
	}

	public boolean hasCustomInventoryName() {
		return this.forgeName != null && this.forgeName.length() > 0;
	}

	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.forgeItemStacks = new ItemStack[this.getSizeInventory()];
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int b0 = nbttagcompound1.getInteger("Slot");
            
            if (b0 >= 0 && b0 < this.forgeItemStacks.length)
            {
                this.forgeItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        if (tag.hasKey("CustomName", 8))
        {
            this.forgeName = tag.getString("CustomName");
        }
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.forgeItemStacks.length; ++i)
        {
            if (this.forgeItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setInteger("Slot", i);
                this.forgeItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tagCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            tagCompound.setString("CustomName", this.forgeName);
        }
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + .5D, (double) this.yCoord + .5D, (double) this.zCoord + .5D) <= 64;
	}

	public void openInventory() {
	}

	public void closeInventory() {
	}

	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return false;
	}
	
	public boolean canForge()
	{
		boolean flag = false;
		for(int i = 0; i < forgeItemStacks.length; i++)
		{
			if(forgeItemStacks[i] != null)
				flag = true;
		}
		return flag;
	}
	
	public ItemStack forgeItem()
	{
		if(this.canForge())
		{
			int count = 0;
			float damage = 0, durability = 0, enchantability = 0;
			
			for(int i = 0; i < 256; i++)
			{
				if(forgeItemStacks[i] != null && forgeItemStacks[i].hasTagCompound()) 
		        {
		            NBTTagCompound itemData = forgeItemStacks[i].getTagCompound();
		            
		            if (itemData.hasKey("damage"))
		            {
						damage += itemData.getFloat("damage");
						durability += itemData.getFloat("durability");
						enchantability += itemData.getFloat("enchantability");
		            }
					count++;
		        }
			}

			return ItemBitSword.initialize(new ItemStack(BitCraftingMod.itemBitSword), (ItemStack[])Arrays.copyOf(forgeItemStacks, 256), damage, durability, enchantability).copy();
			//Returns the new weapon that is created based on certain damage, durability, etc. Also gives the new weapon an array of the Bits used for image generating purposes.
		}
		return null;
	}
}
