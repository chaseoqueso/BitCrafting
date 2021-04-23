package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class FusionSlot extends Slot {

    /** The craft matrix inventory linked to this result slot. */
    private final TileEntityBitFusionTable fusionTable;
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer player;
	private int amountCrafted;
    
	public FusionSlot(EntityPlayer player, IInventory craftingInventory, IInventory inventoryIn, int slotIndex, int xPosition, int yPosition) {
		super(inventoryIn, slotIndex, xPosition, yPosition);
        this.player = player;
        this.fusionTable = (TileEntityBitFusionTable)craftingInventory;
	}

	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}

	public ItemStack decrStackSize(int amount)
	{
		if (this.getHasStack())
		{
			this.amountCrafted += Math.min(amount, this.getStack().getCount());
		}

		return super.decrStackSize(amount);
	}

	protected void onCrafting(ItemStack stack, int amount)
	{
		this.amountCrafted += amount;
		this.onCrafting(stack);
	}

	protected void onSwapCraft(int p_190900_1_)
	{
		this.amountCrafted += p_190900_1_;
	}

	protected void onCrafting(ItemStack stack)
	{
		if (this.amountCrafted > 0)
		{
			stack.onCrafting(this.player.world, this.player, this.amountCrafted);
			net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, fusionTable);
		}

		this.amountCrafted = 0;
		InventoryCraftResult inventorycraftresult = (InventoryCraftResult)this.inventory;
		IRecipe irecipe = inventorycraftresult.getRecipeUsed();

		if (irecipe != null && !irecipe.isDynamic())
		{
			this.player.unlockRecipes(Lists.newArrayList(irecipe));
			inventorycraftresult.setRecipeUsed((IRecipe)null);
		}
	}
	
	@Override
	public ItemStack onTake(EntityPlayer player, ItemStack itemStack)
    {
		if(player.experienceLevel >= this.fusionTable.eventhandler.xpCost || player.capabilities.isCreativeMode)
		{
			FMLCommonHandler.instance().firePlayerCraftingEvent(player, itemStack, fusionTable);
			this.onCrafting(itemStack);
			if(!player.capabilities.isCreativeMode)
				this.player.addExperienceLevel(-this.fusionTable.eventhandler.xpCost);

			for (int i = 0; i < this.fusionTable.getSizeInventory(); ++i)
			{
				ItemStack itemstack1 = this.fusionTable.getStackInSlot(i);

				if (itemstack1 != null)
				{
					if(itemstack1.getItem() == Items.WATER_BUCKET)
						continue;

					this.fusionTable.setInventorySlotContents(i, ItemStack.EMPTY);

					if (itemstack1.getItem().hasContainerItem(itemstack1))
					{
						ItemStack itemstack2 = itemstack1.getItem().getContainerItem(itemstack1);

						if (itemstack2 != null && itemstack2.isItemStackDamageable() && itemstack2.getItemDamage() > itemstack2.getMaxDamage())
						{
							MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(this.player, itemstack2, null));
							continue;
						}

						if (!this.player.inventory.addItemStackToInventory(itemstack2))
						{
							if (this.fusionTable.getStackInSlot(i) == null)
							{
								this.fusionTable.setInventorySlotContents(i, itemstack2);
							}
							else
							{
								this.player.dropItem(itemstack2, false);
							}
						}
					}
				}
			}
		}
		return itemStack;
    }
}
