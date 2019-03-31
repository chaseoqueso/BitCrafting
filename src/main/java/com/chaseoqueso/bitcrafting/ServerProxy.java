package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.gui.GUIHandler;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitDyeTable;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class ServerProxy {
	
	public void registerRenderThings()
	{
		
	}
	
	public void registerNetworkStuff()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(BitCraftingMod.instance, new GUIHandler());
	}
	
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityBitChest.class, BitCraftingMod.MODID + "TileEntityBitChest");
		GameRegistry.registerTileEntity(TileEntityBitCrucible.class, BitCraftingMod.MODID + "TileEntityBitCrucible");
		GameRegistry.registerTileEntity(TileEntityBitForge.class, BitCraftingMod.MODID + "TileEntityBitForge");
		GameRegistry.registerTileEntity(TileEntityBitDyeTable.class, BitCraftingMod.MODID + "TileEntityBitDyeTable");
		GameRegistry.registerTileEntity(TileEntityBitFusionTable.class, BitCraftingMod.MODID + "TileEntityBitFusionTable");
	}
	
}
