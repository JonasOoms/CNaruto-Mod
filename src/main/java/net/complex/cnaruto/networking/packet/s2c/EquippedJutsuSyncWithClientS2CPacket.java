package net.complex.cnaruto.networking.packet.s2c;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EquippedJutsuSyncWithClientS2CPacket
{
    private final CompoundTag nbt;

    public EquippedJutsuSyncWithClientS2CPacket(CompoundTag nbt)
    {
        this.nbt = nbt;
    }

    public EquippedJutsuSyncWithClientS2CPacket(FriendlyByteBuf buf)
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
            Player player = Minecraft.getInstance().player;

            if (player == null) return;

            EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(player);

            equippedJutsu.deserializeNBT(nbt);

        });
        return false;
    }

}
