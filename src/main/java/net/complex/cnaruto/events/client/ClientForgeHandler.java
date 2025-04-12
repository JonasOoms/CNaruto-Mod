package net.complex.cnaruto.events.client;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.client.gui.PlayerStatsMain;
import net.complex.cnaruto.client.gui.VillageChoiceGui;
import net.complex.cnaruto.client.keybinds.Keybindings;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeHandler {
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event)
    {
        if (Keybindings.NARUTO_KEYBINDINGS.DataScrollKey.consumeClick() && Minecraft.getInstance().player != null)
        {
            PlayerStatsMain.open();
        }
    }
}
