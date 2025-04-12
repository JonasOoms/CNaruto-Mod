package net.complex.cnaruto.Jutsu;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.api.CUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

public class JutsuInstance
{
    public final Jutsu jutsu;
    private final String jutsuId;

    private long lastUsedTime;


    public boolean HasCooldown()
    {
        return (System.currentTimeMillis() - lastUsedTime) > jutsu.cooldown;
    }

    public JutsuInstance(RegistryObject<Jutsu> jutsu) {
        this.jutsu = jutsu.get();
        this.jutsuId = jutsu.getId().getPath();

    }

    public JutsuInstance(CompoundTag tag)
    {
        this.jutsuId = tag.getString("jutsuId");
        this.lastUsedTime = tag.getLong("lastUsedTime");
        this.jutsu = CUtils.FindAndReturnFromRegistry(JutsuRegister.JUTSU_REGISTER, new ResourceLocation(CNaruto.MODID, this.jutsuId)).get();
    }

    public void Cast()
    {
        if (!HasCooldown())
        {

        }
    }

    public CompoundTag serializeNBT()
    {
        CompoundTag tag = new CompoundTag();
        tag.putString("jutsuId", jutsuId);
        tag.putLong("lastUsedTime", lastUsedTime);
        return tag;
    }


}
