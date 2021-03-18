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

public class CrucibleRecipes {
    private static final CrucibleRecipes crucibleBase = new CrucibleRecipes();
    /** The list of breakdown results. */
    private HashMap<ItemStack, ItemStack[]> breakDownList = new HashMap<ItemStack, ItemStack[]>();
    private HashMap<ItemStack[], Float> experienceList = new HashMap<ItemStack[], Float>();

    public static CrucibleRecipes instance()
    {
        return crucibleBase;
    }

    private CrucibleRecipes()
    {
    	//Blocks
        this.addCrucibleRecipeForBlock(Blocks.SNOW, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "gray", "lightest", .013F, .08F, .03F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.PLANKS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "light", .017F, .24F, .06F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "light", .017F, .24F, .06F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.GLASS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemClearBit, 64), "gray", "lightest", .023F, .08F, .05F),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemClearBit, 32), "gray", "lightest", .023F, .08F, .05F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.CACTUS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "green", "", .023F, .24F, .03F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "", .023F, .24F, .03F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.COBBLESTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "dark", .02F, 0.55F, .017F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "dark", .02F, 0.55F, .017F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.SANDSTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "lightest", .02F, 0.92F, .027F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "lightest", .02F, 0.92F, .027F)}, 0.3F);
        this.addCrucibleRecipeForBlock(Blocks.ICE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "lightblue", "", .02F, 0.55F, .08F)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.GLOWSTONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "dark", .017F, .24F, .06F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "yellow", "dark", .017F, .24F, .06F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "yellow", "", .025F, .08F, .09F)
        																  }, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.SOUL_SAND, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "dark", .017F, .12F, .017F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "brown", "dark", .017F, .12F, .017F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "red", "darkest", .022F, 1.06F, .08F)
        																  }, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.REDSTONE_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "", .023F, 0.92F, .07F)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.LAPIS_BLOCK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "blue", "", .02F, 0.92F, .10F)}, 1.0F);
        this.addCrucibleRecipeForBlock(Blocks.OBSIDIAN, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "gray", "darkest", .023F, 5.05F, .10F)}, 1.0F);
        
        //Items
        this.addCrucibleRecipeForItem(Items.ROTTEN_FLESH, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "dark", .013F, .08F, .03F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.FLINT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "", .017F, .08F, .03F),
																	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "", .017F, .08F, .03F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.BRICK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "dark", .022F, 0.44F, .02F)}, 0.2F);
        this.addCrucibleRecipeForItem(Items.BONE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .017F, 1.08F, .07F),
																   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "light", .017F, 1.08F, .07F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.NETHERBRICK, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "darkest", .022F, 1.08F, .03F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.IRON_INGOT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .023F, 1.08F, .06F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "gray", "light", .023F, 1.08F, .06F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.QUARTZ, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 48), "red", "lightest", .023F, .24F, .08F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.FIRE_CHARGE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "dark", .022F, 0.55F, .08F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "orange", "dark", .022F, 0.55F, .08F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.SLIME_BALL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "green", "light", .017F, 3.4F, .06F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "green", "light", .017F, 3.4F, .06F)}, 0.8F);
        this.addCrucibleRecipeForItem(Items.ENDER_PEARL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "green", "dark", .017F, .24F, .06F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "green", "dark", .017F, .24F, .06F),
        																  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "purple", "dark", .02F, 1.08F, .09F)
        																  }, 0.7F);
        this.addCrucibleRecipeForItem(Items.GOLD_INGOT, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "light", .017F, .12F, .09F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "yellow", "light", .017F, .12F, .09F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.BLAZE_ROD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "orange", "", .017F, 0.55F, .06F),
																		ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 20), "orange", "", .017F, 0.55F, .06F),
																	    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 12), "yellow", "", .023F, 0.44F, .10F)
																	    }, 1.0F);
        this.addCrucibleRecipeForItem(Items.ENDER_EYE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "light", .03F, 0.44F, .10F),
																		ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "light", .03F, 0.44F, .10F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.DIAMOND, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", .03F, 6.7F, .037F),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "cyan", "", .03F, 6.7F, .037F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.EMERALD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "lime", "", .033F, 3.4F, .08F),
																	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "lime", "", .033F, 3.4F, .08F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.NETHER_STAR, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "purple", "lightest", .1F, 20F, .30F)}, 2.0F);

		/* This section seems like a pain to rebalance so I'm not going to do that right now
        //Swords
        this.addCrucibleRecipeForItem(Items.WOODEN_SWORD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 28), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 29), "brown", "dark", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "brown", "darkest", .05F, .71F, .18F)
																		   }, 0.2F);
        this.addCrucibleRecipeForItem(Items.STONE_SWORD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 7), "brown", "", .05F, .71F, .18F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "gray", "dark", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "darkest", .06F, 1.65F, .05F)
																		  }, 0.4F);
        this.addCrucibleRecipeForItem(Items.IRON_SWORD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 7), "brown", "", .05F, .71F, .18F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "lightest", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "gray", "light", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "", .07F, 3.19F, .17F),
																		 }, 1.4F);
        this.addCrucibleRecipeForItem(Items.GOLDEN_SWORD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 7), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "yellow", "light", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "yellow", "", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "yellow", "dark", .05F, .37F, .27F),
																		   }, 2.0F);
        this.addCrucibleRecipeForItem(Items.DIAMOND_SWORD, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 7), "brown", "", .05F, .71F, .18F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "cyan", "", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "cyan", "dark", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "cyan", "darkest", .09F, 20.21F, .11F)
        																	}, 2.0F);
        
        //Axes
        this.addCrucibleRecipeForItem(Items.WOODEN_AXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																		   }, 0.2F);
		this.addCrucibleRecipeForItem(Items.STONE_AXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "gray", "dark", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "darkest", .06F, 1.65F, .05F)
																		  }, 0.4F);
		this.addCrucibleRecipeForItem(Items.IRON_AXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "lightest", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "gray", "light", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "", .07F, 3.19F, .17F),
																		 }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_AXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "yellow", "light", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "yellow", "", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "yellow", "dark", .05F, .37F, .27F),
																		   }, 2.0F);
        this.addCrucibleRecipeForItem(Items.DIAMOND_AXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "cyan", "", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "cyan", "dark", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "cyan", "darkest", .09F, 20.21F, .11F)
        																	}, 2.0F);
        
        //Hoes
        this.addCrucibleRecipeForItem(Items.WOODEN_HOE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "", .05F, .71F, .18F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																	     }, 0.2F);
		this.addCrucibleRecipeForItem(Items.STONE_HOE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																	    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "", .06F, 1.65F, .05F),
																	    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "dark", .06F, 1.65F, .05F),
																	    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "darkest", .06F, 1.65F, .05F)
																	    }, 0.4F);
		this.addCrucibleRecipeForItem(Items.IRON_HOE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "lightest", .07F, 3.19F, .17F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "light", .07F, 3.19F, .17F),
																	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "gray", "", .07F, 3.19F, .17F),
																	   }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_HOE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "yellow", "light", .05F, .37F, .27F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "yellow", "", .05F, .37F, .27F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "yellow", "dark", .05F, .37F, .27F),
																	     }, 2.0F);
        this.addCrucibleRecipeForItem(Items.DIAMOND_HOE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "cyan", "", .09F, 20.21F, .11F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "cyan", "dark", .09F, 20.21F, .11F),
																		  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 25), "cyan", "darkest", .09F, 20.21F, .11F)
        																  }, 2.0F);
        
        //Pickaxes
        this.addCrucibleRecipeForItem(Items.WOODEN_PICKAXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "", .05F, .71F, .18F),
																	     	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																	     	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																	     	}, 0.2F);
		this.addCrucibleRecipeForItem(Items.STONE_PICKAXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "", .06F, 1.65F, .05F),
																		    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "gray", "dark", .06F, 1.65F, .05F),
																		    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "darkest", .06F, 1.65F, .05F)
																		    }, 0.4F);
		this.addCrucibleRecipeForItem(Items.IRON_PICKAXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "lightest", .07F, 3.19F, .17F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "gray", "light", .07F, 3.19F, .17F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "gray", "", .07F, 3.19F, .17F),
																		   }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_PICKAXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "yellow", "light", .05F, .37F, .27F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "yellow", "", .05F, .37F, .27F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "yellow", "dark", .05F, .37F, .27F),
																		     }, 2.0F);
		this.addCrucibleRecipeForItem(Items.DIAMOND_PICKAXE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 15), "brown", "", .05F, .71F, .18F),
																			  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "cyan", "", .09F, 20.21F, .11F),
																			  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 50), "cyan", "dark", .09F, 20.21F, .11F),
																			  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 35), "cyan", "darkest", .09F, 20.21F, .11F)
																			  }, 2.0F);
        
        //Shovels
        this.addCrucibleRecipeForItem(Items.WOODEN_SHOVEL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																	    	}, 0.2F);
		this.addCrucibleRecipeForItem(Items.STONE_SHOVEL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 5), "gray", "", .06F, 1.65F, .05F),
																	       ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "gray", "dark", .06F, 1.65F, .05F),
																	       ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "gray", "darkest", .06F, 1.65F, .05F)
																	       }, 0.4F);
		this.addCrucibleRecipeForItem(Items.IRON_SHOVEL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "brown", "", .05F, .71F, .18F),
																	      ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 5), "gray", "lightest", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "gray", "light", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "gray", "", .07F, 3.19F, .17F),
																	      }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_SHOVEL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "brown", "", .05F, .71F, .18F),
																	        ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 5), "yellow", "light", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "yellow", "", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "yellow", "dark", .05F, .37F, .27F),
																	        }, 2.0F);
		this.addCrucibleRecipeForItem(Items.DIAMOND_SHOVEL, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 27), "brown", "", .05F, .71F, .18F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 5), "cyan", "", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "cyan", "dark", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 14), "cyan", "darkest", .09F, 20.21F, .11F)
																		     }, 2.0F);
		
		//Helmet
		this.addCrucibleRecipeForItem(Items.LEATHER_HELMET, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "brown", "light", .05F, .71F, .18F),
																	    	 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "brown", "", .05F, .71F, .18F),
																	    	 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "brown", "dark", .05F, .71F, .18F)
																	    	 }, 0.2F);
		this.addCrucibleRecipeForItem(Items.IRON_HELMET, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "gray", "lightest", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "gray", "light", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "gray", "", .07F, 3.19F, .17F),
																	      }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_HELMET, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "yellow", "light", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "yellow", "", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "yellow", "dark", .05F, .37F, .27F),
																	        }, 2.0F);
		this.addCrucibleRecipeForItem(Items.DIAMOND_HELMET, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "cyan", "", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "cyan", "dark", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "cyan", "darkest", .09F, 20.21F, .11F)
																		     }, 2.0F);
		
		//Chestplate
		this.addCrucibleRecipeForItem(Items.LEATHER_CHESTPLATE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "light", .05F, .71F, .18F),
																	    	 	 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "", .05F, .71F, .18F),
																	    	 	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "dark", .05F, .71F, .18F)
																	    	 	 }, 0.2F);
		this.addCrucibleRecipeForItem(Items.IRON_CHESTPLATE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "lightest", .07F, 3.19F, .17F),
																	      	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .07F, 3.19F, .17F),
																	      	  ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "", .07F, 3.19F, .17F),
																      		  }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_CHESTPLATE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "light", .05F, .37F, .27F),
																	        	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "", .05F, .37F, .27F),
																	        	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "dark", .05F, .37F, .27F),
																	        	}, 2.0F);
		this.addCrucibleRecipeForItem(Items.DIAMOND_CHESTPLATE, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", .09F, 20.21F, .11F),
																		     	 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "dark", .09F, 20.21F, .11F),
																		     	 ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "darkest", .09F, 20.21F, .11F)
																		     	 }, 3.0F);
		
		//Leggings
		this.addCrucibleRecipeForItem(Items.LEATHER_LEGGINGS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "light", .05F, .71F, .18F),
																	    	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "", .05F, .71F, .18F),
																	    	   ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "brown", "dark", .05F, .71F, .18F)
																	    	   }, 0.2F);
		this.addCrucibleRecipeForItem(Items.IRON_LEGGINGS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "lightest", .07F, 3.19F, .17F),
																	      	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "light", .07F, 3.19F, .17F),
																	      	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "gray", "", .07F, 3.19F, .17F),
																      		}, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_LEGGINGS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "light", .05F, .37F, .27F),
																	          ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "", .05F, .37F, .27F),
																	          ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "yellow", "dark", .05F, .37F, .27F),
																	          }, 2.0F);
		this.addCrucibleRecipeForItem(Items.DIAMOND_LEGGINGS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "", .09F, 20.21F, .11F),
																		       ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "dark", .09F, 20.21F, .11F),
																		       ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 64), "cyan", "darkest", .09F, 20.21F, .11F)
																		       }, 3.0F);
		
		//Boots
		this.addCrucibleRecipeForItem(Items.LEATHER_BOOTS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "brown", "light", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "brown", "", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "brown", "dark", .05F, .71F, .18F)
																	    	}, 0.2F);
		this.addCrucibleRecipeForItem(Items.IRON_BOOTS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "gray", "lightest", .07F, 3.19F, .17F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "gray", "light", .07F, 3.19F, .17F),
																	     ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "gray", "", .07F, 3.19F, .17F),
																	     }, 1.4F);
		this.addCrucibleRecipeForItem(Items.GOLDEN_BOOTS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "yellow", "light", .05F, .37F, .27F),
																	       ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "yellow", "", .05F, .37F, .27F),
																	       ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "yellow", "dark", .05F, .37F, .27F),
																	       }, 2.0F);
		this.addCrucibleRecipeForItem(Items.DIAMOND_BOOTS, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "cyan", "", .09F, 20.21F, .11F),
																		    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "cyan", "dark", .09F, 20.21F, .11F),
																		    ItemBit.setBit(new ItemStack(BitCraftingItems.ITEMS.itemBit, 40), "cyan", "darkest", .09F, 20.21F, .11F)
																		    }, 2.0F);
		 */
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
        this.experienceList.put(itemstacks, Float.valueOf(experience));
    }

    /**
     * Returns the breakdown result of an item.
     */
    public ItemStack[] getBreakDownResult(ItemStack itemstack)
    {
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
        while (!this.compareItemStacks(itemstack, entry.getKey()));

        return entry.getValue();
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return stack2.getItem() == stack1.getItem() && (stack2.getItemDamage() == 32767 || stack2.getItemDamage() == stack1.getItemDamage());
    }

    private boolean compareItemStackToArray(ItemStack stack, ItemStack[] array)
    {
        boolean flag = false;
        
        for(ItemStack tempstack : array)
        {
        	if(ItemBit.bitsAreEqual(stack, tempstack))
        		flag = true;
        }
        
        return flag;
    }

    public HashMap<ItemStack, ItemStack[]> getBreakDownList()
    {
        return this.breakDownList;
    }

    public float getBreakDownExperience(ItemStack itemstack)
    {
        float ret = itemstack.getItem().getSmeltingExperience(itemstack);
        if (ret != -1) return ret;

        Iterator<Entry<ItemStack[], Float>> iterator = this.experienceList.entrySet().iterator();
        Entry<ItemStack[], Float> entry;

        do
        {
            if (!iterator.hasNext()) 
            {
                return 0.0F;
            }

            entry = (Entry<ItemStack[], Float>)iterator.next();
        }
        while (!this.compareItemStackToArray(itemstack, (ItemStack[])entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }
}
