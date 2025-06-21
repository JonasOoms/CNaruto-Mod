package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChakraManagerSyncWithServerC2SPacket
{

    private CompoundTag nbt;

    public ChakraManagerSyncWithServerC2SPacket(CompoundTag Tag)
    {
        this.nbt = Tag;
    }

    public ChakraManagerSyncWithServerC2SPacket(FriendlyByteBuf buf)
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

            ChakraManager chakraManager = ChakraManagerProvider.get(player);
            chakraManager.deserializeNBT(nbt);
        });
        return true;

    }

}
