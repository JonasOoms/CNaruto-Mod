package net.complex.cnaruto.client.rendering;

import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

@Mod.EventBusSubscriber
public class BlurShaderHandler {


    private static int lastWidth = -1;
    private static int lastHeight = -1;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        int currentWidth = mc.getWindow().getWidth();
        int currentHeight = mc.getWindow().getHeight();

        if (currentWidth != lastWidth || currentHeight != lastHeight) {
            lastWidth = currentWidth;
            lastHeight = currentHeight;

            // Resize your shader here
           if (blurShader != null) {
               blurShader.resize(lastWidth, lastHeight);
           }

        }
    }



    private static PostChain blurShader;
    public static void loadBlurShader() {
        Minecraft mc = Minecraft.getInstance();
        if (blurShader != null) blurShader.close();

        try {
            blurShader = new PostChain(
                    mc.getTextureManager(),
                    mc.getResourceManager(),
                    mc.getMainRenderTarget(),
                    new ResourceLocation(CNaruto.MODID, "shaders/post/blur.json")
            );
            blurShader.resize(mc.getWindow().getWidth(), mc.getWindow().getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static PostPass getPassByName(String name)
    {
        try
        {
            Field passesField = PostChain.class.getDeclaredField("passes");
            passesField.setAccessible(true);
            List<PostPass> passes = (List<PostPass>) passesField.get(blurShader);
            for (PostPass pass : passes) {
                if (pass.getName().equals(name)) {
                    return pass;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void setBlurStrength(float strength) {
        if (blurShader != null) {
            PostPass blurPass = getPassByName("blur");
            if (blurPass != null) {
                EffectInstance effect = blurPass.getEffect();
                if (effect.getUniform("Radius") != null) {
                    effect.safeGetUniform("Radius").set(strength);
                }
            }
        }
    }

    public static void applyBlur() {
        if (blurShader != null) {
            blurShader.process(Minecraft.getInstance().getFrameTime());
            Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
        } else
        {
            loadBlurShader();
        }
    }

    public static void clearShader() {
        if (blurShader != null) {
            blurShader.close();
            blurShader = null;
        }
    }
}