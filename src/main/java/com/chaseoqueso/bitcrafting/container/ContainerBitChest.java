package com.chaseoqueso.bitcrafting.container;

import com.chaseoqueso.bitcrafting.slots.BitSlot;
import com.chaseoqueso.bitcrafting.slots.ForgeSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerBitChest extends Container {

	public TileEntityBitChest tileChest;

	public ContainerBitChest(InventoryPlayer inventory, TileEntityBitChest tileentity, EntityPlayer player) {
		this.tileChest = tileentity;
		tileentity.openInventory(player);

		int xAmount = 16, yAmount = 16, xStart = 5, yStart = -37;
		for (int y = 0; y < yAmount; y++) {
			for (int x = 0; x < xAmount; x++) {
				this.addSlotToContainer(new BitSlot(tileentity, x + y * 16, xStart + x * 10, yStart + y * 10));
			}
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 128 + i * 18));
			}
		}

		for (int i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 184));
		}
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.tileChest.isUsableByPlayer(player);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or you
	 * will crash when someone does that.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 256) {
				if (!this.mergeItemStack(itemstack1, 256, 292, false))
					return ItemStack.EMPTY;
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 256 && index < 283 && (!this.mergeItemStack(itemstack1, 283, 292, false)))
			{
				return ItemStack.EMPTY;
			}
			else if (index >= 283 && index < 292 && (!this.mergeItemStack(itemstack1, 256, 283, false)))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			//if (itemstack1.getCount() == itemstack.getCount())
			//	return ItemStack.EMPTY;
			//slot.onPickupFromSlot(player, itemstack1);
		}
		return itemstack;
	}

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer player)
    {
        super.onContainerClosed(player);
        this.tileChest.closeInventory(player);
    }
}
