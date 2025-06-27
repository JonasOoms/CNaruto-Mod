package net.complex.cnaruto.Data.attributes;

import net.complex.cnaruto.CNaruto;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, CNaruto.MODID);

    public static final RegistryObject<Attribute> HANDSIGN_SPEED = ATTRIBUTES.register("handsign_speed", () -> (Attribute) new RangedAttribute("attribute.mod.handsign_speed", 1.2D, 0.3D, 2.D).setSyncable(true));

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }

    @SubscribeEvent
    public static void onAddAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, HANDSIGN_SPEED.get());
    }

}
