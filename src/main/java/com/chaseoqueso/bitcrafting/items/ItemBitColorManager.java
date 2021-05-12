package com.chaseoqueso.bitcrafting.items;

import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemBitColorManager implements IItemColor {

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        if(stack.getItem() instanceof ItemBit)
        {
            return ItemBit.getColorFromStack(stack);
        }
        return tintIndex;
    }
}
