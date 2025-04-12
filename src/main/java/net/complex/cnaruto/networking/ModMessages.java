package net.complex.cnaruto.networking;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerC2SPacket;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.PlayerLevelStatsSyncRequestC2SPacket;
import net.complex.cnaruto.networking.packet.c2s.PlayerLevelStatsSyncWithServerC2SPacket;
import net.complex.cnaruto.networking.packet.s2c.EquippedJutsuSyncWithClientS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register()
    {
        SimpleChannel net = NetworkRegistry.newSimpleChannel(new ResourceLocation(CNaruto.MODID, "messages"), () -> "1.0", s -> true, s -> true);

        INSTANCE = net;

        net.messageBuilder(PlayerLevelStatsSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(PlayerLevelStatsSyncS2CPacket::new)
                .encoder(PlayerLevelStatsSyncS2CPacket::toBytes)
                .consumerMainThread(PlayerLevelStatsSyncS2CPacket::handle)
                .add();

        net.messageBuilder(PlayerLevelStatsSyncRequestC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerLevelStatsSyncRequestC2SPacket::new)
                .encoder(PlayerLevelStatsSyncRequestC2SPacket::toBytes)
                .consumerMainThread(PlayerLevelStatsSyncRequestC2SPacket::handle)
                .add();

        net.messageBuilder(PlayerLevelStatsSyncWithServerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(PlayerLevelStatsSyncWithServerC2SPacket::new)
                .encoder(PlayerLevelStatsSyncWithServerC2SPacket::toBytes)
                .consumerMainThread(PlayerLevelStatsSyncWithServerC2SPacket::handle)
                .add();

        net.messageBuilder(EquippedJutsuSyncWithServerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EquippedJutsuSyncWithServerC2SPacket::new)
                .encoder(EquippedJutsuSyncWithServerC2SPacket::toBytes)
                .consumerMainThread(EquippedJutsuSyncWithServerC2SPacket::handle)
                .add();

        net.messageBuilder(EquippedJutsuSyncWithServerRequestC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(EquippedJutsuSyncWithServerRequestC2S::new)
                .encoder(EquippedJutsuSyncWithServerRequestC2S::toBytes)
                .consumerMainThread(EquippedJutsuSyncWithServerRequestC2S::handle)
                .add();

        net.messageBuilder(EquippedJutsuSyncWithClientS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EquippedJutsuSyncWithClientS2CPacket::new)
                .encoder(EquippedJutsuSyncWithClientS2CPacket::toBytes)
                .consumerMainThread(EquippedJutsuSyncWithClientS2CPacket::handle)
                .add();


    }

    public static <MSG> void SendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player)
    {
        INSTANCE.send(PacketDistributor.PLAYER.with( () -> player), message);
    }
}

