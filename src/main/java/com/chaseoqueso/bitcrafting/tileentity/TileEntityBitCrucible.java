package com.chaseoqueso.bitcrafting.tileentity;

import java.util.Random;

import com.chaseoqueso.bitcrafting.CrucibleRecipes;
import com.chaseoqueso.bitcrafting.blocks.BlockBitCrucible;
import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.items.tools.ItemBitSword;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBitCrucible extends TileEntity implements ISidedInventory, ITickable {

	private static final int[] slotsTop = new int[] {0};
	private static final int[] slotsBottom = new int[] {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 73, 74, 75, 76, 77, 78, 79, 1};
	private static final int[] slotsSides = new int[] {1};

	private NonNullList<ItemStack> crucibleItemStacks = NonNullList.withSize(82, ItemStack.EMPTY);
	
	public int currentBurnTime;
	public int burnTime;
	public int cookTime;
    private final Random random = new Random();
	
	private String crucibleName;

	@Override
	public String getName()
	{
		return this.hasCustomName() ? this.crucibleName : "container.BitCrucible";
	}

	@Override
	public boolean hasCustomName() {
		return this.crucibleName != null && !this.crucibleName.isEmpty();
	}

	public void setCustomName(String displayName) {
		this.crucibleName = displayName;
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName());
	}

	public int getSizeInventory() {
		return this.crucibleItemStacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for(ItemStack stack : crucibleItemStacks)
		{
			if(!stack.isEmpty())
				return false;
		}

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.crucibleItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(crucibleItemStacks, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(crucibleItemStacks, index);
	}

	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.crucibleItemStacks.set(slot, itemstack);
		if(itemstack != null && itemstack.getCount() > this.getInventoryStackLimit())
			itemstack.setCount(this.getInventoryStackLimit());
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
        this.crucibleItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(tag, crucibleItemStacks);

        this.currentBurnTime = tag.getShort("BurnTime");
        this.cookTime = tag.getShort("CookTime");
        this.burnTime = tag.getShort("ItemBurnTime");

        if (tag.hasKey("CustomName", 8))
        {
            this.crucibleName = tag.getString("CustomName");
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
        tag.setShort("BurnTime", (short)this.currentBurnTime);
        tag.setShort("CookTime", (short)this.cookTime);
		tag.setShort("ItemBurnTime", (short)this.burnTime);
        ItemStackHelper.saveAllItems(tag, crucibleItemStacks);

        if (this.hasCustomInventoryName())
        {
            tag.setString("CustomName", this.crucibleName);
        }

        return tag;
	}
	
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookTime * par1 / 200;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if(this.burnTime == 0)
		{
			this.burnTime = 200;
		}
		
		return this.currentBurnTime * par1 / this.burnTime;
	}
	
	public boolean isBurning()
	{
		return this.currentBurnTime > 0;
	}
	
	@Override
	public void update()
	{
		boolean flag = this.currentBurnTime > 0;
        boolean flag1 = false;

        if (this.currentBurnTime > 0)
        {
            --this.currentBurnTime;
        }

        if (!this.world.isRemote)
        {
            if (this.currentBurnTime != 0 || !this.crucibleItemStacks.get(1).isEmpty() && !this.crucibleItemStacks.get(0).isEmpty())
            {
                if (this.currentBurnTime == 0 && this.canBreakDown())
                {
                    this.burnTime = this.currentBurnTime = getItemBurnTime(this.crucibleItemStacks.get(1));

                    if (this.currentBurnTime > 0)
                    {
                        flag1 = true;

                        if (!this.crucibleItemStacks.get(1).isEmpty())
                        {
                            this.crucibleItemStacks.get(1).shrink(1);

                            if (this.crucibleItemStacks.get(1).getCount() == 0)
                            {
                                this.crucibleItemStacks.set(1, crucibleItemStacks.get(1).getItem().getContainerItem(crucibleItemStacks.get(1)));
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
            else
			{
				this.cookTime = 0;
			}

			flag1 = (flag != this.currentBurnTime > 0);
			BlockBitCrucible.updateBlockState(this.currentBurnTime > 0, this.world, pos);
        }

        if (flag1)
        {
            this.markDirty();
        }
	}
	
	private boolean canBreakDown()
	{
		if(crucibleItemStacks.get(0) == ItemStack.EMPTY)
			return false;
		
		if(crucibleItemStacks.get(0).isItemDamaged())
			return false;

		if(crucibleItemStacks.get(0).getItem() instanceof IItemBitTool)
			return true;
		
		ItemStack[] itemstacks = CrucibleRecipes.instance().getBreakDownResult(crucibleItemStacks.get(0));
		
		if(itemstacks == null)
			return false;
		
		int openSlots = 0;
		int[] added = new int[crucibleItemStacks.size() - 2];
		int[] subtracted = new int[itemstacks.length];
		
		for(int j = 0; j < itemstacks.length; j++)
		{
			for(int i = 2; i < crucibleItemStacks.size(); i++)
			{
				if(crucibleItemStacks.get(i) != ItemStack.EMPTY && crucibleItemStacks.get(i).isItemEqual(itemstacks[j]) && ItemBit.bitsAreEqual(crucibleItemStacks.get(i), itemstacks[j]))
		        {
		            int result = crucibleItemStacks.get(i).getCount() + added[i-2] + itemstacks[j].getCount() + subtracted[j];
		            if(result <= getInventoryStackLimit() && result <= crucibleItemStacks.get(i).getMaxStackSize())
		            {
		            	added[i-2] += itemstacks[j].getCount();
						openSlots++;
						break;
		            } else {
		            	int diff = crucibleItemStacks.get(i).getMaxStackSize() - crucibleItemStacks.get(i).getCount();
		            	subtracted[j] -= diff;
		            	added[i-2] += diff;
		            }
		        }
			}
		}
		
		for(int i = 2; i < crucibleItemStacks.size(); i++)
		{
			if(crucibleItemStacks.get(i) == ItemStack.EMPTY && added[i-2] == 0)
				openSlots++;
		}
		
		return openSlots >= itemstacks.length;
	}
	
	public void breakItemDown()
	{
		if(this.canBreakDown())
		{
			ItemStack[] itemstacks;
			if(crucibleItemStacks.get(0).getItem() instanceof IItemBitTool)
				itemstacks = IItemBitTool.getBits(crucibleItemStacks.get(0));
			else
				itemstacks = CrucibleRecipes.instance().getBreakDownResult(crucibleItemStacks.get(0));
			
			int[] subtracted = new int[itemstacks.length];
			
			for(int j = 0; j < itemstacks.length; j++)
			{
				for(int i = 2; i < crucibleItemStacks.size(); i++)
				{
					if(ItemBit.bitsAreEqual(crucibleItemStacks.get(i), itemstacks[j]))
			        {
			            int result = crucibleItemStacks.get(i).getCount() + itemstacks[j].getCount() + subtracted[j];
			            if(result <= getInventoryStackLimit() && result <= crucibleItemStacks.get(i).getMaxStackSize())
			            {
			            	crucibleItemStacks.get(i).grow(itemstacks[j].getCount() + subtracted[j]);
			            	subtracted[j] = -itemstacks[j].getCount();
							break;
			            } else {
			            	int diff = crucibleItemStacks.get(i).getMaxStackSize() - crucibleItemStacks.get(i).getCount();
			            	subtracted[j] -= diff;
			            	crucibleItemStacks.get(i).grow(diff);
			            }
			        }
				}
			}
			
			for(int j = 0; j < itemstacks.length; j++)
			{
				for(int i = 2; i < crucibleItemStacks.size(); i++)
				{
					if(crucibleItemStacks.get(i) == ItemStack.EMPTY && (itemstacks[j].getCount() + subtracted[j]) != 0)
					{
						ItemStack result = itemstacks[j].copy();
						result.shrink(-subtracted[j]);
						crucibleItemStacks.set(i, result);
						break;
					}
					if(i == crucibleItemStacks.size() - 1 && crucibleItemStacks.get(crucibleItemStacks.size() - 1) != ItemStack.EMPTY)
					{
						float f = this.random.nextFloat() * .6F + .1F;
						float f1 = this.random.nextFloat() * .6F + .1F;
						float f2 = this.random.nextFloat() * .6F + .1F;
						ItemStack itemstack = itemstacks[j].copy();
						
						while(itemstack.getCount() > 0)
						{
							int k = this.random.nextInt(21) + 10;
							
							if(k > itemstack.getCount())
								k = itemstack.getCount();
							
							itemstack.shrink(k);
							EntityItem entityitem = new EntityItem(world, ((float) pos.getX() + f), ((float) pos.getY() + f1), ((float) pos.getZ() + f2), new ItemStack(itemstack.getItem(), k, itemstack.getItemDamage()));
							
							if(itemstack.hasTagCompound())
							{
								entityitem.getItem().setTagCompound(itemstack.getTagCompound().copy());
							}
							
							float f3 = .025F;
							entityitem.motionX = ((float) this.random.nextGaussian() * f3);
							entityitem.motionY = ((float) this.random.nextGaussian() * f3 + .1F);
							entityitem.motionZ = ((float) this.random.nextGaussian() * f3);
							world.spawnEntity(entityitem);
						}
					}
				}
			}
			
			crucibleItemStacks.get(0).shrink(1);
			if(crucibleItemStacks.get(0).getCount() == 0)
				crucibleItemStacks.set(0, ItemStack.EMPTY);
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

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + .5D, (double) pos.getY() + .5D, (double) pos.getZ() + .5D) <= 64;
	}

	@Override
	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return par1 < 2 && (par1 != 1 || isItemFuel(itemstack));
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? slotsBottom : (side == EnumFacing.UP ? slotsTop : slotsSides);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing side) {
		return slot < 2 && this.isItemValidForSlot(slot, itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing side) {
		return side == EnumFacing.DOWN || slot != 1 || itemstack.getItem() == Items.BUCKET;
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
			case 0:
				return this.currentBurnTime;
			case 1:
				return this.burnTime;
			case 2:
				return this.cookTime;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
			case 0:
				this.currentBurnTime = value;
				break;
			case 1:
				this.burnTime = value;
				break;
			case 2:
				this.cookTime = value;
				break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 3;
	}

	@Override
	public void clear()
	{
		crucibleItemStacks.clear();
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}
}
