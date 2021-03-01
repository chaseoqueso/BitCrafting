package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.items.ItemBit;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FusionInputSlot extends FusionSlot {


    public FusionInputSlot(EntityPlayer player, IInventory craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
        super(player, craftingInventory, inventoryIn, slotIndex, xPosition, yPosition);
    }

    public boolean isItemValid(ItemStack stack)
    {
        return stack.getItem() instanceof ItemBit || stack.getItem() == BitCraftingItems.itemBitSword;
    }
}
