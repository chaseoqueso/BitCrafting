package com.chaseoqueso.bitcrafting.alt_vanilla;

import com.google.common.collect.Maps;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Map;

public class BitSwordTextureHandler {
    private final TextureManager textureManager;
    private final Map<String, Instance> loadedMaps = Maps.<String, BitSwordTextureHandler.Instance>newHashMap();

    public BitSwordTextureHandler(TextureManager textureManagerIn)
    {
        this.textureManager = textureManagerIn;
    }

    public void updateMapTexture(MapData mapdataIn)
    {
        this.getMapRendererInstance(mapdataIn).updateMapTexture();
    }

    public void renderMap(MapData mapdataIn, boolean noOverlayRendering)
    {
        this.getMapRendererInstance(mapdataIn).render(noOverlayRendering);
    }

    private BitSwordTextureHandler.Instance getMapRendererInstance(MapData mapdataIn)
    {
        BitSwordTextureHandler.Instance mapitemrenderer$instance = this.loadedMaps.get(mapdataIn.mapName);

        if (mapitemrenderer$instance == null)
        {
            mapitemrenderer$instance = new BitSwordTextureHandler.Instance(mapdataIn);
            this.loadedMaps.put(mapdataIn.mapName, mapitemrenderer$instance);
        }

        return mapitemrenderer$instance;
    }

    @Nullable
    public BitSwordTextureHandler.Instance getMapInstanceIfExists(String p_191205_1_)
    {
        return this.loadedMaps.get(p_191205_1_);
    }

    public void clearLoadedMaps()
    {
        for (BitSwordTextureHandler.Instance mapitemrenderer$instance : this.loadedMaps.values())
        {
            this.textureManager.deleteTexture(mapitemrenderer$instance.location);
        }

        this.loadedMaps.clear();
    }

    @Nullable
    public MapData getData(@Nullable BitSwordTextureHandler.Instance p_191207_1_)
    {
        return p_191207_1_ != null ? p_191207_1_.mapData : null;
    }

    @SideOnly(Side.CLIENT)
    class Instance
    {
        private final MapData mapData;
        private final DynamicTexture mapTexture;
        private final ResourceLocation location;
        private final int[] mapTextureData;

        private Instance(MapData mapdataIn)
        {
            this.mapData = mapdataIn;
            this.mapTexture = new DynamicTexture(128, 128);
            this.mapTextureData = this.mapTexture.getTextureData();
            this.location = BitSwordTextureHandler.this.textureManager.getDynamicTextureLocation("map/" + mapdataIn.mapName, this.mapTexture);

            for (int i = 0; i < this.mapTextureData.length; ++i)
            {
                this.mapTextureData[i] = 0;
            }
        }

        private void updateMapTexture()
        {
            for (int i = 0; i < 16384; ++i)
            {
                int j = this.mapData.colors[i] & 255;

                if (j / 4 == 0)
                {
                    this.mapTextureData[i] = (i + i / 128 & 1) * 8 + 16 << 24;
                }
                else
                {
                    this.mapTextureData[i] = MapColor.COLORS[j / 4].getMapColor(j & 3);
                }
            }

            this.mapTexture.updateDynamicTexture();
        }

        private void render(boolean noOverlayRendering) {
        }
    }
}
