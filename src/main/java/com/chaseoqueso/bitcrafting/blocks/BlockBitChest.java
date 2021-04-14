package com.chaseoqueso.bitcrafting.blocks;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBitChest extends BlockContainer {

    public static PropertyDirection FACING = BlockHorizontal.FACING;
    
	public BlockBitChest(Material material) {
		super(material);
		setUnlocalizedName("BlockBitChest");
		setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blockbitchest"));
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
        setHardness(1.0F);
        setResistance(2.0F);
        setSoundType(SoundType.WOOD);
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH));
	}

    /**
     * Called upon block activation (right click on the block.)
     */
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9)
	{
        if(!world.isRemote) {
            player.openGui(BitCraftingMod.instance, 4, world, position.getX(), position.getY(), position.getZ());
        }

		return true;
	}

    @Override
    public void breakBlock(World world, BlockPos position, IBlockState state)
    {
        TileEntityBitChest tileentitychest = (TileEntityBitChest) world.getTileEntity(position);
        InventoryHelper.dropInventoryItems(world, position, tileentitychest);
        super.breakBlock(world, position, state);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos position, IBlockState state, EntityLivingBase placer, ItemStack itemStack)
    {
        if(itemStack.hasDisplayName())
        {
            TileEntity tileEntity = world.getTileEntity(position);

            if(tileEntity instanceof TileEntityBitChest)
            {
                ((TileEntityBitChest)tileEntity).setChestName(itemStack.getDisplayName());
            }
        }
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
        return new BlockStateContainer(this, new IProperty[] {FACING});
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

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityBitChest();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isFullBlock(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
}
