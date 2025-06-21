package net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns;

import net.complex.cnaruto.client.rendering.CustomArmRenderer.CustomArmRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

/// Provides an interface for handsigns.
public interface IHandsign {
    ResourceLocation GetHandsignIcon();
    CustomArmRenderer.ArmPose GetHandsignArmPose(HumanoidArm arm);
}
