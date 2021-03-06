package com.chaseoqueso.bitcrafting.items;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemBitColorManager implements IItemColor {

    @Override
    public int colorMultiplier(ItemStack stack, int par2) {
        return ItemBit.getColorFromStack(stack);
    }
}
