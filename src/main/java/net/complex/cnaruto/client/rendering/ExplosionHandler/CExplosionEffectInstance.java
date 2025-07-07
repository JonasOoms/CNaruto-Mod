package net.complex.cnaruto.client.rendering.ExplosionHandler;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.RenderLevelStageEvent;

import java.awt.*;

public class CExplosionEffectInstance
{

    private final CExplosionEffect effect;
    private final Vec3 origin;
    private final float size;
    private final Color  color;
    private final int durationTicks;

    CExplosionEffectInstance(CExplosionEffect effect, Vec3 origin, float size, Color color, int durationTicks)
    {
        this.effect = effect;
        this.origin = origin;
        this.size = size;
        this.color = color;
        this.durationTicks = durationTicks;
    }

    public CExplosionEffectInstance(CompoundTag tag)
    {
        int effectId = tag.getInt("explosionEffect");
        this.effect = CExplosions.registrar.Get(effectId);
        this.origin = new Vec3(
                tag.getInt("x"),
                tag.getInt("y"),
                tag.getInt("z")
        );
        this.size = tag.getFloat("size");
        this.color = new Color(tag.getInt("color"));
        this.durationTicks = tag.getInt("durationTicks");

    }

    float timer = 0;

    public float GetTimer()
    {
        return timer;
    }

    public CExplosionEffect getEffect() {
        return effect;
    }

    public Vec3 getOrigin() {
        return origin;
    }

    public float getSize() {
        return size;
    }

    public Color getColor() {
        return color;
    }

    public int getDurationTicks() {
        return durationTicks;
    }

    public CompoundTag Serialize()
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt("explosionEffect", effect.GetID());
        tag.putDouble("x", origin.x);
        tag.putDouble("y", origin.y);
        tag.putDouble("z", origin.z);
        tag.putFloat("size", size);
        tag.putInt("color", color.getRGB());
        tag.putInt("durationTicks", durationTicks);
        return tag;
    }

    /// cascaded down by ClientExplosionHandler
    public EffectStatus render(RenderLevelStageEvent event)
    {
        timer += event.getPartialTick();
        return effect.render(event, this);

    }

}
