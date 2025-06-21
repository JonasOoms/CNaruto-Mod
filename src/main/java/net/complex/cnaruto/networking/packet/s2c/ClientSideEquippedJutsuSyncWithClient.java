package net.complex.cnaruto.networking.packet.s2c;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ClientSideEquippedJutsuSyncWithClient {
    public static void handle(CompoundTag nbt)
    {
        Player player = Minecraft.getInstance().player;

        if (player == null) return;

        EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(player);

        equippedJutsu.deserializeNBT(nbt);
    }
}
