package com.chaseoqueso.bitcrafting.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.items.ItemBit;

public class BlockBitOre extends BlockOre {

	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return BitCraftingMod.itemBit;
	}

	/**
	 * This returns a complete list of items dropped from this block.
	 *
	 * @param world    The current world
	 * @param x        X Position
	 * @param y        Y Position
	 * @param z        Z Position
	 * @param metadata Current metadata
	 * @param fortune  Breakers fortune level
	 * @return A ArrayList containing all items this block drops
	 */
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		String effect = this == BitCraftingMod.blockFireOre ? "fire" : (this == BitCraftingMod.blockEarthOre ? "earth" : (this == BitCraftingMod.blockLightningOre ? "lightning" : (this == BitCraftingMod.blockIceOre ? "ice" : "spatial")));
		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if (item != null) {
				ret.add(ItemBit.setBit(new ItemStack(item, 1, damageDropped(metadata)), "orange", "", .07F, .37F, .06F,
						effect, .01F + world.rand.nextInt(2) / 100F, .1F + world.rand.nextInt(2) / 10F));
			}
		}
		return ret;
	}
}
