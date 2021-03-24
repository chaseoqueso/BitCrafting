package com.chaseoqueso.bitcrafting;

import com.chaseoqueso.bitcrafting.gui.GUIHandler;
import com.chaseoqueso.bitcrafting.init.BitCraftingBlocks;

import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import com.chaseoqueso.bitcrafting.items.ItemBit;
import com.chaseoqueso.bitcrafting.rendering.ModelBitTool;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Comparator;

@Mod(modid = BitCraftingMod.MODID, name = "BitCrafting", version = "1.2")
public class BitCraftingMod {
	
	@SidedProxy(clientSide = "com.chaseoqueso.bitcrafting.ClientProxy", serverSide = "com.chaseoqueso.bitcrafting.ServerProxy")
	public static ServerProxy proxy;
	
	public static final String MODID = "bcm";

	public static CreativeTabs tabBitCraftingMod = new CreativeTabs("tabBitCraftingMod") {

		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(BitCraftingBlocks.BLOCKS.blockBitForge));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void displayAllRelevantItems(NonNullList<ItemStack> stackList)
		{
			super.displayAllRelevantItems(stackList);
			class tabComparator implements Comparator<ItemStack> {
				@Override
				public int compare(ItemStack stack1, ItemStack stack2)
				{
					int stack1isItem = stack1.getItem() instanceof ItemBlock ? 0
											   : stack1.getItem() instanceof ItemBit ? 2 : 1;
					int stack2isItem = stack2.getItem() instanceof ItemBlock ? 0
											   : stack2.getItem() instanceof ItemBit ? 2 : 1;
					return stack1isItem - stack2isItem;
				}
			}

			stackList.sort(new tabComparator());
		}
	};

	public static final SpecialDropEventHandler dropEventHandler = new SpecialDropEventHandler();
	
	@Instance(MODID)
	public static BitCraftingMod instance;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ModelLoaderRegistry.registerLoader(ModelBitTool.LoaderBitTool.INSTANCE);
		GameRegistry.registerWorldGenerator(new BitOreGeneration(), 0);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(BitCraftingMod.instance, new GUIHandler());
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(BitCraftingItems.ITEMS.itemBitColorManager, BitCraftingItems.ITEMS.itemBit);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		
	}
		
}
