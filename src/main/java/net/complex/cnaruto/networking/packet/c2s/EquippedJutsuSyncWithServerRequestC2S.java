package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.EquippedJutsuSyncWithClientS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EquippedJutsuSyncWithServerRequestC2S {
    public EquippedJutsuSyncWithServerRequestC2S()
    {

    }

    public EquippedJutsuSyncWithServerRequestC2S(FriendlyByteBuf buf)
    {


    }

    public void toBytes(FriendlyByteBuf buf)
    {


    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context context = Supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();

            if (player == null) return;

            EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(player);
            CompoundTag Tag = new CompoundTag();
            equippedJutsu.serializeNBT(Tag);

            ModMessages.sendToPlayer(new EquippedJutsuSyncWithClientS2CPacket(Tag), player);




        });

        return true;
    }

}
