package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.gui.GUIHandler;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = BitCraftingMod.MODID, name = "BitCrafting", version = "1.2")
public class BitCraftingMod {
	
	@SidedProxy(clientSide = "com.chaseoqueso.bitcrafting.ClientProxy", serverSide = "com.chaseoqueso.bitcrafting.ServerProxy")
	public static ServerProxy proxy;
	
	public static final String MODID = "bcm";
	
	@Instance(MODID)
	public static BitCraftingMod instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		BitCraftingItems.init();
		BitCraftingItems.register();

		BitCraftingBlocks.init();
		BitCraftingBlocks.register();
		
		GameRegistry.registerWorldGenerator(new BitOreGeneration(), 1);
		
		proxy.registerRenders();
		proxy.registerTileEntities();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		RecipeHelper.addShaped(new ItemStack(BitCraftingBlocks.blockBitCrucible), 3, 3, new Object[] {"ICI", "CFC", "ICI", 'I', Items.IRON_INGOT, 'C', Blocks.COBBLESTONE, 'F', Blocks.FURNACE});
		RecipeHelper.addShaped(new ItemStack(BitCraftingBlocks.blockBitForge), 3, 3, new Object[] {"IBI", "CTC", "ICI", 'I', Items.IRON_INGOT, 'C', Blocks.COBBLESTONE, 'T', Blocks.CRAFTING_TABLE, 'B', Blocks.IRON_BARS});
		RecipeHelper.addShaped(new ItemStack(BitCraftingBlocks.blockBitDyeTable), 3, 3, new Object[] {"GSG", "CTC", "GCG", 'G', Items.GOLD_INGOT, 'C', Blocks.COBBLESTONE, 'T', Blocks.CRAFTING_TABLE, 'S', Blocks.STONE_SLAB});
		RecipeHelper.addShaped(new ItemStack(BitCraftingBlocks.blockBitFusionTable), 3, 3, new Object[] {"DGD", "CTC", "DCD", 'D', Items.DIAMOND, 'C', Blocks.COBBLESTONE, 'T', Blocks.CRAFTING_TABLE, 'G', Blocks.GLOWSTONE});
		RecipeHelper.addShaped(new ItemStack(BitCraftingBlocks.blockBitChest), 3, 3, new Object[] {"BBB", "BCB", "BBB", 'B', BitCraftingItems.itemBit, 'C', Blocks.CHEST});
		RecipeHelper.addShapeless(new ItemStack(Items.NETHERBRICK, 4), Blocks.NETHER_BRICK);

		NetworkRegistry.INSTANCE.registerGuiHandler(BitCraftingMod.instance, new GUIHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		
	}
	
	public static CreativeTabs tabBitCraftingMod = new CreativeTabs("tabBitCraftingMod") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(BitCraftingBlocks.blockBitForge));
		}
	};
		
}
