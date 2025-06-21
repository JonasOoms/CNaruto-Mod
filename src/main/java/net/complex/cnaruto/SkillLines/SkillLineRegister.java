package net.complex.cnaruto.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLines.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class SkillLineRegister {


    public static ResourceLocation SKILL_LINE_KEY = new ResourceLocation(CNaruto.MODID, "skillline");

    public static Supplier<IForgeRegistry<SkillLine>> registrySupplier;

    public static final DeferredRegister<SkillLine> SKILL_LINE_REGISTER = DeferredRegister.create(SKILL_LINE_KEY, CNaruto.MODID);


    //public static final RegistryObject<SkillLine> EXAMPLESKILL = SKILL_LINE_REGISTER.register("exampleskill", () -> new SkillLine("Example Skill Line", "An example skill line!", new ResourceLocation(CNaruto.MODID, "textures/scrollbackground.png"), SkillLineCategories.KEKKEI));
    // ELEMENTS
    public static final RegistryObject<SkillLine> FIRE_RELEASE = SKILL_LINE_REGISTER.register("firerelease", () -> FireReleaseSkillLine.INSTANCE);
    public static final RegistryObject<SkillLine> WATER_RELEASE = SKILL_LINE_REGISTER.register("waterrelease", () -> WaterReleaseSkillLine.INSTANCE );
    public static final RegistryObject<SkillLine> WIND_RELEASE = SKILL_LINE_REGISTER.register("windrelease", () -> WindReleaseSkillLine.INSTANCE);
    public static final RegistryObject<SkillLine> LIGHTNING_RELEASE = SKILL_LINE_REGISTER.register("lightningrelease", () -> LightningReleaseSkillLine.INSTANCE);
    public static final RegistryObject<SkillLine> EARTH_RELEASE = SKILL_LINE_REGISTER.register("earthrelease", () -> EarthReleaseSkillLine.INSTANCE);

    // KEKKEI GENKAI

    // MISC

    public static final RegistryObject<SkillLine> SENJUTSU = SKILL_LINE_REGISTER.register("senjutsu", () -> SageJutsuSkillLine.INSTANCE);

    public static void Register(IEventBus eventbus)
      {
      SKILL_LINE_REGISTER.register(eventbus);
      }



}
