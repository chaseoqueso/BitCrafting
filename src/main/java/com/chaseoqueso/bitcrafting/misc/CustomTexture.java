package com.chaseoqueso.bitcrafting.misc;

import java.awt.image.BufferedImage;
import java.io.IOException;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.IResourceManager;

public class CustomTexture extends AbstractTexture {
	
	private int[] colorArray;
	
	public CustomTexture(int[] array)
	{
		colorArray = array;
	}
	
	@Override
	public void loadTexture(IResourceManager par1) throws IOException {
		this.deleteGlTexture();
	    BufferedImage icon = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
	    for (int i = 0; i < this.colorArray.length; i++) {
	        if(this.colorArray[i] != 0xFFFFFE)
	        	icon.setRGB(i % 16, i / 16, this.colorArray[i]);
	    }
	 
	    TextureUtil.uploadTextureImage(this.getGlTextureId(), icon);
	}

}
