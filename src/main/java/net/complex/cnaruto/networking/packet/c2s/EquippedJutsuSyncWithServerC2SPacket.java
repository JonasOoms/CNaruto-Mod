package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EquippedJutsuSyncWithServerC2SPacket
{
    private CompoundTag nbt;

    public EquippedJutsuSyncWithServerC2SPacket(CompoundTag Tag)
    {
        this.nbt = Tag;
    }

    public EquippedJutsuSyncWithServerC2SPacket(FriendlyByteBuf buf)
    {
        this(buf.readNbt());
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() -> {

            ServerPlayer player = ctx.getSender();

            EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(player);
            equippedJutsu.deserializeNBT(nbt);
        });
        return true;

    }

}
