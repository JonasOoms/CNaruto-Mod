package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.ChakraManagerSyncWithClientS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.EquippedJutsuSyncWithClientS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChakraManagerSyncWithServerRequestC2S {
    public ChakraManagerSyncWithServerRequestC2S()
    {

    }

    public ChakraManagerSyncWithServerRequestC2S(FriendlyByteBuf buf)
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

            ChakraManager chakraManager = ChakraManagerProvider.get(player);
            CompoundTag Tag = new CompoundTag();
            chakraManager.serializeNBT(Tag);

            ModMessages.sendToPlayer(new ChakraManagerSyncWithClientS2CPacket(Tag), player);




        });

        return true;
    }

}
