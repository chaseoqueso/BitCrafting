package com.chaseoqueso.bitcrafting.items.templates;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.util.ResourceLocation;

public class ItemShovelTemplate extends ItemToolTemplate {

    public ItemShovelTemplate()
    {
        super();
        setUnlocalizedName("ItemShovelTemplate");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itemshoveltemplate"));
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
    }
}
