package net.complex.cnaruto.events.client.MainMenu;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT)
public class MenuReplacer {

    private static boolean hasGameMusicBeenReplaced = false;

//    @SubscribeEvent
//    public static void OnGuiOpen(ScreenEvent.Init.Post event)
//    {
//        if (event.getScreen() instanceof  TitleScreen titleScreen)
//        {
//            Minecraft mc = Minecraft.getInstance();
//            SoundManager sm = mc.getSoundManager();
//            //sm.stop(null, SoundSource.MUSIC);
//            System.out.println("Stopped music");
//        }
//    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
    }



}
