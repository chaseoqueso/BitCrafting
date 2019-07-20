package com.chaseoqueso.bitcrafting.tileentity;

import java.util.Arrays;
import java.util.Random;

import com.chaseoqueso.bitcrafting.CrucibleRecipes;
import com.chaseoqueso.bitcrafting.blocks.BlockBitCrucible;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.items.ItemBitSword;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileEntityBitCrucible extends TileEntity implements ISidedInventory {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 73, 74, 75, 76, 77, 78, 79, 1};
	private static final int[] slotsSides = new int[] {1};
	
	private ItemStack[] crucibleItemStacks = new ItemStack[82];
	
	public int burnTime;
	public int currentBurnTime;
	public int cookTime;
    private final Random random = new Random();
	
	private String crucibleName;
	
	public void setCrucibleName(String displayName) {
		this.crucibleName = displayName;
	}

	public int getSizeInventory() {
		return this.crucibleItemStacks.length;
	}

	public ItemStack getStackInSlot(int index) {
		return this.crucibleItemStacks[index];
	}

	public ItemStack decrStackSize(int par1, int par2) {
		if(this.crucibleItemStacks[par1] != null)
		{
			ItemStack itemstack;
			if(this.crucibleItemStacks[par1].stackSize <= par2)
			{
				itemstack = this.crucibleItemStacks[par1];
				this.crucibleItemStacks[par1] = null;
		        this.markDirty();
				return itemstack;
			} else {
				itemstack = this.crucibleItemStacks[par1].splitStack(par2);
				if(this.crucibleItemStacks[par1].stackSize == 0)
					this.crucibleItemStacks[par1] = null;
		        this.markDirty();
				return itemstack;
			}
		}
		return null;
	}

	public ItemStack getStackInSlotOnClosing(int slot) {
		if(this.crucibleItemStacks[slot] != null)
		{
			ItemStack itemstack = this.crucibleItemStacks[slot];
			this.crucibleItemStacks[slot] = null;
			return itemstack;
		}
		return null;
	}

	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.crucibleItemStacks[slot] = itemstack;
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit())
			itemstack.stackSize = this.getInventoryStackLimit();
        this.markDirty();
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.crucibleName : "Bit Crucible";
	}

	public boolean hasCustomInventoryName() {
		return this.crucibleName != null && this.crucibleName.length() > 0;
	}

	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.crucibleItemStacks = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < this.crucibleItemStacks.length)
            {
                this.crucibleItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.burnTime = tag.getShort("BurnTime");
        this.cookTime = tag.getShort("CookTime");
        this.currentBurnTime = getItemBurnTime(this.crucibleItemStacks[1]);

        if (tag.hasKey("CustomName", 8))
        {
            this.crucibleName = tag.getString("CustomName");
        }
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
	{
		super.writeToNBT(tagCompound);
        tagCompound.setShort("BurnTime", (short)this.burnTime);
        tagCompound.setShort("CookTime", (short)this.cookTime);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.crucibleItemStacks.length; ++i)
        {
            if (this.crucibleItemStacks[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.crucibleItemStacks[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        tagCompound.setTag("Items", nbttaglist);

        if (this.hasCustomInventoryName())
        {
            tagCompound.setString("CustomName", this.crucibleName);
        }
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookTime * par1 / 200;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if(this.currentBurnTime == 0)
		{
			this.currentBurnTime = 200;
		}
		
		return this.burnTime * par1 / this.currentBurnTime;
	}
	
	public boolean isBurning()
	{
		return this.burnTime > 0;
	}
	
	@Override
	public void updateEntity()
	{
		boolean flag = this.burnTime > 0;
        boolean flag1 = false;

        if (this.burnTime > 0)
        {
            --this.burnTime;
        }

        if (!this.worldObj.isRemote)
        {
            if (this.burnTime != 0 || this.crucibleItemStacks[1] != null && this.crucibleItemStacks[0] != null)
            {
                if (this.burnTime == 0 && this.canBreakDown())
                {
                    this.currentBurnTime = this.burnTime = getItemBurnTime(this.crucibleItemStacks[1]);

                    if (this.burnTime > 0)
                    {
                        flag1 = true;

                        if (this.crucibleItemStacks[1] != null)
                        {
                            --this.crucibleItemStacks[1].stackSize;

                            if (this.crucibleItemStacks[1].stackSize == 0)
                            {
                                this.crucibleItemStacks[1] = crucibleItemStacks[1].getItem().getContainerItem(crucibleItemStacks[1]);
                            }
                        }
                    }
                }

                if (this.isBurning() && this.canBreakDown())
                {
                    ++this.cookTime;

                    if (this.cookTime == 200)
                    {
                        this.cookTime = 0;
                        this.breakItemDown();
                        flag1 = true;
                    }
                }
                else
                {
                    this.cookTime = 0;
                }
            }

            if (flag != this.burnTime > 0)
            {
                flag1 = true;
                BlockBitCrucible.updateBlockState(this.burnTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
            }
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private boolean canBreakDown()
	{
		if(crucibleItemStacks[0] == null)
			return false;
		
		if(crucibleItemStacks[0].isItemDamaged())
			return false;

		if(crucibleItemStacks[0].getItem() instanceof ItemBitSword)
			return true;
		
		ItemStack[] itemstacks = CrucibleRecipes.instance().getBreakDownResult(crucibleItemStacks[0]);
		
		if(itemstacks == null) 
			return false;
		
		int openSlots = 0;
		int[] added = new int[crucibleItemStacks.length - 2];
		int[] subtracted = new int[itemstacks.length];
		
		for(int j = 0; j < itemstacks.length; j++)
		{
			for(int i = 2; i < crucibleItemStacks.length; i++)
			{
				if(crucibleItemStacks[i] != null && crucibleItemStacks[i].isItemEqual(itemstacks[j]) && ItemBit.bitsAreEqual(crucibleItemStacks[i], itemstacks[j]))
		        {
		            int result = crucibleItemStacks[i].stackSize + added[i-2] + itemstacks[j].stackSize + subtracted[j];
		            if(result <= getInventoryStackLimit() && result <= crucibleItemStacks[i].getMaxStackSize())
		            {
		            	added[i-2] += itemstacks[j].stackSize;
						openSlots++;
						break;
		            } else {
		            	int diff = crucibleItemStacks[i].getMaxStackSize() - crucibleItemStacks[i].stackSize;
		            	subtracted[j] -= diff;
		            	added[i-2] += diff;
		            }
		        }
			}
		}
		
		for(int i = 2; i < crucibleItemStacks.length; i++)
		{
			if(crucibleItemStacks[i] == null && added[i-2] == 0)
				openSlots++;
		}
		
		return openSlots >= itemstacks.length;
	}
	
	public void breakItemDown()
	{
		if(this.canBreakDown())
		{
			ItemStack[] itemstacks;
			if(crucibleItemStacks[0].getItem() instanceof ItemBitSword)
				itemstacks = ItemBitSword.getBits(crucibleItemStacks[0]);
			else
				itemstacks = CrucibleRecipes.instance().getBreakDownResult(crucibleItemStacks[0]);
			
			int[] subtracted = new int[itemstacks.length];
			
			for(int j = 0; j < itemstacks.length; j++)
			{
				for(int i = 2; i < crucibleItemStacks.length; i++)
				{
					if(crucibleItemStacks[i] != null && crucibleItemStacks[i].isItemEqual(itemstacks[j]) && ItemBit.bitsAreEqual(crucibleItemStacks[i], itemstacks[j]))
			        {
			            int result = crucibleItemStacks[i].stackSize + itemstacks[j].stackSize + subtracted[j];
			            if(result <= getInventoryStackLimit() && result <= crucibleItemStacks[i].getMaxStackSize())
			            {
			            	crucibleItemStacks[i].stackSize += itemstacks[j].stackSize + subtracted[j];
			            	subtracted[j] = -itemstacks[j].stackSize;
							break;
			            } else {
			            	int diff = crucibleItemStacks[i].getMaxStackSize() - crucibleItemStacks[i].stackSize;
			            	subtracted[j] -= diff;
			            	crucibleItemStacks[i].stackSize += diff;
			            }
			        }
				}
			}
			
			for(int j = 0; j < itemstacks.length; j++)
			{
				for(int i = 2; i < crucibleItemStacks.length; i++)
				{
					if(crucibleItemStacks[i] == null && (itemstacks[j].stackSize + subtracted[j]) != 0)
					{
						crucibleItemStacks[i] = itemstacks[j].copy();
						break;
					}
					if(i == crucibleItemStacks.length - 1 && crucibleItemStacks[crucibleItemStacks.length - 1] != null)
					{
						float f = this.random.nextFloat() * .6F + .1F;
						float f1 = this.random.nextFloat() * .6F + .1F;
						float f2 = this.random.nextFloat() * .6F + .1F;
						ItemStack itemstack = itemstacks[j].copy();
						
						while(itemstack.stackSize > 0)
						{
							int k = this.random.nextInt(21) + 10;
							
							if(k > itemstack.stackSize)
								k = itemstack.stackSize;
							
							itemstack.stackSize -= k;
							EntityItem entityitem = new EntityItem(worldObj, (double) ((float) this.xCoord + f), (double) ((float) this.yCoord + f1), (double) ((float) this.zCoord + f2), new ItemStack(itemstack.getItem(), k, itemstack.getItemDamage()));
							
							if(itemstack.hasTagCompound())
							{
								entityitem.getEntityItem().setTagCompound((NBTTagCompound) itemstack.getTagCompound().copy());
							}
							
							float f3 = .025F;
							entityitem.motionX = (double) ((float) this.random.nextGaussian() * f3);
							entityitem.motionY = (double) ((float) this.random.nextGaussian() * f3 + .1F);
							entityitem.motionZ = (double) ((float) this.random.nextGaussian() * f3);
							worldObj.spawnEntityInWorld(entityitem);
						}
					}
				}
			}
			
			crucibleItemStacks[0].stackSize--;
			if(crucibleItemStacks[0].stackSize == 0) 
				crucibleItemStacks[0] = null;
		}
	}
	
	public static int getItemBurnTime(ItemStack itemstack)
	{
		return TileEntityFurnace.getItemBurnTime(itemstack);
	}
	
	public static boolean isItemFuel(ItemStack itemstack)
	{
		return getItemBurnTime(itemstack) > 0;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double) this.xCoord + .5D, (double) this.yCoord + .5D, (double) this.zCoord + .5D) <= 64;
	}

	public void openInventory() {
	}

	public void closeInventory() {
	}

	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 == 1 ? isItemFuel(itemstack) : true;
	}

	public int[] getAccessibleSlotsFromSide(int par1) {
		return par1 == 0 ? slotsBottom : (par1 == 1 ? slotsTop : slotsSides);
	}

	public boolean canInsertItem(int par1, ItemStack itemstack, int par3) {
		return par1 >= 2 ? false : this.isItemValidForSlot(par1, itemstack);
	}

	public boolean canExtractItem(int par1, ItemStack itemstack, int par3) {
		return par3 != 0 || par1 != 1 || itemstack.getItem() == Items.bucket;
	}
	
}
