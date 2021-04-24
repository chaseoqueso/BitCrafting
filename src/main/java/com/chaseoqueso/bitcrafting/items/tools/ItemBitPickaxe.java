package com.chaseoqueso.bitcrafting.items.tools;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ItemBitPickaxe extends ItemPickaxe implements IItemBitTool {

    public ItemBitPickaxe()
    {
        super(EnumHelper.addToolMaterial("BitPickaxe", 0, Integer.MAX_VALUE, 0, -4, 0));
        setUnlocalizedName("ItemBitPickaxe");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembitpickaxe"));
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
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        Material material = state.getMaterial();
        if(material != Material.IRON && material != Material.ANVIL && material != Material.ROCK)
            return super.getDestroySpeed(stack, state);

        if(stack.hasTagCompound())
        {
            NBTTagCompound itemData = stack.getTagCompound();
            return itemData.getFloat("Damage");
        }
        return 0;
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
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", Math.max(damage - 2, 0), 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.8F, 0));
        }

        return multimap;
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return (stack.isItemEnchanted() || (stack.hasTagCompound() && stack.getTagCompound().hasKey("EffectArray")));
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
    {
        if(stack.hasTagCompound() && !(attacker instanceof EntityPlayer && ( (EntityPlayer)attacker ).capabilities.isCreativeMode))
        {
            NBTTagCompound itemData = stack.getTagCompound();
            itemData.setInteger("Uses", itemData.getInteger("Uses") + 2);
            if(itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
            {
                attacker.renderBrokenItemStack(stack);
                stack.shrink(1);
            }
        }
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase blockDestroyer)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D && stack.hasTagCompound() && !(blockDestroyer instanceof EntityPlayer && ( (EntityPlayer)blockDestroyer ).capabilities.isCreativeMode))
        {
            NBTTagCompound itemData = stack.getTagCompound();
            itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
            if(itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
            {
                blockDestroyer.renderBrokenItemStack(stack);
                stack.shrink(1);
            }
        }
        return true;
    }

    public void activateAllEffects(ItemStack stack, IBlockState state, BlockPos pos, EntityLivingBase player, World worldIn, List<ItemStack> drops)
    {
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D && stack.hasTagCompound())
        {
            NBTTagCompound itemData = stack.getTagCompound();
            if(itemData.hasKey("EffectArray") && itemData.getFloat("Damage") == getDestroySpeed(stack, state))
            {
                Random rand = worldIn.rand;
                NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
                for(int i = 0; i < effectlist.tagCount(); ++i)
                {
                    NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
                    if(rand.nextFloat() < effectData.getFloat("chance"))
                    {
                        ((ItemBitPickaxe)stack.getItem()).activateEffect(effectData.getString("effect"), effectData.getFloat("power"), drops, pos, state, player, worldIn, stack);
                    }
                }
            }
        }
    }

    public void activateEffect(String effect, float power, List<ItemStack> drops, BlockPos pos, IBlockState state, EntityLivingBase player, World world, ItemStack pickaxe)
    {
        switch(effect)
        {
            case "fire":
                List<ItemStack> newDrops = new ArrayList();

                NonNullList<ItemStack> defaultDrops = NonNullList.create();
                state.getBlock().getDrops(defaultDrops, world, pos, state, 0);

                for(int i = 0; i < defaultDrops.size(); ++i)
                {
                    final float bonusChance = 0.1f;
                    int bonusAmount = 1;
                    Random rand = world.rand;
                    for(int j = 0; j < power; ++j)
                    {
                        double chance = rand.nextDouble();

                        if(chance < bonusChance)
                        {
                            ++bonusAmount;
                        }
                    }

                    ItemStack drop = defaultDrops.get(i);
                    ItemStack smeltResult = FurnaceRecipes.instance().getSmeltingResult(drop);

                    if(smeltResult == ItemStack.EMPTY)
                    {
                        newDrops.add(drop);
                    }
                    else
                    {
                        smeltResult.setCount(bonusAmount);
                        newDrops.add(smeltResult);
                    }
                }
                drops.clear();
                drops.addAll(newDrops);
                break;

            case "lightning":
                Potion haste = Potion.getPotionFromResourceLocation("haste");
                PotionEffect potionEffect;
                if(player.isPotionActive(haste))
                {
                    potionEffect = new PotionEffect(haste, (int)power * 20, Math.min(player.getActivePotionEffect(haste).getAmplifier() + 1, 3));
                    player.removePotionEffect(haste);
                }
                else
                {
                    potionEffect = new PotionEffect(haste, (int)power * 20, 1);
                }
                player.addPotionEffect(potionEffect);
                break;

            case "earth":
                double lookAngle = player.rotationYaw + 90;
                if(lookAngle < 0)
                    lookAngle += 360;
                lookAngle = Math.toRadians(lookAngle);

                double lookPitch = player.rotationPitch;
                if(lookPitch < 0)
                    lookPitch += 360;
                lookPitch = Math.toRadians(lookPitch);

                double xAmount = Math.cos(lookAngle);
                double yAmount = -Math.sin(lookPitch);
                double zAmount = Math.sin(lookAngle);

                double maxHorAmount = Math.max(Math.abs(xAmount),Math.abs(zAmount));

                if(Math.abs(yAmount) > 0.8)
                {
                    xAmount = 0;
                    yAmount = 1 * Math.signum(yAmount);
                    zAmount = 0;
                }
                else if(Math.abs(xAmount) == maxHorAmount)
                {
                    xAmount = 1 * Math.signum(xAmount);
                    yAmount = 0;
                    zAmount = 0;
                }
                else
                {
                    xAmount = 0;
                    yAmount = 0;
                    zAmount = 1 * Math.signum(zAmount);
                }

                //Temporarily remove the earth effect to avoid infinite recursion
                NBTTagCompound itemData = pickaxe.getTagCompound();
                NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
                NBTTagCompound effectData = effectlist.getCompoundTagAt(0);

                for(int j = 0; j < effectlist.tagCount(); ++j)
                {
                    effectData = effectlist.getCompoundTagAt(j);
                    if(effectData.getString("effect").equals("earth"))
                    {
                        effectlist.removeTag(j);
                    }
                }
                itemData.setTag("EffectArray", effectlist);

                for(int i = 1; i < power; ++i)
                {
                    BlockPos position = new BlockPos(pos.getX() + xAmount*i, pos.getY() + yAmount*i, pos.getZ() + zAmount*i);
                    IBlockState blockAtPosition = world.getBlockState(position);

                    Material material = blockAtPosition.getMaterial();
                    if(material != Material.IRON && material != Material.ANVIL && material != Material.ROCK)
                        break;

                    //Harvest the block
                    blockAtPosition.getBlock().harvestBlock(world, (EntityPlayer) player, position, blockAtPosition, null, pickaxe);
                    world.setBlockToAir(position);
                }

                //Add the earth effect back to the pickaxe
                effectlist.appendTag(effectData);
                itemData.setTag("EffectArray", effectlist);
                break;

            case "ice":
                drops.clear();
                drops.add(new ItemStack(state.getBlock()));
                drops.add(new ItemStack(Items.SNOWBALL, (int)(world.rand.nextFloat() * power)));
                break;

            case "spatial":
                List<Block> potentialOres = new ArrayList();
                for(String ore : OreDictionary.getOreNames())
                {
                    if(ore.contains("ore"))
                    {
                        for(ItemStack i : OreDictionary.getOres(ore))
                        {
                            if(i.getItem() instanceof  ItemBlock)
                                potentialOres.add(( (ItemBlock)i.getItem() ).getBlock());
                        }
                    }
                }

                potentialOres.remove(state.getBlock());
                for(int i = 0; i < potentialOres.size(); ++i)
                {
                    if(potentialOres.get(i).getHarvestLevel(potentialOres.get(i).getDefaultState()) > state.getBlock().getHarvestLevel(state))
                    {
                        potentialOres.remove(i);
                        --i;
                    }
                }

                if(potentialOres.size() == 0)
                    break;

                NonNullList<ItemStack> oreDrops = NonNullList.create();

                Block blockOre = potentialOres.get((int)(world.rand.nextFloat() * potentialOres.size()));
                world.setBlockState(pos, blockOre.getDefaultState());
                blockOre.getDrops(oreDrops, world, pos, blockOre.getDefaultState(), EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, pickaxe));
                world.setBlockToAir(pos);

                drops.clear();
                drops.addAll(oreDrops);
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
            float durability = itemData.getFloat("Durability");
            tooltip.add("Durability: " + String.format("%.0f", durability - itemData.getInteger("Uses")) + "/" + String.format("%.0f", durability));
            tooltip.add("Enchantability: " + String.format("%.0f", itemData.getFloat("Enchantability")));
            tooltip.add("Harvest Level: " + itemData.getInteger("HarvestLevel"));
            if (itemData.hasKey("EffectArray")) {
                NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
                for(int i = 0; i < effectlist.tagCount(); ++i)
                {
                    NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
                    String text = (effectData.getString("effect").equals("fire")
                            ? TextFormatting.RED + "Flametouch" + TextFormatting.RESET
                            : effectData.getString("effect").equals("earth")
                                      ? TextFormatting.DARK_GRAY + "Tunneler" + TextFormatting.RESET
                                      : effectData.getString("effect").equals("lightning")
                                                ? TextFormatting.YELLOW + "Speedmine" + TextFormatting.RESET
                                                : effectData.getString("effect").equals("ice")
                                                          ? TextFormatting.AQUA + "Snowtouch" + TextFormatting.RESET
                                                          : TextFormatting.DARK_PURPLE + "" + TextFormatting.OBFUSCATED + "Anomalize" + TextFormatting.RESET);
                    tooltip.add(text + " (" + String.format("%.3f", effectData.getFloat("chance")*100) + "%, " + String.format("%.3f", effectData.getFloat("power")) + ")");
                }
            }
        }
    }
}
