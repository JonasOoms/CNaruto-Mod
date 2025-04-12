package net.complex.cnaruto.networking.packet.s2c;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerLevelStatsSyncS2CPacket {

    private final CompoundTag nbt;

    public PlayerLevelStatsSyncS2CPacket(CompoundTag nbt)
    {
        this.nbt = nbt;
    }

    public PlayerLevelStatsSyncS2CPacket(FriendlyByteBuf buf)
    {
        this(buf.readNbt());
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context context = Supplier.get();
        context.enqueueWork(() -> {
            Player player = Minecraft.getInstance().player;

            if (player == null) return;

            PlayerLevelStats cap = PlayerLevelStatsProvider.get(player);



            cap.loadDataNBT(nbt);



        });

        return true;
    }

}
