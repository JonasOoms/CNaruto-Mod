package net.complex.cnaruto.sounds;

import net.complex.cnaruto.CNaruto;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, CNaruto.MODID);
    public static final RegistryObject<SoundEvent> MENU_MUSIC = registerSoundEvents("menu_music");
    public static final RegistryObject<SoundEvent> JUTSU_HAND_SIGN = registerSoundEvents("jutsu_hand_sign");
    public static final RegistryObject<SoundEvent> JUTSU_ACTIVATION = registerSoundEvents("jutsu_activation");


    private static RegistryObject<SoundEvent> registerSoundEvents(String name)
    {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(CNaruto.MODID, name)));
    }

    public static void register(IEventBus eventBus)
    {
        SOUND_EVENTS.register(eventBus);
    }
}
