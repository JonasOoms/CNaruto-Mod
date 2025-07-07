package net.complex.cnaruto.client.rendering.ExplosionHandler;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

/// Handles explosion effect on the client. Does the rendering and camera shake

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientExplosionHandler
{

    private static ArrayList<CExplosionEffectInstance> explosionInstances = new ArrayList<>();


    public static void PushExplosion(CExplosionEffectInstance instance)
    {
        explosionInstances.add(instance);
    }

    @SubscribeEvent
    public static void RenderLevel(RenderLevelStageEvent event)
    {

        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES) return;

        ArrayList<CExplosionEffectInstance> toRemove = new ArrayList<>();
        for (CExplosionEffectInstance instance : explosionInstances)
        {
            PoseStack stack = event.getPoseStack();
            Camera camera = event.getCamera();
            Vec3 campos = camera.getPosition();
            Vec3 explosionOrigin = instance.getOrigin();
            Vec3 delta = new Vec3(
                    explosionOrigin.x - campos.x,
                    explosionOrigin.y - campos.y,
                    explosionOrigin.z - campos.z
            );
            stack.pushPose();

            stack.translate(delta.x, delta.y, delta.z);
//            RenderSystem.applyModelViewMatrix();
//            stack.mulPoseMatrix(event.getProjectionMatrix());



            EffectStatus status = instance.render(event);


            stack.popPose();

            if (status == EffectStatus.STOPPED)
            {
                toRemove.add(instance);
            }
        }

        explosionInstances.removeAll(toRemove);


    }

}
