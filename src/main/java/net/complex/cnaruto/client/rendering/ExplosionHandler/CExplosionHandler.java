package net.complex.cnaruto.client.rendering.ExplosionHandler;

import net.complex.cnaruto.client.rendering.ExplosionHandler.Explosions.DefaultExplosionEffect;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.ExplosionEffects.SendExplosionEffectToClientS2CPacket;
import net.complex.cnaruto.systems.CNarutoSystemsManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

import java.awt.*;

public class CExplosionHandler {
    public static void SendExplosionEffect(CExplosionEffect effect, Vec3 origin, float size, Color color, int durationTicks)
    {
        MinecraftServer server = CNarutoSystemsManager.getInstance().GetServer();
        if (server == null) return;

        CExplosionEffectInstance instance = new CExplosionEffectInstance(effect, origin, size, color, durationTicks);
        for (ServerPlayer player : server.getPlayerList().getPlayers())
        {
            ModMessages.sendToPlayer(new SendExplosionEffectToClientS2CPacket(instance), player);
        }
    }
}
