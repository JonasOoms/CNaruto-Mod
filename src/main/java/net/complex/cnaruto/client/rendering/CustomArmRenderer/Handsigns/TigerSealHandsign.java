package net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns;

import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.CustomArmRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;

public class TigerSealHandsign implements IHandsign {
    private final static ResourceLocation icon = new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/tiger.png");

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

                    stack.translate(-0.57*sign,-0.5f,-0.3f);

                    stack.mulPose(Axis.YP.rotationDegrees(180*sign));


                    stack.mulPose(Axis.ZP.rotationDegrees(-30*sign));

                };
    }
}
