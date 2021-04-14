package com.chaseoqueso.bitcrafting.items;

import java.util.Arrays;
import java.util.List;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.init.BitCraftingItems;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nullable;

import static com.chaseoqueso.bitcrafting.BitCraftingMod.isItemstackInList;

public class ItemBit extends Item {

	public static final String[] colors = { "gray", "brown", "red", "orange", "yellow", "lime", "green", "cyan",
			"lightblue", "blue", "purple", "magenta", "pink" };
	public static final String[] shades = { "darkest", "dark", "", "light", "lightest" };
	public static final String[] effects = { "fire", "earth", "lightning", "ice", "spatial" };

	public ItemBit() {
		setUnlocalizedName("ItemBit");
		setCreativeTab(BitCraftingMod.tabBitCraftingMod);
	}

	public static ItemStack setColor(ItemStack stack, int color, int shade) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setString("color", colors[color]);
		itemData.setString("shade", shades[shade]);
		return stack;
	}

	public static ItemStack setColor(ItemStack stack, String color, String shade) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setString("color", color);
		itemData.setString("shade", shade);
		return stack;
	}

	public static ItemStack setEffect(ItemStack stack, int effect, float chance, float power) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setString("effect", effects[effect]);
		itemData.setFloat("chance", chance);
		itemData.setFloat("power", power);
		return stack;
	}

	public static ItemStack setEffect(ItemStack stack, String effect, float chance, float power) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setString("effect", effect);
		itemData.setFloat("chance", chance);
		itemData.setFloat("power", power);
		return stack;
	}

	public static ItemStack setClear(ItemStack stack, boolean flag) {
		ItemStack output;
		if (flag)
			output = new ItemStack(BitCraftingItems.ITEMS.itemClearBit, stack.getCount());
		else
			output = new ItemStack(BitCraftingItems.ITEMS.itemBit, stack.getCount());
		output.setTagCompound(stack.getTagCompound());
		return output;
	}

	public static int getColorInt(ItemStack stack) {
		if (!stack.hasTagCompound())
			return 0;
		NBTTagCompound itemData = stack.getTagCompound();
		return Arrays.asList(colors).indexOf(itemData.getString("color"));
	}

	public static int getShadeInt(ItemStack stack) {
		if (!stack.hasTagCompound())
			return 2;
		NBTTagCompound itemData = stack.getTagCompound();
		return Arrays.asList(colors).indexOf(itemData.getString("shade"));
	}

	public static ItemStack setBit(ItemStack stack, int color, int shade, float damage, float durability, float enchantability) {
		return setBit(stack, colors[color], shades[shade], damage, durability, enchantability, null, 0, 0, 0);
	}

	public static ItemStack setBit(ItemStack stack, String color, String shade, float damage, float durability, float enchantability) {
		return setBit(stack, color, shade, damage, durability, enchantability, null, 0, 0, 0);
	}

	public static ItemStack setBit(ItemStack stack, int color, int shade, float damage, float durability, float enchantability, int harvestLevel) {
		return setBit(stack, colors[color], shades[shade], damage, durability, enchantability, null, 0, 0, harvestLevel);
	}

	public static ItemStack setBit(ItemStack stack, String color, String shade, float damage, float durability, float enchantability, int harvestLevel) {
		return setBit(stack, color, shade, damage, durability, enchantability, null, 0, 0, harvestLevel);
	}

	public static ItemStack setBit(ItemStack stack, int color, int shade, float damage, float durability, float enchantability, int effect, float chance, float power) {
		return setBit(stack, colors[color], shades[shade], damage, durability, enchantability, effect > 0 ? effects[effect] : null, chance, power, 0);
	}

	public static ItemStack setBit(ItemStack stack, String color, String shade, float damage, float durability, float enchantability, String effect, float chance, float power) {
		return setBit(stack, color, shade, damage, durability, enchantability, effect, chance, power, 0);
	}

	public static ItemStack setBit(ItemStack stack, int color, int shade, float damage, float durability, float enchantability, int effect, float chance, float power, int harvestLevel) {
		return setBit(stack, colors[color], shades[shade], damage, durability, enchantability, effect > 0 ? effects[effect] : null, chance, power, harvestLevel);
	}

	public static ItemStack setBit(ItemStack stack, String color, String shade, float damage, float durability, float enchantability, String effect, float chance, float power, int harvestLevel) {
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setString("color", color);
		itemData.setString("shade", shade);
		itemData.setFloat("damage", damage);
		itemData.setFloat("durability", durability);
		itemData.setFloat("enchantability", enchantability);
		itemData.setInteger("harvestLevel", harvestLevel);

		if(effect != null) {
			itemData.setString("effect", effect);
			itemData.setFloat("chance", chance);
			itemData.setFloat("power", power);
		}
		return stack;
	}

	public static ItemStack setCrucibleExperience(ItemStack stack, float exp)
	{
		if (!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		NBTTagCompound itemData = stack.getTagCompound();
		itemData.setFloat("expReward", exp);
		return stack;
	}

	public static float getCrucibleExperience(ItemStack stack)
	{
		if (!stack.hasTagCompound())
			return 0;

		NBTTagCompound itemData = stack.getTagCompound();
		if(!itemData.hasKey("expReward"))
			return 0;

		return itemData.getFloat("expReward");
	}

	public static boolean bitsAreEqual(ItemStack stack1, ItemStack stack2) {
		if (!stack1.hasTagCompound() || !stack2.hasTagCompound())
			return false;
		NBTTagCompound itemData1 = stack1.getTagCompound();
		NBTTagCompound itemData2 = stack2.getTagCompound();
		if (!(itemData1.getString("color").equals(itemData2.getString("color")))
				|| !(itemData1.getString("shade").equals(itemData2.getString("shade")))
				|| !(itemData1.getFloat("damage") == itemData2.getFloat("damage"))
				|| !(itemData1.getFloat("durability") == itemData2.getFloat("durability"))
				|| !(itemData1.getFloat("enchantability") == itemData2.getFloat("enchantability"))
				|| !(itemData1.getInteger("harvestLevel") == itemData2.getInteger("harvestLevel"))
				|| !(itemData1.hasKey("effect") == itemData2.hasKey("effect"))
				|| (itemData1.hasKey("effect") && itemData2.hasKey("effect")
						&& !(itemData1.getString("effect").equals(itemData2.getString("effect")))))
			return false;
		return true;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound itemData = stack.getTagCompound();
			if (itemData.hasKey("effect"))
				return "item." + itemData.getString("effect");
			if (itemData.hasKey("color"))
				return "item." + itemData.getString("shade") + itemData.getString("color");
		}
		return "item.nullBit";
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (stack.hasTagCompound()) {
			NBTTagCompound itemData = stack.getTagCompound();
			tooltip.add("Damage Rating: " + String.format("%.3f", itemData.getFloat("damage")));
			tooltip.add("Durability Rating: " + String.format("%.3f", itemData.getFloat("durability")));
			tooltip.add("Enchantability Rating: " + String.format("%.3f", itemData.getFloat("enchantability")));
			tooltip.add("Harvest Level: " + itemData.getInteger("harvestLevel"));
			if (itemData.hasKey("effect")) {
				String text = (String) (itemData.getString("effect").equals("fire")
												? TextFormatting.RED + "Fire" + TextFormatting.RESET
												: itemData.getString("effect").equals("earth")
														  ? TextFormatting.DARK_GRAY + "Earth" + TextFormatting.RESET
														  : itemData.getString("effect").equals("lightning")
																	? TextFormatting.YELLOW + "Lightning" + TextFormatting.RESET
																	: itemData.getString("effect").equals("ice")
																			  ? TextFormatting.AQUA + "Ice" + TextFormatting.RESET
																			  : TextFormatting.DARK_PURPLE + "" + TextFormatting.OBFUSCATED
																				+ "Anomaly" + TextFormatting.RESET);
				tooltip.add(text + " (" + String.format("%.3f", itemData.getFloat("chance")*100) + "%, " + String.format("%.3f", itemData.getFloat("power")) + ")");
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {
		if (!stack.hasTagCompound())
			return false;
		NBTTagCompound itemData = stack.getTagCompound();
		return itemData.hasKey("effect");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(tab == BitCraftingMod.tabBitCraftingMod) {
			for (int col = 0; col < colors.length; col++) {
				for (int shd = 0; shd < shades.length; shd++) {
					ItemStack bitStack = new ItemStack(this);
					NBTTagCompound itemData = new NBTTagCompound();
					bitStack.setTagCompound(itemData);

					itemData.setString("color", colors[col]);
					itemData.setString("shade", shades[shd]);
					itemData.setFloat("damage", .1F);
					itemData.setFloat("durability", 20F);
					itemData.setFloat("enchantability", .2F);
					itemData.setInteger("harvestLevel", 4);

					items.add(bitStack);
				}
			}
			for (int eff = 0; eff < effects.length; eff++) {
				ItemStack bitStack = new ItemStack(this);
				NBTTagCompound itemData = new NBTTagCompound();

				bitStack.setTagCompound(itemData);

				switch (eff) {
					case 0:
						itemData.setString("color", "orange");
						itemData.setString("shade", "");
						break;
					case 1:
						itemData.setString("color", "brown");
						itemData.setString("shade", "dark");
						break;
					case 2:
						itemData.setString("color", "yellow");
						itemData.setString("shade", "light");
						break;
					case 3:
						itemData.setString("color", "lightblue");
						itemData.setString("shade", "light");
						break;
					case 4:
						itemData.setString("color", "purple");
						itemData.setString("shade", "");
						break;
					default:
						itemData.setString("color", "gray");
						itemData.setString("shade", "darkest");
				}

				itemData.setFloat("damage", 0.1F);
				itemData.setFloat("durability", 20F);
				itemData.setFloat("enchantability", 0.2F);
				itemData.setString("effect", effects[eff]);
				itemData.setFloat("chance", 0.01F);
				itemData.setFloat("power", 0.1F);
				itemData.setInteger("harvestLevel", 4);

				items.add(bitStack);
			}
		}
	}

	public static int getColorFromDye(ItemStack stack) {
		if (stack.getItem() == Items.WATER_BUCKET)
			return -2;

		//The dye table doesn't accept black or white dyes because of how shades work

		if(isItemstackInList(stack, OreDictionary.getOres("dyeGray")) || isItemstackInList(stack, OreDictionary.getOres("dyeLightGray")))
			return 0;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeBrown")))
			return 1;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeRed")))
			return 2;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeOrange")))
			return 3;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeYellow")))
			return 4;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeLime")))
			return 5;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeGreen")))
			return 6;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeCyan")))
			return 7;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeLightBlue")))
			return 8;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeBlue")))
			return 9;

		if(isItemstackInList(stack, OreDictionary.getOres("dyePurple")))
			return 10;

		if(isItemstackInList(stack, OreDictionary.getOres("dyeMagenta")))
			return 11;

		if(isItemstackInList(stack, OreDictionary.getOres("dyePink")))
			return 12;

		return -1;
	}

	public static int getColorFromStack(ItemStack stack) {
		if (stack == ItemStack.EMPTY || !(stack.hasTagCompound() && stack.getTagCompound().hasKey("color"))
			|| stack.getItem() instanceof ItemClearBit) {
			return -1;
		}
		// Goes through every index in the color and shade arrays and picks the right
		// color to tint.
		switch (Arrays.asList(colors).indexOf(stack.getTagCompound().getString("color"))) {
			case 0: // Gray
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x101010;
					case 1:
						return 0x404040;
					case 2:
						return 0x808080;
					case 3:
						return 0xC0C0C0;
					case 4:
						return 0xFFFFFF;
				}
			case 1: // Brown
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x27170C;
					case 1:
						return 0x3A2213;
					case 2:
						return 0x56331C;
					case 3:
						return 0xAD6738;
					case 4:
						return 0xDAAB8B;
				}
			case 2: // Red
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x330000;
					case 1:
						return 0x990000;
					case 2:
						return 0xFF0000;
					case 3:
						return 0xFF6666;
					case 4:
						return 0xFFCCCC;
				}
			case 3: // Orange
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x663D00;
					case 1:
						return 0xB36B00;
					case 2:
						return 0xFF9900;
					case 3:
						return 0xFFC266;
					case 4:
						return 0xFFEBCC;
				}
			case 4: // Yellow
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x333300;
					case 1:
						return 0x999900;
					case 2:
						return 0xFFFF00;
					case 3:
						return 0xFFFF66;
					case 4:
						return 0xFFFFCC;
				}
			case 5: // Lime
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x336600;
					case 1:
						return 0x59B300;
					case 2:
						return 0x80FF00;
					case 3:
						return 0xB3FF66;
					case 4:
						return 0xE6FFCC;
				}
			case 6: // Green
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x1C270C;
					case 1:
						return 0x45611F;
					case 2:
						return 0x6F9B31;
					case 3:
						return 0xADD477;
					case 4:
						return 0xE8F3D8;
				}
			case 7: // Cyan
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x003333;
					case 1:
						return 0x009999;
					case 2:
						return 0x00FFFF;
					case 3:
						return 0x77FFFF;
					case 4:
						return 0xCCFFFF;
				}
			case 8: // Light Blue
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x001A33;
					case 1:
						return 0x0059B3;
					case 2:
						return 0x3399FF;
					case 3:
						return 0x80BFFF;
					case 4:
						return 0xCCE6FF;
				}
			case 9: // Blue
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x0F0F3D;
					case 1:
						return 0x1F1F7A;
					case 2:
						return 0x3333CC;
					case 3:
						return 0x8585E0;
					case 4:
						return 0xD6D6F5;
				}
			case 10: // Purple
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x1A0033;
					case 1:
						return 0x4D0099;
					case 2:
						return 0x8000FF;
					case 3:
						return 0xB366FF;
					case 4:
						return 0xE6CCFF;
				}
			case 11: // Magenta
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x330033;
					case 1:
						return 0x990099;
					case 2:
						return 0xFF00FF;
					case 3:
						return 0xFF66FF;
					case 4:
						return 0xFFCCFF;
				}
			case 12: // Pink
				switch (Arrays.asList(shades).indexOf(stack.getTagCompound().getString("shade"))) {
					case 0:
						return 0x330011;
					case 1:
						return 0x990033;
					case 2:
						return 0xFF0055;
					case 3:
						return 0xFF6699;
					case 4:
						return 0xFFCCDD;
				}
			default:
				return 0xFFFFFF;
		}
	}
}
