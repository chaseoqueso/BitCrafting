package com.chaseoqueso.bitcrafting.items.templates;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.util.ResourceLocation;

public class ItemAxeTemplate extends ItemToolTemplate {

    public ItemAxeTemplate()
    {
        super();
        setUnlocalizedName("ItemAxeTemplate");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itemaxetemplate"));
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
    }
}
