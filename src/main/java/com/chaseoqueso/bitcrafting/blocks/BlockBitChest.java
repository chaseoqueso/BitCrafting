package com.chaseoqueso.bitcrafting.blocks;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitChest;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBitChest extends BlockContainer {
    
	public BlockBitChest(Material p_i45386_1_) {
		super(p_i45386_1_);
        setCreativeTab(BitCraftingMod.tabBitCraftingMod);
        setHardness(1.0F);
        setResistance(2.0F);
        setSoundType(SoundType.WOOD);
	}

    /**
     * Called upon block activation (right click on the block.)
     */
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9)
	{
        if(!world.isRemote)
		    player.openGui(BitCraftingMod.instance, 4, world, position.getX(), position.getY(), position.getZ());

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
