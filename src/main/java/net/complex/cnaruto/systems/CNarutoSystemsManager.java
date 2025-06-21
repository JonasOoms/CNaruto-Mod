package net.complex.cnaruto.systems;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.systems.ChakraControl.ChakraControlSubSystem;
import net.complex.cnaruto.systems.JutsuSystem.JutsuSchedulerSubSystem;
import net.complex.cnaruto.systems.StatSystem.PlayerStatSubsystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CNaruto.MODID)
public final class CNarutoSystemsManager {

    private static CNarutoSystemsManager INSTANCE;
    private ServerLevel level;

    // Systems
    private JutsuSchedulerSubSystem jutsuSchedulerSubSystem = null;
    private ChakraControlSubSystem chakraControlSubSystem = null;
    private PlayerStatSubsystem playerStatSubsystem = null;

    public static CNarutoSystemsManager getInstance()
    {
        if (INSTANCE == null) throw new IllegalStateException("[CNARUTO]: CNarutoSystemsManager was accessed with no world loaded.");

        return INSTANCE;
    }

    public JutsuSchedulerSubSystem GetJutsuSchedulerSubSystem()
    {
        return jutsuSchedulerSubSystem;
    }

    public ChakraControlSubSystem GetChakraControlSubSystem()
    {
        return chakraControlSubSystem;
    }

    public PlayerStatSubsystem GetPlayerStatSubSystem()
    {
        return playerStatSubsystem;
    }

    public CNarutoSystemsManager(ServerLevel level)
    {
        this.level = level;

        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        jutsuSchedulerSubSystem = new JutsuSchedulerSubSystem();
        chakraControlSubSystem = new ChakraControlSubSystem();
        playerStatSubsystem = new PlayerStatSubsystem();

        jutsuSchedulerSubSystem.Register(eventBus);
        chakraControlSubSystem.Register(eventBus);
        playerStatSubsystem.Register(eventBus);


        System.out.println("[CNARUTO]: CNarutoSystemsManager was loaded!");
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event)
    {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        if (INSTANCE == null)
        {
            INSTANCE = new CNarutoSystemsManager(((ServerLevel) event.getLevel()).getLevel());
        }
    }

    @SubscribeEvent
    public static void onWorldUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        if (INSTANCE != null) {
            INSTANCE = null;

            System.out.println("[CNARUTO]: CNarutoSystemsManager unloaded!");
        }
    }


}
