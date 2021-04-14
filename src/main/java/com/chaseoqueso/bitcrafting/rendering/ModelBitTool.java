package com.chaseoqueso.bitcrafting.rendering;

import com.chaseoqueso.bitcrafting.BitCraftingMod;
import com.chaseoqueso.bitcrafting.items.tools.IItemBitTool;
import com.chaseoqueso.bitcrafting.items.tools.ItemBitSword;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.TRSRTransformer;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nullable;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.*;
import java.util.function.Function;

public class ModelBitTool implements IModel
{
    public static final IModel MODEL = new ModelBitTool();

    private ResourceLocation blankLocation;
    private int[] pixelColors;

    public ModelBitTool() { this(new int[256]); }

    public ModelBitTool(int[] colors)
    {
        blankLocation = new ResourceLocation(BitCraftingMod.MODID, "items/blank");

        if(colors.length != 256)
        {
            System.out.println("ERROR: Attempted to create a ModelBitSword with a color array of length != 256.");
            pixelColors = new int[256];
            return;
        }

        pixelColors = colors;
    }

    @Override
    public Collection<ResourceLocation> getTextures()
    {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

        builder.add(blankLocation);

        return builder.build();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        Map<ItemCameraTransforms.TransformType, TRSRTransformation> tempMap = PerspectiveMapWrapper.getTransforms(state);
        Map<ItemCameraTransforms.TransformType, TRSRTransformation> transformMap;
        if(tempMap.size() > 0)
        {
            transformMap = new EnumMap(tempMap);
        }
        else
        {
            transformMap = new EnumMap(ItemCameraTransforms.TransformType.class);
        }

        transformMap.put(ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, 0.1875f, 0.125f),
                                                                                                                            new Quat4f(-0.3265056f, -0.6272114f, 0.3265056f, 0.6272114f),
                                                                                                                            new Vector3f(0.85f, 0.85f, 0.85f),
                                                                                                                            null)));
        transformMap.put(ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, 0.1875f, 0.125f),
                                                                                                                           new Quat4f(-0.3265056f, 0.6272114f, -0.3265056f, 0.6272114f),
                                                                                                                           new Vector3f(0.85f, 0.85f, 0.85f),
                                                                                                                           null)));
        transformMap.put(ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, 0.1875f, 0.125f),
                                                                                                                            new Quat4f(-0.1530459f, -0.6903455f, 0.1530459f, 0.6903455f),
                                                                                                                            new Vector3f(0.68f, 0.68f, 0.68f),
                                                                                                                            null)));
        transformMap.put(ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, 0.1875f, 0.125f),
                                                                                                                           new Quat4f(-0.1530459f, 0.6903455f, -0.1530459f, 0.6903455f),
                                                                                                                           new Vector3f(0.68f, 0.68f, 0.68f),
                                                                                                                           null)));
        transformMap.put(ItemCameraTransforms.TransformType.GROUND, TRSRTransformation.blockCenterToCorner(new TRSRTransformation(new Vector3f(0, 0.125f, 0),
                                                                                                           new Quat4f(0, 0, 0, 1),
                                                                                                           new Vector3f(0.5f, 0.5f, 0.5f),
                                                                                                           null)));

        TRSRTransformation transform = state.apply(Optional.empty()).orElse(TRSRTransformation.identity());
        TextureAtlasSprite particleSprite = null;
        ImmutableList.Builder<BakedQuad> builder = ImmutableList.builder();

        int alpha = 0xFF000000;

        int pixelCount = 0;
        for(int i = 0; i < 256; ++i) {
            //Only add a quad if the pixel is not transparent
            if(pixelColors[i] != -1) {
                int x = i % 16;
                int y = i / 16;
                TextureAtlasSprite sprite = bakedTextureGetter.apply(blankLocation);
                builder.add(QuadBuilder.genQuad(format, transform, x, y, sprite, EnumFacing.NORTH, pixelColors[i] + alpha, pixelCount));
                builder.add(QuadBuilder.genQuad(format, transform, x, y, sprite, EnumFacing.SOUTH, pixelColors[i] + alpha, pixelCount));

                if(x == 0 || pixelColors[i-1] == -1)
                {
                    builder.add(QuadBuilder.genQuad(format, transform, x, y, sprite, EnumFacing.WEST, pixelColors[i] + alpha, pixelCount));
                }
                if(x == 15 || pixelColors[i+1] == -1)
                {
                    builder.add(QuadBuilder.genQuad(format, transform, x, y, sprite, EnumFacing.EAST, pixelColors[i] + alpha, pixelCount));
                }
                if(y == 0 || pixelColors[i-16] == -1)
                {
                    builder.add(QuadBuilder.genQuad(format, transform, x, y, sprite, EnumFacing.UP, pixelColors[i] + alpha, pixelCount));
                }
                if(y == 15 || pixelColors[i+16] == -1)
                {
                    builder.add(QuadBuilder.genQuad(format, transform, x, y, sprite, EnumFacing.DOWN, pixelColors[i] + alpha, pixelCount));
                }

                ++pixelCount;
            }
        }

        return new BakedBitTool(this, builder.build(), particleSprite, format, Maps.immutableEnumMap(transformMap), Maps.newHashMap(), transform.isIdentity());
    }

    @Override
    public ModelBitTool process(ImmutableMap<String, String> customData)
    {
        String colorArrayString = customData.get("colorArrayString");
        String[] items = colorArrayString.split(",");

        int[] textureArray = new int[256];

        for (int i = 0; i < items.length; i++) {
            try {
                textureArray[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: Something went wrong with converting the sword's color data from string to int array\nColor Array: " + colorArrayString);
            };
        }

        return new ModelBitTool(textureArray);
    }

    /**
     * Allows to use different textures for the model.
     * There are 3 layers:
     * base - The empty bucket/container
     * fluid - A texture representing the liquid portion. Non-transparent = liquid
     * cover - An overlay that's put over the liquid (optional)
     * <p/>
     * If no liquid is given a hardcoded variant for the bucket is used.
     */
    @Override
    public ModelBitTool retexture(ImmutableMap<String, String> textures)
    {
        return new ModelBitTool();
    }

    public enum LoaderBitTool implements ICustomModelLoader
    {
        INSTANCE;

        @Override
        public boolean accepts(ResourceLocation modelLocation)
        {
            return modelLocation.getResourceDomain().equals(BitCraftingMod.MODID) &&
                    (modelLocation.getResourcePath().contains("itembitsword")
                  || modelLocation.getResourcePath().contains("itembitpickaxe")
                  || modelLocation.getResourcePath().contains("itembitaxe")
                  || modelLocation.getResourcePath().contains("itembithoe")
                  || modelLocation.getResourcePath().contains("itembitshovel"));
        }

        @Override
        public IModel loadModel(ResourceLocation modelLocation)
        {
            return MODEL;
        }

        @Override
        public void onResourceManagerReload(IResourceManager resourceManager)
        {
            //I think I need to clear the cache here but idk what that means or how to do it (probably something to do with BakedBitSword.cache but that's private so idk)
        }
    }

    private static final class BakedBitToolOverrideHandler extends ItemOverrideList
    {
        public static final BakedBitToolOverrideHandler INSTANCE = new BakedBitToolOverrideHandler();
        private BakedBitToolOverrideHandler()
        {
            super(ImmutableList.of());
        }

        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, @Nullable World world, @Nullable EntityLivingBase entity)
        {
            if (!(stack.getItem() instanceof IItemBitTool))
            {
                System.out.println("Not a Bit Tool apparently. Instead it's: " + stack.getItem());
                return originalModel;
            }

            BakedBitTool model = (BakedBitTool)originalModel;

            String arrayString = IItemBitTool.getColorArrayAsString(stack);

            if (!model.cache.containsKey(arrayString))
            {
                ModelBitTool parent = model.parent.process(ImmutableMap.of("colorArrayString", arrayString));
                Function<ResourceLocation, TextureAtlasSprite> textureGetter;
                textureGetter = location -> Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(location.toString());

                IBakedModel bakedModel = parent.bake(new SimpleModelState(model.transforms), model.format, textureGetter);
                model.cache.put(arrayString, bakedModel);
                return bakedModel;
            }

            return model.cache.get(arrayString);
        }
    }

    private static final class BakedBitTool extends BakedItemModel
    {
        private final ModelBitTool parent;
        private final Map<String, IBakedModel> cache;
        private final VertexFormat format;
        private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

        BakedBitTool(ModelBitTool parent,
                     ImmutableList<BakedQuad> quads,
                     TextureAtlasSprite particle,
                     VertexFormat format,
                     ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                     Map<String, IBakedModel> cache,
                     boolean untransformed)
        {
            super(quads, particle, transforms, BakedBitToolOverrideHandler.INSTANCE, untransformed);
            this.transforms = transforms;
            this.format = format;
            this.parent = parent;
            this.cache = cache;
        }
    }

    private static class QuadBuilder
    {
        public static UnpackedBakedQuad genQuad(VertexFormat format, TRSRTransformation transform, float x, float y, TextureAtlasSprite sprite, EnumFacing facing, int color, int tint)
        {
            float u1 = sprite.getInterpolatedU(x);
            float v1 = sprite.getInterpolatedV(y);
            float u2 = sprite.getInterpolatedU(x + 1);
            float v2 = sprite.getInterpolatedV(y + 1);

            return putQuad(format, transform, facing, sprite, color, tint, x, y, u1, v1, u2, v2);
        }

        private static UnpackedBakedQuad putQuad(VertexFormat format, TRSRTransformation transform, EnumFacing side, TextureAtlasSprite sprite, int color, int tint,
                                                 float x, float y,
                                                 float u1, float v1, float u2, float v2)
        {
            UnpackedBakedQuad.Builder builder = new UnpackedBakedQuad.Builder(format);

            builder.setQuadTint(tint);
            builder.setQuadOrientation(side);
            builder.setTexture(sprite);

            float x1 = x;
            float x2 = x + 1;
            float y1 = y;
            float y2 = y + 1;
            float z1 = 8.5f/16f;
            float z2 = 7.5f/16f;

            x1 /= 16f;
            y1 /= 16f;
            x2 /= 16f;
            y2 /= 16f;

            float tmp = y1;
            y1 = 1f - y2;
            y2 = 1f - tmp;

            // only apply the transform if it's not identity
            boolean hasTransform = !transform.isIdentity();
            IVertexConsumer consumer = hasTransform ? new TRSRTransformer(builder, transform) : builder;

            switch(side)
            {
                case SOUTH:
                    putVertex(consumer, format, side, x1, y1, z1, u1, v2, color);
                    putVertex(consumer, format, side, x2, y1, z1, u2, v2, color);
                    putVertex(consumer, format, side, x2, y2, z1, u2, v1, color);
                    putVertex(consumer, format, side, x1, y2, z1, u1, v1, color);
                    break;
                case NORTH:
                    putVertex(consumer, format, side, x1, y1, z2, u1, v2, color);
                    putVertex(consumer, format, side, x1, y2, z2, u1, v1, color);
                    putVertex(consumer, format, side, x2, y2, z2, u2, v1, color);
                    putVertex(consumer, format, side, x2, y1, z2, u2, v2, color);
                    break;
                case WEST:
                    putVertex(consumer, format, side, x1, y1, z1, u1, v2, color);
                    putVertex(consumer, format, side, x1, y2, z1, u1, v1, color);
                    putVertex(consumer, format, side, x1, y2, z2, u2, v1, color);
                    putVertex(consumer, format, side, x1, y1, z2, u2, v2, color);
                    break;
                case EAST:
                    putVertex(consumer, format, side, x2, y1, z2, u1, v2, color);
                    putVertex(consumer, format, side, x2, y2, z2, u1, v1, color);
                    putVertex(consumer, format, side, x2, y2, z1, u2, v1, color);
                    putVertex(consumer, format, side, x2, y1, z1, u2, v2, color);
                    break;
                case UP:
                    putVertex(consumer, format, side, x1, y2, z1, u1, v2, color);
                    putVertex(consumer, format, side, x2, y2, z1, u1, v1, color);
                    putVertex(consumer, format, side, x2, y2, z2, u2, v1, color);
                    putVertex(consumer, format, side, x1, y2, z2, u2, v2, color);
                    break;
                case DOWN:
                    putVertex(consumer, format, side, x2, y1, z1, u1, v2, color);
                    putVertex(consumer, format, side, x1, y1, z1, u2, v2, color);
                    putVertex(consumer, format, side, x1, y1, z2, u2, v1, color);
                    putVertex(consumer, format, side, x2, y1, z2, u1, v1, color);
                    break;
            }

            return builder.build();
        }

        private static void putVertex(IVertexConsumer consumer, VertexFormat format, EnumFacing side,
                                      float x, float y, float z, float u, float v, int color)
        {
            for (int e = 0; e < format.getElementCount(); e++)
            {
                switch (format.getElement(e).getUsage())
                {
                    case POSITION:
                        consumer.put(e, x, y, z, 1f);
                        break;
                    case COLOR:
                        float r = ((color >> 16) & 0xFF) / 255f; // red
                        float g = ((color >>  8) & 0xFF) / 255f; // green
                        float b = ((color >>  0) & 0xFF) / 255f; // blue
                        float a = ((color >> 24) & 0xFF) / 255f; // alpha
                        consumer.put(e, r, g, b, a);
                        break;
                    case NORMAL:
                        float offX = (float) side.getFrontOffsetX();
                        float offY = (float) side.getFrontOffsetY();
                        float offZ = (float) side.getFrontOffsetZ();
                        consumer.put(e, offX, offY, offZ, 0f);
                        break;
                    case UV:
                        if (format.getElement(e).getIndex() == 0)
                        {
                            consumer.put(e, u, v, 0f, 1f);
                            break;
                        }
                        // else fallthrough to default
                    default:
                        consumer.put(e);
                        break;
                }
            }
        }
    }
}
