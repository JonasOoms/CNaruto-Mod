package net.complex.cnaruto.networking;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.networking.packet.c2s.*;
import net.complex.cnaruto.networking.packet.c2s.ChakraChargeManagerSystem.ChakraChargeManagerDeleteC2SPacket;
import net.complex.cnaruto.networking.packet.c2s.ChakraChargeManagerSystem.ChakraChargeManagerPostC2SPacket;
import net.complex.cnaruto.networking.packet.c2s.JutsuSystem.HandSignSoundRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.JutsuSystem.JutsuSystemCastRequestC2S;
import net.complex.cnaruto.networking.packet.s2c.ChakraChargeManagerSystem.ChakraChargeManagerDeleteS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.ChakraChargeManagerSystem.ChakraChargeManagerPostS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.ChakraManagerSyncWithClientS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.EquippedJutsuSyncWithClientS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.ExplosionEffects.SendExplosionEffectToClientS2CPacket;
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

        net.messageBuilder(ChakraManagerSyncWithServerC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChakraManagerSyncWithServerC2SPacket::new)
                .encoder(ChakraManagerSyncWithServerC2SPacket::toBytes)
                .consumerMainThread(ChakraManagerSyncWithServerC2SPacket::handle)
                .add();

        net.messageBuilder(ChakraManagerSyncWithClientS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ChakraManagerSyncWithClientS2CPacket::new)
                .encoder(ChakraManagerSyncWithClientS2CPacket::toBytes)
                .consumerMainThread(ChakraManagerSyncWithClientS2CPacket::handle)
                .add();

        net.messageBuilder(ChakraManagerSyncWithServerRequestC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChakraManagerSyncWithServerRequestC2S::new)
                .encoder(ChakraManagerSyncWithServerRequestC2S::toBytes)
                .consumerMainThread(ChakraManagerSyncWithServerRequestC2S::handle)
                .add();

        net.messageBuilder(JutsuSystemCastRequestC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(JutsuSystemCastRequestC2S::new)
                .encoder(JutsuSystemCastRequestC2S::toBytes)
                .consumerMainThread(JutsuSystemCastRequestC2S::handle)
                .add();

        net.messageBuilder(HandSignSoundRequestC2S.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(HandSignSoundRequestC2S::new)
                .encoder(HandSignSoundRequestC2S::toBytes)
                .consumerMainThread(HandSignSoundRequestC2S::handle)
                .add();

        net.messageBuilder(IncreaseSkillLineLevelC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(IncreaseSkillLineLevelC2SPacket::new)
                .encoder(IncreaseSkillLineLevelC2SPacket::toBytes)
                .consumerMainThread(IncreaseSkillLineLevelC2SPacket::handle)
                .add();

        net.messageBuilder(ChakraChargeManagerPostC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChakraChargeManagerPostC2SPacket::new)
                .encoder(ChakraChargeManagerPostC2SPacket::toBytes)
                .consumerMainThread(ChakraChargeManagerPostC2SPacket::handle)
                .add();

        net.messageBuilder(ChakraChargeManagerDeleteC2SPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(ChakraChargeManagerDeleteC2SPacket::new)
                .encoder(ChakraChargeManagerDeleteC2SPacket::toBytes)
                .consumerMainThread(ChakraChargeManagerDeleteC2SPacket::handle)
                .add();

        net.messageBuilder(ChakraChargeManagerPostS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ChakraChargeManagerPostS2CPacket::new)
                .encoder(ChakraChargeManagerPostS2CPacket::toBytes)
                .consumerMainThread(ChakraChargeManagerPostS2CPacket::handle)
                .add();

        net.messageBuilder(ChakraChargeManagerDeleteS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ChakraChargeManagerDeleteS2CPacket::new)
                .encoder(ChakraChargeManagerDeleteS2CPacket::toBytes)
                .consumerMainThread(ChakraChargeManagerDeleteS2CPacket::handle)
                .add();

        net.messageBuilder(SendExplosionEffectToClientS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SendExplosionEffectToClientS2CPacket::new)
                .encoder(SendExplosionEffectToClientS2CPacket::toBytes)
                .consumerMainThread(SendExplosionEffectToClientS2CPacket::handle)
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

