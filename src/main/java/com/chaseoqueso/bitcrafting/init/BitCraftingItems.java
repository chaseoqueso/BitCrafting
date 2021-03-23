package com.chaseoqueso.bitcrafting.init;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.items.ItemBitColorManager;
import com.chaseoqueso.bitcrafting.items.templates.*;
import com.chaseoqueso.bitcrafting.items.tools.*;
import com.chaseoqueso.bitcrafting.items.ItemClearBit;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class BitCraftingItems {

    @GameRegistry.ObjectHolder(value = BitCraftingMod.MODID)
    public static class ITEMS {
        public static Item itemBit = new ItemBit().setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembit"));
        public static Item itemClearBit = new ItemClearBit().setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itemclearbit"));
        public static Item itemBitSword = new ItemBitSword();
        public static Item itemBitPickaxe = new ItemBitPickaxe();
        public static Item itemBitAxe = new ItemBitAxe();
        public static Item itemBitHoe = new ItemBitHoe();
        public static Item itemBitShovel = new ItemBitShovel();
        public static Item itemSwordTemplate = new ItemSwordTemplate();
        public static Item itemPickaxeTemplate = new ItemPickaxeTemplate();
        public static Item itemAxeTemplate = new ItemAxeTemplate();
        public static Item itemHoeTemplate = new ItemHoeTemplate();
        public static Item itemShovelTemplate = new ItemShovelTemplate();

        public static IItemColor itemBitColorManager = new ItemBitColorManager();
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {

        event.getRegistry().registerAll(ITEMS.itemBit,
                                        ITEMS.itemClearBit,
                                        ITEMS.itemBitSword,
                                        ITEMS.itemBitPickaxe,
                                        ITEMS.itemBitAxe,
                                        ITEMS.itemBitHoe,
                                        ITEMS.itemBitShovel,
                                        ITEMS.itemSwordTemplate,
                                        ITEMS.itemPickaxeTemplate,
                                        ITEMS.itemAxeTemplate,
                                        ITEMS.itemHoeTemplate,
                                        ITEMS.itemShovelTemplate);
    }
}
