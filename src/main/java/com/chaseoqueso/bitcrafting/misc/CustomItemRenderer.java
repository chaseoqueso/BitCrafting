package com.chaseoqueso.bitcrafting.misc;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;

public class CustomItemRenderer implements IItemRenderer {

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		// TODO Auto-generated method stub
		
	}
/*
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
	static final boolean testflagColour = true;          // if true - render cube with each face a different colour
	static final boolean testflagIsFull3D = false;       // if true - rotate the icon in EQUIPPED 3rd person view - eg sword.  If false - don't rotate, eg pumpkin pie
	static boolean wrongRendererMsgWritten = false;      // if renderer is called with the wrong item, it prints an error msg once only, by setting this flag to stop subsequent prints

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch (type) {
			case ENTITY:
			case EQUIPPED:
			case EQUIPPED_FIRST_PERSON:
			case INVENTORY:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type) {
			case ENTITY: {
				return (helper == ItemRendererHelper.ENTITY_BOBBING ||
						helper == ItemRendererHelper.ENTITY_ROTATION
						); // not helper == ItemRendererHelper.BLOCK_3D
			}
			case EQUIPPED: {
				return false;
				// not (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
			}
			case EQUIPPED_FIRST_PERSON: {
				return false;
				// not (helper == ItemRendererHelper.EQUIPPED_BLOCK);
			}
			case INVENTORY: {
				return false;
				// not (helper == ItemRendererHelper.INVENTORY_BLOCK);
			}
			default: {
				return false;
			}
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		if (type == ItemRenderType.INVENTORY) {
			drawAs2D(type, item);
		} else {
			drawAsSlice(type, item);
		}
		return;
	}

	// draw the inventory icon as flat 2D
	// caller expects you to render over [0,0,0] to [16, 16, 0]
	private void drawAs2D(ItemRenderType type, ItemStack stack) {

		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();

		//IIcon icon1 = item.getItem().getIconFromDamage(0);
		IIcon icon1 = BitCraftingMod.itemBitSword.getIcon(stack, 0);

		double minU1 = (double)icon1.getMinU();
		double minV1 = (double)icon1.getMinV();
		double maxU1 = (double)icon1.getMaxU();
		double maxV1 = (double)icon1.getMaxV();

		tessellator.addVertexWithUV(16.0, 16.0, 0.0, maxU1, maxV1);
		tessellator.addVertexWithUV(16.0,  0.0, 0.0, maxU1, minV1);
		tessellator.addVertexWithUV( 0.0,  0.0, 0.0, minU1, minV1);
		tessellator.addVertexWithUV( 0.0, 16.0, 0.0, minU1, maxV1);
		tessellator.draw();
	}

	private enum TransformationTypes {NONE, DROPPED, INFRAME};

	private void drawAsSlice(ItemRenderType type, ItemStack stack) {
		/
	    ItemBitSword facesItem;
	
	    if (item.getItem() instanceof ItemBitSword) {
	    	facesItem = (ItemBitSword)item.getItem();
	    } else {  // the renderer was called using the wrong item -?
	      if (!wrongRendererMsgWritten) {
	        wrongRendererMsgWritten = true;
	        System.out.println("CustomItemRenderer.renderItem called with wrong Item:" + item.getDisplayName());
	      }
	      return;
	    }

	    final float THICKNESS = 0.0625F;
	
	    Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	
	    // adjust rendering space to match what caller expects
	    TransformationTypes transformationToBeUndone = TransformationTypes.NONE;
	    switch (type) {
		  	case EQUIPPED:
		  	case EQUIPPED_FIRST_PERSON: {
		  		break; // caller expects us to render over [0,0,0] to [1,1,-THICKNESS], no transformation necessary
		  	}
		  	case ENTITY: {
		  		// translate our coordinates so that [0,0,0] to [1,1,1] translates to the [-0.5, 0.0, 0.0] to [0.5, 1.0, 1.0] expected by the caller.
		  		if (RenderItem.renderInFrame) {
		  			transformationToBeUndone = TransformationTypes.INFRAME;   // must undo the transformation when we're finished rendering
		  			GL11.glTranslatef(-0.5F, -0.3F, THICKNESS/2.0F);
		  		} else {
		  			transformationToBeUndone = TransformationTypes.DROPPED;   // must undo the transformation when we're finished rendering
		  			GL11.glTranslatef(-0.5F, 0.0F, 0.0F);
		  		}
		  		break;
		  	}
		  	case INVENTORY: {
		  		break;
		  	}
		  	default:
		  		break; // never here
    	}
	
	    // xpos face blue
	    IIcon icon = facesItem.getFace(5);
	    tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    if (testflagColour) tessellator.setColorOpaque(0, 0, 255);
	    tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMaxV());
	    tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());
	
	    // xneg face purple
	    icon = facesItem.getFace(4);
	    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	    if (testflagColour) tessellator.setColorOpaque(255, 0, 255);
	    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
	    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMaxV());
	
	    // zneg face white
	    icon = facesItem.getFace(2);
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    if (testflagColour) tessellator.setColorOpaque(255, 255, 255);
	    tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMaxV());
	    tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMaxV());
	
	    // zpos face green
	    icon = facesItem.getFace(3);
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    if (testflagColour) tessellator.setColorOpaque(0, 255, 0);
	    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
	    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());
	
	    // ypos face red
	    icon = facesItem.getFace(1);
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    if (testflagColour) tessellator.setColorOpaque(255, 0, 0);
	    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
	    tessellator.addVertexWithUV(1.0, 1.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(0.0, 1.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());
	
	    // yneg face yellow
	    icon = facesItem.getFace(0);
	    tessellator.setNormal(0.0F, -1.0F, 0.0F);
	    if (testflagColour) tessellator.setColorOpaque(255, 255, 0);
	    tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV());
	    tessellator.addVertexWithUV(0.0, 0.0, -THICKNESS, (double)icon.getMaxU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(1.0, 0.0, -THICKNESS, (double)icon.getMinU(), (double)icon.getMinV());
	    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV());
	
	    tessellator.draw();
	
	    switch (transformationToBeUndone) {
	      case NONE: {
	        break;
	      }
	      case DROPPED: {
	        GL11.glTranslatef(0.5F, 0.0F, 0.0F);
	        break;
	      }
	      case INFRAME: {
	        GL11.glTranslatef(0.5F, 0.3F, -THICKNESS/2.0F);
	        break;
	      }
	      default:
	        break;
	    }
	    /
		GL11.glPushMatrix();
        TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
        Item item = stack.getItem();
		IIcon iicon = BitCraftingMod.itemBitSword.getIcon(stack, 0);
		
		//texturemanager.bindTexture(ItemBitSword.getResourceLocation(stack));
        TextureUtil.func_152777_a(false, false, 1.0F);
        Tessellator tessellator = Tessellator.instance;
        float f = iicon.getMinU();
        float f1 = iicon.getMaxU();
        float f2 = iicon.getMinV();
        float f3 = iicon.getMaxV();
        float f4 = 0.0F;
        float f5 = 0.3F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glTranslatef(-f4, -f5, 0.0F);
        float f6 = 1.5F;
        GL11.glScalef(f6, f6, f6);
        GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
        GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);

        if (stack.hasEffect(0))
        {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            texturemanager.bindTexture(RES_ITEM_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(768, 1, 1, 0);
            float f7 = 0.76F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float f8 = 0.125F;
            GL11.glScalef(f8, f8, f8);
            float f9 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = (float)(Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		//texturemanager.bindTexture(ItemBitSword.getResourceLocation(stack));
        TextureUtil.func_147945_b();
	}*/
}
