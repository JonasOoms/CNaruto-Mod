package net.complex.cnaruto.Data;

import net.complex.cnaruto.CNaruto;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Mod.EventBusSubscriber(modid = CNaruto.MODID)
public class ChakraManagerProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<ChakraManager> CHAKRA_MANAGER = CapabilityManager.get(new CapabilityToken<ChakraManager>() {});
    private ChakraManager chakraManager = new ChakraManager();
    private final LazyOptional<ChakraManager> optional = LazyOptional.of(this::createChakraManager);

    private ChakraManager createChakraManager() {
        if (this.chakraManager == null)
        {
            this.chakraManager = new ChakraManager();
        }
        return this.chakraManager;
    }



    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == CHAKRA_MANAGER)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    public static ChakraManager get(LivingEntity entity)
    {
        ChakraManager props = entity.getCapability(CHAKRA_MANAGER).orElse(new ChakraManager());
        return props;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        createChakraManager().serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        createChakraManager().deserializeNBT(compoundTag);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player)
        {
            if (!event.getObject().getCapability(CHAKRA_MANAGER).isPresent())
            {
                event.addCapability(new ResourceLocation(CNaruto.MODID, "chakra"), new ChakraManagerProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(CHAKRA_MANAGER).ifPresent(oldStore -> {
                event.getEntity().getCapability(CHAKRA_MANAGER).ifPresent(newStore ->
                {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }


}
