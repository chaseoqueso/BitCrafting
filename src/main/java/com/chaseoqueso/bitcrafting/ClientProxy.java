package com.chaseoqueso.bitcrafting;

import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends ServerProxy {
	
	@Override
	public void registerRenderThings()
	{
		//MinecraftForgeClient.registerItemRenderer(BitCraftingMod.itemBitSword, (IItemRenderer) new CustomItemRenderer());
	}

}
