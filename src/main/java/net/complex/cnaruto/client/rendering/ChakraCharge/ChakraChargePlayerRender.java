package net.complex.cnaruto.client.rendering.ChakraCharge;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.models.ModelRegister;
import net.complex.cnaruto.client.rendering.models.PlayerAuraModel;
import net.complex.cnaruto.systems.ChakraChargeManagerSubSystem.ClientChakraChargeManagerSubSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ChakraChargePlayerRender {


    @SubscribeEvent
    public static void RenderAura(RenderPlayerEvent.Post event) {
        Player player = event.getEntity();
        if (player != null)
        {

            if (ClientChakraChargeManagerSubSystem.IsCharging(player))
            {
                float chargeAnimationTracker = ClientChakraChargeManagerSubSystem.GetChargeTime(player) / 20.f;
                if (player.isAlive())
                {
                    ModelRegister.PLAYERAURAMODEL.render(event.getPoseStack(), event.getMultiBufferSource(), chargeAnimationTracker, 153.f/255.f, 224.f/255.f, 254/255.f, 0.5f);
                }
            }

        }

    }

}
