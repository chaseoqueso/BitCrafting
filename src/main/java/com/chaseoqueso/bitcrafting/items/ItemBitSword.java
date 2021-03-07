package com.chaseoqueso.bitcrafting.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class ItemBitSword extends ItemSword {
	
	public ItemBitSword()
	{
		super(EnumHelper.addToolMaterial("BitSword", 0, Integer.MAX_VALUE, 0, -4, 0));
		setUnlocalizedName("ItemBitSword");
		setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembitsword"));
		setCreativeTab(null);
	}

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(ItemStack stack, int pass)
    {
		if(!stack.hasTagCompound())
			return 0;
        NBTTagCompound itemData = stack.getTagCompound();
        int[] colors = itemData.getIntArray("ColorArray");
        return colors[itemData.getIntArray("Indices")[pass]];
    }
	
	private static void addNoise(int[] colorIn)
	{
		Random rand = new Random();
		for(int i = 0; i < colorIn.length; i++)
		{
			if(colorIn[i] != -1)
			{
				int r = (colorIn[i] >> 16) & 255;
				int g = (colorIn[i] >> 8) & 255;
				int b = colorIn[i] & 255;
				int alter = rand.nextInt(15) - 7;
				r = (Math.max(Math.min(r + alter, 255), 0));
				g = (Math.max(Math.min(g + alter, 255), 0));
				b = (Math.max(Math.min(b + alter, 255), 0));
				colorIn[i] = (r << 16) + (g << 8) + b;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return (stack.isItemEnchanted() || (stack.hasTagCompound() && stack.getTagCompound().hasKey("EffectArray")));
    }
	
    /**
     * Called only at the moment the weapon is forged.
     * Sets the weapon's name and Bit array, as well as the name used for accessing the ResourceLocation of the weapon's texture.
     * 
     * @param stack The ItemStack of the BitSword that is being created.
     * @param array The array of ItemBits that make up the weapon (used for color data).
	 * @param damage The damage of the sword
	 * @param durability The durability of the sword
	 * @param enchantability The enchantability of the sword
     * @param effects A list of all special effects that the sword has
	 * @param effectChances A list of the probabilities of a given effect triggering
	 * @param effectPowers A list of the strength of each effect
	 * @return The ItemStack, but with NBT data attached.
     */
	public static ItemStack initialize(ItemStack stack, NonNullList<ItemStack> array, float damage, float durability, float enchantability, ArrayList<String> effects, ArrayList<Float> effectChances, ArrayList<Float> effectPowers)
	{
    	//Initialize all variables.
		NBTTagCompound tagCompound = new NBTTagCompound();
		NBTTagList nbttaglist = new NBTTagList();
		NBTTagList nbteffectlist = new NBTTagList();
		int[] colorArray = new int[256];

		stack.setTagCompound(tagCompound);
		
		//Goes through the ItemBit array and turns them into NBT data for the BitSword. Turns nulls into dataless Bits. Takes the color of each Bit and puts it in the color array.
        for(int i = 0; i < array.size(); i++)
        {
			if(array.get(i) == ItemStack.EMPTY)
			{
				array.set(i, new ItemStack(BitCraftingItems.ITEMS.itemBit));
			}

            NBTTagCompound nbttagcompound = new NBTTagCompound();
            array.get(i).writeToNBT(nbttagcompound);
            nbttaglist.appendTag(nbttagcompound);
            colorArray[i] = ItemBit.getColorFromStack(array.get(i));
        }
        addNoise(colorArray);
        
        //Adds each effect to nbteffectlist as an NBT tag with effect name, chance, and power
        if(effects.size() > 0)
        {
        	for(int j = 0; j < effects.size(); ++j)
        	{
	            NBTTagCompound nbttagcompound = new NBTTagCompound();
	            nbttagcompound.setString("effect", effects.get(j));
	            nbttagcompound.setFloat("chance", effectChances.get(j));
	            nbttagcompound.setFloat("power", effectPowers.get(j));
	            nbteffectlist.appendTag(nbttagcompound);
        	}
            tagCompound.setTag("EffectArray", nbteffectlist);
        }

        //Sets all NBT data
        tagCompound.setTag("BitArray", nbttaglist);
        tagCompound.setIntArray("ColorArray", colorArray);
        tagCompound.setFloat("Damage", damage);
        tagCompound.setFloat("Durability", durability);
        tagCompound.setFloat("Enchantability", enchantability);
        tagCompound.setInteger("Uses", 0);

        System.out.println(damage);
        
	    return stack;
	}
	
	public static ItemStack[] getBits(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			return null;
		NBTTagCompound itemData = stack.getTagCompound();
		NBTTagList nbttaglist = itemData.getTagList("BitArray", 10);
		ArrayList<ItemStack> array = new ArrayList<ItemStack>();
		for (int i = 0, j = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			ItemStack tempstack = new ItemStack(nbttagcompound);
			if(tempstack.hasTagCompound())
			{
				boolean existsAlready = false;
				for(int l = 0; l < j; l++)
				{
					if(tempstack.isItemEqual(array.get(l)) && ItemBit.bitsAreEqual(tempstack, array.get(l)) && array.get(l).getCount() < array.get(l).getMaxStackSize())
					{
						existsAlready = true;
						array.get(l).grow(1);
						break;
					}
				}
				if(!existsAlready)
				{
					array.add(tempstack);
					j++;
				}
			}
        }
		return array.toArray(new ItemStack[array.size()]);
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase par2, EntityLivingBase par3)
    {
		if(stack.hasTagCompound())
		{
			NBTTagCompound itemData = stack.getTagCompound();
	        itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
	        if(itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
	        	stack.shrink(1);
	        if(itemData.hasKey("EffectArray"))
	        {
	        	Random rand = new Random();
	        	NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
	        	for(int i = 0; i < effectlist.tagCount(); ++i)
	        	{
	        		NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
	        		if(rand.nextFloat() < effectData.getFloat("chance"))
	        		{
	        			activateEffect(effectData.getString("effect"), effectData.getFloat("power"));
	        		}
	        	}
	        }
		}
        return true;
    }
	
	private void activateEffect(String effect, float power) 
	{
		if(effect.equals("fire"))
		{
			return;
		}
		if(effect.equals("earth"))
		{
			return;
		}
		if(effect.equals("lightning"))
		{
			return;
		}
		if(effect.equals("ice"))
		{
			return;
		}
		if(effect.equals("spatial"))
		{
			return;
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D && stack.hasTagCompound())
        {
        	NBTTagCompound itemData = stack.getTagCompound();
	        itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
	        if(itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
	        	stack.shrink(1);
        }
        return true;
    }
	
	@Override
	public int getMaxDamage(ItemStack stack)
    {
		if(!stack.hasTagCompound())
	        return super.getDamage(stack);
		NBTTagCompound itemData = stack.getTagCompound();
        return (int)itemData.getFloat("Durability");
    }
	
	@Override
	public int getDamage(ItemStack stack)
    {
		if(!stack.hasTagCompound())
	        return super.getDamage(stack);
		NBTTagCompound itemData = stack.getTagCompound();
        return itemData.getInteger("Uses");
    }
	
	@Override
	public boolean isDamaged(ItemStack stack)
    {
		if(!stack.hasTagCompound())
	        return false;
		NBTTagCompound itemData = stack.getTagCompound();
        return itemData.getInteger("Uses") > 0;
    }
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        if(stack.hasTagCompound())
        {
    		NBTTagCompound itemData = stack.getTagCompound();
        	tooltip.add("Durability: " + String.format("%.0f", itemData.getFloat("Durability")));
        	tooltip.add("Enchantability: " + String.format("%.0f", itemData.getFloat("Enchantability")));
        	if (itemData.hasKey("EffectArray")) {
        		NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
        		for(int i = 0; i < effectlist.tagCount(); ++i)
        		{
        			NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
					String text = (String) (effectData.getString("effect").equals("fire")
							? TextFormatting.RED + "Enflame" + TextFormatting.RESET
							: effectData.getString("effect").equals("earth")
									? TextFormatting.DARK_GRAY + "Stonestrike" + TextFormatting.RESET
									: effectData.getString("effect").equals("lightning")
											? TextFormatting.YELLOW + "Stormcall" + TextFormatting.RESET
											: effectData.getString("effect").equals("ice")
													? TextFormatting.AQUA + "Frostbite" + TextFormatting.RESET
													: TextFormatting.DARK_PURPLE + "" + TextFormatting.OBFUSCATED
															+ "Anomolize" + TextFormatting.RESET);
					tooltip.add(text + " (" + String.format("%.3f", effectData.getFloat("chance")*100) + "%, " + String.format("%.3f", effectData.getFloat("power")) + ")");
        		}
			}
        }
    }
	
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack)
    {
		Multimap<String, AttributeModifier> multimap = HashMultimap.create();

        double damage;
        if(stack.hasTagCompound() && slot == EntityEquipmentSlot.MAINHAND)
        {
        	NBTTagCompound itemData = stack.getTagCompound();
        	damage = itemData.getFloat("Damage");
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double)damage, 0));
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }
    
    @Override
    public int getItemEnchantability(ItemStack stack)
    {
    	if(stack.hasTagCompound())
        {
        	NBTTagCompound itemData = stack.getTagCompound();
        	return (int)itemData.getFloat("Enchantability");
        } else {
        	return super.getItemEnchantability();
        }
    }
    
    
    
    /*
	public static ResourceLocation getResourceLocation(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			return new ResourceLocation("bcm", "textures/items/itemBit.png");
        NBTTagCompound itemData = stack.getTagCompound();
        return new ResourceLocation(itemData.getString("Location"));
	}
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
    {
		IIcon i = itemIcon;
		if(!stack.hasTagCompound())
			return i;
		
        NBTTagCompound itemData = stack.getTagCompound();
	    BufferedImage icon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	    int[] colorArray = itemData.getIntArray("ColorArray");
	    
	    for (int j = 0; j < colorArray.length; j++) {
	        if(colorArray[j] != -1)
	        	icon.setRGB(j % 16, j / 16, colorArray[j]);
	    }
	    
		return Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(getResourceLocation(stack).toString());
    }*/
}
