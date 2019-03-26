package com.chaseoqueso.bitcrafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.chaseoqueso.bitcrafting.items.ItemBit;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CrucibleRecipes {
    private static final CrucibleRecipes crucibleBase = new CrucibleRecipes();
    /** The list of breakdown results. */
    private Map breakDownList = new HashMap();
    private Map experienceList = new HashMap();

    public static CrucibleRecipes instance()
    {
        return crucibleBase;
    }

    private CrucibleRecipes()
    {
    	//Blocks
        this.addCrucibleRecipeForBlock(Blocks.snow, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "gray", "lightest", .04F, .25F, .10F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.planks, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "light", .05F, .71F, .18F)}, 0.1F);
        this.addCrucibleRecipeForBlock(Blocks.glass, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemClearBit, 32), "gray", "lightest", .07F, .25F, .16F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.cactus, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "green", "", .07F, .71F, .10F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.cobblestone, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "gray", "dark", .06F, 1.65F, .05F)}, 0.2F);
        this.addCrucibleRecipeForBlock(Blocks.sandstone, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "lightest", .06F, 2.76F, .08F)}, 0.3F);
        this.addCrucibleRecipeForBlock(Blocks.ice, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "lightblue", "", .06F, 1.65F, .24F)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.glowstone, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 28), "yellow", "dark", .05F, .71F, .18F),
        																  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 4), "yellow", "", .075F, .25F, .27F)
        																  }, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.soul_sand, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 28), "brown", "dark", .05F, .37F, .05F),
        																  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 4), "red", "darkest", .065F, 3.19F, .24F)
        																  }, 0.5F);
        this.addCrucibleRecipeForBlock(Blocks.redstone_block, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "red", "", .07F, 2.76F, .22F)}, 0.6F);
        this.addCrucibleRecipeForBlock(Blocks.lapis_block, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "blue", "", .06F, 2.76F, .30F)}, 1.0F);
        this.addCrucibleRecipeForBlock(Blocks.obsidian, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "gray", "darkest", .07F, 15.15F, .30F)}, 1.0F);
        
        //Items
        this.addCrucibleRecipeForItem(Items.rotten_flesh, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "red", "dark", .04F, .25F, .10F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.flint, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "gray", "", .05F, .25F, .10F)}, 0.1F);
        this.addCrucibleRecipeForItem(Items.brick, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "red", "dark", .065F, 1.32F, .06F)}, 0.2F);
        this.addCrucibleRecipeForItem(Items.bone, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "gray", "light", .05F, 3.19F, .22F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.netherbrick, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "red", "darkest", .065F, 3.19F, .10F)}, 0.4F);
        this.addCrucibleRecipeForItem(Items.iron_ingot, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "gray", "light", .07F, 3.19F, .17F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.quartz, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 16), "red", "lightest", .07F, .71F, .24F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.fire_charge, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "orange", "dark", .065F, 1.65F, .24F)}, 0.7F);
        this.addCrucibleRecipeForItem(Items.slime_ball, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "green", "light", .05F, 10.10F, .18F)}, 0.8F);
        this.addCrucibleRecipeForItem(Items.ender_pearl, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 28), "green", "dark", .05F, .71F, .18F),
        																  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 4), "purple", "dark", .06F, 3.19F, .27F)
        																  }, 0.7F);
        this.addCrucibleRecipeForItem(Items.gold_ingot, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "yellow", "light", .05F, .37F, .27F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.blaze_rod, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 28), "orange", "", .05F, 1.65F, .18F),
																	    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 4), "yellow", "", .07F, 1.32F, .30F)
																	    }, 1.0F);
        this.addCrucibleRecipeForItem(Items.ender_eye, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "lime", "light", .09F, 1.32F, .30F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.diamond, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "cyan", "", .09F, 20.21F, .11F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.emerald, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "lime", "", .1F, 10.10F, .24F)}, 1.0F);
        this.addCrucibleRecipeForItem(Items.nether_star, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "purple", "lightest", .1F, 20.21F, .30F)}, 2.0F);
        
        //Swords
        this.addCrucibleRecipeForItem(Items.wooden_sword, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 28), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 29), "brown", "dark", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "brown", "darkest", .05F, .71F, .18F)
																		   }, 0.2F);
        this.addCrucibleRecipeForItem(Items.stone_sword, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 7), "brown", "", .05F, .71F, .18F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "gray", "dark", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "darkest", .06F, 1.65F, .05F)
																		  }, 0.4F);
        this.addCrucibleRecipeForItem(Items.iron_sword, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 7), "brown", "", .05F, .71F, .18F),
																		 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "lightest", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "gray", "light", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "", .07F, 3.19F, .17F),
																		 }, 1.4F);
        this.addCrucibleRecipeForItem(Items.golden_sword, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 7), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "yellow", "light", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "yellow", "", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "yellow", "dark", .05F, .37F, .27F),
																		   }, 2.0F);
        this.addCrucibleRecipeForItem(Items.diamond_sword, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 7), "brown", "", .05F, .71F, .18F),
																			ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "cyan", "", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "cyan", "dark", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "cyan", "darkest", .09F, 20.21F, .11F)
        																	}, 2.0F);
        
        //Axes
        this.addCrucibleRecipeForItem(Items.wooden_axe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																		   }, 0.2F);
		this.addCrucibleRecipeForItem(Items.stone_axe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "gray", "dark", .06F, 1.65F, .05F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "darkest", .06F, 1.65F, .05F)
																		  }, 0.4F);
		this.addCrucibleRecipeForItem(Items.iron_axe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "lightest", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "gray", "light", .07F, 3.19F, .17F),
																		 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "", .07F, 3.19F, .17F),
																		 }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_axe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "yellow", "light", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "yellow", "", .05F, .37F, .27F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "yellow", "dark", .05F, .37F, .27F),
																		   }, 2.0F);
        this.addCrucibleRecipeForItem(Items.diamond_axe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																			ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "cyan", "", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "cyan", "dark", .09F, 20.21F, .11F),
																			ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "cyan", "darkest", .09F, 20.21F, .11F)
        																	}, 2.0F);
        
        //Hoes
        this.addCrucibleRecipeForItem(Items.wooden_hoe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "", .05F, .71F, .18F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																	     }, 0.2F);
		this.addCrucibleRecipeForItem(Items.stone_hoe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																	    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "", .06F, 1.65F, .05F),
																	    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "dark", .06F, 1.65F, .05F),
																	    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "darkest", .06F, 1.65F, .05F)
																	    }, 0.4F);
		this.addCrucibleRecipeForItem(Items.iron_hoe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																	   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "lightest", .07F, 3.19F, .17F),
																	   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "light", .07F, 3.19F, .17F),
																	   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "gray", "", .07F, 3.19F, .17F),
																	   }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_hoe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "yellow", "light", .05F, .37F, .27F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "yellow", "", .05F, .37F, .27F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "yellow", "dark", .05F, .37F, .27F),
																	     }, 2.0F);
        this.addCrucibleRecipeForItem(Items.diamond_hoe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "cyan", "", .09F, 20.21F, .11F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "cyan", "dark", .09F, 20.21F, .11F),
																		  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 25), "cyan", "darkest", .09F, 20.21F, .11F)
        																  }, 2.0F);
        
        //Pickaxes
        this.addCrucibleRecipeForItem(Items.wooden_pickaxe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "", .05F, .71F, .18F),
																	     	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																	     	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																	     	}, 0.2F);
		this.addCrucibleRecipeForItem(Items.stone_pickaxe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "", .06F, 1.65F, .05F),
																		    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "gray", "dark", .06F, 1.65F, .05F),
																		    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "darkest", .06F, 1.65F, .05F)
																		    }, 0.4F);
		this.addCrucibleRecipeForItem(Items.iron_pickaxe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "lightest", .07F, 3.19F, .17F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "gray", "light", .07F, 3.19F, .17F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "gray", "", .07F, 3.19F, .17F),
																		   }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_pickaxe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "yellow", "light", .05F, .37F, .27F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "yellow", "", .05F, .37F, .27F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "yellow", "dark", .05F, .37F, .27F),
																		     }, 2.0F);
		this.addCrucibleRecipeForItem(Items.diamond_pickaxe, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 15), "brown", "", .05F, .71F, .18F),
																			  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "cyan", "", .09F, 20.21F, .11F),
																			  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 50), "cyan", "dark", .09F, 20.21F, .11F),
																			  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 35), "cyan", "darkest", .09F, 20.21F, .11F)
																			  }, 2.0F);
        
        //Shovels
        this.addCrucibleRecipeForItem(Items.wooden_shovel, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "dark", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 32), "brown", "darkest", .05F, .71F, .18F)
																	    	}, 0.2F);
		this.addCrucibleRecipeForItem(Items.stone_shovel, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "brown", "", .05F, .71F, .18F),
																		   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 5), "gray", "", .06F, 1.65F, .05F),
																	       ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "gray", "dark", .06F, 1.65F, .05F),
																	       ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "gray", "darkest", .06F, 1.65F, .05F)
																	       }, 0.4F);
		this.addCrucibleRecipeForItem(Items.iron_shovel, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "brown", "", .05F, .71F, .18F),
																	      ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 5), "gray", "lightest", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "gray", "light", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "gray", "", .07F, 3.19F, .17F),
																	      }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_shovel, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "brown", "", .05F, .71F, .18F),
																	        ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 5), "yellow", "light", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "yellow", "", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "yellow", "dark", .05F, .37F, .27F),
																	        }, 2.0F);
		this.addCrucibleRecipeForItem(Items.diamond_shovel, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 27), "brown", "", .05F, .71F, .18F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 5), "cyan", "", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "cyan", "dark", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 14), "cyan", "darkest", .09F, 20.21F, .11F)
																		     }, 2.0F);
		
		//Helmet
		this.addCrucibleRecipeForItem(Items.leather_helmet, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "brown", "light", .05F, .71F, .18F),
																	    	 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "brown", "", .05F, .71F, .18F),
																	    	 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "brown", "dark", .05F, .71F, .18F)
																	    	 }, 0.2F);
		this.addCrucibleRecipeForItem(Items.iron_helmet, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "gray", "lightest", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "gray", "light", .07F, 3.19F, .17F),
																	      ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "gray", "", .07F, 3.19F, .17F),
																	      }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_helmet, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "yellow", "light", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "yellow", "", .05F, .37F, .27F),
																	        ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "yellow", "dark", .05F, .37F, .27F),
																	        }, 2.0F);
		this.addCrucibleRecipeForItem(Items.diamond_helmet, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "cyan", "", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "cyan", "dark", .09F, 20.21F, .11F),
																		     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "cyan", "darkest", .09F, 20.21F, .11F)
																		     }, 2.0F);
		
		//Chestplate
		this.addCrucibleRecipeForItem(Items.leather_chestplate, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "brown", "light", .05F, .71F, .18F),
																	    	 	 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "brown", "", .05F, .71F, .18F),
																	    	 	  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "brown", "dark", .05F, .71F, .18F)
																	    	 	 }, 0.2F);
		this.addCrucibleRecipeForItem(Items.iron_chestplate, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "gray", "lightest", .07F, 3.19F, .17F),
																	      	  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "gray", "light", .07F, 3.19F, .17F),
																	      	  ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "gray", "", .07F, 3.19F, .17F),
																      		  }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_chestplate, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "yellow", "light", .05F, .37F, .27F),
																	        	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "yellow", "", .05F, .37F, .27F),
																	        	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "yellow", "dark", .05F, .37F, .27F),
																	        	}, 2.0F);
		this.addCrucibleRecipeForItem(Items.diamond_chestplate, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "cyan", "", .09F, 20.21F, .11F),
																		     	 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "cyan", "dark", .09F, 20.21F, .11F),
																		     	 ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "cyan", "darkest", .09F, 20.21F, .11F)
																		     	 }, 3.0F);
		
		//Leggings
		this.addCrucibleRecipeForItem(Items.leather_leggings, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "brown", "light", .05F, .71F, .18F),
																	    	   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "brown", "", .05F, .71F, .18F),
																	    	   ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "brown", "dark", .05F, .71F, .18F)
																	    	   }, 0.2F);
		this.addCrucibleRecipeForItem(Items.iron_leggings, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "gray", "lightest", .07F, 3.19F, .17F),
																	      	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "gray", "light", .07F, 3.19F, .17F),
																	      	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "gray", "", .07F, 3.19F, .17F),
																      		}, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_leggings, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "yellow", "light", .05F, .37F, .27F),
																	          ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "yellow", "", .05F, .37F, .27F),
																	          ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "yellow", "dark", .05F, .37F, .27F),
																	          }, 2.0F);
		this.addCrucibleRecipeForItem(Items.diamond_leggings, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "cyan", "", .09F, 20.21F, .11F),
																		       ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "cyan", "dark", .09F, 20.21F, .11F),
																		       ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 64), "cyan", "darkest", .09F, 20.21F, .11F)
																		       }, 3.0F);
		
		//Boots
		this.addCrucibleRecipeForItem(Items.leather_boots, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "brown", "light", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "brown", "", .05F, .71F, .18F),
																	    	ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "brown", "dark", .05F, .71F, .18F)
																	    	}, 0.2F);
		this.addCrucibleRecipeForItem(Items.iron_boots, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "gray", "lightest", .07F, 3.19F, .17F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "gray", "light", .07F, 3.19F, .17F),
																	     ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "gray", "", .07F, 3.19F, .17F),
																	     }, 1.4F);
		this.addCrucibleRecipeForItem(Items.golden_boots, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "yellow", "light", .05F, .37F, .27F),
																	       ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "yellow", "", .05F, .37F, .27F),
																	       ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "yellow", "dark", .05F, .37F, .27F),
																	       }, 2.0F);
		this.addCrucibleRecipeForItem(Items.diamond_boots, new ItemStack[] {ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "cyan", "", .09F, 20.21F, .11F),
																		    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "cyan", "dark", .09F, 20.21F, .11F),
																		    ItemBit.setBit(new ItemStack(BitCraftingMod.itemBit, 40), "cyan", "darkest", .09F, 20.21F, .11F)
																		    }, 2.0F);
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
        Iterator iterator = this.breakDownList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext())
            {
                return null;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.compareItemStacks(itemstack, (ItemStack)entry.getKey()));

        return (ItemStack[])entry.getValue();
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

    public Map getBreakDownList()
    {
        return this.breakDownList;
    }

    public float getBreakDownExperience(ItemStack itemstack)
    {
        float ret = itemstack.getItem().getSmeltingExperience(itemstack);
        if (ret != -1) return ret;

        Iterator iterator = this.experienceList.entrySet().iterator();
        Entry entry;

        do
        {
            if (!iterator.hasNext()) 
            {
                return 0.0F;
            }

            entry = (Entry)iterator.next();
        }
        while (!this.compareItemStackToArray(itemstack, (ItemStack[])entry.getKey()));

        return ((Float)entry.getValue()).floatValue();
    }
}
