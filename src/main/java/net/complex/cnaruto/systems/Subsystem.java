package net.complex.cnaruto.systems;

import net.minecraftforge.eventbus.api.IEventBus;

/// Subsystems work by cascading events down from the main systems manager when appropriate.
/// This way, only when CNarutos main systems are properly functioning will the subsystems be called.
public abstract class Subsystem {

    public abstract void Register(IEventBus eventBus);
}
