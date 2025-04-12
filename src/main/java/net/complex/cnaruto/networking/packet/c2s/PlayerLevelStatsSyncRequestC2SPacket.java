package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PlayerLevelStatsSyncRequestC2SPacket {



    public PlayerLevelStatsSyncRequestC2SPacket()
    {

    }

    public PlayerLevelStatsSyncRequestC2SPacket(FriendlyByteBuf buf)
    {


    }

    public void toBytes(FriendlyByteBuf buf)
    {


    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context context = Supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer  player = context.getSender();

            if (player == null) return;

            PlayerLevelStats PlayerLevelStats = PlayerLevelStatsProvider.get(player);
            CompoundTag Tag = new CompoundTag();
            PlayerLevelStats.saveDataNBT(Tag);

            ModMessages.sendToPlayer(new PlayerLevelStatsSyncS2CPacket(Tag), player);




        });

        return true;
    }

}
