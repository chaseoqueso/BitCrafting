package com.chaseoqueso.bitcrafting.blocks;

import java.util.Random;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitDyeTable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBitDyeTable extends BlockContainer {
	
	public BlockBitDyeTable(Material material) {
		super(material);
		setCreativeTab(BitCraftingMod.tabBitCraftingMod);
		setHardness(3.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9)
	{
		player.openGui(BitCraftingMod.instance, 2, world, position.getX(), position.getY(), position.getZ());
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BitCraftingBlocks.blockBitDyeTable);
	}

	@Override
	public ItemStack getItem(World world, BlockPos position, IBlockState state)
	{
		return new ItemStack(BitCraftingBlocks.blockBitDyeTable);
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack itemStack)
	{
		if(itemStack.hasDisplayName())
		{
			TileEntity tileEntity = world.getTileEntity(position);

			if(tileEntity instanceof TileEntityBitDyeTable)
			{
				((TileEntityBitDyeTable)tileEntity).setDyeTableName(itemStack.getDisplayName());
			}
		}
	}
	
	public void breakBlock(World world, BlockPos position, IBlockState state)
	{
		TileEntityBitDyeTable tileentity = (TileEntityBitDyeTable) world.getTileEntity(position);
		InventoryHelper.dropInventoryItems(world, position, tileentity);
		super.breakBlock(world, position, state);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2) 
	{
		return new TileEntityBitDyeTable();
	}
}
