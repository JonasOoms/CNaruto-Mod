package net.complex.cnaruto.entities;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.entities.jutsu_entities.FireBallJutsuFireballProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CNaruto.MODID);

    public static final RegistryObject<EntityType<FireBallJutsuFireballProjectile>> FIREBALLJUTSU_FIREBALL =
            ENTITIES.register("fireballjutsu_fireball", () ->
                    EntityType.Builder.<FireBallJutsuFireballProjectile>of(FireBallJutsuFireballProjectile::new, MobCategory.MISC)
                            .fireImmune()
                            .sized(0.3125f,0.3125f)
                            .clientTrackingRange(4)
                            .updateInterval(10)
                            .build("fireballjutsu_fireball")
            );


    public static void register(IEventBus bus) {
        ENTITIES.register(bus);
    }

}
