package net.complex.cnaruto.client.rendering.ExplosionHandler.ExplosionModels;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.RenderTypes.CNarutoRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.joml.Vector3f;

import java.awt.*;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class SphereModel {

    public static void RenderSphere(MultiBufferSource source, PoseStack stack, Color color, float radius, int latitudes, int longitudes) {


        VertexConsumer buffer = source.getBuffer(CNarutoRenderTypes.COLORED_SPHERE);
//

        for (int lat = 0; lat < latitudes; lat++) {
            double o1 = Math.PI * lat / latitudes - Math.PI / 2;
            double o2 = Math.PI * (lat + 1) / latitudes - Math.PI / 2;
            for (int lon = 0; lon < longitudes; lon++) {
                double p1 = 2 * Math.PI * lon / longitudes;
                double p2 = 2 * Math.PI * (lon + 1) / longitudes;

                Vector3f v1 = sphereDir(o1, p1);
                Vector3f v2 = sphereDir(o2, p1);
                Vector3f v3 = sphereDir(o2, p2);
                Vector3f v4 = sphereDir(o1, p2);

                // tri 1
                putVertex(buffer, stack, v1, radius, color);
                putVertex(buffer, stack, v2, radius, color);
                putVertex(buffer, stack, v3, radius, color);

                // tri 2
                putVertex(buffer, stack, v1, radius, color);
                putVertex(buffer, stack ,v3, radius, color);
                putVertex(buffer, stack ,v4, radius, color);

            }
        }


//        RenderSystem.setShaderColor(1,
//                1,
//                1,
//                1);


    }

    private static void putVertex(VertexConsumer consumer, PoseStack stack, Vector3f dir, float r, Color color) {
        consumer.vertex(stack.last().pose(), dir.x() * r, dir.y() * r, dir.z() * r)
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
                .normal(dir.x(), dir.y(), dir.z())
                .endVertex();
    }

    private static Vector3f sphereDir(double θ, double ϕ) {
        return new Vector3f(
                (float) (Math.cos(θ) * Math.cos(ϕ)),
                (float) (Math.sin(θ)),
                (float) (Math.cos(θ) * Math.sin(ϕ))
        );
    }
}