package net.complex.cnaruto.api;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;

import java.io.IOException;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CRenderUtils {

    private static ShaderInstance maskShader;

   // @SubscribeEvent
   // public static void onRegisterShaders(RegisterShadersEvent event) throws IOException
    //{
   //     event.registerShader(
  //              new ShaderInstance(event.getResourceProvider(), new ResourceLocation(CNaruto.MODID, "maskshader"), DefaultVertexFormat.POSITION_TEX), shader -> maskShader = shader);
//
    //}

    public static void MaskShaderRender(GuiGraphics guiGraphics, ResourceLocation fill, ResourceLocation mask, int x, int y, int width, int height)
    {
        RenderSystem.setShader(() -> maskShader);

        maskShader.setSampler("Sampler0", 0);
        maskShader.setSampler("Sampler1", 1);

        RenderSystem.setShaderTexture(0, fill);
        Minecraft.getInstance().getTextureManager().bindForSetup(fill);

        RenderSystem.setShaderTexture(1, mask);
        Minecraft.getInstance().getTextureManager().bindForSetup(mask);


        Minecraft mc = Minecraft.getInstance();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.getBuilder();

        Matrix4f matrix = guiGraphics.pose().last().pose();

        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);

        buffer.vertex(matrix, x,         y,          0.0F).uv(0.0F, 0.0F).endVertex(); // Top-left
        buffer.vertex(matrix, x + width, y,          0.0F).uv(1.0F, 0.0F).endVertex(); // Top-right
        buffer.vertex(matrix, x + width, y + height, 0.0F).uv(1.0F, 1.0F).endVertex(); // Bottom-right
        buffer.vertex(matrix, x,         y + height, 0.0F).uv(0.0F, 1.0F).endVertex(); // Bottom-left

        tesselator.end();

    }

    public static void tileTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height, int textureResolution) {
        for (int dx = 0; dx < width; dx += textureResolution) {
            for (int dy = 0; dy < height; dy += textureResolution) {
                int tileWidth = Math.min(textureResolution, width - dx);
                int tileHeight = Math.min(textureResolution, height - dy);
                guiGraphics.blit(texture, x + dx, y + dy, 0, 0, tileWidth, tileHeight, textureResolution, textureResolution);
            }
        }
    }

    public static void drawFittedComponentText(GuiGraphics guiGraphics, Font font, Component component, int x, int y, int width, int height, int color) {
        String text = component.getString(); // Flatten to plain string for measuring

        int textWidth = font.width(text);
        int textHeight = 8; // Default font height

        // Determine uniform scale that fits within the box
        float scaleX = (float) width / (float) textWidth;
        float scaleY = (float) height / (float) textHeight;
        float scale = Math.min(scaleX, scaleY);

        // Clamp scale to avoid weird cases
        scale = Math.max(0.1f, Math.min(scale, 4.0f));

        float scaledWidth = textWidth * scale;
        float scaledHeight = textHeight * scale;

        float drawX = x + (width - scaledWidth) / 2f;
        float drawY = y + (height - scaledHeight) / 2f;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(drawX, drawY, 0);
        poseStack.scale(scale, scale, 1.0f);

        guiGraphics.drawString(font, component, 0, 0, color, false);

        poseStack.popPose();
    }

}
