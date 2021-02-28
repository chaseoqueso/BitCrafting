package com.chaseoqueso.bitcrafting.blocks;

import java.util.Random;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockBitCrucible extends BlockContainer {

	public static PropertyDirection FACING = BlockHorizontal.FACING;
    public static PropertyBool BURNING = PropertyBool.create("burning");
	
	public BlockBitCrucible(Material material, boolean isActive) {
		super(material);
		setCreativeTab(BitCraftingMod.tabBitCraftingMod);
		setHardness(5.0F).setResistance(10.0F);
		setSoundType(SoundType.METAL);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9)
	{

		if(!world.isRemote)
			player.openGui(BitCraftingMod.instance, 0, world, position.getX(), position.getY(), position.getZ());

		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BitCraftingBlocks.blockBitCrucible);
	}

	@Override
	public ItemStack getItem(World world, BlockPos position, IBlockState state)
	{
		return new ItemStack(BitCraftingBlocks.blockBitCrucible);
	}
	
	@Override
	public void onBlockAdded(World world, BlockPos position, IBlockState state)
	{
		super.onBlockAdded(world, position, state);
		this.direction(world, position, state);
	}

	private void direction(World world, BlockPos position, IBlockState state) {
		if(!world.isRemote)
		{
			IBlockState north = world.getBlockState(position.north());
			IBlockState south = world.getBlockState(position.south());
			IBlockState west = world.getBlockState(position.west());
			IBlockState east = world.getBlockState(position.east());
			EnumFacing face = state.getValue(FACING);

			if(face == EnumFacing.NORTH & north.isFullBlock() && !south.isFullBlock())
				face = EnumFacing.SOUTH;
			else if(face == EnumFacing.SOUTH & south.isFullBlock() && !north.isFullBlock())
				face = EnumFacing.SOUTH;
			else if(face == EnumFacing.WEST & west.isFullBlock() && !east.isFullBlock())
				face = EnumFacing.EAST;
			else if(face == EnumFacing.EAST & east.isFullBlock() && !west.isFullBlock())
				face = EnumFacing.WEST;

			world.setBlockState(position, state.withProperty(FACING, face), 2);
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state,  EntityLivingBase placer, ItemStack itemstack)
	{
		world.setBlockState(position, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rotation)
	{
		return state.withProperty(FACING, rotation.rotate((EnumFacing) state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror)
	{
		return state.withRotation(mirror.toRotation((EnumFacing) state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {BURNING, FACING});
	}

	public static void updateBlockState(boolean burning, World world, BlockPos position)
	{
		IBlockState state = world.getBlockState(position);
		TileEntity tileentity = world.getTileEntity(position);
		
		if(burning)
			world.setBlockState(position, BitCraftingBlocks.blockBitCrucible.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true), 3);
		else
			world.setBlockState(position, BitCraftingBlocks.blockBitCrucible.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false), 3);
		
		if(tileentity != null)
		{
			tileentity.validate();
			world.setTileEntity(position, tileentity);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing facing = EnumFacing.getFront(meta);

		if(facing.getAxis() == EnumFacing.Axis.Y)
			facing = EnumFacing.NORTH;

		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}
	
	public void breakBlock(World world, BlockPos position, IBlockState state)
	{
		TileEntityBitCrucible tileentity = (TileEntityBitCrucible) world.getTileEntity(position);
		InventoryHelper.dropInventoryItems(world, position, tileentity);
		super.breakBlock(world, position, state);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos position, Random rand)
	{
		if(state.getValue(BURNING))
		{
			int direction = state.getValue(FACING).getIndex();
			
			float x = (float) position.getX() + .5F, y = (float) position.getY() + rand.nextFloat() * 6.0F / 16.0F, z = (float) position.getZ() + .5F, x2 = rand.nextFloat() * .3F, z2 = rand.nextFloat() * .3F;
			
			if(direction == 2 || direction == 3 || direction == 4 || direction == 5)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, (double) (x - z2), (double) y, (double) (z + x2), 0.0F, 0.0F, 0.0F);
				world.spawnParticle(EnumParticleTypes.FLAME, (double) (x - z2), (double) y, (double) (z + x2), 0.0F, 0.0F, 0.0F);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2) 
	{
		return new TileEntityBitCrucible();
	}
}
