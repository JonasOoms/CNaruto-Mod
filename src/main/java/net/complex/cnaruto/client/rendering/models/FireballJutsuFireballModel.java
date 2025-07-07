package net.complex.cnaruto.client.rendering.models;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.entities.jutsu_entities.FireBallJutsuFireballProjectile;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FireballJutsuFireballModel extends GeoModel<FireBallJutsuFireballProjectile> {

	private final ResourceLocation model = new ResourceLocation(CNaruto.MODID, "geo/fireballjutsu.geo.json");
	private final ResourceLocation texture = new ResourceLocation(CNaruto.MODID, "textures/entities/fireballjutsu.png");
	private final ResourceLocation animations = new ResourceLocation(CNaruto.MODID, "animations/fireballjutsu.animation.json");

	@Override
	public ResourceLocation getModelResource(FireBallJutsuFireballProjectile fireBallJutsuFireballProjectile) {
		return model;
	}

	@Override
	public ResourceLocation getTextureResource(FireBallJutsuFireballProjectile fireBallJutsuFireballProjectile) {
		return texture;
	}

	@Override
	public ResourceLocation getAnimationResource(FireBallJutsuFireballProjectile fireBallJutsuFireballProjectile) {
		return animations;
	}
}