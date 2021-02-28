package com.chaseoqueso.bitcrafting.tileentity;

import java.util.Iterator;
import java.util.List;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.math.AxisAlignedBB;

import com.chaseoqueso.bitcrafting.blocks.BlockBitChest;
import com.chaseoqueso.bitcrafting.container.ContainerBitChest;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;

public class TileEntityBitChest extends TileEntityLockableLoot implements ITickable {

    private NonNullList<ItemStack> chestContents = NonNullList.withSize(256, ItemStack.EMPTY);
    
    /** The number of players currently using this chest */
    public int numPlayersUsing;
    private int ticksSinceSync;
    /** The current angle of the lid (between 0 and 1) */
    public float lidAngle;
    /** The angle of the lid last tick */
    public float prevLidAngle;

    public void setChestName(String displayName) {
		this.customName = displayName;
	}

	@Override
	public boolean isEmpty()
    {
        for(ItemStack stack : this.chestContents)
        {
            if(!stack.isEmpty())
                return false;
        }

        return true;
    }
    
    /**
     * Returns the name of the inventory
     */
    @Override
    public String getName()
    {
        return this.hasCustomName() ? this.customName : "Bit Chest";
    }

    /**
     * Returns if the inventory is named
     */
    @Override
    public boolean hasCustomName()
    {
        return this.customName != null && this.customName.length() > 0;
    }

	@Override
	public int getSizeInventory() {
		return this.chestContents.size();
	}

	@Override
    public Container createContainer(InventoryPlayer inventory, EntityPlayer player)
    {
        return new ContainerBitChest(inventory, this, player);
    }

    @Override
    public String getGuiID()
    {
        return BitCraftingMod.MODID + ":BitChest";
    }

    @Override
    protected NonNullList<ItemStack> getItems()
    {
        return chestContents;
    }

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
    public void openInventory(EntityPlayer player)
    {
        if (this.numPlayersUsing < 0)
        {
            this.numPlayersUsing = 0;
        }

        ++this.numPlayersUsing;
        this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
        this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
    }

	@Override
    public void closeInventory(EntityPlayer player)
    {
        if (this.getBlockType() instanceof BlockBitChest)
        {
            --this.numPlayersUsing;
            this.world.addBlockEvent(pos, this.getBlockType(), 1, this.numPlayersUsing);
            this.world.notifyNeighborsOfStateChange(pos, this.getBlockType(), false);
        }
    }
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
        NBTTagList nbttaglist = tag.getTagList("Items", 10);
        this.chestContents = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
        
        if(!this.checkLootAndRead(tag))
            ItemStackHelper.loadAllItems(tag, chestContents);

        if(tag.hasKey("CustomName", 8))
            customName = tag.getString("CustomName");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
        NBTTagList nbttaglist = new NBTTagList();

        if(!this.checkLootAndWrite(tag))
            ItemStackHelper.saveAllItems(tag, chestContents);

        if(tag.hasKey("CustomName", 8))
            tag.setString("CustomName", this.customName);

        return tag;
	}

	@Override
    public void update()
    {
        ++this.ticksSinceSync;
        float f;

        if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + pos.getX() + pos.getY() + pos.getZ()) % 200 == 0)
        {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((double)((float)pos.getX() - 5.0F), (double)((float)pos.getY() - 5.0F), (double)((float)pos.getZ() - 5.0F), (double)((float)(pos.getX() + 1) + 5.0F), (double)((float)(pos.getY() + 1) + 5.0F), (double)((float)(pos.getZ() + 1) + 5.0F)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext())
            {
                EntityPlayer entityplayer = (EntityPlayer)iterator.next();

                if (entityplayer.openContainer instanceof ContainerBitChest)
                {
                    if (((ContainerBitChest)entityplayer.openContainer).tileChest == this)
                    {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d2;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F)
        {
            double d1 = (double)pos.getX() + 0.5D;
            d2 = (double)pos.getZ() + 0.5D;

            this.world.playSound(null, d1, (double)pos.getY() + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F)
        {
            float f1 = this.lidAngle;

            if (this.numPlayersUsing > 0)
            {
                this.lidAngle += f;
            }
            else
            {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F)
            {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (this.lidAngle < f2 && f1 >= f2)
            {
                d2 = (double)pos.getX() + 0.5D;
                double d0 = (double)pos.getZ() + 0.5D;

                this.world.playSound(null, d2, (double)pos.getY() + 0.5D, d2, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F)
            {
                this.lidAngle = 0.0F;
            }
        }
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return stack.getItem() instanceof ItemBit;
    }
}
