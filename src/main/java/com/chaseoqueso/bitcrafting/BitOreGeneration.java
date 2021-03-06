package com.chaseoqueso.bitcrafting;

import java.util.Random;

import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class BitOreGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension())
		{
		case -1:
			generateNether(world, random, chunkX, chunkZ);
			break;
		case 0:
			generateOverworld(world, random, chunkX, chunkZ);
			break;
		case 1:
			generateEnd(world, random, chunkX, chunkZ);
			break;
		}
		
	}
	
	public void generateNether(World world, Random rand, int x, int z) {
		
	}
	
	public void generateOverworld(World world, Random rand, int x, int z) {
		generateOre(BitCraftingBlocks.BLOCKS.blockFireOre, world, rand, x, z, 2, 4, 2, 0, 50, Blocks.STONE);
		generateOre(BitCraftingBlocks.BLOCKS.blockEarthOre, world, rand, x, z, 2, 4, 2, 0, 50, Blocks.STONE);
		generateOre(BitCraftingBlocks.BLOCKS.blockLightningOre, world, rand, x, z, 2, 4, 2, 0, 50, Blocks.STONE);
		generateOre(BitCraftingBlocks.BLOCKS.blockIceOre, world, rand, x, z, 2, 4, 2, 0, 50, Blocks.STONE);
		generateOre(BitCraftingBlocks.BLOCKS.blockSpatialOre, world, rand, x, z, 2, 4, 2, 0, 50, Blocks.STONE);
	}
	
	public void generateEnd(World world, Random rand, int x, int z) {
		
	}

	public void generateOre(Block block, World world, Random rand, int chunkX, int chunkZ, int minVeinSize, 
			int maxVeinSize, int chance, int minY, int maxY, Block generateIn) {/*
		int veinSize = minVeinSize + rand.nextInt(maxVeinSize - minVeinSize);
		int heightRange = maxY - minY;
		WorldGenMinable gen = new WorldGenMinable(block, veinSize, generateIn);
		for(int i = 0; i < chance; i++)
		{
			int xrand = chunkX * 16 + rand.nextInt(16);
			int yrand = rand.nextInt(heightRange) + minY;
			int zrand = chunkZ * 16 + rand.nextInt(16);
			gen.generate(world, rand, xrand, yrand, zrand);
		}*/
	}

}
