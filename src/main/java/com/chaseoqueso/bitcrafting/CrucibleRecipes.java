package com.chaseoqueso.bitcrafting;

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
        this.addCrucibleRecipeForBlock(Blocks.SNOW, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .013F, .08F, .03F),
                                                                     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "lightblue", "lightest", .013F, .08F, .03F, "ice", 0.001F, 0.01F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.PLANKS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "light", .017F, .24F, .06F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "", .017F, .24F, .06F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.GLASS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemClearBit, 64), "gray", "lightest", .023F, .08F, .05F),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemClearBit, 32), "gray", "lightest", .023F, .08F, .05F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.CACTUS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "green", "", .023F, .24F, .03F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "dark", .023F, .24F, .03F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.COBBLESTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", .02F, 0.55F, .017F, 1),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", .02F, 0.55F, .017F, 1)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.SANDSTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "lightest", .02F, 0.92F, .027F, 1),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "lightest", .02F, 0.92F, .027F, 1)}, 0.3F);
        this.addCrucibleRecipeForBlock(Blocks.ICE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "lightblue", "", .02F, 0.55F, .05F, "ice", 0.002F, 0.025F)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.GLOWSTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "dark", .017F, .24F, .06F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "yellow", "darkest", .017F, .24F, .06F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "yellow", "", .025F, .08F, .06F, "lightning", 0.002F, 0.02F)}, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.SOUL_SAND, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "dark", .017F, .12F, .017F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "brown", "", .017F, .12F, .017F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "red", "darkest", .022F, 1.06F, .08F)}, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.NETHER_BRICK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "darkest", .022F, 1.08F, .03F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "dark", .022F, 1.08F, .03F, 1)}, 0.4F);
        this.addCrucibleRecipeForBlock(Blocks.REDSTONE_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "", .023F, 0.92F, .06F, "lightning", 0.001F, 0.01F, 2)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.LAPIS_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "blue", "", .02F, 0.92F, .10F, 1)}, 1.0F);
        this.addCrucibleRecipeForBlock(Blocks.OBSIDIAN, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "gray", "darkest", .023F, 5.05F, .07F, "earth", 0.001F, 0.02F, 4)}, 1.0F);
		this.addCrucibleRecipeForBlock(Blocks.PRISMARINE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "cyan", "dark", .026F, 1.08F, .08F, 1)}, 1.0F);
		this.addCrucibleRecipeForBlock(Blocks.PURPUR_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "magenta", "dark", .026F, 1.08F, .10F)}, 1.0F);
        
        //Items
        this.addCrucibleRecipeForItem(Items.ROTTEN_FLESH, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "dark", .013F, .08F, .03F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.FLINT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "", .017F, .08F, .03F),
																	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "light", .017F, .08F, .03F, "earth", 0.001F, 0.005F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.BRICK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "dark", .022F, 0.44F, .02F, "earth", 0.001F, 0.005F)}, 0.2F);
        this.addCrucibleRecipeForItem(Items.BONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .017F, 1.08F, .07F),
																   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .017F, 1.08F, .07F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.GUNPOWDER, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", .02F, 0.44F, .04F),
                                                                        ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", .02F, 0.44F, .04F, "fire", 0.001F, 0.01F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.IRON_INGOT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .023F, 1.08F, .06F, 2),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .023F, 1.08F, .06F, 2)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.QUARTZ, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "lightest", .023F, .24F, .08F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.FIRE_CHARGE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "dark", .022F, 0.55F, .08F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "orange", "", .022F, 0.55F, .08F, "fire", 0.001F, 0.02F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.SLIME_BALL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "green", "light", .017F, 3.4F, .06F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "lightest", .017F, 3.4F, .06F)}, 0.8F);
        this.addCrucibleRecipeForItem(Items.ENDER_PEARL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "green", "dark", .017F, .24F, .06F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 16), "purple", "", .02F, 1.08F, .06F, "spatial", 0.002F, 0.02F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.GOLD_INGOT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "light", .017F, .12F, .09F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "lightest", .017F, .12F, .09F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.BLAZE_ROD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "", .017F, 0.55F, .06F),
																		ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "orange", "light", .017F, 0.55F, .06F),
																	    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "yellow", "", .023F, 0.44F, .06F, "fire", 0.002F, 0.02F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.ENDER_EYE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "light", .03F, 0.44F, .10F),
																		ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "", .03F, 0.44F, .06F, "spatial", 0.002F, 0.025F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.DIAMOND, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", .03F, 6.7F, .037F, 3),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "light", .03F, 6.7F, .037F, 3)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.EMERALD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "", .025F, 8F, .05F, 2),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "light", .025F, 8F, .05F, 2)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.NETHER_STAR, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "lightest", .05F, 10F, .15F, 3),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "light", .05F, 10F, .15F, 3)}, 2.0F);
        this.addCrucibleRecipeForItem(Items.PRISMARINE_SHARD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lightblue", "light", .03F, 0.44F, .03F),
                                                                               ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "lightest", .03F, 0.44F, .03F, "ice", 0.001F, 0.02F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.PRISMARINE_CRYSTALS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "cyan", "lightest", .035F, 0.88F, .06F, "ice", 0.002F, 0.025F)}, 1.0F);
    }

    public void addExternalCrucibleRecipes()
    {
        NonNullList<ItemStack> results;

        //Steel
        results = OreDictionary.getOres("ingotSteel");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .025F, 1.50F, .05F, 2),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .025F, 1.50F, .05F, 2)}, 0.7F);
        }

        //Copper
        results = OreDictionary.getOres("ingotCopper");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "dark", .020F, 1.0F, .06F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "orange", "darkest", .020F, 1.0F, .06F, 1)}, 0.5F);
        }

        //Aluminum
        results = OreDictionary.getOres("ingotAluminum");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .020F, 0.50F, .05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .020F, 0.50F, .05F, 1)}, 0.5F);
        }

        //Silver
        results = OreDictionary.getOres("ingotSilver");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .022F, 1.0F, .07F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lightblue", "lightest", .022F, 1.0F, .07F, 1)}, 0.7F);
        }

        //Electrum
        results = OreDictionary.getOres("ingotElectrum");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "lightest", .023F, 1.08F, .06F, "lightning", 0.001F, 0.01F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "light", .023F, 1.08F, .06F, "lightning", 0.001F, 0.01F, 1)}, 0.7F);
        }

        //Brass
        results = OreDictionary.getOres("ingotBrass");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "", .023F, 1.50F, .05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "dark", .023F, 1.50F, .05F, 1)}, 0.5F);
        }

        //Titanium
        results = OreDictionary.getOres("ingotTitanium");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lightblue", "lightest", .025F, 5F, .03F, 3),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .025F, 5F, .03F, 3)}, 0.7F);
        }

        //Lead
        results = OreDictionary.getOres("ingotLead");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", .020F, 2F, .07F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", .020F, 2F, .07F, 1)}, 0.7F);
        }

        //Zinc
        results = OreDictionary.getOres("ingotZinc");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .020F, 0.50F, .05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .020F, 0.50F, .05F, 1)}, 0.5F);
        }

        //Platinum
        results = OreDictionary.getOres("ingotPlatinum");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "lightest", .024F, 1F, .07F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "lightest", .024F, 1F, .07F, 1)}, 0.7F);
        }

        //Tungsten
        results = OreDictionary.getOres("ingotTungsten");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", .025F, 5F, .06F, 3),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "darkest", .025F, 5F, .06F, 3)}, 0.7F);
        }

        //Nickel
        results = OreDictionary.getOres("ingotNickel");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "lightest", .020F, 0.50F, .05F, 1),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "lightest", .020F, 0.50F, .05F, 1)}, 0.5F);
        }

        //Ruby
        results = OreDictionary.getOres("gemRuby");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "", .020F, .12F, .07F),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "light", .020F, .12F, .07F, "fire", 0.001F, 0.01F)}, 1.0F);
        }

        //Sapphire
        results = OreDictionary.getOres("gemSapphire");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "", .020F, .12F, .07F),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "blue", "light", .020F, .12F, .07F, "ice", 0.001F, 0.01F)}, 1.0F);
        }

        //Amethyst
        results = OreDictionary.getOres("gemAmethyst");
        for(ItemStack result : results)
        {
            this.addCrucibleRecipeForItem(result.getItem(), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "", .020F, .12F, .07F),
                                                                             ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "purple", "light", .020F, .12F, .07F, "spatial", 0.001F, 0.01F)}, 1.0F);
        }

        //Tinker's Construct Materials
        if(Loader.isModLoaded("tconstruct")) {
            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "materials")), 1, 9), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "", .010F, 2.0F, .07F),
                                                                                                                                                                                                 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "dark", .010F, 2.0F, .07F)}, 0.6F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "materials")), 1, 10), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", .020F, 1.0F, .07F),
                                                                                                                                                                                                  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "dark", .020F, 1.0F, .07F)}, 0.6F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "materials")), 1, 11), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "", .015F, 0.5F, .05F),
                                                                                                                                                                                                  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "orange", "", .015F, 0.5F, .05F, "fire", 0.001F, 0.01F)}, 0.6F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 0), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "blue", "", .025F, 5.0F, .07F, 4),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "blue", "dark", .025F, 5.0F, .07F, 4)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 1), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "red", "", .024F, 5.2F, .07F, 4),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "red", "dark", .024F, 5.2F, .07F, 4)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 2), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "", .033F, 5.1F, .04F, 4),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "purple", "dark", .033F, 5.1F, .04F, 4)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 3), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "pink", "", .03F, 3F, .07F, 3),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "pink", "light", .03F, 3F, .07F, 3)}, 1F);

            this.addCrucibleRecipe(new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("tconstruct", "ingots")), 1, 4), new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "pink", "", .025F, 1.25F, .09F, 2),
                                                                                                                                                                                              ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "pink", "dark", .025F, 1.25F, .09F, 2)}, 1F);
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
        System.out.println("Adding Crucible Recipe for: " + input);
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
        ItemStack[] results = entry.getValue();
        if(results.length == 0) return null;

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

        if(expEntry != null)
            experience = expEntry.getValue()/results.length;

        for(ItemStack result : results)
        {
            ItemBit.setCrucibleExperience(result, experience);
        }

        return results;
    }

    private boolean compareItemsInStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }

    public HashMap<ItemStack, ItemStack[]> getBreakDownList()
    {
        return this.breakDownList;
    }

    public float getBreakDownExperience(ItemStack itemstack)
    {
        return ItemBit.getCrucibleExperience(itemstack);
    }
}
