package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.rendering.BitChestRenderer;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelDynBucket;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientProxy extends ServerProxy {

	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent event) {

		setRender(BitCraftingBlocks.BLOCKS.blockBitCrucible);
		setRender(BitCraftingBlocks.BLOCKS.blockBitForge);
		setRender(BitCraftingBlocks.BLOCKS.blockBitDyeTable);
		setRender(BitCraftingBlocks.BLOCKS.blockBitFusionTable);
		setRender(BitCraftingBlocks.BLOCKS.blockFireOre);
		setRender(BitCraftingBlocks.BLOCKS.blockEarthOre);
		setRender(BitCraftingBlocks.BLOCKS.blockLightningOre);
		setRender(BitCraftingBlocks.BLOCKS.blockIceOre);
		setRender(BitCraftingBlocks.BLOCKS.blockSpatialOre);
		setRender(BitCraftingBlocks.BLOCKS.blockBitChest);

		setRender(BitCraftingItems.ITEMS.itemBit);
		setRender(BitCraftingItems.ITEMS.itemClearBit);
		setRender(BitCraftingItems.ITEMS.itemBitSword);

		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBitChest.class, new BitChestRenderer());
		ServerProxy.registerTileEntities();
	}

	public static void setRender(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}

	public static void setRender(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

}
