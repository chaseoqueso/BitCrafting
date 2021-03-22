package com.chaseoqueso.bitcrafting.tileentity;

import com.chaseoqueso.bitcrafting.items.ItemBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

public class TileEntityBitDyeTable extends TileEntity implements IInventory {

	private NonNullList<ItemStack> tableItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);
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
		return this.tableItemStacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : tableItemStacks)
		{
			if(!stack.isEmpty())
				return false;
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) { return this.tableItemStacks.get(index); }

	@Override
	public ItemStack decrStackSize(int index, int count) {
		if(!this.tableItemStacks.get(index).isEmpty())
		{
			ItemStack itemstack;
			if(this.tableItemStacks.get(index).getCount() <= count)
			{
				itemstack = this.tableItemStacks.get(index);
				this.tableItemStacks.set(index, ItemStack.EMPTY);
                this.eventhandler.onCraftMatrixChanged(this);
                this.markDirty();
				return itemstack;
			} else {
				itemstack = this.tableItemStacks.get(index).splitStack(count);
				if(this.tableItemStacks.get(index).getCount() == 0)
					this.tableItemStacks.set(index, ItemStack.EMPTY);
                this.eventhandler.onCraftMatrixChanged(this);
                this.markDirty();
				return itemstack;
			}
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.tableItemStacks.set(slot, itemstack);
		if(itemstack != ItemStack.EMPTY && itemstack.getCount() > this.getInventoryStackLimit())
			itemstack.setCount(this.getInventoryStackLimit());
        this.eventhandler.onCraftMatrixChanged(this);
        this.markDirty();
	}

	@Override
	public String getName() {
		return this.hasCustomName() ? this.dyeTableName : "Bit forge";
	}

	@Override
	public boolean hasCustomName() {
		return this.dyeTableName != null && this.dyeTableName.length() > 0;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(tableItemStacks, index);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
		this.tableItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(tag, tableItemStacks);

        if (tag.hasKey("CustomName", 8))
        {
            this.dyeTableName = tag.getString("CustomName");
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		ItemStackHelper.saveAllItems(tag, tableItemStacks);

        if (this.hasCustomName())
        {
			tag.setString("CustomName", this.dyeTableName);
        }

        return tag;
	}
	
	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + .5D, (double) pos.getY() + .5D, (double) pos.getZ() + .5D) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player){}

	@Override
	public void closeInventory(EntityPlayer player){}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 0 ? itemstack.getItem() instanceof ItemBit : itemstack.getItem() instanceof ItemDye;
	}

	@Override
	public void clear()	{ tableItemStacks.clear(); }

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value)	{}

	@Override
	public int getFieldCount()
	{
		return 0;
	}
}
