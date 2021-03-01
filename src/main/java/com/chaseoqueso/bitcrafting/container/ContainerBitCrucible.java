package com.chaseoqueso.bitcrafting.container;

import com.chaseoqueso.bitcrafting.CrucibleRecipes;
import com.chaseoqueso.bitcrafting.slots.CrucibleSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBitCrucible extends Container {
	
	private TileEntityBitCrucible tileCrucible;
	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;
	
	public ContainerBitCrucible(InventoryPlayer player, TileEntityBitCrucible tileentity)
	{
		this.tileCrucible = tileentity;
		this.addSlotToContainer(new Slot(tileentity, 0, 15, 19));
		this.addSlotToContainer(new Slot(tileentity, 1, 15, 55));
		
		int xAmount = 10, yAmount = 8, xStart = 66, yStart = 4;
		for(int y = 0; y < yAmount; y++)
		{
			for(int x = 0; x < xAmount; x++)
			{
				this.addSlotToContainer(new CrucibleSlot(player.player, tileentity, 2 + x + y*10, xStart + x*10, yStart + y*10));
			}
		}
		
		int i;
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
	}
	
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileCrucible);
	}
	
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener listener = (IContainerListener) this.listeners.get(i);

			if(this.lastBurnTime != this.tileCrucible.burnTime)
			{
				listener.sendWindowProperty(this, 0, this.tileCrucible.burnTime);
			}
			if(this.lastItemBurnTime != this.tileCrucible.currentBurnTime)
			{
				listener.sendWindowProperty(this, 1, this.tileCrucible.currentBurnTime);
			}
			if(this.lastCookTime != this.tileCrucible.cookTime)
			{
				listener.sendWindowProperty(this, 2, this.tileCrucible.cookTime);
			}
		}
		
		this.lastBurnTime = this.tileCrucible.burnTime;
		this.lastCookTime = this.tileCrucible.cookTime;
		this.lastItemBurnTime = this.tileCrucible.currentBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		if(id == 0)
			this.tileCrucible.cookTime = data;
		if(id == 1)
			this.tileCrucible.burnTime = data;
		if(id == 2)
			this.tileCrucible.currentBurnTime = data;
		
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.tileCrucible.isUsableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(index >= 2 && index < 82)
			{
				if(!this.mergeItemStack(itemstack1, 82, 118, false))
					return null;

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if(index != 1 && index != 0)
			{
				if(CrucibleRecipes.instance().getBreakDownResult(itemstack1) != null)
				{
					if(!this.mergeItemStack(itemstack1, 0, 1, false) && TileEntityFurnace.isItemFuel(itemstack1))
						return null;
				}
				else if(TileEntityFurnace.isItemFuel(itemstack1))
				{
					if(!this.mergeItemStack(itemstack1, 1, 2, false))
						return null;
				}
				else if(index >= 82 && index < 109)
				{
					if(!this.mergeItemStack(itemstack1, 109, 118, false))
						return null;
				}
				else if(index >= 109 && index < 118 && !this.mergeItemStack(itemstack1, 82, 109, false))
				{
					return null;
				} 
			}
			else if(!this.mergeItemStack(itemstack1, 82, 118, false))
			{
				return null;
			}

			if(itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if(itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;

			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}
}
