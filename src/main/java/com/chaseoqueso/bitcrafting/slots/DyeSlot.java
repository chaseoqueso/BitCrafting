package com.chaseoqueso.bitcrafting.slots;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class DyeSlot extends SlotCrafting {
    /** The craft matrix inventory linked to this result slot. */
    private final IInventory craftMatrix;
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;

	public DyeSlot(EntityPlayer p_i1823_1_, IInventory p_i1823_2_, IInventory p_i1823_3_, int p_i1823_4_,
			int p_i1823_5_, int p_i1823_6_) {
		super(p_i1823_1_, p_i1823_2_, p_i1823_3_, p_i1823_4_, p_i1823_5_, p_i1823_6_);
		// TODO Auto-generated constructor stub
        this.thePlayer = p_i1823_1_;
        this.craftMatrix = p_i1823_2_;
	}

	public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_)
    {
        FMLCommonHandler.instance().firePlayerCraftingEvent(p_82870_1_, p_82870_2_, craftMatrix);
        this.onCrafting(p_82870_2_);

        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);

            if (itemstack1 != null)
            {
            	if(itemstack1.getItem() == Items.water_bucket)
            		continue;
            	
                this.craftMatrix.decrStackSize(i, 1);

                if (itemstack1.getItem().hasContainerItem(itemstack1))
                {
                    ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

                    if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage())
                    {
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(thePlayer, itemstack2));
                        continue;
                    }

                    if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !this.thePlayer.inventory.addItemStackToInventory(itemstack2))
                    {
                        if (this.craftMatrix.getStackInSlot(i) == null)
                        {
                            this.craftMatrix.setInventorySlotContents(i, itemstack2);
                        }
                        else
                        {
                            this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
                        }
                    }
                }
            }
        }
    }
	
}
