package net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns;

import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.CustomArmRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class HorseSealHandsign implements IHandsign {
    private static final ResourceLocation icon = new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/horse.png");

    @Override
    public ResourceLocation GetHandsignIcon() {
        return icon;
    }

    @Override
    public CustomArmRenderer.ArmPose GetHandsignArmPose(HumanoidArm arm) {
        return (stack, equip, swing, sign) -> {


            stack.translate(
                    sign * (0 + 0.64000005F),
                    (0 - 0.6F + equip * -0.6F),
                    (0 - 0.71999997F)
            );

            stack.translate(-0.04*sign,-0.3f,0f);

            stack.mulPose(Axis.YP.rotationDegrees(270*sign));

            stack.mulPose(Axis.XP.rotationDegrees(50));

        };
    }
}
