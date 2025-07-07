package net.complex.cnaruto.systems;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.systems.ChakraChargeManagerSubSystem.ChakraChargeManagerSubSystem;
import net.complex.cnaruto.systems.ChakraControl.ChakraControlSubSystem;
import net.complex.cnaruto.systems.JutsuSystem.JutsuSchedulerSubSystem;
import net.complex.cnaruto.systems.ServerTimer.ServerTimerSubsystem;
import net.complex.cnaruto.systems.StatSystem.PlayerStatSubsystem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CNaruto.MODID)
public final class CNarutoSystemsManager {

    public static CNarutoSystemsManager INSTANCE;
    private MinecraftServer server;

    // Systems
    private JutsuSchedulerSubSystem jutsuSchedulerSubSystem = null;
    private ChakraControlSubSystem chakraControlSubSystem = null;
    private PlayerStatSubsystem playerStatSubsystem = null;
    private ChakraChargeManagerSubSystem chakraChargeManagerSubSystem = null;
    private ServerTimerSubsystem serverTimerSubsystem = null;

    public static CNarutoSystemsManager getInstance()
    {
        if (INSTANCE == null) throw new IllegalStateException("[CNARUTO]: CNarutoSystemsManager was accessed with no world loaded.");

        return INSTANCE;
    }

    public MinecraftServer GetServer()
    {
        return server;
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

    public ChakraChargeManagerSubSystem GetChakraChargeManagerSubSystem() {return chakraChargeManagerSubSystem;}

    public ServerTimerSubsystem GetServerTimerSubSystem() {return serverTimerSubsystem;}


    public CNarutoSystemsManager(MinecraftServer server)
    {
        this.server = server;

        IEventBus eventBus = MinecraftForge.EVENT_BUS;

        jutsuSchedulerSubSystem = new JutsuSchedulerSubSystem();
        chakraControlSubSystem = new ChakraControlSubSystem();
        playerStatSubsystem = new PlayerStatSubsystem();
        chakraChargeManagerSubSystem = new ChakraChargeManagerSubSystem(server);
        serverTimerSubsystem = new ServerTimerSubsystem();

        jutsuSchedulerSubSystem.Register(eventBus);
        chakraControlSubSystem.Register(eventBus);
        playerStatSubsystem.Register(eventBus);
        chakraChargeManagerSubSystem.Register(eventBus);
        serverTimerSubsystem.Register(eventBus);

        System.out.println("[CNARUTO]: CNarutoSystemsManager was loaded!");
    }

    @SubscribeEvent
    public static void onWorldLoad(LevelEvent.Load event)
    {
        if (!(event.getLevel() instanceof ServerLevel)) return;

        if (INSTANCE == null)
        {
            INSTANCE = new CNarutoSystemsManager(((ServerLevel) event.getLevel()).getServer());
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
