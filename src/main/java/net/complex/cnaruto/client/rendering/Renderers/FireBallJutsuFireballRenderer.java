package net.complex.cnaruto.client.rendering.Renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.models.FireballJutsuFireballModel;
import net.complex.cnaruto.entities.jutsu_entities.FireBallJutsuFireballProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid= CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class FireBallJutsuFireballRenderer extends EntityRenderer<FireBallJutsuFireballProjectile> {

    private final FireballJutsuFireballModel<FireBallJutsuFireballProjectile> model;

    public FireBallJutsuFireballRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
        this.model = new FireballJutsuFireballModel<>(pContext.bakeLayer(FireballJutsuFireballModel.LAYER_LOCATION));
    }

    @Override
    public void render(FireBallJutsuFireballProjectile ent, float yaw, float pt, PoseStack pose,
                       MultiBufferSource buffers, int light)
    {
        pose.pushPose();


        float partialTicks = Minecraft.getInstance().getPartialTick();


        System.out.println(ent.getXRot());

        pose.mulPose(Axis.YP.rotationDegrees(ent.getYRot() - 180));
        pose.mulPose(Axis.XP.rotationDegrees(ent.getXRot()));

        model.render(pose, buffers, light, OverlayTexture.NO_OVERLAY, 1,1,1,1);
        pose.popPose();
        //super.render(ent, yaw, pt, pose, buffers, light);

    }

    @Override
    public ResourceLocation getTextureLocation(FireBallJutsuFireballProjectile fireBallJutsuFireballProjectile) {
        return null;
    }

    @SubscribeEvent
    public static void onRegisterLayerDefs(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                FireballJutsuFireballModel.LAYER_LOCATION,
                FireballJutsuFireballModel::createBodyLayer
        );
    }
}
