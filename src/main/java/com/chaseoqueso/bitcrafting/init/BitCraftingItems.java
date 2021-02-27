package com.chaseoqueso.bitcrafting.init;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.items.ItemBitSword;
import com.chaseoqueso.bitcrafting.items.ItemClearBit;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BitCraftingItems {

	public static Item itemBit;
	public static Item itemClearBit;
	public static Item itemBitSword;

    public static void init() 
    {
        CreativeTabs tabBitCraftingMod = BitCraftingMod.tabBitCraftingMod;
		itemBit = new ItemBit().setUnlocalizedName("ItemBit").setCreativeTab(tabBitCraftingMod);
		itemClearBit = new ItemClearBit().setUnlocalizedName("ItemClearBit").setCreativeTab(tabBitCraftingMod);
		itemBitSword = new ItemBitSword().setUnlocalizedName("ItemBitSword").setCreativeTab(null);
    }

    public static void register() 
    {
		ForgeRegistries.ITEMS.register(itemBit);
		ForgeRegistries.ITEMS.register(itemClearBit);
		ForgeRegistries.ITEMS.register(itemBitSword);
    }

    public static void registerRenders()
    {
        registerRender(itemBit);
        registerRender(itemClearBit);
        registerRender(itemBitSword);
    }

    public static void registerRender(Item item)
    {
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(BitCraftingMod.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}
