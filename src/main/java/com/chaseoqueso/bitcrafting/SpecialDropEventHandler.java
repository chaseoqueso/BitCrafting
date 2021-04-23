package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;
import com.chaseoqueso.bitcrafting.items.tools.ItemBitAxe;
import com.chaseoqueso.bitcrafting.items.tools.ItemBitPickaxe;
import com.chaseoqueso.bitcrafting.items.tools.ItemBitShovel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber
public class SpecialDropEventHandler {

    @SubscribeEvent
    public static void onHarvestBlock(BlockEvent.HarvestDropsEvent event)
    {
        EntityPlayer player = event.getHarvester();
        if(player == null)
            return;

        ItemStack stack = player.getHeldItemMainhand();
        List<ItemStack> newDrops = event.getDrops();

        if(stack.getItem() instanceof IItemBitTool)
        {
            if(stack.getItem() instanceof ItemBitPickaxe)
            {
                ( (ItemBitPickaxe)stack.getItem() ).activateAllEffects(stack, event.getState(), event.getPos(), player, player.world, newDrops, event.isSilkTouching(), event.getFortuneLevel());
            }

            if(stack.getItem() instanceof ItemBitAxe)
            {
                ( (ItemBitAxe)stack.getItem() ).activateAllEffects(stack, event.getState(), event.getPos(), player, player.world, newDrops);
            }

            if(stack.getItem() instanceof ItemBitShovel)
            {
                ( (ItemBitShovel)stack.getItem() ).activateAllEffects(stack, event.getState(), event.getPos(), player, player.world, newDrops);
            }
        }
    }
}
