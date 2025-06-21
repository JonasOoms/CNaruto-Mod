package net.complex.cnaruto.networking.packet.c2s.JutsuSystem;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.complex.cnaruto.sounds.ModSounds;
import net.complex.cnaruto.systems.CNarutoSystemsManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

/// Only needed because a certain someone wanted other players charge ups to be telegraphed...
public class HandSignSoundRequestC2S {


    public HandSignSoundRequestC2S() {

    }

    public HandSignSoundRequestC2S(FriendlyByteBuf buf) {

    }

    public void toBytes(FriendlyByteBuf buf)
    {

    }


    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();

            if (player == null) return;

            player.level().playSound(player, player.blockPosition(), ModSounds.JUTSU_HAND_SIGN.get(), SoundSource.PLAYERS, 1.0f, 1.0f);

        });
        return false;
    }

}
