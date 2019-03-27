package com.chaseoqueso.bitcrafting.container;

import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.slots.DyeSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitDyeTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerBitDyeTable extends Container {
	
	private TileEntityBitDyeTable tileDyeTable;
    public IInventory[] craftResult = {new InventoryCraftResult(), new InventoryCraftResult(), new InventoryCraftResult(), new InventoryCraftResult(), new InventoryCraftResult()};
	
	public ContainerBitDyeTable(InventoryPlayer player, TileEntityBitDyeTable tileentity)
	{
		this.tileDyeTable = tileentity;
		this.tileDyeTable.setEventHandler(this);
		
		this.addSlotToContainer(new Slot(tileentity, 0, 62, 14));
		this.addSlotToContainer(new Slot(tileentity, 1, 98, 14));

		int i;
		for(i = 0; i < 5; i++)
			this.addSlotToContainer(new DyeSlot(player.player, tileentity, craftResult[i], i + 2, 44 + i*18, 48));
		
		for(i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(player, j + i*9 + 9, 8 + j * 18, 90 + i*18));
			}
		}
		
		for(i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(player, i, 8 + i*18, 148));
		}
		
        this.onCraftMatrixChanged(this.tileDyeTable);
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
		if(tileDyeTable.getStackInSlot(0) == null || tileDyeTable.getStackInSlot(1) == null)
		{
			setResultsNull();
			return;
		}
		int color = ItemBit.getColorFromDye(tileDyeTable.getStackInSlot(1));
		ItemStack stack = tileDyeTable.getStackInSlot(0).copy();
		stack.stackSize = 1;
		if(color == -2)
			this.craftResult[2].setInventorySlotContents(0, ItemBit.setClear(stack, true).copy());
		else if(color == -1)
			setResultsNull();
		else
			for(int i = 0; i < 5; i++)
				this.craftResult[i].setInventorySlotContents(0, ItemBit.setClear(ItemBit.setColor(stack, color, i), false).copy());
    }
	
	private void setResultsNull()
	{
		for(int i = 0; i < 5; i++)
			this.craftResult[i].setInventorySlotContents(0, null);
	}
	
	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_)
    {
		InventoryPlayer inventoryplayer = p_75134_1_.inventory;
	
	    if (inventoryplayer.getItemStack() != null)
	    {
	        p_75134_1_.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), false);
	        inventoryplayer.setItemStack((ItemStack)null);
	    }
    }

	public boolean canInteractWith(EntityPlayer player) {
		return this.tileDyeTable.isUseableByPlayer(player);
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

			if(par2 < 7)
			{
				if(!this.mergeItemStack(itemstack1, 7, 43, false))
					return null;
				slot.onSlotChange(itemstack1, itemstack);
			} else if(par2 >= 7 && par2 < 34 && !this.mergeItemStack(itemstack1, 34, 43, false)) {
				return null;
			} else if(par2 >= 34 && par2 < 43 && !this.mergeItemStack(itemstack1, 7, 34, false)) {
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
