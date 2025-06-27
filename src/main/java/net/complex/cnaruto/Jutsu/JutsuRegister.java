package net.complex.cnaruto.Jutsu;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.JutsuCategories.FireRelease.FireBallJutsu;
import net.complex.cnaruto.Jutsu.JutsuCategories.WaterRelease.*;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class JutsuRegister {

    public static final ResourceLocation JUTSU_REGISTER_KEY = new ResourceLocation(CNaruto.MODID, "jutsu");

    public static Supplier<IForgeRegistry<Jutsu>> registrySupplier;

    public static final DeferredRegister<Jutsu> JUTSU_REGISTER = DeferredRegister.create(JUTSU_REGISTER_KEY, CNaruto.MODID);

    public static final RegistryObject<Jutsu> EXAMPLE_WATER_JUTSU = ExampleWaterJutsu.INSTANCE.Register( "example_water_jutsu" ,JUTSU_REGISTER);

    public static final RegistryObject<Jutsu> FIREBALL_JUTSU = FireBallJutsu.INSTANCE.Register( "fireball_jutsu" ,JUTSU_REGISTER);

    public static void Register(IEventBus modbus)
    {

        JUTSU_REGISTER.register(modbus);
    }

}
