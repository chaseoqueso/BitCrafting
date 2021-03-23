package com.chaseoqueso.bitcrafting.items.tools;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class ItemBitHoe extends ItemHoe implements IItemBitTool {

    public ItemBitHoe()
    {
        super(EnumHelper.addToolMaterial("BitHoe", 0, Integer.MAX_VALUE, 0, -4, 0));
        setUnlocalizedName("ItemBitHoe");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembithoe"));
        setCreativeTab(null);
    }

    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass, net.minecraft.entity.player.EntityPlayer player, IBlockState blockState)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound itemData = stack.getTagCompound();
            return itemData.getInteger("HarvestLevel");
        }
        return -1;
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

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return (stack.isItemEnchanted() || (stack.hasTagCompound() && stack.getTagCompound().hasKey("EffectArray")));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase player)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound itemData = stack.getTagCompound();
            itemData.setInteger("Uses", itemData.getInteger("Uses") + 2);
            if(itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
                stack.shrink(1);
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase blockDestroyer)
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

    public void activateEffect(String effect, float power, List<ItemStack> drops, BlockPos pos, IBlockState state, EntityLivingBase blockDestroyer, World world)
    {
        switch(effect)
        {
            case "fire":
                break;

            case "lightning":
                break;

            case "earth":
                break;

            case "ice":
                break;

            case "spatial":
                break;
        }
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        if(!stack.hasTagCompound())
            return -1;
        NBTTagCompound itemData = stack.getTagCompound();
        return (int)itemData.getFloat("Durability");
    }

    @Override
    public int getDamage(ItemStack stack)
    {
        if(!stack.hasTagCompound())
            return -1;
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
    public int getItemEnchantability(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound itemData = stack.getTagCompound();
            return (int)itemData.getFloat("Enchantability");
        } else {
            return -1;
        }
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
            tooltip.add("Harvest Level: " + itemData.getInteger("HarvestLevel"));
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
}
