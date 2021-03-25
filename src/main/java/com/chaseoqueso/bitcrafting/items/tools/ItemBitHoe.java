package com.chaseoqueso.bitcrafting.items.tools;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import javafx.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class ItemBitHoe extends ItemHoe implements IItemBitTool {

    public static List<ItemStack> allCrops;

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
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 1, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3 / Math.pow(damage, 0.75), 0));
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

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, true);

        if(raytraceresult == null || worldIn.isRemote)
            return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);

        if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK)
        {
            BlockPos blockpos = raytraceresult.getBlockPos();

            if (!worldIn.isBlockModifiable(playerIn, blockpos) || !playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
            {
                return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
            }

            if (worldIn.getBlockState(blockpos).getMaterial() == Material.WATER ||
                    worldIn.getBlockState(blockpos).getMaterial() == Material.ICE)
            {
                if (itemstack.hasTagCompound())
                {
                    NBTTagCompound itemData = itemstack.getTagCompound();
                    if(itemData.hasKey("EffectArray"))
                    {
                        Random rand = worldIn.rand;
                        NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
                        for(int i = 0; i < effectlist.tagCount(); ++i)
                        {
                            NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
                            if(rand.nextFloat() < effectData.getFloat("chance") && effectData.getString("effect").equals("ice"))
                            {
                                activateEffect("ice", effectData.getFloat("power"), blockpos, worldIn.getBlockState(blockpos), playerIn, worldIn, itemstack);
                            }
                        }
                    }
                }
            }

        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float p_180614_6_, float p_180614_7_, float p_180614_8_) {
        ItemStack stack = player.getHeldItem(hand);

        if(!stack.hasTagCompound() || world.isRemote)
            return super.onItemUse(player, world, pos, hand, facing, p_180614_6_, p_180614_7_, p_180614_8_);

        Map<String, Float> effectsToActivate = new HashMap<>();
        NBTTagCompound itemData = stack.getTagCompound();
        if(itemData.hasKey("EffectArray"))
        {
            Random rand = world.rand;
            NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
            for(int i = 0; i < effectlist.tagCount(); ++i)
            {
                NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
                if(rand.nextFloat() < effectData.getFloat("chance"))
                {
                    effectsToActivate.put(effectData.getString("effect"), effectData.getFloat("power"));
                }
            }
        }

        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if(block instanceof BlockCrops && ( (BlockCrops)block ).isMaxAge(state))
        {
            if(effectsToActivate.containsKey("fire"))
            {
                activateEffect("fire", effectsToActivate.get("fire"), pos, state, player, world, stack);
            }
            else if(effectsToActivate.containsKey("spatial"))
            {
                activateEffect("spatial", effectsToActivate.get("spatial"), pos, state, player, world, stack);
            }
            else
            {
                NonNullList<ItemStack> drops = NonNullList.create();
                block.getDrops(drops, world, pos, state, 0);

                for (ItemStack drop : drops) {
                    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), drop));
                }

                world.setBlockToAir(pos);
            }

            itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
            return EnumActionResult.SUCCESS;
        }

        if (!player.canPlayerEdit(pos.offset(facing), facing, stack)) {
            return EnumActionResult.FAIL;
        } else {
            int hook = ForgeEventFactory.onHoeUse(stack, player, world, pos);
            if (hook != 0) {
                return hook > 0 ? EnumActionResult.SUCCESS : EnumActionResult.FAIL;
            } else {
                if (facing != EnumFacing.DOWN && world.isAirBlock(pos.up())) {
                    if (block == Blocks.GRASS || block == Blocks.GRASS_PATH) {
                        if(effectsToActivate.containsKey("earth"))
                        {
                            activateEffect("earth", effectsToActivate.get("earth"), pos, state, player, world, stack);
                        }
                        else
                        {
                            this.setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                        }
                        return EnumActionResult.SUCCESS;
                    }

                    if (block == Blocks.DIRT) {
                        switch(state.getValue(BlockDirt.VARIANT)) {
                            case DIRT:
                                this.setBlock(stack, player, world, pos, Blocks.FARMLAND.getDefaultState());
                                return EnumActionResult.SUCCESS;
                            case COARSE_DIRT:
                                this.setBlock(stack, player, world, pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                                return EnumActionResult.SUCCESS;
                        }
                    }
                }

                return EnumActionResult.PASS;
            }
        }
    }

    public void activateEffect(String effect, float power, BlockPos pos, IBlockState state, EntityPlayer player, World world, ItemStack hoe)
    {
        if(allCrops == null)
        {
            allCrops = new ArrayList<>();
            for(String crop : OreDictionary.getOreNames())
            {
                if(crop.toLowerCase().contains("crop"))
                {
                    allCrops.addAll(OreDictionary.getOres(crop));
                }
            }
        }

        switch(effect)
        {
            case "fire":
                Item cropItem = state.getBlock().getItemDropped(state, world.rand, 0);
                if(cropItem == Items.WHEAT)
                {
                    world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Items.BREAD)));
                }
                else
                {
                    ItemStack smeltResult = FurnaceRecipes.instance().getSmeltingResult(new ItemStack(cropItem));

                    if(smeltResult != ItemStack.EMPTY)
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

                        world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(smeltResult.getItem(), fortune, smeltResult.getMetadata())));
                    }
                    else
                    {
                        NonNullList<ItemStack> drops = NonNullList.create();
                        state.getBlock().getDrops(drops, world, pos, state, 0);

                        for (ItemStack drop : drops) {
                            world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), drop));
                        }
                    }
                }
                world.setBlockToAir(pos);
                break;

            case "lightning":
                break;

            case "earth":
                for(int xDiff = -(int)power; xDiff < power; ++xDiff)
                {
                    for(int zDiff = -(int)power; zDiff < power; ++zDiff)
                    {
                        BlockPos newPos = new BlockPos(pos.getX() + xDiff, pos.getY(), pos.getZ() + zDiff);
                        Block block = world.getBlockState(newPos).getBlock();
                        if(!(block == Blocks.GRASS || block == Blocks.GRASS_PATH))
                            continue;

                        if(world.isAirBlock(newPos.up()))
                            world.setBlockState(newPos, Blocks.FARMLAND.getDefaultState(), 11);
                    }
                }
                break;

            case "ice":
                Material material = state.getMaterial();
                if(material != Material.WATER && material != Material.ICE)
                    break;

                if(!hoe.hasTagCompound())
                    break;

                for(int xDiff = -(int)power; xDiff < power; ++xDiff)
                {
                    for(int zDiff = -(int)power; zDiff < power; ++zDiff)
                    {
                        BlockPos newPos = new BlockPos(pos.getX() + xDiff, pos.getY(), pos.getZ() + zDiff);
                        IBlockState newState = world.getBlockState(newPos);

                        if(newState.getMaterial() == material)
                        {
                            Block block = newState.getBlock();
                            if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
                            {
                                world.setBlockState(newPos, Blocks.FROSTED_ICE.getDefaultState());
                            }
                            else if (block == Blocks.FROSTED_ICE)
                            {
                                world.setBlockState(newPos, Blocks.ICE.getDefaultState());
                            }
                            else
                            {
                                world.setBlockState(newPos, Blocks.PACKED_ICE.getDefaultState());
                            }

                            NBTTagCompound itemData = hoe.getTagCompound();
                            itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
                        }
                    }
                }
                break;

            case "spatial":
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

                ItemStack drop = allCrops.get(world.rand.nextInt(allCrops.size()));
                world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(drop.getItem(), fortune, drop.getMetadata())));
                world.setBlockToAir(pos);
                break;
        }
    }


    @Override
    protected void setBlock(ItemStack stack, EntityPlayer player, World world, BlockPos pos, IBlockState state) {
        world.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
        if (!world.isRemote) {
            world.setBlockState(pos, state, 11);

            if(!stack.hasTagCompound())
                return;

            NBTTagCompound itemData = stack.getTagCompound();
            itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
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
                            ? TextFormatting.RED + "Hellreaper" + TextFormatting.RESET
                            : effectData.getString("effect").equals("earth")
                                      ? TextFormatting.DARK_GRAY + "Earthrend" + TextFormatting.RESET
                                      : effectData.getString("effect").equals("lightning")
                                                ? TextFormatting.YELLOW + "idk man" + TextFormatting.RESET
                                                : effectData.getString("effect").equals("ice")
                                                          ? TextFormatting.AQUA + "Tidefreeze" + TextFormatting.RESET
                                                          : TextFormatting.DARK_PURPLE + "" + TextFormatting.OBFUSCATED
                                                            + "Anomolize" + TextFormatting.RESET);
                    tooltip.add(text + " (" + String.format("%.3f", effectData.getFloat("chance")*100) + "%, " + String.format("%.3f", effectData.getFloat("power")) + ")");
                }
            }
        }
    }
}
