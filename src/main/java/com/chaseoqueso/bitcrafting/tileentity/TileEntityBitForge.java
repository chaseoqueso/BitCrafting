package com.chaseoqueso.bitcrafting.tileentity;

import com.chaseoqueso.bitcrafting.container.ContainerBitForge;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.items.templates.ItemPickaxeTemplate;
import com.chaseoqueso.bitcrafting.items.templates.ItemSwordTemplate;
import com.chaseoqueso.bitcrafting.items.templates.ItemToolTemplate;
import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TileEntityBitForge extends TileEntity implements IInventory {
	
	private NonNullList<ItemStack> forgeItemStacks = NonNullList.withSize(257, ItemStack.EMPTY);
	private String forgeName = "Bit Forge";
	protected ContainerBitForge eventhandler;
	
	public void setForgeName(String displayName) {
		this.forgeName = displayName;
	}

	@Override
	public String getName() {
		return this.forgeName;
	}

	@Override
	public boolean hasCustomName() {
		return this.forgeName != null && !this.forgeName.isEmpty();
	}
	
	public void setEventHandler(Container handler)
	{
		this.eventhandler = (ContainerBitForge) handler;
	}

	public int getSizeInventory() {
		return this.forgeItemStacks.size();
	}

	public ItemStack getStackInSlot(int index) {
		return this.forgeItemStacks.get(index);
	}

	public ItemStack decrStackSize(int index, int count) {
		if(this.forgeItemStacks.get(index) != ItemStack.EMPTY)
		{
			ItemStack itemstack;
			if(this.forgeItemStacks.get(index).getCount() <= count)
			{
				itemstack = this.forgeItemStacks.get(index);
				this.forgeItemStacks.set(index, ItemStack.EMPTY);
                this.eventhandler.onCraftMatrixChanged(this);
                super.markDirty();
				return itemstack;
			} else {
				itemstack = this.forgeItemStacks.get(index).splitStack(count);
				if(this.forgeItemStacks.get(index).getCount() == 0)
				{
					this.forgeItemStacks.set(index, ItemStack.EMPTY);
				}
                this.eventhandler.onCraftMatrixChanged(this);
				super.markDirty();
				return itemstack;
			}
		}
		return null;
	}

	public ItemStack decrStackSizeNoUpdate(int index, int count) {
		if(this.forgeItemStacks.get(index) != ItemStack.EMPTY)
		{
			ItemStack itemstack;
			if(this.forgeItemStacks.get(index).getCount() <= count)
			{
				itemstack = this.forgeItemStacks.get(index);
				this.forgeItemStacks.set(index, ItemStack.EMPTY);
				super.markDirty();
				return itemstack;
			} else {
				itemstack = this.forgeItemStacks.get(index).splitStack(count);
				if(this.forgeItemStacks.get(index).getCount() == 0)
					this.forgeItemStacks.set(index, ItemStack.EMPTY);
				super.markDirty();
				return itemstack;
			}
		}
		return null;
	}

	public void setInventorySlotContents(int slot, ItemStack itemstack) {
		this.forgeItemStacks.set(slot, itemstack);
		if(itemstack != null && itemstack.getCount() > this.getInventoryStackLimit())
			itemstack.setCount(this.getInventoryStackLimit());
        this.eventhandler.onCraftMatrixChanged(this);
		super.markDirty();
	}

	public void setInventorySlotContentsNoUpdate(int slot, ItemStack itemstack) {
		this.forgeItemStacks.set(slot, itemstack);
		
		if(itemstack != null && itemstack.getCount() > this.getInventoryStackLimit())
		{
			itemstack.setCount(this.getInventoryStackLimit());
		}
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.forgeName : "Bit Forge";
	}

	public boolean hasCustomInventoryName() {
		return this.forgeName != null && this.forgeName.length() > 0;
	}

	public int getInventoryStackLimit() {
		return 1;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag)
	{
		super.readFromNBT(tag);
        this.forgeItemStacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(tag, this.forgeItemStacks);

        if (tag.hasKey("CustomName", 8))
        {
            this.forgeName = tag.getString("CustomName");
        }
		super.markDirty();
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag)
	{
		super.writeToNBT(tag);
		ItemStackHelper.saveAllItems(tag, forgeItemStacks);

        if (this.hasCustomInventoryName())
        {
            tag.setString("CustomName", this.forgeName);
        }

        return tag;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) {
		return world.getTileEntity(pos) == this && player.getDistanceSq((double) pos.getX() + .5D, (double) pos.getY() + .5D, (double) pos.getZ() + .5D) <= 64;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	public boolean isItemValidForSlot(int par1, ItemStack itemstack) {
		return false;
	}
	
	public boolean canForge()
	{
		if(forgeItemStacks.get(256).isEmpty() || !(forgeItemStacks.get(256).getItem() instanceof ItemToolTemplate))
			return false;

		for(int i = 0; i < forgeItemStacks.size() - 1; i++)
		{
			if(!forgeItemStacks.get(i).isEmpty())
				return true;
		}

		return false;
	}
	
	public ItemStack forgeItem()
	{
		if(this.canForge())
		{
			float damage = 0, durability = 0, enchantability = 0;
			ArrayList<String> effects = new ArrayList<String>();
			ArrayList<Float> effectChances = new ArrayList<Float>();
			ArrayList<Float> effectPowers = new ArrayList<Float>();
			Map<Integer, Integer> harvestLevelOccurrances = new HashMap();
			
			for(int i = 0; i < 256; i++)
			{
				if(forgeItemStacks.get(i) != ItemStack.EMPTY && forgeItemStacks.get(i).hasTagCompound())
		        {
		            NBTTagCompound itemData = forgeItemStacks.get(i).getTagCompound();
		            
		            if (itemData.hasKey("damage"))
		            {
						damage += itemData.getFloat("damage");
						durability += itemData.getFloat("durability");
						enchantability += itemData.getFloat("enchantability");

						int harvestLevel = itemData.getInteger("harvestLevel");
						if(harvestLevelOccurrances.containsKey(harvestLevel))
						{
							harvestLevelOccurrances.put(harvestLevel, harvestLevelOccurrances.get(harvestLevel) + 1);
						}
						else
						{
							harvestLevelOccurrances.put(harvestLevel, 1);
						}
		            }
		            
		            if (itemData.hasKey("effect"))
		            {
		            	String e = itemData.getString("effect");
		            	if(effects.contains(e))
		            	{
		            		int index = effects.indexOf(e);
		            		effectChances.set(index, 1 - ((1 - effectChances.get(index)) * (1 - itemData.getFloat("chance"))));
		            		effectPowers.set(index, effectPowers.get(index) + itemData.getFloat("power"));
		            	}
		            	else
		            	{
			            	effects.add(e);
			            	effectChances.add(itemData.getFloat("chance"));
			            	effectPowers.add(itemData.getFloat("power"));
		            	}
		            }
		        }
			}

			int harvestLevel = 0;
			for(int level : harvestLevelOccurrances.keySet())
			{
				if(level > harvestLevel && harvestLevelOccurrances.get(level) > 64)
				{
					harvestLevel = level;
				}
			}

			NonNullList<ItemStack> forgeStacksClone = NonNullList.withSize(forgeItemStacks.size() - 1, ItemStack.EMPTY);
			for (int i = 0; i < forgeItemStacks.size() - 1; ++i) {
				forgeStacksClone.set(i, forgeItemStacks.get(i).copy());
			}

			//Returns the new tool that is created based on certain damage, durability, etc. Also gives the new weapon an array of the Bits used for image generating purposes.
			Item templateType = forgeItemStacks.get(256).getItem();
			if(templateType instanceof ItemSwordTemplate)
				return IItemBitTool.initialize(new ItemStack(BitCraftingItems.ITEMS.itemBitSword), forgeStacksClone, damage, durability, enchantability, effects, effectChances, effectPowers, harvestLevel).copy();
			if(templateType instanceof ItemPickaxeTemplate)
				return IItemBitTool.initialize(new ItemStack(BitCraftingItems.ITEMS.itemBitPickaxe), forgeStacksClone, damage, durability, enchantability, effects, effectChances, effectPowers, harvestLevel).copy();
		}
		return null;
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack stack : forgeItemStacks ) {
			if(!stack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(forgeItemStacks, index);
	}

	@Override
	public void clear()	{ forgeItemStacks.clear(); }

	@Override
	public int getField(int id) { return 0; }

	@Override
	public void setField(int id, int value)	{}

	@Override
	public int getFieldCount()
	{
		return 0;
	}
}
