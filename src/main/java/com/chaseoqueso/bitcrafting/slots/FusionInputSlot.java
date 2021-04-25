package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.items.ItemBit;

import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FusionInputSlot extends Slot {


    public FusionInputSlot(IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(inventoryIn, slotIndex, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBit || stack.getItem() instanceof IItemBitTool;
    }

    public int getSlotStackLimit() {
        return 1;
    }
}
