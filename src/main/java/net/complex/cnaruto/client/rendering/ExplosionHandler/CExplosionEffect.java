package net.complex.cnaruto.client.rendering.ExplosionHandler;

import net.minecraftforge.client.event.RenderLevelStageEvent;

public abstract class CExplosionEffect {

    private int ID;
    private boolean isRegistered;

    public void register(int id)
    {
        ID = id;
        isRegistered = true;
    }

    public boolean isRegistered()
    {
        return isRegistered;
    }

    public final int GetID()
    {
        return ID;
    }


   /// event is cascaded down by an explosion instance
    public abstract EffectStatus render(RenderLevelStageEvent event, CExplosionEffectInstance instance);


}
