package com.chaseoqueso.bitcrafting.container;

import com.chaseoqueso.bitcrafting.slots.BitSlot;
import com.chaseoqueso.bitcrafting.slots.ForgeSlot;
import com.chaseoqueso.bitcrafting.slots.TemplateSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBitForge extends Container {
	
	protected TileEntityBitForge tileForge;
    public IInventory craftResult = new InventoryCraftResult();
    private World world;
	
	public ContainerBitForge(InventoryPlayer player, TileEntityBitForge tileentity)
	{
		this.tileForge = tileentity;
		this.tileForge.setEventHandler(this);
		world = player.player.world;
		
		int xAmount = 16, yAmount = 16, xStart = -35, yStart = -37;
		for(int y = 0; y < yAmount; y++)
		{
			for(int x = 0; x < xAmount; x++)
			{
				this.addSlotToContainer(new BitSlot(tileentity, x + y*16, xStart + x*10, yStart + y*10));
			}
		}
		this.addSlotToContainer(new ForgeSlot(player.player, tileentity, craftResult, 0, 169, 39));
		this.addSlotToContainer(new TemplateSlot(tileentity, 256, 138, 59));
		
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

		onCraftMatrixChanged(tileForge);
	}
	
	@Override
	public void onCraftMatrixChanged(IInventory p_75130_1_)
    {
		if (p_75130_1_ == this.tileForge)
        {
			if(tileForge.canForge())
				this.craftResult.setInventorySlotContents(0, tileForge.forgeItem().copy());
			else
				this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
        }
		super.onCraftMatrixChanged(p_75130_1_);
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.tileForge.isUsableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(par2);

		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if(par2 < 257)
			{
				if(!this.mergeItemStack(itemstack1, 257, 293, false))
					return ItemStack.EMPTY;
				slot.onSlotChange(itemstack1, itemstack);
			} else if(par2 >= 257 && par2 < 284 && (!this.mergeItemStack(itemstack1, 284, 293, false))) {
					return ItemStack.EMPTY;
			} else if(par2 >= 284 && par2 < 293 && (!this.mergeItemStack(itemstack1, 257, 284, false))) {
					return ItemStack.EMPTY;
			}
			if(itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
			if(itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}

	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player)
	{
		if (clickTypeIn == ClickType.QUICK_CRAFT) {
			if(getDragEvent(dragType) == 2)
			{
				this.tileForge.preventContainerUpdate = true;

				ItemStack result = super.slotClick(slotId, dragType, clickTypeIn, player);

				this.tileForge.preventContainerUpdate = false;
				onCraftMatrixChanged(this.tileForge);

				return result;
			}
		}

		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}
}
