package com.chaseoqueso.bitcrafting.items.tools;

import java.util.*;
import java.util.function.Predicate;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.vecmath.Vector3d;

public class ItemBitSword extends ItemSword implements IItemBitTool {
	
	public ItemBitSword()
	{
		super(EnumHelper.addToolMaterial("BitSword", 0, Integer.MAX_VALUE, 11.11F, -4, 0));
		setUnlocalizedName("ItemBitSword");
		setRegistryName(new ResourceLocation(BitCraftingMod.MODID, "itembitsword"));
		setCreativeTab(null);
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		//Bit tools have a default efficiency of 11.11. If this is the efficiency that is returned by the super call,
		//that means swords are the ideal tool for this block, and therefore we should return this sword's damage as its efficiency.
		if(super.getDestroySpeed(stack, state) != 11.11F)
			return 1;

		if(stack.hasTagCompound())
		{
			NBTTagCompound itemData = stack.getTagCompound();
			return itemData.getFloat("Damage");
		}
		return 0;
	}

	public void activateEffect(String effect, float power, EntityLivingBase target, EntityLivingBase player, World world)
	{
		switch(effect)
		{
			case "fire":
				BlockPos targetPos = target.getPosition();
				if(world.isAirBlock(targetPos) && Blocks.FIRE.canPlaceBlockAt(world, targetPos))
					world.setBlockState(targetPos, Blocks.FIRE.getDefaultState());

				double lookAngle = player.rotationYaw;
				if(lookAngle < 0)
					lookAngle += 360;

				double minAngle = lookAngle - 30f;
				if(minAngle < 0)
					minAngle += 360;

				double maxAngle = lookAngle + 30f;
				if(maxAngle >= 360)
					maxAngle -= 360;

				igniteNeighborsWithinCone(world, pos -> world.isAirBlock(pos) && Blocks.FIRE.canPlaceBlockAt(world, pos), target.getPosition(), target.getPosition(), 1 + power, minAngle, maxAngle);

				target.setFire((int)power);
				break;

			case "lightning":
				Vector3d targetVector = new Vector3d(target.posX, target.posY, target.posZ);
				Vector3d playerVector = new Vector3d(player.posX, player.posY, player.posZ);

				targetVector.sub(playerVector);
				targetVector.normalize();
				Vector3d positionDiffScaled = targetVector;

				for(int i = 0; i < power; ++i) {
					EntityLightningBolt lightningBolt = new EntityLightningBolt(world, 0D, 0D, 0D, false);
					lightningBolt.setLocationAndAngles(target.posX + (positionDiffScaled.x * i * 2), target.posY, target.posZ + (positionDiffScaled.z * i), 0, 0);
					world.addWeatherEffect(lightningBolt);
				}
				break;

			case "earth":
				final float knockbackVelocity = 0.2f;
				//target.knockBack(player, power * knockbackVelocity, player.posX - target.posX, player.posZ - target.posZ);
				target.addVelocity(0, power * knockbackVelocity, 0);
				break;

			case "ice":
				Potion slowness = Potion.getPotionFromResourceLocation("slowness");
				PotionEffect slow;
				if(target.isPotionActive(slowness))
				{
					slow = new PotionEffect(slowness, (int)power * 20, target.getActivePotionEffect(slowness).getAmplifier() + 1);
					target.removePotionEffect(slowness);
				}
				else
				{
					slow = new PotionEffect(slowness, (int)power * 20, 1);
				}
				target.addPotionEffect(slow);
				break;

			case "spatial":
				boolean teleportSuccess = false;
				int attempts = 0;
				Random rand = world.rand;
				while(!teleportSuccess && attempts < 1000) {
					double radius = power * 2;
					double x = target.posX + (rand.nextDouble() - 0.5) * 2 * radius;
					double y = target.posY + (rand.nextDouble() - 0.5) * 2 * radius;
					double z = target.posZ + (rand.nextDouble() - 0.5) * 2 * radius;

					teleportSuccess = target.attemptTeleport(x, y, z);
					++attempts;
				}
				break;
		}
	}

	private void igniteNeighborsWithinCone(World world, Predicate<BlockPos> blockPosPredicate, BlockPos origin, BlockPos targetPos, double maxDistance, double minAngle, double maxAngle)
	{
		for(int xDiff = -1; xDiff <= 1; ++xDiff)
		{
			for(int yDiff = -1; yDiff <= 1; ++yDiff)
			{
				for(int zDiff = -1; zDiff <= 1; ++zDiff)
				{
					//Move to next block if this is the origin block
					if(xDiff == 0 && yDiff == 0 && zDiff == 0)
						continue;

					BlockPos nextBlock = new BlockPos(origin.getX() + xDiff, origin.getY() + yDiff, origin.getZ() + zDiff);

					//Move to next block if this one's invalid
					if(!blockPosPredicate.test(nextBlock))
						continue;

					//Move to next block if this one's beyond the max distance from the player
					if(distance(nextBlock, targetPos) > maxDistance)
						continue;

					//Move to next block if this one's outside of the cone angle
					double angleFromPlayer = Math.toDegrees(Math.atan2(nextBlock.getZ() - targetPos.getZ(), nextBlock.getX() - targetPos.getX()));
					angleFromPlayer -= 90;
					if(angleFromPlayer < 0)
						angleFromPlayer += 360;
					if(angleFromPlayer >= 360)
						angleFromPlayer -= 360;

					if(minAngle > maxAngle) {
						if(angleFromPlayer < minAngle && angleFromPlayer > maxAngle)
							continue;
					}
					else {
						if (angleFromPlayer < minAngle || angleFromPlayer > maxAngle)
							continue;
					}

					//If we made it this far, add the block to the map
					world.setBlockState(nextBlock, Blocks.FIRE.getDefaultState());
					igniteNeighborsWithinCone(world, blockPosPredicate, nextBlock, targetPos, maxDistance, minAngle, maxAngle);
				}
			}
		}
	}

	private double distance(BlockPos pos1, BlockPos pos2)
	{
		return Math.sqrt(Math.pow(pos1.getX() - pos2.getX(), 2) + Math.pow(pos1.getY() - pos2.getY(), 2) + Math.pow(pos1.getZ() - pos2.getZ(), 2));
	}

	private int hashBlockPos(BlockPos pos)
	{
		return 137*pos.getX() + 149*pos.getY() + 163*pos.getZ();
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
			multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.4000000953674316D, 0));
        }

        return multimap;
    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return (stack.isItemEnchanted() || (stack.hasTagCompound() && stack.getTagCompound().hasKey("EffectArray")));
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker)
	{
		if(stack.hasTagCompound())
		{
			NBTTagCompound itemData = stack.getTagCompound();

			if(!(attacker instanceof EntityPlayer && ( (EntityPlayer)attacker ).capabilities.isCreativeMode))
			{
				itemData.setInteger("Uses", itemData.getInteger("Uses") + 1);
				if (itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
				{
					attacker.renderBrokenItemStack(stack);
					stack.shrink(1);
				}
			}

			if(itemData.hasKey("EffectArray"))
			{
				Random rand = attacker.world.rand;
				NBTTagList effectlist = itemData.getTagList("EffectArray", 10);
				for(int i = 0; i < effectlist.tagCount(); ++i)
				{
					NBTTagCompound effectData = effectlist.getCompoundTagAt(i);
					if(rand.nextFloat() < effectData.getFloat("chance"))
					{
						activateEffect(effectData.getString("effect"), effectData.getFloat("power"), target, attacker, target.world);
					}
				}
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
			itemData.setInteger("Uses", itemData.getInteger("Uses") + 2);
			if(itemData.getInteger("Uses") >= itemData.getFloat("Durability"))
			{
				blockDestroyer.renderBrokenItemStack(stack);
				stack.shrink(1);
			}
		}
		return true;
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
	public void setDamage(ItemStack stack, int damage)
	{
		if(!stack.hasTagCompound())
			return;

		if(damage < 0)
			damage = 0;

		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setInteger("Uses", damage);
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
			float durability = itemData.getFloat("Durability");
			tooltip.add("Durability: " + String.format("%.0f", durability - itemData.getInteger("Uses")) + "/" + String.format("%.0f", durability));
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
															+ "Anomalize" + TextFormatting.RESET);
					tooltip.add(text + " (" + String.format("%.3f", effectData.getFloat("chance")*100) + "%, " + String.format("%.3f", effectData.getFloat("power")) + ")");
				}
			}
		}
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
}
