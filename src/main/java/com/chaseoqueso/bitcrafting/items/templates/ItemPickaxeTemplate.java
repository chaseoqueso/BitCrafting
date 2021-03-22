package com.chaseoqueso.bitcrafting.items.templates;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.util.ResourceLocation;

public class ItemPickaxeTemplate extends ItemToolTemplate {

    public ItemPickaxeTemplate()
    {
        super();
        setUnlocalizedName("ItemPickaxeTemplate");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itempickaxetemplate"));
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
    }
}
