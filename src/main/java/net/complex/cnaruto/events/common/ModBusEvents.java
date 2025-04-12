package net.complex.cnaruto.events.common;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuRegister;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEvents
{
    @SubscribeEvent
    public static void NewRegistryEvent(NewRegistryEvent event)
    {
        RegistryBuilder<SkillLine> registryBuilder = new RegistryBuilder<>();
        registryBuilder.setName(SkillLineRegister.SKILL_LINE_KEY);
        SkillLineRegister.registrySupplier = event.create(registryBuilder);

        RegistryBuilder<Jutsu> registryBuilderJutsu = new RegistryBuilder<>();
        registryBuilderJutsu.setName(JutsuRegister.JUTSU_REGISTER_KEY);
        JutsuRegister.registrySupplier = event.create(registryBuilderJutsu);


    }
}
