package com.chaseoqueso.bitcrafting.gui;

import com.chaseoqueso.bitcrafting.container.ContainerBitCrucible;
import com.chaseoqueso.bitcrafting.container.ContainerBitDyeTable;
import com.chaseoqueso.bitcrafting.container.ContainerBitForge;
import com.chaseoqueso.bitcrafting.container.ContainerBitFusionTable;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitDyeTable;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GUIHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			TileEntityBitCrucible tileentity = (TileEntityBitCrucible) world.getTileEntity(x, y, z);
			return new ContainerBitCrucible(player.inventory, tileentity);
		} else if(ID == 1) {
			TileEntityBitForge tileentity = (TileEntityBitForge) world.getTileEntity(x, y, z);
			return new ContainerBitForge(player.inventory, tileentity);
		} else if(ID == 2) {
			TileEntityBitDyeTable tileentity = (TileEntityBitDyeTable) world.getTileEntity(x, y, z);
			return new ContainerBitDyeTable(player.inventory, tileentity);
		} else if(ID == 3) {
			TileEntityBitFusionTable tileentity = (TileEntityBitFusionTable) world.getTileEntity(x, y, z);
			return new ContainerBitFusionTable(player.inventory, tileentity);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		if(ID == 0)
		{
			TileEntityBitCrucible tileentity = (TileEntityBitCrucible) world.getTileEntity(x, y, z);
			return new GUIBitCrucible(player.inventory, tileentity);
		} else if(ID == 1) {
			TileEntityBitForge tileentity = (TileEntityBitForge) world.getTileEntity(x, y, z);
			return new GUIBitForge(player.inventory, tileentity);
		} else if(ID == 2) {
			TileEntityBitDyeTable tileentity = (TileEntityBitDyeTable) world.getTileEntity(x, y, z);
			return new GUIBitDyeTable(player.inventory, tileentity);
		} else if(ID == 3) {
			TileEntityBitFusionTable tileentity = (TileEntityBitFusionTable) world.getTileEntity(x, y, z);
			return new GUIBitFusionTable(player.inventory, tileentity);
		}
		return null;
	}
}
