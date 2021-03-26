package com.chaseoqueso.bitcrafting.items.tools;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ItemBitShovel extends ItemSpade implements IItemBitTool {

    private static final List<Block> SHOVEL_BLOCKS = Arrays.asList(Blocks.CLAY, Blocks.DIRT, Blocks.FARMLAND, Blocks.GRASS, Blocks.GRAVEL, Blocks.MYCELIUM, Blocks.SAND, Blocks.SNOW, Blocks.SNOW_LAYER, Blocks.GRASS_PATH, Blocks.CONCRETE_POWDER);

    public ItemBitShovel()
    {
        super(EnumHelper.addToolMaterial("BitShovel", 0, Integer.MAX_VALUE, 0, -4, 0));
        setUnlocalizedName("ItemBitShovel");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembitshovel"));
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
        if(material != Material.CLAY && material != Material.CRAFTED_SNOW && material != Material.GRASS && material != Material.SNOW && material != Material.GROUND && material != Material.SAND)
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
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage - 1.5, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.attackSpeed, 0));
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
                stack.shrink(1);
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
                stack.shrink(1);
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
                        ((ItemBitShovel)stack.getItem()).activateEffect(effectData.getString("effect"), effectData.getFloat("power"), drops, pos, state, player, worldIn, stack);
                    }
                }
            }
        }
    }

    public void activateEffect(String effect, float power, List<ItemStack> drops, BlockPos pos, IBlockState state, EntityLivingBase player, World world, ItemStack shovel)
    {
        switch(effect)
        {
            case "fire":
                List<ItemStack> newDrops = new ArrayList();

                NonNullList<ItemStack> defaultDrops = NonNullList.create();
                state.getBlock().getDrops(defaultDrops, world, pos, state, 0);

                for(int i = 0; i < defaultDrops.size(); ++i)
                {
                    final float fortuneChance = 0.1f;
                    int fortune = 1;
                    Random rand = world.rand;
                    for(int j = 0; j < power; ++j)
                    {
                        double chance = rand.nextDouble();

                        if(chance < fortuneChance)
                        {
                            ++fortune;
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
                        smeltResult.setCount(fortune);
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

                for(int i = 1; i < power + 1; ++i)
                {
                    BlockPos position = new BlockPos(pos.getX() + xAmount*i, pos.getY() + yAmount*i, pos.getZ() + zAmount*i);
                    IBlockState blockAtPosition = world.getBlockState(position);

                    Material material = blockAtPosition.getMaterial();
                    if(material != Material.CLAY && material != Material.CRAFTED_SNOW && material != Material.GRASS && material != Material.SNOW && material != Material.GROUND && material != Material.SAND)
                        break;

                    NonNullList<ItemStack> blockDrops = NonNullList.create();
                    blockAtPosition.getBlock().getDrops(blockDrops, world, position, state, 0);

                    for (ItemStack drop : blockDrops) {
                        world.spawnEntity(new EntityItem(world, position.getX(), position.getY(), position.getZ(), drop));
                    }

                    world.setBlockToAir(position);
                }
                break;

            case "ice":
                for(int i = 0; i < drops.size(); ++i)
                {
                    Block drop = state.getBlock();
                    if(drop == Blocks.DIRT && state.getBlock().getMetaFromState(state) == 1)
                    {
                        drops.set(i, new ItemStack(Blocks.GRAVEL));
                    }
                    else if(drop == Blocks.GRAVEL)
                    {
                        drops.set(i, new ItemStack(Blocks.COBBLESTONE));
                    }
                    else if(drop == Blocks.SNOW)
                    {
                        drops.set(i, new ItemStack(Blocks.ICE));
                    }
                    else if(drop == Blocks.CLAY)
                    {
                        drops.set(i, new ItemStack(Blocks.HARDENED_CLAY));
                    }
                    else if(drop == Blocks.CONCRETE_POWDER)
                    {
                        drops.set(i, new ItemStack(Blocks.CONCRETE, 1, state.getBlock().getMetaFromState(state)));
                    }
                    else if(drop == Blocks.SAND)
                    {
                        drops.set(i, new ItemStack(Blocks.SANDSTONE));
                    }
                    else if(drop == Blocks.MYCELIUM)
                    {
                        drops.set(i, new ItemStack(Blocks.BROWN_MUSHROOM, 4));
                    }
                    else if(state.getMaterial() == Material.GROUND)
                    {
                        if(drop != Blocks.DIRT || state.getBlock().getMetaFromState(state) == 0)
                            drops.set(i, new ItemStack(Blocks.DIRT, 1, 1));
                    }
                }
                break;

            case "spatial":
                drops.clear();
                drops.add(new ItemStack(SHOVEL_BLOCKS.get(world.rand.nextInt(SHOVEL_BLOCKS.size()))));
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
                    String text = (String) (effectData.getString("effect").equals("fire")
                                                    ? TextFormatting.RED + "Scorchspade" + TextFormatting.RESET
                                                    : effectData.getString("effect").equals("earth")
                                                              ? TextFormatting.DARK_GRAY + "Excavate" + TextFormatting.RESET
                                                              : effectData.getString("effect").equals("lightning")
                                                                        ? TextFormatting.YELLOW + "Swiftshovel" + TextFormatting.RESET
                                                                        : effectData.getString("effect").equals("ice")
                                                                                  ? TextFormatting.AQUA + "Cryocompress" + TextFormatting.RESET
                                                                                  : TextFormatting.DARK_PURPLE + "" + TextFormatting.OBFUSCATED
                                                                                    + "Anomalize" + TextFormatting.RESET);
                    tooltip.add(text + " (" + String.format("%.3f", effectData.getFloat("chance")*100) + "%, " + String.format("%.3f", effectData.getFloat("power")) + ")");
                }
            }
        }
    }
}
