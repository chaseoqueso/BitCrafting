package com.chaseoqueso.bitcrafting.blocks;

import java.util.Random;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBitForge extends BlockContainer {

    private final Random random = new Random();
	
	public BlockBitForge(Material material) {
		super(material);
		setUnlocalizedName("BlockBitForge");
		setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "blockbitforge"));
		setCreativeTab(BitCraftingMod.tabBitCraftingMod);
		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos position, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float par7, float par8, float par9)
	{
		player.openGui(BitCraftingMod.instance, 1, world, position.getX(), position.getY(), position.getZ());
		return true;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BitCraftingBlocks.BLOCKS.blockBitForge);
	}
	
	public ItemStack getItem(World world, BlockPos position, IBlockState state)
	{
		return new ItemStack(BitCraftingBlocks.BLOCKS.blockBitForge);
	}
	
	public void onBlockPlacedBy(World world, BlockPos position, IBlockState state,  EntityLivingBase placer, ItemStack itemstack)
	{
		if(itemstack.hasDisplayName())
			((TileEntityBitForge) world.getTileEntity(position)).setForgeName(itemstack.getDisplayName());
	}
	
	public void breakBlock(World world, BlockPos position, IBlockState state)
	{
		TileEntityBitForge tileentity = (TileEntityBitForge) world.getTileEntity(position);
		InventoryHelper.dropInventoryItems(world, position, tileentity);
		super.breakBlock(world, position, state);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2) 
	{
		return new TileEntityBitForge();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state)
	{
		return EnumBlockRenderType.MODEL;
	}
}
