package com.chaseoqueso.bitcrafting.container;

import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.slots.FusionInputSlot;
import com.chaseoqueso.bitcrafting.slots.FusionSlot;
import com.chaseoqueso.bitcrafting.tileentity.TileEntityBitFusionTable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ContainerBitFusionTable extends Container {

	private TileEntityBitFusionTable tileFusionTable;
	private EntityPlayer player;
	public IInventory craftResult = new InventoryCraftResult();
	public int xpCost = 0;

	public ContainerBitFusionTable(InventoryPlayer player, TileEntityBitFusionTable tileentity) {
		this.tileFusionTable = tileentity;
		this.player = player.player;
		this.tileFusionTable.setEventHandler(this);

		this.addSlotToContainer(new FusionInputSlot(tileentity, 0, 25, 34));

		int i;
		for (i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				this.addSlotToContainer(new Slot(tileentity, 1 + i * 3 + j, 62 + j * 18, 16 + i * 18));

		this.addSlotToContainer(new FusionSlot(player.player, tileentity, craftResult, 0, 144, 34));

		for (i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 90 + i * 18));
			}
		}

		for (i = 0; i < 9; i++) {
			this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 148));
		}

		this.onCraftMatrixChanged(this.tileFusionTable);
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		boolean canFuse = false;

		for (int i = 1; i < 10; i++) {
			if (tileFusionTable.getStackInSlot(i).getItem() instanceof ItemBit)
				canFuse = true;
		}

		ItemStack stack = tileFusionTable.getStackInSlot(0);

		if (!(stack.getItem() instanceof ItemBit || (stack.getItem() instanceof IItemBitTool && stack.isItemDamaged())))
			canFuse = false;

		if (!canFuse) {
			this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
			xpCost = 0;
			return;
		}

		stack = stack.copy();

		if (stack.getItem() instanceof ItemBit) {
			NBTTagCompound tagCompound = stack.getTagCompound();
			stack.setCount(1);

			String effect = "";
			float damage = 0, durability = 0, enchantability = 0, chance = 0, power = 0;
			int harvestLevel = 0;
			ItemStack tempstack;

			for (int i = 0; i < 10; ++i) {
				tempstack = tileFusionTable.getStackInSlot(i);
				if (tempstack.getItem() instanceof ItemBit) {
					if (!tempstack.hasTagCompound()) {
						return;
					}

					NBTTagCompound tempTagCompound = tempstack.getTagCompound();
					damage += tempTagCompound.getFloat("damage") * tempstack.getCount();
					durability += tempTagCompound.getFloat("durability") * tempstack.getCount();
					enchantability += tempTagCompound.getFloat("enchantability") * tempstack.getCount();
					int tempHarvestLevel = tempTagCompound.getInteger("harvestLevel");
					if(tempHarvestLevel > harvestLevel)
						harvestLevel = tempHarvestLevel;

					if (tempTagCompound.hasKey("effect"))
					{
						if (effect.equals(""))
						{
							effect = tempTagCompound.getString("effect");
							chance = 1 - (float)Math.pow(1 - tempTagCompound.getFloat("chance"), tempstack.getCount());
							power = tempTagCompound.getFloat("power") * tempstack.getCount();
						}
						else
						{
							chance = 1 - ((1 - chance) * (float)Math.pow(1 - tempTagCompound.getFloat("chance"), tempstack.getCount()));
							power += tempTagCompound.getFloat("power") * tempstack.getCount();
						}
					}
				}
			}

			xpCost = this.xpCost(damage, durability, enchantability, chance, power);
			if (this.player.experienceLevel >= xpCost || this.player.capabilities.isCreativeMode)
			{
				if (effect.equals(""))
					this.craftResult.setInventorySlotContents(0, ItemBit.setBit(stack, tagCompound.getString("color"), tagCompound.getString("shade"), damage, durability, enchantability, harvestLevel).copy());
				else
					this.craftResult.setInventorySlotContents(0, ItemBit.setBit(stack, tagCompound.getString("color"), tagCompound.getString("shade"), damage, durability, enchantability, effect, chance, power, harvestLevel).copy());
			}
		}
		else if(stack.getItem() instanceof IItemBitTool)
		{
			NBTTagCompound tagCompound = stack.getTagCompound();
			int uses = tagCompound.getInteger("Uses");
			int harvestLevel = tagCompound.getInteger("HarvestLevel");

			ItemStack tempstack;
			for (int i = 9; i > 0; i--) {
				tempstack = tileFusionTable.getStackInSlot(i);

				if (tempstack != ItemStack.EMPTY)
				{
					if (!tempstack.hasTagCompound())
						continue;

					if(tempstack.getTagCompound().getInteger("harvestLevel") < harvestLevel)
					{
						this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
						return;
					}

					if (uses > 0)
						uses -= Math.min(Math.max((int) tempstack.getTagCompound().getFloat("durability"), 1) * tempstack.getCount(), uses);
					else
						break;
				}
			}

			tagCompound.setInteger("Uses", uses);
			this.craftResult.setInventorySlotContents(0, stack);
		}
		else
		{
			this.craftResult.setInventorySlotContents(0, ItemStack.EMPTY);
		}
	}

	private int xpCost(float damage, float durability, float enchantability, float chance, float power) {
		//Used a linear regression based on intended experience cost, rounded to nicer numbers. Effects added on top
		return (int) ( Math.max(damage * 16 + durability * 0.4F + enchantability * 50 - 6.4, 0) + (chance * 100 + power * 5) );
	}

	@Override
	public void onContainerClosed(EntityPlayer p_75134_1_) {
		InventoryPlayer inventoryplayer = p_75134_1_.inventory;

		if (inventoryplayer.getItemStack() != ItemStack.EMPTY) {
			p_75134_1_.dropItem(inventoryplayer.getItemStack(), false);
			inventoryplayer.setItemStack(ItemStack.EMPTY);
		}
	}

	public boolean canInteractWith(EntityPlayer player) {
		return this.tileFusionTable.isUsableByPlayer(player);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 11)
			{
				if (!this.mergeItemStack(itemstack1, 11, 47, false))
					return ItemStack.EMPTY;
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (index >= 11 && index < 38 && !this.mergeItemStack(itemstack1, 38, 47, false))
			{
				return ItemStack.EMPTY;
			}
			else if (index >= 38 && index < 47 && !this.mergeItemStack(itemstack1, 11, 38, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.getCount() == 0)
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
				return ItemStack.EMPTY;
			slot.onTake(player, itemstack1);
		}
		return itemstack;
	}
}
