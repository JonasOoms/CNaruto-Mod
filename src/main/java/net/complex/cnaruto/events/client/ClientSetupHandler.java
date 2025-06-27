package net.complex.cnaruto.events.client;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.rendering.Renderers.FireBallJutsuFireballRenderer;
import net.complex.cnaruto.entities.ModEntities;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetupHandler {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {

        event.enqueueWork(() -> {
            EntityRenderers.register(
                    ModEntities.FIREBALLJUTSU_FIREBALL.get(),
                    FireBallJutsuFireballRenderer::new
            );
        });

    }

}
