package net.complex.cnaruto.client.rendering;

import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT)
public class DojutsuBlindingRenderer {

    public static PostChain postChain;

    @SubscribeEvent
    public static void onRenderLevelStage(RenderLevelStageEvent event) throws IOException {
        if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_LEVEL) {
            Minecraft mc = Minecraft.getInstance();

            //BlurShaderHandler.setBlurStrength(50);
            //BlurShaderHandler.applyBlur();

            if (false) {

               // Minecraft.getInstance().gameRenderer.loadEffect(new ResourceLocation("minecraft:shaders/program/color_convolve.json"));

                //postChain = mc.
              //  postChain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
               // postChain.process(mc.getFrameTime());
//                try {
//                    if (postChain == null) {
//
//                        postChain = new PostChain(mc.getTextureManager(), mc.getResourceManager(), mc.getMainRenderTarget(), new ResourceLocation(CNaruto.MODID, "shaders/post/blur.json"));
//                        postChain.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
//                    }
//
//                    postChain.process(mc.getFrameTime());
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
            }
        }
    }
}
