package com.chaseoqueso.bitcrafting.misc;

import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
 
/**
 * @author Master801
 */
@SideOnly(Side.CLIENT)
public class SpriteAtlasCoreBase extends TextureAtlasSprite {//TODO
 
    protected SpriteAtlasCoreBase(String spriteName) {
        super(spriteName);
    }
    
    protected final void resetSprite() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ReflectionHelper.findMethod(TextureAtlasSprite.class, this, new String[] {"n", "func_130102_n", "resetSprite"}).invoke(this);
    }
 
    protected final void fixTransparentPixels(int[][] array) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ReflectionHelper.findMethod(TextureAtlasSprite.class, this, new String[] {"a", "func_147961_a", "fixTransparentPixels"}).invoke(this, array);
    }
 
    protected final int[][] prepareAnisotropicFiltering(int[][] array, int par2, int par3) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return (int[][]) ReflectionHelper.findMethod(TextureAtlasSprite.class, this, new String[] {"a", "func_147960_a", "prepareAnisotropicFiltering"}).invoke(this, array, par2, par3);
    }
 
    protected final void allocateFrameTextureData(int par1) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ReflectionHelper.findMethod(TextureAtlasSprite.class, this, new String[] {"e", "func_130099_d", "allocateFrameTextureData"}).invoke(this, par1);
    }
 
    protected final boolean getFieldUseAnisotropicFiltering() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        return (Boolean) ReflectionHelper.findMethod(TextureAtlasSprite.class, this, new String[] {"k", "field_147966_k", "useAnisotropicFiltering"}).invoke(this);
    }
 
    protected final void setFieldUseAnisotropicFiltering(boolean useAnisotropicFiltering) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        ReflectionHelper.findMethod(TextureAtlasSprite.class, this, new String[] {"k", "field_147966_k", "useAnisotropicFiltering"}).invoke(this, useAnisotropicFiltering);
    }
 
    protected final AnimationMetadataSection getAnimationMetadataSection() {
        return ReflectionHelper.getPrivateValue(TextureAtlasSprite.class, this, "j", "field_110982_k", "animationMetadata");
    }
 
    protected final void setAnimtationMetadataSection(AnimationMetadataSection newAnimtationMetadataSection) {
        ReflectionHelper.setPrivateValue(TextureAtlasSprite.class, this, newAnimtationMetadataSection, "j", "field_110982_k", "animationMetadata");
    }
 
    public final List<int[][]> getFramesTextureData() {
        return ReflectionHelper.getPrivateValue(TextureAtlasSprite.class, this, "a", "field_110976_a", "framesTextureData");
    }
 
    @Override
    public void generateMipmaps(int mipmapLevel) {
        /*
        getFramesTextureData().clear();
        if (width != height) {
            CoreResources.CORE_LIBRARY_LOGGER.error("Inconsistent Sprite ({}) width ({}) and height ({})!", "\"" + getIconName() + "|" + toString() + "\"", width, height);
            return;
        }
        for(int i = 0; i < getFramesTextureData().size(); i++) {
            getFramesTextureData().add(TextureUtil.generateMipmapData(mipmapLevel, new Double(Math.sqrt(width * height)).intValue(), getFramesTextureData().get(i)));
        }
        */
 
        super.generateMipmaps(mipmapLevel);//TODO Remove, this is just a temporary solution for the problem.
    }
 
	public static class CustomSpriteAtlas extends SpriteAtlasCoreBase {
	 
	    private final int[] rgb;
	 
	    public CustomSpriteAtlas(String spriteName, BufferedImage image, int spriteDimensions) {
	        super(spriteName);
	        width = image.getWidth();
	        height = image.getHeight();
	        rgb = image.getRGB(0, 0, getIconWidth(), getIconHeight(), new int[getIconWidth() * getIconHeight()], 0, spriteDimensions);
	    }
	 
	    @Override
	    public boolean load(IResourceManager resourceManager, ResourceLocation resourceLocation) {//loadSprite doesn't get called when this is returned as false.
	        getFramesTextureData().clear();
	        int[][] mipmapLevels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
	        if (mipmapLevels.length <= 0) {
	            System.out.println("ERROR: Couldn't generate mipmap levels?");
	            return true;
	        } else {
	            for (int i = 0; i < mipmapLevels.length; i++) {
	                mipmapLevels[i] = getRGBArray();
	            }
	            getFramesTextureData().add(mipmapLevels);
	        }
	        return false;
	    }
	 
	    public boolean doesUseResourceLocation() {
	        return false;
	    }
	 
	    public int[] getRGBArray() {
	        return rgb;
	    }
	 
	    @Override
	    public void generateMipmaps(int mipmapLevel) {
	        getFramesTextureData().clear();
	        if (getRGBArray() != null && getRGBArray().length > 1) {
	            int[][] mipmapData;
				try {
					mipmapData = prepareAnisotropicFiltering(new int[mipmapLevel + 1][], getIconWidth(), getIconHeight());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					return;
				}
	            if (isNullOrEmpty(mipmapData)) {
	            	System.out.println(String.format("ERROR: Couldn't generate mipmaps for sprite \"%s\"! Mipmap data is null?", getIconName()));
	                return;
	            }
	            for (int i = 0; i < mipmapData.length; i++) {
	                mipmapData[i] = getRGBArray();
	            }
	            getFramesTextureData().add(mipmapData);
	        } else {
	            System.out.println(String.format("WARNING: Couldn't get the RGB array for sprite \"%s\"!", getIconName()));
	        }
	    }
	    
	    @Override
	    public void updateAnimation() {
	        if (!this.doesUseResourceLocation()) {
	            return;//Don't update animations if we're not using a resource location, override this if you don't want this to be the default behavior.
	        }
	        super.updateAnimation();
	    }
	    
	    private boolean isNullOrEmpty(int[][] data)
	    {
	    	if(data == null)
	    		return true;
	    	for(int x = 0; x < data.length; x++)
	    		for(int y = 0; y < data[0].length; y++)
	    			if(data[x][y] != 0)
	    				return false;
	    	return true;
	    }
	    
	    @Override
	    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
            return !this.doesUseResourceLocation();
	    }
	}
}