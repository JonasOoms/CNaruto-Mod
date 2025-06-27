package net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns;

import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.CustomArmRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import org.joml.Quaternionf;

public class BoarSealHandsign implements IHandsign {

    public static final ResourceLocation icon = new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/boar.png");

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

            stack.translate(-.1f*sign,0.6f,-0.3f);



            //stack.pushPose();
            stack.mulPose(Axis.YP.rotationDegrees(180*sign));
            stack.mulPose(Axis.XP.rotationDegrees(180));
            stack.mulPose(Axis.ZP.rotationDegrees(-70*sign));

        };
    }
}
