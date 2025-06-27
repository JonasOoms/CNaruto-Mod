package net.complex.cnaruto.systems.ChakraChargeManagerSubSystem;

import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.ChakraChargeManagerSystem.ChakraChargeManagerDeleteS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.ChakraChargeManagerSystem.ChakraChargeManagerPostS2CPacket;
import net.complex.cnaruto.systems.CNarutoSystemsManager;
import net.complex.cnaruto.systems.Subsystem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.UUID;

public class ChakraChargeManagerSubSystem extends Subsystem {

    private MinecraftServer server;
    private HashMap<UUID, Float> chargeLookup = new HashMap<>();

    private static float CHARGETIME = 1.f * 20;

    public ChakraChargeManagerSubSystem(MinecraftServer server) {
        this.server = server;
    }

    public boolean IsCharging(UUID uuid) {
        return chargeLookup.containsKey(uuid);
    }

    public boolean IsCharging(Player player) {
        return IsCharging(player.getUUID());
    }

    /// TODO: Check for player validity
    public void Put(UUID uuid) {
        // freeze player in place
        Player player = server.getPlayerList().getPlayer(uuid);
        if (player == null) return;
        chargeLookup.put(uuid, 0.0f);

        // sync with client
        SendPacketToAllPlayers(new ChakraChargeManagerPostS2CPacket(uuid));

    }

    /// TODO: Check for player validity
    public void Remove(UUID uuid) {
        Player player = server.getPlayerList().getPlayer(uuid);
        if (player == null) return;
        chargeLookup.remove(uuid);

        // sync with client
        SendPacketToAllPlayers(new ChakraChargeManagerDeleteS2CPacket(uuid));

    }

    private <T> void SendPacketToAllPlayers(T packet)
    {
        for (ServerPlayer player : server.getPlayerList().getPlayers())
        {
            System.out.println("Packet sent to " + player.getDisplayName());
            ModMessages.sendToPlayer(packet, player);
        }
    }

    @SubscribeEvent
    public void onPlayerLeft(PlayerEvent.PlayerLoggedInEvent event) {
        if (IsCharging(event.getEntity())) {
            Remove(event.getEntity().getUUID());
        }
    }

    @SubscribeEvent
    public void OnTick(TickEvent.ServerTickEvent event) {
        chargeLookup.forEach((uuid, timer) ->
        {
            ServerPlayer player = (ServerPlayer) server.getPlayerList().getPlayer(uuid);
            if (player == null) return;

            //player.getCombatTracker().
            float timerDelta = timer + CNarutoSystemsManager.getInstance().GetServerTimerSubSystem().getPartialTick();
            chargeLookup.put(uuid, timerDelta);

            if (timerDelta >= CHARGETIME) {
                ChakraManager chakraManager = ChakraManagerProvider.get(player);
                int maxChakra = chakraManager.GetMaxChakra(player);

                int deltaChakra = (int) Math.ceil(maxChakra*0.0666f);

                chakraManager.SetChakra(chakraManager.GetChakra() + deltaChakra, player);
                chargeLookup.put(uuid, 0.f);
            }
        });
    }

    @Override
    public void Register(IEventBus eventBus) {
        eventBus.register(this);
    }
}
