package net.complex.cnaruto.networking.packet.c2s;

import ca.weblite.objc.Client;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

/// This packet checks if the cooldown is correct on server side for the jutsu, and then casts the jutsu and syncs the cooldowns
public class ClientJutsuInstanceUseC2SPacket {

    private CompoundTag nbt;

    public ClientJutsuInstanceUseC2SPacket(CompoundTag Tag)
    {
        this.nbt = Tag;
    }

    public ClientJutsuInstanceUseC2SPacket(FriendlyByteBuf buf)
    {
        this(buf.readNbt());
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeNbt(nbt);
    }
}
