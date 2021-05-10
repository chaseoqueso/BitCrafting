package com.chaseoqueso.bitcrafting;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.items.ItemBit;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class CrucibleRecipes {
    private static final CrucibleRecipes crucibleBase = new CrucibleRecipes();
    /** The list of breakdown results. */
    private HashMap<ItemStack, ItemStack[]> breakDownList = new HashMap<ItemStack, ItemStack[]>();
    private HashMap<ItemStack, Float> experienceList = new HashMap<ItemStack, Float>();

    public static CrucibleRecipes instance()
    {
        return crucibleBase;
    }

    private CrucibleRecipes()
    {
		//Note: Bits were previously very powerful, so I changed all recipes to give 3x as many and made them 3x as weak

    	//Blocks
        this.addCrucibleRecipeForBlock(Blocks.SNOW, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "gray", "lightest", 0.010F, 0.1F, 0.03F),
                                                                     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "lightblue", "lightest", 0.010F, 0.1F, 0.03F, "ice", 0.001F, 0.01F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.PLANKS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "", 0.016F, 0.25F, 0.06F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "dark", 0.016F, 0.25F, 0.06F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.GLASS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemClearBit, 64), "gray", "lightest", 0.023F, 0.1F, 0.04F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.CACTUS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "", 0.023F, 0.2F, 0.03F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "dark", 0.023F, 0.2F, 0.03F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.COBBLESTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", 0.02F, 0.5F, 0.02F, 1),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", 0.02F, 0.5F, 0.02F, 1)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.SANDSTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "lightest", 0.02F, 0.5F, 0.02F, 1),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "light", 0.02F, 0.5F, 0.02F, 1)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.IRON_BARS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 24), "gray", "light", 0.023F, 1.0F, 0.06F, 2),
                                                                          ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "gray", "", 0.023F, 1.0F, 0.06F, 2)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.ICE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lightblue", "", 0.02F, 0.5F, 0.05F, "ice", 0.001F, 0.02F)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.GLOWSTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "dark", 0.023F, 0.2F, 0.07F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "yellow", "", 0.02F, 0.2F, 0.05F, "lightning", 0.002F, 0.01F)}, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.SOUL_SAND, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "dark", 0.017F, 1.0F, 0.06F)}, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.REDSTONE_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "", 0.023F, 1.0F, 0.05F, "lightning", 0.001F, 0.02F, 2)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.LAPIS_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "", 0.020F, 1.0F, 0.10F, 1)}, 1.0F);
        this.addCrucibleRecipeForBlock(Blocks.OBSIDIAN, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "darkest", 0.020F, 5.0F, 0.05F, "earth", 0.001F, 0.02F, 4)}, 1.0F);
        
        //Items
        this.addCrucibleRecipeForItem(Items.ROTTEN_FLESH, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "dark", 0.013F, .1F, 0.03F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.FLINT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "light", 0.017F, 0.1F, 0.03F, "earth", 0.001F, 0.01F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.BRICK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "dark", 0.022F, 0.5F, 0.02F, "earth", 0.001F, 0.01F)}, 0.2F);
        this.addCrucibleRecipeForItem(Items.BONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", 0.017F, 1.0F, 0.07F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.GUNPOWDER, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "dark", 0.02F, 0.5F, 0.04F, "fire", 0.001F, 0.01F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.IRON_INGOT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "", 0.023F, 1.0F, 0.06F, 2),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "dark", 0.023F, 1.0F, 0.06F, 2)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.QUARTZ, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "lightest", 0.020F, 0.2F, 0.07F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.SLIME_BALL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "light", 0.010F, 10.0F, 0.03F)}, 0.5F);
        this.addCrucibleRecipeForItem(Items.MAGMA_CREAM, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "light", 0.010F, 10.0F, 0.03F, "fire", 0.001F, 0.01F)}, 0.5F);
        this.addCrucibleRecipeForItem(Items.ENDER_PEARL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "green", "dark", 0.02F, 1.0F, 0.06F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "purple", "", 0.017F, 0.2F, 0.05F, "spatial", 0.001F, 0.01F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.GOLD_INGOT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "", 0.016F, 0.13F, 0.09F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "dark", 0.016F, 0.13F, 0.09F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.BLAZE_ROD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "yellow", "", 0.017F, 0.5F, 0.06F),
																	    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "orange", "", 0.023F, 0.5F, 0.05F, "fire", 0.001F, 0.02F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.ENDER_EYE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "", 0.025F, 1F, 0.05F, "spatial", 0.001F, 0.02F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.DIAMOND, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", 0.027F, 6.0F, 0.04F, 3),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "dark", 0.027F, 6.0F, 0.04F, 3)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.EMERALD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "", 0.023F, 8.0F, 0.05F, 2),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "dark", 0.023F, 8.0F, 0.05F, 2)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.NETHER_STAR, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "lightest", 0.025F, 5.0F, .15F, 4),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "light", 0.025F, 5.0F, .15F, 4)}, 2.0F);
        this.addCrucibleRecipeForItem(Items.PRISMARINE_SHARD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "lightest", 0.025F, 0.5F, 0.03F, "ice", 0.001F, 0.02F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.PRISMARINE_CRYSTALS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "light", 0.027F, 1.0F, 0.05F, "ice", 0.002F, 0.02F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.CHORUS_FRUIT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "magenta", "dark", 0.020F, 1.0F, 0.05F, "spatial", 0.002F, 0.02F)}, 0.7F);
    }

    public void addExternalCrucibleRecipes()
    {
        NonNullList<ItemStack> results;

        //Steel
        results = OreDictionary.getOres("ingotSteel");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", 0.025F, 1.50F, 0.05F, 2),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", 0.025F, 1.50F, 0.05F, 2)}, 0.7F);
        }

        //Copper
        results = OreDictionary.getOres("ingotCopper");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "dark", 0.020F, 1.0F, 0.06F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "orange", "darkest", 0.020F, 1.0F, 0.06F, 1)}, 0.5F);
        }

        //Aluminum
        results = OreDictionary.getOres("ingotAluminum");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", 0.020F, 0.50F, 0.05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", 0.020F, 0.50F, 0.05F, 1)}, 0.5F);
        }

        //Silver
        results = OreDictionary.getOres("ingotSilver");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "", 0.022F, 1.0F, 0.07F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lightblue", "light", 0.022F, 1.0F, 0.07F, 1)}, 0.7F);
        }

        //Electrum
        results = OreDictionary.getOres("ingotElectrum");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "light", 0.023F, 1.08F, 0.05F, "lightning", 0.001F, 0.01F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "", 0.023F, 1.08F, 0.05F, "lightning", 0.001F, 0.01F, 1)}, 0.7F);
        }

        //Brass
        results = OreDictionary.getOres("ingotBrass");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "", 0.023F, 1.50F, 0.05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "dark", 0.023F, 1.50F, 0.05F, 1)}, 0.5F);
        }

        //Titanium
        results = OreDictionary.getOres("ingotTitanium");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lightblue", "light", 0.025F, 5F, 0.03F, 3),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "light", 0.025F, 5F, 0.03F, 3)}, 0.7F);
        }

        //Lead
        results = OreDictionary.getOres("ingotLead");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", 0.020F, 2F, 0.07F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", 0.020F, 2F, 0.07F, 1)}, 0.7F);
        }

        //Zinc
        results = OreDictionary.getOres("ingotZinc");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", 0.020F, 0.50F, 0.05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", 0.020F, 0.50F, 0.05F, 1)}, 0.5F);
        }

        //Platinum
        results = OreDictionary.getOres("ingotPlatinum");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "lightest", 0.024F, 1F, 0.07F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "lightest", 0.024F, 1F, 0.07F, 1)}, 0.7F);
        }

        //Tungsten
        results = OreDictionary.getOres("ingotTungsten");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", 0.025F, 5F, 0.06F, 3),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "darkest", 0.025F, 5F, 0.06F, 3)}, 0.7F);
        }

        //Nickel
        results = OreDictionary.getOres("ingotNickel");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "lightest", 0.020F, 0.50F, 0.05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", 0.020F, 0.50F, 0.05F, 1)}, 0.5F);
        }

        //Ruby
        results = OreDictionary.getOres("gemRuby");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "", 0.020F, .12F, 0.07F),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "dark", 0.020F, .12F, 0.05F, "fire", 0.001F, 0.01F)}, 1.0F);
        }

        //Sapphire
        results = OreDictionary.getOres("gemSapphire");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "", 0.020F, .12F, 0.07F),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "blue", "dark", 0.020F, .12F, 0.05F, "ice", 0.001F, 0.01F)}, 1.0F);
        }

        //Amethyst
        results = OreDictionary.getOres("gemAmethyst");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "", 0.020F, .12F, 0.07F),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "purple", "dark", 0.020F, .12F, 0.05F, "spatial", 0.001F, 0.01F)}, 1.0F);
        }

        //Tinker's Construct Materials
        if(Loader.isModLoaded("tconstruct")) {
            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "materials")), 1, 9), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "", 0.010F, 2.0F, 0.07F),
                                                                                                                                                                                                 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "dark", 0.010F, 2.0F, 0.07F)}, 0.6F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "materials")), 1, 10), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", 0.020F, 1.0F, 0.07F),
                                                                                                                                                                                                  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "dark", 0.020F, 1.0F, 0.07F)}, 0.6F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "materials")), 1, 11), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "", 0.015F, 0.5F, 0.05F),
                                                                                                                                                                                                  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "orange", "", 0.015F, 0.5F, 0.05F, "fire", 0.001F, 0.01F)}, 0.6F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 0), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "", 0.025F, 5.0F, 0.07F, 4),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "blue", "dark", 0.025F, 5.0F, 0.07F, 4)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 1), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "", 0.024F, 5.2F, 0.07F, 4),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "dark", 0.024F, 5.2F, 0.07F, 4)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 2), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "", 0.033F, 5.1F, 0.04F, 4),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "purple", "dark", 0.033F, 5.1F, 0.04F, 4)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 3), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "pink", "", 0.03F, 3F, 0.07F, 3),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "pink", "light", 0.03F, 3F, 0.07F, 3)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 4), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "pink", "", 0.025F, 1.25F, 0.09F, 2),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "pink", "dark", 0.025F, 1.25F, 0.09F, 2)}, 1F);
        }
    }
    
    public void addCrucibleRecipeForBlock(Block input, ItemStack[] itemstacks, float experience)
    {
        this.addCrucibleRecipeForItem(Item.getItemFromBlock(input), itemstacks, experience);
    }

    public void addCrucibleRecipeForItem(Item input, ItemStack[] itemstacks, float experience)
    {
        this.addCrucibleRecipe(new ItemStack(input, 1, 32767), itemstacks, experience);
    }

    public void addCrucibleRecipe(ItemStack input, ItemStack[] itemstacks, float experience)
    {
        this.breakDownList.put(input, itemstacks);
        this.experienceList.put(input, experience);
    }

    /**
     * Returns the breakdown result of an item.
     */
    public ItemStack[] getBreakDownResult(ItemStack itemstack)
    {
        //Iterate through the map of potential crucible results and find the results for the given itemstack
        Iterator<Entry<ItemStack, ItemStack[]>> iterator = this.breakDownList.entrySet().iterator();
        Entry<ItemStack, ItemStack[]> entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = iterator.next();
        }
        while (!this.compareItemsInStacks(itemstack, entry.getKey()));

        ItemStack[] results = entry.getValue().clone();
        if(results.length == 0)
            return null;

        return results;
    }

    public float getExpResult(ItemStack itemstack)
    {
        //Iterate through the map of experience rewards based on input and find the results for the given itemstack
        Iterator<Entry<ItemStack, Float>> expIterator = this.experienceList.entrySet().iterator();
        Entry<ItemStack, Float> expEntry;
        float experience = 0;
        do
        {
            if (!expIterator.hasNext())
            {
                expEntry = null;
                break;
            }

            expEntry = expIterator.next();
        }
        while (!this.compareItemsInStacks(itemstack, expEntry.getKey()));

        return expEntry.getValue();
    }

    private boolean compareItemsInStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }

    public HashMap<ItemStack, ItemStack[]> getBreakDownList()
    {
        return this.breakDownList;
    }
}
