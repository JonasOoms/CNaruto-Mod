package net.complex.cnaruto.events.common;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.minecraft.client.telemetry.events.WorldLoadEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.Collection;

@Mod.EventBusSubscriber(modid = CNaruto.MODID)
public class ForgeEvents {

    @SubscribeEvent
    public static void ChatEvent(ServerChatEvent event)
    {

    }

}
