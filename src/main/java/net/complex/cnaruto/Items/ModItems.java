package net.complex.cnaruto.Items;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Items.item.RamenItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{

    public static DeferredRegister<Item> items = DeferredRegister.create(ForgeRegistries.ITEMS, CNaruto.MODID);

    public static final RegistryObject<Item> RAMEN = items.register("ramen",
            RamenItem::new);

    public static void register(IEventBus bus)
    {
        items.register(bus);
    }

}
