package com.chaseoqueso.bitcrafting.items;

import java.util.Arrays;
import java.util.List;

import com.chaseoqueso.bitcrafting.BitCraftingMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemClearBit extends ItemBit {
	
	public ItemClearBit()
	{
		setUnlocalizedName("ItemClearBit");
		setTextureName(BitCraftingMod.MODID + ":itemclearbit");
	}
	
	@Override
    public String getUnlocalizedName(ItemStack stack) 
    {
		return "item.clearbit";
    }
	
	@Override
	public int getColorFromItemStack(ItemStack stack, int par2)
	{
		return 0xFFFFFF;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List itemList) 
    {
		ItemStack bitStack = new ItemStack(item);
		NBTTagCompound itemData = new NBTTagCompound();
		
        bitStack.setTagCompound(itemData);
        itemData.setString("color", "gray");
        itemData.setString("shade", "lightest");
        itemData.setFloat("damage", .1F);
        itemData.setFloat("durability", 20F);
        itemData.setFloat("enchantability", .2F);
        
        itemList.add(bitStack);
    }
}
