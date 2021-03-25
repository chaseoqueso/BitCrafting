package com.chaseoqueso.bitcrafting.items.tools;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;
import java.util.*;

public class ItemBitAxe extends ItemAxe implements IItemBitTool {

    public ItemBitAxe()
    {
        super(EnumHelper.addToolMaterial("BitAxe", 0, Integer.MAX_VALUE, 0, -4, 0), 0, 0);
        setUnlocalizedName("ItemBitAxe");
        setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembitaxe"));
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
        if(material != Material.WOOD && material != Material.PLANTS && material != Material.VINE)
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
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", damage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -3.0, 0));
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

    public void activateAllEffects(ItemStack stack, IBlockState state, BlockPos pos, EntityPlayer player, World worldIn, List<ItemStack> drops)
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
                        ((ItemBitAxe)stack.getItem()).activateEffect(effectData.getString("effect"), effectData.getFloat("power"), drops, pos, state, player, worldIn, stack);
                    }
                }
            }
        }
    }

    public void activateEffect(String effect, float power, List<ItemStack> drops, BlockPos pos, IBlockState state, EntityPlayer player, World world, ItemStack axe)
    {
        final float fortuneChance = 0.1f;
        switch(effect)
        {
            case "fire":
                List<ItemStack> newDrops = new ArrayList();

                NonNullList<ItemStack> defaultDrops = NonNullList.create();
                state.getBlock().getDrops(defaultDrops, world, pos, state, 0);

                for(int i = 0; i < defaultDrops.size(); ++i)
                {
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
                int blocksLeft;

                blocksLeft = harvestNeighborsAtPosition(world, pos, state, (int)power);
                if(blocksLeft <= 0)
                    break;

                blocksLeft = harvestNeighborsAtPosition(world, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), state, (int)power);
                if(blocksLeft <= 0)
                    break;

                for(int xDiff = -1; xDiff <= 1; ++xDiff)
                {
                    for(int zDiff = -1; zDiff <= 1; ++zDiff)
                    {
                        if(blocksLeft > 0) {
                            BlockPos newPos = new BlockPos(pos.getX() + xDiff, pos.getY(), pos.getZ() + zDiff);
                            blocksLeft = harvestNeighborsAtPosition(world, newPos, state, blocksLeft);
                        }
                    }
                }
                break;

            case "earth":
                drops.clear();
                world.setBlockState(pos, state);

                int highestLevel = 0;

                Map<Integer, List<Block>> potentialOres = new HashMap<>();
                potentialOres.put(0, new ArrayList<>());
                potentialOres.get(0).add(Blocks.STONE);

                for(String ore : OreDictionary.getOreNames())
                {
                    if(ore.contains("ore"))
                    {
                        for(ItemStack i : OreDictionary.getOres(ore))
                        {
                            Block newBlock = ( (ItemBlock)i.getItem() ).getBlock();
                            int harvestLevel = newBlock.getHarvestLevel(newBlock.getDefaultState());

                            if(harvestLevel > getHarvestLevel(axe, null, null, null))
                                continue;

                            if(potentialOres.containsKey(harvestLevel))
                            {
                                potentialOres.get(harvestLevel).add(newBlock);
                            }
                            else
                            {
                                potentialOres.put(harvestLevel, new ArrayList<>());
                                potentialOres.get(harvestLevel).add(newBlock);
                            }

                            if(harvestLevel > highestLevel)
                            {
                                highestLevel = harvestLevel;
                            }
                        }
                    }
                }

                List<Double> harvestLevelWeights = new ArrayList<>();
                final double weightRatio = 0.25f;
                final double powerBonus = 0.5f;
                float totalWeight = 0;

                for(int i = 0; i <= highestLevel; ++i)
                {
                    double weight = Math.pow(weightRatio, i/(power * powerBonus));
                    harvestLevelWeights.add(weight);
                    totalWeight += weight;
                }

                double oreWeight = world.rand.nextDouble() * totalWeight;
                int oreLevel;
                for(oreLevel = 0; oreLevel < highestLevel; ++oreLevel)
                {
                    if(oreWeight < harvestLevelWeights.get(oreLevel))
                        break;
                }

                List<Block> blocksAtOreLevel = potentialOres.get(oreLevel);
                int oreChoice = (int)(world.rand.nextFloat() * blocksAtOreLevel.size());
                IBlockState newBlock = blocksAtOreLevel.get(oreChoice).getDefaultState();

                blocksLeft = petrifyNeighborsAtPosition(world, pos, state, newBlock, (int)power);
                if(blocksLeft <= 0)
                    break;

                blocksLeft = petrifyNeighborsAtPosition(world, new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), state, newBlock, (int)power);
                if(blocksLeft <= 0)
                    break;

                for(int xDiff = -1; xDiff <= 1; ++xDiff)
                {
                    for(int zDiff = -1; zDiff <= 1; ++zDiff)
                    {
                        if(blocksLeft > 0) {
                            BlockPos newPos = new BlockPos(pos.getX() + xDiff, pos.getY(), pos.getZ() + zDiff);
                            blocksLeft = petrifyNeighborsAtPosition(world, newPos, state, newBlock, blocksLeft);
                        }
                    }
                }
                break;

            case "ice":
                List<IProperty> properties = new ArrayList<>(state.getPropertyKeys());
                if(properties.size() < 2)
                    break;

                ItemStack sapling = new ItemStack(Blocks.SAPLING, 1, state.getBlock().getMetaFromState(state) % 4);

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

                sapling.setCount(fortune);
                drops.clear();
                drops.add(sapling);

                break;

            case "spatial":
                fortune = 1;
                rand = world.rand;

                for(int j = 0; j < power; ++j)
                {
                    double chance = rand.nextDouble();

                    if(chance < fortuneChance)
                    {
                        ++fortune;
                    }
                }

                int meta = rand.nextInt(BlockPlanks.EnumType.values().length);
                for(int i = 0; i < drops.size(); ++i)
                {
                    ItemStack drop = drops.get(i);
                    Item dropItem = Blocks.LOG.getItemDropped(state, world.rand, 0);
                    if(meta > 3)
                    {
                        dropItem = Blocks.LOG2.getItemDropped(state, world.rand, 0);
                        meta -= 4;
                    }
                    drops.set(i, new ItemStack(dropItem, drop.getCount() * fortune, meta));
                }
                break;
        }
    }

    private int harvestNeighborsAtPosition(World world, BlockPos position, IBlockState state, int blocksLeft)
    {
        if(!harvestBlockAtPosition(world, position, state)) {
            return blocksLeft;
        }
        --blocksLeft;

        if(blocksLeft <= 0)
            return 0;

        blocksLeft = harvestNeighborsAtPosition(world, new BlockPos(position.getX(), position.getY() + 1, position.getZ()), state, blocksLeft);

        if(blocksLeft <= 0)
            return 0;

        for(int xDiff = -1; xDiff <= 1; ++xDiff)
        {
            for(int zDiff = -1; zDiff <= 1; ++zDiff)
            {
                if(blocksLeft <= 0)
                    return 0;

                BlockPos newPos = new BlockPos(position.getX() + xDiff, position.getY(), position.getZ() + zDiff);
                blocksLeft = harvestNeighborsAtPosition(world, newPos, state, blocksLeft);
            }
        }

        return harvestNeighborsAtPosition(world, new BlockPos(position.getX(), position.getY() - 1, position.getZ()), state, blocksLeft);
    }

    private boolean harvestBlockAtPosition(World world, BlockPos position, IBlockState state)
    {
        IBlockState blockAtPosition = world.getBlockState(position);
        if(blockAtPosition.getBlock() == state.getBlock()
            || (blockAtPosition.getBlock() == Blocks.LEAVES && blockAtPosition.getProperties().get(BlockNewLog.VARIANT) == state))
        {
            NonNullList<ItemStack> blockDrops = NonNullList.create();
            blockAtPosition.getBlock().getDrops(blockDrops, world, position, state, 0);

            for (ItemStack drop : blockDrops) {
                world.spawnEntity(new EntityItem(world, position.getX(), position.getY(), position.getZ(), drop));
            }

            world.setBlockToAir(position);
            return true;
        }
        return false;
    }

    private int petrifyNeighborsAtPosition(World world, BlockPos position, IBlockState state, IBlockState newBlock, int blocksLeft)
    {
        if(!petrifyBlockAtPosition(world, position, state, newBlock)) {
            return blocksLeft;
        }
        --blocksLeft;

        if(blocksLeft <= 0)
            return 0;

        blocksLeft = petrifyNeighborsAtPosition(world, new BlockPos(position.getX(), position.getY() + 1, position.getZ()), state, newBlock, blocksLeft);

        if(blocksLeft <= 0)
            return 0;

        for(int xDiff = -1; xDiff <= 1; ++xDiff)
        {
            for(int zDiff = -1; zDiff <= 1; ++zDiff)
            {
                if(blocksLeft <= 0)
                    return 0;

                BlockPos newPos = new BlockPos(position.getX() + xDiff, position.getY(), position.getZ() + zDiff);
                blocksLeft = petrifyNeighborsAtPosition(world, newPos, state, newBlock, blocksLeft);
            }
        }

        return petrifyNeighborsAtPosition(world, new BlockPos(position.getX(), position.getY() - 1, position.getZ()), state, newBlock, blocksLeft);
    }

    private boolean petrifyBlockAtPosition(World world, BlockPos position, IBlockState state, IBlockState newBlock)
    {
        IBlockState blockAtPosition = world.getBlockState(position);
        if(blockAtPosition.getBlock() == state.getBlock())
        {
            world.setBlockState(position, newBlock);
            return true;
        }
        return false;
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
                            ? TextFormatting.RED + "Barkburn" + TextFormatting.RESET
                            : effectData.getString("effect").equals("earth")
                                      ? TextFormatting.DARK_GRAY + "Petrify" + TextFormatting.RESET
                                      : effectData.getString("effect").equals("lightning")
                                                ? TextFormatting.YELLOW + "Galestrike" + TextFormatting.RESET
                                                : effectData.getString("effect").equals("ice")
                                                          ? TextFormatting.AQUA + "Cryoreversion" + TextFormatting.RESET
                                                          : TextFormatting.DARK_PURPLE + "" + TextFormatting.OBFUSCATED + "Anomolize" + TextFormatting.RESET);
                    tooltip.add(text + " (" + String.format("%.3f", effectData.getFloat("chance")*100) + "%, " + String.format("%.3f", effectData.getFloat("power")) + ")");
                }
            }
        }
    }
}
