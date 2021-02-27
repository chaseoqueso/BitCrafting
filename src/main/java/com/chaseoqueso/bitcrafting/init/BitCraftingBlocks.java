package com.chaseoqueso.bitcrafting.init;

import net.minecraft.block.Block;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.blocks.BlockBitChest;
import com.chaseoqueso.bitcrafting.blocks.BlockBitCrucible;
import com.chaseoqueso.bitcrafting.blocks.BlockBitDyeTable;
import com.chaseoqueso.bitcrafting.blocks.BlockBitForge;
import com.chaseoqueso.bitcrafting.blocks.BlockBitFusionTable;
import com.chaseoqueso.bitcrafting.blocks.BlockBitOre;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class BitCraftingBlocks {

	public static Block blockBitCrucible;
	public static Block blockBitCrucibleActive;
	public static Block blockBitForge;
	public static Block blockBitDyeTable;
	public static Block blockBitFusionTable;
	public static Block blockFireOre;
	public static Block blockEarthOre;
	public static Block blockLightningOre;
	public static Block blockIceOre;
	public static Block blockSpatialOre;
	public static Block blockBitChest;

    public static void init()
    {
		blockBitCrucible = new BlockBitCrucible(Material.IRON, false).setUnlocalizedName("BlockBitCrucible");
		blockBitCrucibleActive = new BlockBitCrucible(Material.IRON, true).setUnlocalizedName("BlockBitCrucibleActive");
		blockBitForge = new BlockBitForge(Material.IRON).setUnlocalizedName("BlockBitForge");
		blockBitDyeTable = new BlockBitDyeTable(Material.IRON).setUnlocalizedName("BlockBitDyeTable");
		blockBitFusionTable = new BlockBitFusionTable(Material.IRON).setUnlocalizedName("BlockBitFusionTable");
		blockFireOre = new BlockBitOre().setUnlocalizedName("BlockFireOre");
		blockEarthOre = new BlockBitOre().setUnlocalizedName("BlockEarthOre");
		blockLightningOre = new BlockBitOre().setUnlocalizedName("BlockLightningOre");
		blockIceOre = new BlockBitOre().setUnlocalizedName("BlockIceOre");
		blockSpatialOre = new BlockBitOre().setUnlocalizedName("BlockSpatialOre");
		blockBitChest = new BlockBitChest(Material.WOOD).setUnlocalizedName("BlockBitChest");
    }

    public static void register()
    {
		ForgeRegistries.BLOCKS.register(blockBitCrucible);
		ForgeRegistries.BLOCKS.register(blockBitCrucibleActive);
		ForgeRegistries.BLOCKS.register(blockBitForge);
		ForgeRegistries.BLOCKS.register(blockBitDyeTable);
		ForgeRegistries.BLOCKS.register(blockBitFusionTable);
		ForgeRegistries.BLOCKS.register(blockFireOre);
		ForgeRegistries.BLOCKS.register(blockEarthOre);
		ForgeRegistries.BLOCKS.register(blockLightningOre);
		ForgeRegistries.BLOCKS.register(blockIceOre);
		ForgeRegistries.BLOCKS.register(blockSpatialOre);
		ForgeRegistries.BLOCKS.register(blockBitChest);
    }

    public static void registerRenders()
    {
        registerRender(blockBitCrucible);
        registerRender(blockBitCrucibleActive);
        registerRender(blockBitForge);
        registerRender(blockBitDyeTable);
        registerRender(blockBitFusionTable);
        registerRender(blockFireOre);
        registerRender(blockEarthOre);
        registerRender(blockLightningOre);
        registerRender(blockIceOre);
        registerRender(blockSpatialOre);
        registerRender(blockBitChest);
    }

    public static void registerRender(Block block)
    {
        Item item = Item.getItemFromBlock(block);
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(BitCraftingMod.MODID + ":" + item.getUnlocalizedName().substring(5), "inventory"));
    }
}
