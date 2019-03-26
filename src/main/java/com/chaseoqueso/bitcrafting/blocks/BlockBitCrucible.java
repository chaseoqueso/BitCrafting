package com.chaseoqueso.bitcrafting.blocks;

import java.util.Random;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitCrucible;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
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
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockBitCrucible extends BlockContainer {
	

    @SideOnly(Side.CLIENT)
    private IIcon front;
    private static boolean isBurning;
    private final boolean isBurning2;
    private final Random random = new Random();
	
	public BlockBitCrucible(Material material, boolean isActive) {
		super(material);
		this.setHardness(1.0F);
		isBurning2 = isActive;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister reg)
    {
        this.blockIcon = reg.registerIcon(BitCraftingMod.MODID + ":BitCrucibleSide");
        this.front = reg.registerIcon(this.isBurning2 ? BitCraftingMod.MODID + ":BitCrucibleActive" : BitCraftingMod.MODID + ":BitCrucibleInactive");
    }
	
	@Override
	public IIcon getIcon(int side, int meta)
	{
		return meta == 0 ? (side == 3 ? this.front : this.blockIcon) : (side != meta ? this.blockIcon : this.front);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		player.openGui(BitCraftingMod.instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par3)
	{
		return Item.getItemFromBlock(BitCraftingMod.blockBitCrucible);
	}
	
	@Override
	public Item getItem(World world, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(BitCraftingMod.blockBitCrucible);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		this.direction(world, x, y, z);
	}

	private void direction(World world, int x, int y, int z) {
		if(!world.isRemote)
		{
			Block block = world.getBlock(x, y, z - 1);
            Block block1 = world.getBlock(x, y, z + 1);
            Block block2 = world.getBlock(x - 1, y, z);
            Block block3 = world.getBlock(x + 1, y, z);
            byte b0 = 3;

            if (block.func_149730_j() && !block1.func_149730_j())
            {
                b0 = 3;
            }

            if (block1.func_149730_j() && !block.func_149730_j())
            {
                b0 = 2;
            }

            if (block2.func_149730_j() && !block3.func_149730_j())
            {
                b0 = 5;
            }

            if (block3.func_149730_j() && !block2.func_149730_j())
            {
                b0 = 4;
            }
			
			world.setBlockMetadataWithNotify(x, y, z, b0, 2);
		}
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemstack)
	{
		int direction = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		
		if(direction == 0)
		{
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if(direction == 1)
		{
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if(direction == 2)
		{
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if(direction == 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
		
		if(itemstack.hasDisplayName())
		{
			((TileEntityBitCrucible) world.getTileEntity(x, y, z)).setCrucibleName(itemstack.getDisplayName());
		}
	}
	
	public static void updateBlockState(boolean burning, World world, int x, int y, int z)
	{
		int direction = world.getBlockMetadata(x, y, z);
		TileEntity tileentity = world.getTileEntity(x, y, z);
		isBurning = true;
		
		if(burning)
			world.setBlock(x, y, z, BitCraftingMod.blockBitCrucibleActive);
		else
			world.setBlock(x, y, z, BitCraftingMod.blockBitCrucible);
		
		isBurning = false;
		world.setBlockMetadataWithNotify(x, y, z, direction, 2);
		
		if(tileentity != null)
		{
			tileentity.validate();
			world.setTileEntity(x, y, z, tileentity);
		}
	}
	
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if(!isBurning)
		{
			TileEntityBitCrucible tileentity = (TileEntityBitCrucible) world.getTileEntity(x, y, z);
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
		}
		super.breakBlock(world, x, y, z, block, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if(this.isBurning2)
		{
			int direction = world.getBlockMetadata(x, y, z);
			
			float xx = (float) x + .5F, yy = (float) y + random.nextFloat() * 6.0F / 16.0F, zz = (float) z + .5F, xx2 = random.nextFloat() * .3F, zz2 = random.nextFloat() * .3F; 
			
			if(direction == 2 || direction == 3 || direction == 4 || direction == 5)
			{
				world.spawnParticle("smoke", (double) (xx - zz2), (double) yy, (double) (zz + xx2), 0.0F, 0.0F, 0.0F);
				world.spawnParticle("flame", (double) (xx - zz2), (double) yy, (double) (zz + xx2), 0.0F, 0.0F, 0.0F);
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int par2) 
	{
		return new TileEntityBitCrucible();
	}
}
