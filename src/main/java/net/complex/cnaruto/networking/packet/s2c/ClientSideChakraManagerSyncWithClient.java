package net.complex.cnaruto.networking.packet.s2c;

import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class ClientSideChakraManagerSyncWithClient {
    public static void handle(CompoundTag nbt)
    {
        Player player = Minecraft.getInstance().player;

        if (player == null) return;

        ChakraManager chakraManager = ChakraManagerProvider.get(player);

        chakraManager.deserializeNBT(nbt);
    }

}
