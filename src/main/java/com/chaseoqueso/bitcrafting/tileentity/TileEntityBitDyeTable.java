package com.chaseoqueso.bitcrafting.tileentity;

import com.chaseoqueso.bitcrafting.items.ItemBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.EnumHelper;
import scala.actors.threadpool.Arrays;

public class TileEntityBitDyeTable extends TileEntity implements IInventory {
	
	private ItemStack[] tableItemStacks = new ItemStack[2];
	private String dyeTableName = "Dye Table";
	private Container eventhandler;
	
	public void setDyeTableName(String displayName) {
		this.dyeTableName = displayName;
	}
	
	public void setEventHandler(Container handler)
	{
		this.eventhandler = handler;
	}

	@Override
	public int getSizeInventory() {
		return this.tableItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.tableItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int par1, int par2) {
		if(this.tableItemStacks[par1] != null)
		{
			ItemStack itemstack;
			if(this.tableItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.tableItemStacks[par1];
				this.tableItemStacks[par1] = null;
                this.eventhandler.onCraftMatrixChanged(this);
                this.markDirty();
				return itemstack;
			} else {
				itemstack = this.tableItemStacks[par1].splitStack(par2);
				if(this.tableItemStacks[par1].stackSize == 0)
					this.tableItemStacks[par1] = null;
                this.eventhandler.onCraftMatrixChanged(this);
                this.markDirty();
				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.tableItemStacks[slot] != null)
		{
			ItemStack itemstack = this.tableItemStacks[slot];
			this.tableItemStacks[slot] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.tableItemStacks[slot] = itemstack;
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			itemstack.stackSize = this.getInventoryStackLimit();
        this.eventhandler.onCraftMatrixChanged(this);
        this.markDirty();
	}

	@Override
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.dyeTableName : "Bit forge";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return this.dyeTableName != null && this.dyeTableName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.tableItemStacks = new ItemStack[this.getSizeInventory()];
        
        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");
            
            if (b0 >= 0 && b0 < this.tableItemStacks.length)
            {
                this.tableItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        if (tag.hasKey("CustomName", 8))
        {
            this.dyeTableName = tag.getString("CustomName");
        }
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
        NBTTagList nbttaglist = new NBTTagList();
        
        for (int i = 0; i < this.tableItemStacks.length; ++i)
        {
            if (this.tableItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.tableItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tagCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            tagCompound.setString("CustomName", this.dyeTableName);
        }
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + .5D, (double) this.yCoord + .5D, (double) this.zCoord + .5D) <= 64;
	}

	@Override
	public void openInventory() {
	}

	@Override
	public void closeInventory() {
		
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 0 ? itemstack.getItem() instanceof ItemBit : itemstack.getItem() instanceof ItemDye;
	}
}
