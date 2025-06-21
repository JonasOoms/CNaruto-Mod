package net.complex.cnaruto.client.rendering.CustomArmRenderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class CustomArmRenderer {
    public interface ArmPose {
        void apply(PoseStack stack, float equipProgress, float swingProgress, float sign);
    }


    public static final ArmPose VANILLA_FIRST_PERSON = (stack, equip, swing, sign) -> {
        float root = Mth.sqrt(swing);
        float swingX = -0.3F * Mth.sin(root * (float)Math.PI);
        float swingY =  0.4F * Mth.sin(root * ((float)Math.PI * 2F));
        float swingZ = -0.4F * Mth.sin(swing * (float)Math.PI);

        // initial translation
        stack.translate(
                sign * (swingX + 0.64000005F),
                (swingY - 0.6F + equip * -0.6F),
                (swingZ - 0.71999997F)
        );

        // yaw into view
        stack.mulPose(Axis.YP.rotationDegrees(sign * 45.0F));

        // additional swing rotations
        float swingSq = Mth.sin(swing * swing * (float)Math.PI);
        float swingRoot = Mth.sin(root * (float)Math.PI);
        stack.mulPose(Axis.YP.rotationDegrees(sign * swingRoot * 70.0F));
        stack.mulPose(Axis.ZP.rotationDegrees(sign * swingSq * -20.0F));

        // “pick up” the hand
        stack.translate(sign * -1.0F, 3.6F, 3.5F);
        stack.mulPose(Axis.ZP.rotationDegrees(sign * 120.0F));
        stack.mulPose(Axis.XP.rotationDegrees(200.0F));
        stack.mulPose(Axis.YP.rotationDegrees(sign * -135.0F));
        stack.translate(sign * 5.6F, 0.0F, 0.0F);
    };

    /**
     * Core rendering method—now takes an ArmPose strategy.
     */
    public static void renderPlayerArm(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            float equipProgress,
            float swingProgress,
            HumanoidArm armSide,
            ArmPose customPose
    ) {
        Minecraft mc = Minecraft.getInstance();
        boolean isRight = (armSide == HumanoidArm.RIGHT);
        float sign = isRight ? 1.0F : -1.0F;

        AbstractClientPlayer player = mc.player;
        PlayerRenderer renderer = (PlayerRenderer)mc.getEntityRenderDispatcher()
                .getRenderer(player);


        poseStack.pushPose();
        customPose.apply(poseStack, equipProgress, swingProgress, sign);


        RenderSystem.setShaderTexture(0, player.getSkinTextureLocation());


        poseStack.pushPose();

        if (isRight) {
            renderer.renderRightHand(poseStack, buffer, packedLight, player);
        } else {
            renderer.renderLeftHand (poseStack, buffer, packedLight, player);
        }
        poseStack.popPose();

        poseStack.popPose();
    }


    private static void transformHandSigns(PoseStack stack, HumanoidArm arm) {

    }

    // convenience overload: default to vanilla first person
    public static void renderPlayerArm(
            PoseStack poseStack,
            MultiBufferSource buffer,
            int packedLight,
            float equipProgress,
            float swingProgress,
            HumanoidArm armSide
    ) {
        renderPlayerArm(poseStack, buffer, packedLight,
                equipProgress, swingProgress,
                armSide,
                VANILLA_FIRST_PERSON);
    }
}

