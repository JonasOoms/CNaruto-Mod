package net.complex.cnaruto.networking.packet.s2c;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ClientSidePlayerLevelStatsSyncS2CPacket {
    public static void handle(CompoundTag nbt)
    {

        Player player = Minecraft.getInstance().player;

        if (player == null) return;

        PlayerLevelStats cap = PlayerLevelStatsProvider.get(player);


        cap.loadDataNBT(nbt);

    }
}
