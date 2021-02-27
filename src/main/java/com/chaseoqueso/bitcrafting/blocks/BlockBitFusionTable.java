package com.chaseoqueso.bitcrafting.blocks;

import java.util.Random;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockBitFusionTable extends BlockContainer {
	
	private IIcon top;
	private IIcon bottom;
    private final Random random = new Random();
	
	public BlockBitFusionTable(Material material) {
		super(material);
		setCreativeTab(BitCraftingMod.tabBitCraftingMod);
		setHardness(5.0F);
		setResistance(10.0F);
		setSoundType(SoundType.METAL);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.bottom = reg.registerIcon(BitCraftingMod.MODID + ":FusionTable_0");
        this.top = reg.registerIcon(BitCraftingMod.MODID + ":FusionTable_1");
        this.blockIcon = reg.registerIcon(BitCraftingMod.MODID + ":FusionTable_2");
    }
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return side == 0 ? this.bottom : (side == 1 ? this.top : this.blockIcon);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		player.openGui(BitCraftingMod.instance, 3, world, x, y, z);
		return true;
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemFromBlock(BitCraftingMod.blockBitFusionTable);
	}
	
	public Item getItem(World world, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(BitCraftingMod.blockBitFusionTable);
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
	{
		if(itemstack.hasDisplayName())
			((TileEntityBitFusionTable) world.getTileEntity(x, y, z)).setFusionTableName(itemstack.getDisplayName());
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TileEntityBitFusionTable tileentity = (TileEntityBitFusionTable) world.getTileEntity(x, y, z);
		if(tileentity != null)
		{
			for(int i = 0; i < tileentity.getSizeInventory(); i++)
			{
				ItemStack itemstack = tileentity.getStackInSlot(i);
				if(itemstack != null)
				{
					float f = this.random.nextFloat() * .6F + .1F;
					float f1 = this.random.nextFloat() * .6F + .1F;
					float f2 = this.random.nextFloat() * .6F + .1F;
					
					while(itemstack.stackSize > 0)
					{
						int j = this.random.nextInt(21) + 10;
						
						if(j > itemstack.stackSize)
							j = itemstack.stackSize;
						
						itemstack.stackSize -= j;
						EntityItem entityitem = new EntityItem(world, (double) ((float) x + f), (double) ((float) y + f1), (double) ((float) z + f2), new ItemStack(itemstack.getItem(), j, itemstack.getItemDamage()));
						
						if(itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
						}
						
						float f3 = .025F;
						entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + .1F);
						entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			world.func_147453_f(x, y, z, block);
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2) 
	{
		return new TileEntityBitFusionTable();
	}
}
