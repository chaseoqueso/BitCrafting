package com.chaseoqueso.bitcrafting.slots;

import com.chaseoqueso.bitcrafting.gui.GUIBitForge;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitForge;

import com.google.common.collect.Lists;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerDestroyItemEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class ForgeSlot extends Slot {

    /** The craft matrix inventory linked to this result slot. */
    private final TileEntityBitForge craftMatrix;
    /** The player that is using the GUI where this slot resides. */
    private EntityPlayer player;
    public GUIBitForge gui;
    private int amountCrafted;
	
	public ForgeSlot(EntityPlayer player, IInventory tileForge, IInventory result, int index, int x, int y) {
		super(result, index, x, y);
        this.player = player;
        this.craftMatrix = (TileEntityBitForge)tileForge;
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
            net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, craftMatrix);
        }

        this.amountCrafted = 0;
        InventoryCraftResult inventorycraftresult = (InventoryCraftResult)this.inventory;
        IRecipe irecipe = inventorycraftresult.getRecipeUsed();

        if (irecipe != null && !irecipe.isDynamic())
        {
            this.player.unlockRecipes(Lists.newArrayList(irecipe));
            inventorycraftresult.setRecipeUsed(null);
        }
    }
	
	@Override
    public ItemStack onTake(EntityPlayer playerIn, ItemStack stack)
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
                        MinecraftForge.EVENT_BUS.post(new PlayerDestroyItemEvent(player, itemstack2, null));
                        continue;
                    }

                    if (!this.player.inventory.addItemStackToInventory(itemstack2))
                    {
                        if (this.craftMatrix.getStackInSlot(i) == null)
                        {
                            this.craftMatrix.setInventorySlotContents(i, itemstack2);
                        }
                        else
                        {
                            this.player.dropItem(itemstack2, false);
                        }
                    }
                }
            }
        }

        return stack;
    }

    @Override
    public void onSlotChanged() {}
}
