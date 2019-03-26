package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;

public class FusionSlot extends SlotCrafting {

    /** The craft matrix inventory linked to this result slot. */
    private final TileEntityBitFusionTable fusionTable;
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer thePlayer;
    
	public FusionSlot(EntityPlayer p_i1823_1_, IInventory p_i1823_2_, IInventory p_i1823_3_, int p_i1823_4_, int p_i1823_5_, int p_i1823_6_) {
		super(p_i1823_1_, p_i1823_2_, p_i1823_3_, p_i1823_4_, p_i1823_5_, p_i1823_6_);
        this.thePlayer = p_i1823_1_;
        this.fusionTable = (TileEntityBitFusionTable)p_i1823_2_;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack)
    {
		if(player.experienceLevel >= this.fusionTable.eventhandler.xpCost || player.capabilities.isCreativeMode)
		{
	        thePlayer.addExperienceLevel(-this.fusionTable.eventhandler.xpCost);
			super.onPickupFromSlot(player, stack);
		}
    }
}
