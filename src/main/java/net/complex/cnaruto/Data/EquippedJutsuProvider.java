package net.complex.cnaruto.Data;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.EquippedJutsuSyncWithClientS2CPacket;
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
public class EquippedJutsuProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<EquippedJutsu> EQUIPPED_JUTSU = CapabilityManager.get(new CapabilityToken<EquippedJutsu>() {});
    private EquippedJutsu EquippedJutsu = null;
    private final LazyOptional<EquippedJutsu> optional = LazyOptional.of(this::CreateEquippedJutsu);


    private EquippedJutsu CreateEquippedJutsu()
    {
        if (this.EquippedJutsu == null)
        {
            this.EquippedJutsu = new EquippedJutsu();
        }
        return this.EquippedJutsu;
    }

    public static EquippedJutsu get(LivingEntity entity)
    {
        net.complex.cnaruto.Data.EquippedJutsu props = entity.getCapability(EQUIPPED_JUTSU).orElse(new EquippedJutsu());
       return props;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == EQUIPPED_JUTSU)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CreateEquippedJutsu().serializeNBT(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag)
    {
        CreateEquippedJutsu().deserializeNBT(compoundTag);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            if (!event.getObject().getCapability(EQUIPPED_JUTSU).isPresent())
            {
                event.addCapability(new ResourceLocation(CNaruto.MODID, "equippedjutsu"), new EquippedJutsuProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(EQUIPPED_JUTSU).ifPresent(oldStore -> {
                event.getEntity().getCapability(EQUIPPED_JUTSU).ifPresent(newStore ->
                {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }

}
