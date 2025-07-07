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
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class FireBallJutsuFireballRenderer extends GeoEntityRenderer<FireBallJutsuFireballProjectile> {

    private final FireballJutsuFireballModel model;

    public FireBallJutsuFireballRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new FireballJutsuFireballModel());
        this.model = new FireballJutsuFireballModel();

    }

    @Override
    public void render(FireBallJutsuFireballProjectile ent, float yaw, float pt, PoseStack pose,
                       MultiBufferSource buffers, int light)
    {
        pose.pushPose();


        float partialTicks = Minecraft.getInstance().getPartialTick();


        System.out.println(ent.getXRot());

        float power = ent.GetPower();
        pose.scale(power,power,power);
        pose.mulPose(Axis.YP.rotationDegrees(ent.getYRot()));
        pose.mulPose(Axis.XP.rotationDegrees(-ent.getXRot()));

        super.render(ent, yaw, pt, pose, buffers, light);

        pose.popPose();
        //super.render(ent, yaw, pt, pose, buffers, light);

    }

}
