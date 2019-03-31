package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.alt_vanilla.BitChestRenderer;
import com.chaseoqueso.bitcrafting.alt_vanilla.ItemRenderBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends ServerProxy {
	
	@Override
	public void registerRenderThings()
	{
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBitChest.class, new BitChestRenderer());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BitCraftingMod.blockBitChest), new ItemRenderBitChest());
	}

}
