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
public class PlayerLevelStatsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<PlayerLevelStats> PLAYER_LEVELSTATS = CapabilityManager.get(new CapabilityToken<PlayerLevelStats>() {});

    private PlayerLevelStats PlayerLevelStats = null;
    private final LazyOptional<PlayerLevelStats> optional = LazyOptional.of(this::CreatePlayerLevelStats);

    Player player;

    public PlayerLevelStatsProvider(Player player)
    {
        this.player = player;
    }

    private PlayerLevelStats CreatePlayerLevelStats()
    {
        if (this.PlayerLevelStats == null)
        {
            this.PlayerLevelStats = new PlayerLevelStats();
            if (this.player != null) {
                this.PlayerLevelStats.SetPlayer(this.player);
            }
        }
        return this.PlayerLevelStats;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction)
    {
       if (capability == PLAYER_LEVELSTATS)
       {
           return optional.cast();
       }

       return LazyOptional.empty();
    }

    public static PlayerLevelStats get(LivingEntity entity)
    {
        net.complex.cnaruto.Data.PlayerLevelStats props = entity.getCapability(PLAYER_LEVELSTATS).orElse(new PlayerLevelStats());
        return props;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        CreatePlayerLevelStats().saveDataNBT(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag)
    {
        CreatePlayerLevelStats().loadDataNBT(compoundTag);
    }

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player player)
        {
            if (!event.getObject().getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).isPresent())
            {
                event.addCapability(new ResourceLocation(CNaruto.MODID, "playerlevelstats"), new PlayerLevelStatsProvider(player));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if(event.isWasDeath()) {
            event.getOriginal().reviveCaps();
            event.getOriginal().getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent(oldStore -> {
                event.getEntity().getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent(newStore ->
                {
                    newStore.copyFrom(oldStore);
                });
            });
            event.getOriginal().invalidateCaps();
        }
    }


}
