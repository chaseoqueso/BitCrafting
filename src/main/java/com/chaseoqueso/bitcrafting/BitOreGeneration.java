package com.chaseoqueso.bitcrafting;

import java.util.Random;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BitOreGeneration implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		switch(world.provider.dimensionId)
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
		
	}
	
	public void generateEnd(World world, Random rand, int x, int z) {
		
	}
	
	public void generateOre(Block block, World world, Random rand, int chunkX, int chunkZ, int minVeinSize, 
			int maxVeinSize, int chance, int minY, int maxY, Block generateIn) {
		int veinSize = minVeinSize + rand.nextInt(maxVeinSize - minVeinSize);
		int heightRange = maxY - minY;
		WorldGenMinable gen = new WorldGenMinable(block, veinSize, generateIn);
		for(int i = 0; i < 0; i++)
		{
			int xrand = chunkX * 16 + rand.nextInt(16);
			int yrand = rand.nextInt(heightRange) + minY;
			int zrand = chunkZ * 16 + rand.nextInt(16);
		}
	}

}
