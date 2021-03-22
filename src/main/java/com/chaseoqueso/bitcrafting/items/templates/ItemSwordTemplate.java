package com.chaseoqueso.bitcrafting.items.templates;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;

public class ItemSwordTemplate extends ItemToolTemplate {

    public ItemSwordTemplate()
    {
        super();
        setUnlocalizedName("ItemSwordTemplate");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itemswordtemplate"));
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
    }
}
