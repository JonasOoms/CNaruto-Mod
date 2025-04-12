package net.complex.cnaruto.events.client;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.keybinds.Keybindings;
import net.complex.cnaruto.client.rendering.EyeRenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModHandler {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event)
    {

    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event)
    {
        event.register(Keybindings.NARUTO_KEYBINDINGS.DataScrollKey);
        event.register(Keybindings.NARUTO_KEYBINDINGS.JutsuBarKey);
        System.out.println("[CNaruto] registered keys!");
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.AddLayers event)
    {
        for (String skintype : event.getSkins())
        {
            PlayerRenderer playerRenderer = event.getSkin(skintype);
            playerRenderer.addLayer(new EyeRenderLayer(playerRenderer));

        }
    }

}
