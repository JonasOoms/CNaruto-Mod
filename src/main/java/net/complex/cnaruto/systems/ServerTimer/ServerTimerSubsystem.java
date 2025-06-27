package net.complex.cnaruto.systems.ServerTimer;

import net.complex.cnaruto.systems.Subsystem;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


public class ServerTimerSubsystem extends Subsystem {

    private static final long TICK_NANOS = 50_000_000L;

    private long lastTickTime = System.nanoTime();
    private float partialTick = 0.f;

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END)
        {
            long now = System.nanoTime();
            long delta = now - lastTickTime;

            partialTick = Math.min(1f, delta/TICK_NANOS);
            lastTickTime = now;
        }
    }

    public float getPartialTick() {
        return partialTick;
    }

    @Override
    public void Register(IEventBus eventBus) {
        eventBus.register(this);
    }
}
