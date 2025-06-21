package net.complex.cnaruto.command;

import net.complex.cnaruto.CNaruto;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CNarutoCommands {

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event)
    {
        SkillLineCommand.register(event.getDispatcher());
        PointCommand.register(event.getDispatcher());
    }

}
