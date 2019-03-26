package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.gui.GUIBitForge;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class ForgeSlot extends SlotCrafting {

    /** The craft matrix inventory linked to this result slot. */
    private final TileEntityBitForge craftMatrix;
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;
    public GUIBitForge gui;
	
	public ForgeSlot(EntityPlayer player, IInventory tileForge, IInventory result, int index, int x, int y) {
		super(player, tileForge, result, index, x, y);
        this.thePlayer = player;
        this.craftMatrix = (TileEntityBitForge)tileForge;
	}
	
	public ForgeSlot setGUI(GUIBitForge GUI)
	{
		this.gui = GUI;
		return this;
	}
	
	@Override
    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack)
    {
		FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, craftMatrix);
        this.onCrafting(stack);
		
        for (int i = 0; i < this.craftMatrix.getSizeInventory(); ++i)
        {
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);

            if (itemstack1 != null)
            {
                this.craftMatrix.decrStackSizeNoUpdate(i, 1);

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
                            this.craftMatrix.setInventorySlotContentsNoUpdate(i, itemstack2);
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
