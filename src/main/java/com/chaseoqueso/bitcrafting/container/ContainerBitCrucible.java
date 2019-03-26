package com.chaseoqueso.bitcrafting.container;

import com.chaseoqueso.bitcrafting.CrucibleRecipes;
import com.chaseoqueso.bitcrafting.slots.CrucibleSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

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
	
	public void addCraftingToCrafters(ICrafting craft)
	{
		super.addCraftingToCrafters(craft);
		craft.sendProgressBarUpdate(this, 0, this.tileCrucible.cookTime);
		craft.sendProgressBarUpdate(this, 1, this.tileCrucible.burnTime);
		craft.sendProgressBarUpdate(this, 2, this.tileCrucible.currentBurnTime);
	}
	
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); ++i)
		{
			ICrafting craft = (ICrafting) this.crafters.get(i);
			
			if(this.lastCookTime != this.tileCrucible.cookTime)
			{
				craft.sendProgressBarUpdate(this, 0, this.tileCrucible.cookTime);
			}
			if(this.lastBurnTime != this.tileCrucible.burnTime)
			{
				craft.sendProgressBarUpdate(this, 1, this.tileCrucible.burnTime);
			}
			if(this.lastItemBurnTime != this.tileCrucible.currentBurnTime)
			{
				craft.sendProgressBarUpdate(this, 2, this.tileCrucible.currentBurnTime);
			}
		}
		
		this.lastBurnTime = this.tileCrucible.burnTime;
		this.lastCookTime = this.tileCrucible.cookTime;
		this.lastItemBurnTime = this.tileCrucible.currentBurnTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2)
	{
		if(par1 == 0)
			this.tileCrucible.cookTime = par2;
		if(par1 == 1)
			this.tileCrucible.burnTime = par2;
		if(par1 == 2)
			this.tileCrucible.currentBurnTime = par2;
		
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.tileCrucible.isUseableByPlayer(player);
	}
	
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(par2 >= 2 && par2 < 82)
			{
				if(!this.mergeItemStack(itemstack1, 82, 118, false))
					return null;
				slot.onSlotChange(itemstack1, itemstack);
			} else if(par2 != 1 && par2 != 0) {
				if(CrucibleRecipes.instance().getBreakDownResult(itemstack1) != null)
				{
					if(!this.mergeItemStack(itemstack1, 0, 1, false) && TileEntityFurnace.isItemFuel(itemstack1))
						return null;
				} else if(TileEntityFurnace.isItemFuel(itemstack1)) {
					if(!this.mergeItemStack(itemstack1, 1, 2, false))
						return null;
				} else if(par2 >= 82 && par2 < 109) {
					if(!this.mergeItemStack(itemstack1, 109, 118, false))
						return null;
				} else if(par2 >= 109 && par2 < 118 && !this.mergeItemStack(itemstack1, 82, 109, false)) {
					return null;
				} 
			} else if(!this.mergeItemStack(itemstack1, 82, 118, false)) {
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
