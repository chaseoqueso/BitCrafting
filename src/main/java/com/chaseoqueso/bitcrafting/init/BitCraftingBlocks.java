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
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

@Mod.EventBusSubscriber
public class BitCraftingBlocks {

	@GameRegistry.ObjectHolder(value = BitCraftingMod.MODID)
	public static class BLOCKS {
		public static Block blockBitCrucible = new BlockBitCrucible(Material.IRON);
		public static Block blockBitForge = new BlockBitForge(Material.IRON);
		public static Block blockBitDyeTable = new BlockBitDyeTable(Material.IRON);
		public static Block blockBitFusionTable = new BlockBitFusionTable(Material.IRON);
		public static Block blockFireOre = new BlockBitOre().setUnlocalizedName("BlockFireOre").setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blockfireore"));
		public static Block blockEarthOre = new BlockBitOre().setUnlocalizedName("BlockEarthOre").setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blockearthore"));
		public static Block blockLightningOre = new BlockBitOre().setUnlocalizedName("BlockLightningOre").setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blocklightningore"));
		public static Block blockIceOre = new BlockBitOre().setUnlocalizedName("BlockIceOre").setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blockiceore"));
		public static Block blockSpatialOre = new BlockBitOre().setUnlocalizedName("BlockSpatialOre").setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blockspatialore"));
		public static Block blockBitChest = new BlockBitChest(Material.WOOD);
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {

		event.getRegistry().registerAll(BLOCKS.blockBitCrucible,
										BLOCKS.blockBitForge,
										BLOCKS.blockBitDyeTable,
										BLOCKS.blockBitFusionTable,
										BLOCKS.blockFireOre,
										BLOCKS.blockEarthOre,
										BLOCKS.blockLightningOre,
										BLOCKS.blockIceOre,
										BLOCKS.blockSpatialOre,
										BLOCKS.blockBitChest);
	}

	@SubscribeEvent
	public static void registerItemBlocks(RegistryEvent.Register<Item> event) {

		event.getRegistry().registerAll(getItemBlock(BLOCKS.blockBitCrucible),
										getItemBlock(BLOCKS.blockBitForge),
										getItemBlock(BLOCKS.blockBitDyeTable),
										getItemBlock(BLOCKS.blockBitFusionTable),
										getItemBlock(BLOCKS.blockFireOre),
										getItemBlock(BLOCKS.blockEarthOre),
										getItemBlock(BLOCKS.blockLightningOre),
										getItemBlock(BLOCKS.blockIceOre),
										getItemBlock(BLOCKS.blockSpatialOre),
										getItemBlock(BLOCKS.blockBitChest));

		OreDictionary.registerOre("oreBitFire", BitCraftingBlocks.BLOCKS.blockFireOre);
		OreDictionary.registerOre("oreBitEarth", BitCraftingBlocks.BLOCKS.blockEarthOre);
		OreDictionary.registerOre("oreBitLightning", BitCraftingBlocks.BLOCKS.blockLightningOre);
		OreDictionary.registerOre("oreBitIce", BitCraftingBlocks.BLOCKS.blockIceOre);
		OreDictionary.registerOre("oreBitSpatial", BitCraftingBlocks.BLOCKS.blockSpatialOre);
	}

	public static Item getItemBlock(Block block) {
		return new ItemBlock(block).setRegistryName(block.getRegistryName());
	}
}
