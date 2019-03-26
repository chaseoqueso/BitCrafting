package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.items.ItemBit;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class BitSlot extends Slot {

	public BitSlot(IInventory inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return stack.getItem() instanceof ItemBit;
	}
}
