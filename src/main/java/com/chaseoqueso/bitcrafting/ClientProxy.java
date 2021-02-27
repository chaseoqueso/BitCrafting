package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.alt_vanilla.BitChestRenderer;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends ServerProxy {
	
	@Override
	public void registerRenders()
	{
		BitCraftingItems.registerRenders();
		BitCraftingBlocks.registerRenders();

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBitChest.class, new BitChestRenderer());
		//MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(BitCraftingBlocks.blockBitChest), new ItemRenderBitChest());
	}

}
