package net.complex.cnaruto.client.rendering.ExplosionHandler.Explosions;

import com.eliotlash.mclib.math.functions.utility.Lerp;
import com.eliotlash.mclib.utils.MathHelper;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.complex.cnaruto.client.rendering.ExplosionHandler.CExplosionEffect;
import net.complex.cnaruto.client.rendering.ExplosionHandler.CExplosionEffectInstance;
import net.complex.cnaruto.client.rendering.ExplosionHandler.EffectStatus;
import net.complex.cnaruto.client.rendering.ExplosionHandler.ExplosionModels.SphereModel;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import java.awt.*;

public class DefaultExplosionEffect extends CExplosionEffect {





    @Override
    public EffectStatus render(RenderLevelStageEvent event, CExplosionEffectInstance instance) {
        if (instance.GetTimer() < instance.getDurationTicks())
        {
            if (event.getStage() == RenderLevelStageEvent.Stage.AFTER_PARTICLES)
            {
                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                Color shaderColor = instance.getColor();

                MultiBufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();

                PoseStack stack = event.getPoseStack();
                stack.pushPose();

                float delta = instance.GetTimer() / instance.getDurationTicks();

                float finalScale = Mth.lerp(delta, 0, instance.getSize());
                float finalOpacity = Mth.lerp(delta, shaderColor.getAlpha(), 0);

                stack.scale(finalScale, finalScale, finalScale);

                Color finalColor = new Color(
                        shaderColor.getRed() / 255.f,
                        shaderColor.getGreen() / 255.f,
                        shaderColor.getBlue() / 255.f,
                        finalOpacity / 255.f
                );

                SphereModel.RenderSphere(buffers, event.getPoseStack(),finalColor
                ,1, 8,8);
                stack.popPose();

            }
            return EffectStatus.RUNNING;
        }
        return EffectStatus.STOPPED;
    }
}
