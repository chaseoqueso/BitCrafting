package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.gui.GUIHandler;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;

import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.rendering.ModelBitSword;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = BitCraftingMod.MODID, name = "BitCrafting", version = "1.2")
public class BitCraftingMod {
	
	@SidedProxy(clientSide = "com.chaseoqueso.bitcrafting.ClientProxy", serverSide = "com.chaseoqueso.bitcrafting.ServerProxy")
	public static ServerProxy proxy;
	
	public static final String MODID = "bcm";

	public static CreativeTabs tabBitCraftingMod = new CreativeTabs("tabBitCraftingMod") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(BitCraftingBlocks.BLOCKS.blockBitForge));
		}
	};
	
	@Instance(MODID)
	public static BitCraftingMod instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModelLoaderRegistry.registerLoader(ModelBitSword.LoaderBitSword.INSTANCE);
		//GameRegistry.registerWorldGenerator(new BitOreGeneration(), 1);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(BitCraftingMod.instance, new GUIHandler());
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(BitCraftingItems.ITEMS.itemBitColorManager, BitCraftingItems.ITEMS.itemBit);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		
	}
		
}
