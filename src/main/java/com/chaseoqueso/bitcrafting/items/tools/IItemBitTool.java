package com.chaseoqueso.bitcrafting.items.tools;

import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public interface IItemBitTool {

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
    static ItemStack initialize(ItemStack stack, NonNullList<ItemStack> array, float damage, float durability, float enchantability, ArrayList<String> effects, ArrayList<Float> effectChances, ArrayList<Float> effectPowers, int harvestLevel)
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

        //Sets all NBT data
        tagCompound.setTag("BitArray", nbttaglist);
        tagCompound.setIntArray("ColorArray", colorArray);
        tagCompound.setFloat("Damage", damage);
        tagCompound.setFloat("Durability", durability);
        tagCompound.setFloat("Enchantability", enchantability);
        tagCompound.setInteger("HarvestLevel", harvestLevel);
        tagCompound.setInteger("Uses", 0);

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

        return stack;
    }

    static void addNoise(int[] colorIn)
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

    static ItemStack[] getBits(ItemStack stack)
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

    static String getColorArrayAsString(ItemStack stack)
    {
        if(!stack.hasTagCompound())
            return "";

        NBTTagCompound itemData = stack.getTagCompound();
        if(!itemData.hasKey("ColorArray"))
            return "";

        return Arrays.toString(itemData.getIntArray("ColorArray")).replaceAll("\\[", "").replaceAll(" ", "").replaceAll("\\]", "").replaceAll("\\s", "");
    }
}
