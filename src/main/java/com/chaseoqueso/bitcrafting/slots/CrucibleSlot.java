package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.CrucibleRecipes;

import com.chaseoqueso.bitcrafting.items.ItemBit;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class CrucibleSlot extends BitSlot {
	/** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;
    private int stackSize;
    private float expPerBit;

    public CrucibleSlot(EntityPlayer player, IInventory inventory, int index, int x, int y)
    {
        super(inventory, index, x, y);
        this.thePlayer = player;
    }

    /**
     * Check if the stack is a valid item for this slot. Always true beside for the armor slots.
     */
    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return false;
    }

    /**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            this.stackSize += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    public void setExperience(float value)
    {
        expPerBit = value;
    }

    @Override
    public ItemStack onTake(EntityPlayer player, ItemStack itemStack)
    {
        super.onTake(player, itemStack);
        this.onCrafting(itemStack);
        return itemStack;
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    @Override
    protected void onCrafting(ItemStack itemStack, int amount)
    {
        this.stackSize += amount;
        this.onCrafting(itemStack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void onCrafting(ItemStack itemStack)
    {
        itemStack.onCrafting(this.thePlayer.world, this.thePlayer, this.stackSize);

        if (!this.thePlayer.world.isRemote)
        {
            int stackSize = this.stackSize;
            int expRemaining = this.stackSize;
            int expOrbAmount;

            float experience = expPerBit * stackSize;

            if (experience == 0.0F)
            {
                expRemaining = 0;
            }
            else if (experience < 1.0F)
            {
                expOrbAmount = MathHelper.floor((float)stackSize * experience);

                if (expOrbAmount < MathHelper.ceil((float)stackSize * experience) && (float)Math.random() < (float)stackSize * experience - (float)expOrbAmount)
                {
                    ++expOrbAmount;
                }

                expRemaining = expOrbAmount;
            }

            while (expRemaining > 0)
            {
                expOrbAmount = EntityXPOrb.getXPSplit(expRemaining);
                expRemaining -= expOrbAmount;
                this.thePlayer.world.spawnEntity(new EntityXPOrb(this.thePlayer.world, this.thePlayer.posX, this.thePlayer.posY + 0.5D, this.thePlayer.posZ + 0.5D, expOrbAmount));
            }
        }

        this.stackSize = 0;
    }
}
