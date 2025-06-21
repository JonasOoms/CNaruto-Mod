package net.complex.cnaruto.Data;

import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.ChakraManagerSyncWithClientS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.loading.FMLEnvironment;

public class ChakraManager {


    private int Chakra = 0;
    private int SenjutsuChakra = 0;
    private boolean isChakraControlOn = false;

    public int GetChakra() {return Chakra;}
    public void SetChakra(int chakra, Player player) {
        this.Chakra = chakra;
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER)
        {
            CompoundTag syncTag = new CompoundTag();
            this.serializeNBT(syncTag);
            ModMessages.sendToPlayer(new ChakraManagerSyncWithClientS2CPacket(syncTag), (ServerPlayer) player);
        }
    }

    public int GetMaxChakra(Player player)
    {
        // here we need a chakra function

        PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);
        return (200 + stats.GetSpirit()*10);
    }


    public int GetSenjutsuChakra() {return SenjutsuChakra;}
    public void SetSenjutsuChakra(int chakra, Player player) {
        SenjutsuChakra = chakra;
        if (FMLEnvironment.dist == Dist.DEDICATED_SERVER)
        {
            CompoundTag syncTag = new CompoundTag();
            this.serializeNBT(syncTag);
            ModMessages.sendToPlayer(new ChakraManagerSyncWithClientS2CPacket(syncTag), (ServerPlayer) player);
        }
    }

    public boolean isChakraControlOn() {return isChakraControlOn;}

    public void serializeNBT(CompoundTag tag) {

        tag.putInt("Chakra", Chakra);
        tag.putInt("SenjutsuChakra", SenjutsuChakra);
        tag.putBoolean("isChakraControlOn", isChakraControlOn);

    }

    public void deserializeNBT(CompoundTag compoundTag) {
        this.Chakra = compoundTag.getInt("Chakra");
        this.SenjutsuChakra = compoundTag.getInt("SenjutsuChakra");
        this.isChakraControlOn = compoundTag.getBoolean("isChakraControlOn");
    }

    public void copyFrom(ChakraManager from)
    {
        this.Chakra = from.Chakra;
        this.SenjutsuChakra = from.SenjutsuChakra;
        this.isChakraControlOn = from.isChakraControlOn;
    }
}
