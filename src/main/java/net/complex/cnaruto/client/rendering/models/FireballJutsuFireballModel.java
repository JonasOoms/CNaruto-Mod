package net.complex.cnaruto.client.rendering.models;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.entities.jutsu_entities.FireBallJutsuFireballProjectile;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class FireballJutsuFireballModel<T extends FireBallJutsuFireballProjectile> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(CNaruto.MODID, "fireballjutsumodel"), "main");
	private final ModelPart Ball;
	private final ModelPart Trail;

	private final static ResourceLocation fireballTexture = new ResourceLocation(CNaruto.MODID, "textures/entities/fireball.png");
	private final static ResourceLocation fire = new ResourceLocation(CNaruto.MODID, "textures/entities/fire.png");

	public FireballJutsuFireballModel(ModelPart root) {
		this.Ball = root.getChild("Ball");
		this.Trail = root.getChild("Trail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Ball = partdefinition.addOrReplaceChild("Ball", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition Trail = partdefinition.addOrReplaceChild("Trail", CubeListBuilder.create(), PartPose.offset(0.0F, 8.0F, 0.0F));

		PartDefinition cube_r1 = Trail.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 16.0F, -1.5708F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 32);
	}

	public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
	{

		VertexConsumer ballVc = buffers.getBuffer(
				RenderType.entityCutoutNoCull(fireballTexture)
		);

		Ball.render(poseStack, ballVc, packedLight, packedOverlay, red, green, blue, alpha);

		VertexConsumer trailVc = buffers.getBuffer(RenderType.entityCutoutNoCull(fire));
		Trail.render(poseStack, trailVc, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void setupAnim(FireBallJutsuFireballProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {

//		Ball.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//		Trail.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}