package net.complex.cnaruto.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLines.*;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SkillLineRegister {


    public static ResourceLocation SKILL_LINE_KEY = new ResourceLocation(CNaruto.MODID, "skillline");

    public static Supplier<IForgeRegistry<SkillLine>> registrySupplier;

    public static final DeferredRegister<SkillLine> SKILL_LINE_REGISTER = DeferredRegister.create(SKILL_LINE_KEY, CNaruto.MODID);


    //public static final RegistryObject<SkillLine> EXAMPLESKILL = SKILL_LINE_REGISTER.register("exampleskill", () -> new SkillLine("Example Skill Line", "An example skill line!", new ResourceLocation(CNaruto.MODID, "textures/scrollbackground.png"), SkillLineCategories.KEKKEI));

    public static final RegistryObject<SkillLine> FIRE_RELEASE = SKILL_LINE_REGISTER.register("firerelease", () -> FireRelease.INSTANCE);
    public static final RegistryObject<SkillLine> WATER_RELEASE = SKILL_LINE_REGISTER.register("waterrelease", () -> WaterRelease.INSTANCE );
    public static final RegistryObject<SkillLine> WIND_RELEASE = SKILL_LINE_REGISTER.register("windrelease", () -> WindRelease.INSTANCE);
    public static final RegistryObject<SkillLine> LIGHTNING_RELEASE = SKILL_LINE_REGISTER.register("lightningrelease", () -> LightningRelease.INSTANCE);
    public static final RegistryObject<SkillLine> EARTH_RELEASE = SKILL_LINE_REGISTER.register("earthrelease", () -> EarthRelease.INSTANCE);


    public static void Register(IEventBus eventbus)
      {
      SKILL_LINE_REGISTER.register(eventbus);
      }



}
