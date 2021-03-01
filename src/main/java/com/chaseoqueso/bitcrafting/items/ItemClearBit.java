package com.chaseoqueso.bitcrafting.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemClearBit extends ItemBit {
	
	public ItemClearBit()
	{
		setUnlocalizedName("ItemClearBit");
	}
	
	@Override
    public String getUnlocalizedName(ItemStack stack) 
    {
		return "item.clearbit";
    }
	
	@Override
	public int colorMultiplier(ItemStack stack, int par2)
	{
		return 0xFFFFFF;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
		ItemStack bitStack = new ItemStack(this);
		NBTTagCompound itemData = new NBTTagCompound();
		
        bitStack.setTagCompound(itemData);
        itemData.setString("color", "gray");
        itemData.setString("shade", "lightest");
        itemData.setFloat("damage", .1F);
        itemData.setFloat("durability", 20F);
        itemData.setFloat("enchantability", .2F);

		items.add(bitStack);
    }
}
