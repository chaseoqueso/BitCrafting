package com.chaseoqueso.bitcrafting.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.items.ItemBit;

public class BlockBitOre extends BlockOre {

	public BlockBitOre() {
		super();
		this.setHarvestLevel("pickaxe", 2);
		this.setLightLevel(0.5F);
		setCreativeTab(BitCraftingMod.tabBitCraftingMod);
		setHardness(3.0F);
		setResistance(5.0F);
		setSoundType(SoundType.STONE);
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return BitCraftingMod.itemBit;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random p_149745_1_) {
		return p_149745_1_.nextInt(2) + 1;
	}

	private Random rand = new Random();

	@Override
	public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_) {
		return MathHelper.getRandomIntegerInRange(rand, 3, 7);
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
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		String[] bitdata = (this == BitCraftingMod.blockFireOre ? new String[] { "orange", "", "fire" }
								: (this == BitCraftingMod.blockEarthOre ? new String[] { "brown", "dark", "earth" }
										: (this == BitCraftingMod.blockLightningOre ? new String[] { "yellow", "light", "lightning" }
												: (this == BitCraftingMod.blockIceOre ? new String[] { "lightblue", "light", "ice" }
														: new String[] { "purple", "", "spatial" }))));
		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++) {
			Item item = getItemDropped(metadata, world.rand, fortune);
			if (item != null) {
				ret.add(ItemBit.setBit(new ItemStack(item, 1, damageDropped(metadata)), bitdata[0], bitdata[1], .07F,
						.37F, .06F, bitdata[2], .01F, .1F));
			}
		}
		return ret;
	}
}
