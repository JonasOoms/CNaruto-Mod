package net.complex.cnaruto.client.rendering.models;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.Random;


public class PlayerAuraModel<T extends Entity> extends EntityModel<T> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(CNaruto.MODID, "textures/entities/aura.png");
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CNaruto.MODID, "player_aura"), "main");
    private final ModelPart base;
    private final ModelPart baseAlt;
    private final ModelPart cone;
    private final ModelPart coneAlt;
    private final ModelPart bone;

    public float animationLength = 1.5f;

    public PlayerAuraModel(ModelPart root) {
        this.base = root.getChild("base");
        this.baseAlt = this.base.getChild("basealt");
        this.cone = root.getChild("cone");
        this.coneAlt = this.cone.getChild("conealt");
        this.bone = root.getChild("top");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-8.0F, -15.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.9163F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = base.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-8.0F, -15.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, -0.9163F, 1.5708F, 0.0F));

        PartDefinition cube_r3 = base.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(64, 16).addBox(-8.0F, -15.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, 0.0F, 0.9163F, -1.5708F, 0.0F));

        PartDefinition cube_r4 = base.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(64, 16).addBox(-8.0F, -15.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, -0.9163F, -1.5708F, 0.0F));

        PartDefinition cube_r5 = base.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(64, 16).addBox(-8.0F, -15.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r6 = base.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(64, 16).addBox(-8.0F, -15.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -8.0F, 0.9163F, 0.0F, 0.0F));

        PartDefinition basealt = base.addOrReplaceChild("basealt", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

        PartDefinition cube_r7 = basealt.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-8.0F, -18.7527F, 8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -4.2613F, 0.0F, -0.9163F, 1.5708F, 0.0F));

        PartDefinition cube_r8 = basealt.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-8.0F, -18.7527F, -8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, -4.2613F, 0.0F, 0.9163F, 1.5708F, 0.0F));

        PartDefinition cube_r9 = basealt.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-8.0F, -18.7527F, 8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 16).addBox(-8.0F, -18.7527F, 8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.2613F, -2.0F, -0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r10 = basealt.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(64, 16).mirror().addBox(-8.0F, -18.7527F, -8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(64, 16).addBox(-8.0F, -18.7527F, -8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.2613F, 2.0F, 0.9163F, 0.0F, 0.0F));

        PartDefinition cube_r11 = basealt.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(64, 16).addBox(-8.0F, -18.7527F, 8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -4.2613F, 0.0F, -0.9163F, -1.5708F, 0.0F));

        PartDefinition cube_r12 = basealt.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(64, 16).addBox(-8.0F, -18.7527F, -8.2508F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -4.2613F, 0.0F, 0.9163F, -1.5708F, 0.0F));

        PartDefinition cone = partdefinition.addOrReplaceChild("cone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r13 = cone.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -1.0F, 0.0F, -0.0873F, -1.5708F, 0.0F));

        PartDefinition cube_r14 = cone.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -1.0F, 0.0F, 0.0873F, -1.5708F, 0.0F));

        PartDefinition cube_r15 = cone.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 11.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r16 = cone.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -11.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition conealt = cone.addOrReplaceChild("conealt", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r17 = conealt.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -0.5F, 0.0F, 0.0873F, -1.5708F, 0.0F));

        PartDefinition cube_r18 = conealt.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, -0.5F, 0.0F, -0.0873F, -1.5708F, 0.0F));

        PartDefinition cube_r19 = conealt.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 9.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r20 = conealt.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -37.0F, 0.0F, 16.0F, 38.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, -9.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition top = partdefinition.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r21 = top.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -48.0F, 0.0F, 16.0F, 49.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.0F, -1.0F, 0.0F, -0.1745F, -1.5708F, 0.0F));

        PartDefinition cube_r22 = top.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -48.0F, 0.0F, 16.0F, 49.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-10.0F, -1.0F, 0.0F, 0.1745F, -1.5708F, 0.0F));

        PartDefinition cube_r23 = top.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -48.0F, 0.0F, 16.0F, 49.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 11.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r24 = top.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 38).addBox(-8.0F, -48.0F, 0.0F, 16.0F, 49.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -11.0F, -0.1745F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    public void render(PoseStack poseStack, MultiBufferSource buffers, float animationProgress, float r, float g, float b, float a)
    {
        VertexConsumer vb = buffers.getBuffer(
                RenderType.entityTranslucent(
                        TEXTURE)
        );

        poseStack.pushPose();
        Random random = new Random();
        float mul = random.nextFloat(0.1f);
        poseStack.scale(1 - mul, 1 - 0.1f, 1 - 0.1f);
        poseStack.mulPose(Axis.XP.rotationDegrees(180));
        poseStack.translate(0,-1.5,0);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        float alphaProgress = Mth.lerp(animationProgress/animationLength, 0.0F, a);
        float alpha = Float.min(a, alphaProgress);
        //RenderSystem.setShaderColor(1.f,1.f,1.f,alpha);

        float period = 2*Mth.PI/20.f;

        float cwAngle = 360 * Mth.sin(animationProgress*period);
        float ccwAngle = 360 * Mth.cos(animationProgress*period);

        // base, cw
        poseStack.pushPose();
        {
            poseStack.mulPose(Axis.YP.rotationDegrees(cwAngle));
            base.render(poseStack, vb, 255, OverlayTexture.NO_OVERLAY, r, g, b, alpha);
        }
        poseStack.popPose();

        // cone, ccw
        poseStack.pushPose();
        {
            poseStack.mulPose(Axis.YP.rotationDegrees(ccwAngle));
            cone.render(poseStack, vb, 255, OverlayTexture.NO_OVERLAY, r, g, b, alpha);
        }
        poseStack.popPose();

        // top, cw
        poseStack.pushPose();
        {
            poseStack.mulPose(Axis.YP.rotationDegrees(cwAngle));
            bone.render(poseStack, vb, 255, OverlayTexture.NO_OVERLAY, r, g, b, alpha);
        }
        poseStack.popPose();




        //ModelRegister.PLAYERAURAMODEL.renderToBuffer(poseStack, vb, 255, OverlayTexture.NO_OVERLAY, 1.f, 1f, 1f, 0.5f);
        poseStack.popPose();

        RenderSystem.setShaderColor(1.f,1.f,1.f,1.f);

    }


    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        base.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        cone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}