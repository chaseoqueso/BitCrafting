package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.gui.GUIHandler;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitDyeTable;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ServerProxy {
	
	public void registerRenders()
	{
		
	}
	
	public void registerNetworkStuff()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(BitCraftingMod.instance, new GUIHandler());
	}
	
	public void registerTileEntities()
	{
		GameRegistry.registerTileEntity(TileEntityBitChest.class, new ResourceLocation(BitCraftingMod.MODID, ":TileEntityBitChest"));
		GameRegistry.registerTileEntity(TileEntityBitCrucible.class, new ResourceLocation(BitCraftingMod.MODID, ":TileEntityBitCrucible"));
		GameRegistry.registerTileEntity(TileEntityBitForge.class, new ResourceLocation(BitCraftingMod.MODID, ":TileEntityBitForge"));
		GameRegistry.registerTileEntity(TileEntityBitDyeTable.class, new ResourceLocation(BitCraftingMod.MODID, ":TileEntityBitDyeTable"));
		GameRegistry.registerTileEntity(TileEntityBitFusionTable.class, new ResourceLocation(BitCraftingMod.MODID, ":TileEntityBitFusionTable"));
	}
	
}
