package com.chaseoqueso.bitcrafting.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return BitCraftingItems.itemBit;
	}

	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random random) {
		return random.nextInt(2) + 1;
	}

	@Override
	public int getExpDrop(IBlockState state, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
	{
		Random rand = world instanceof World ? ((World)world).rand : new Random();
		return MathHelper.getInt(rand, 3, 7);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		String[] bitdata = (this == BitCraftingBlocks.blockFireOre ? new String[] { "orange", "", "fire" }
								: (this == BitCraftingBlocks.blockEarthOre ? new String[] { "brown", "dark", "earth" }
										: (this == BitCraftingBlocks.blockLightningOre ? new String[] { "yellow", "light", "lightning" }
												: (this == BitCraftingBlocks.blockIceOre ? new String[] { "lightblue", "light", "ice" }
														: new String[] { "purple", "", "spatial" }))));

		Random rand = world instanceof World ? ((World)world).rand : new Random();
		int count = quantityDroppedWithBonus(fortune, rand);

		for (int i = 0; i < count; i++)
		{
			Item item = getItemDropped(state, rand, fortune);
			if (item != null)
			{
				drops.add(ItemBit.setBit(new ItemStack(item, 1, damageDropped(state)), bitdata[0], bitdata[1], .07F,
						.37F, .06F, bitdata[2], .01F, .1F));
			}
		}
	}
}
