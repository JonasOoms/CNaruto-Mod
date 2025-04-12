package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

// Used only in certain client choices to sync up choices with server. This packet should not be sent lightly!
public class PlayerLevelStatsSyncWithServerC2SPacket {

    private final CompoundTag nbt;

    public PlayerLevelStatsSyncWithServerC2SPacket(CompoundTag Tag)
    {
        nbt = Tag;
    }

    public PlayerLevelStatsSyncWithServerC2SPacket(FriendlyByteBuf buf)
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

            ServerPlayer player = context.getSender();

            PlayerLevelStats ServerLevelStats = PlayerLevelStatsProvider.get(player);
            ServerLevelStats.loadDataNBT(nbt);





        });

        return true;
    }

}
