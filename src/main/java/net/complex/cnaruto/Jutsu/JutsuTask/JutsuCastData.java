package net.complex.cnaruto.Jutsu.JutsuTask;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class JutsuCastData
{
    private final ServerPlayer player;
    private final Vec3 pos;
    // potential overcharge?


    public JutsuCastData(ServerPlayer player, Vec3 pos) {
        this.player = player;
        this.pos = pos;
    }

    public ServerPlayer GetPlayer() {return player;}

    public Vec3 GetPos() {return pos;}

}
