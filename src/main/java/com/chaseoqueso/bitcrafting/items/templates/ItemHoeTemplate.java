package com.chaseoqueso.bitcrafting.items.templates;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.util.ResourceLocation;

public class ItemHoeTemplate extends ItemToolTemplate {

    public ItemHoeTemplate()
    {
        super();
        setUnlocalizedName("ItemHoeTemplate");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itemhoetemplate"));
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
    }
}
