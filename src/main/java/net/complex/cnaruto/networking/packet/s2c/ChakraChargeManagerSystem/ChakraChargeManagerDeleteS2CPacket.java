package net.complex.cnaruto.networking.packet.s2c.ChakraChargeManagerSystem;

import net.complex.cnaruto.systems.ChakraChargeManagerSubSystem.ClientChakraChargeManagerSubSystem;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChakraChargeManagerDeleteS2CPacket
{
    UUID uuid;

    public ChakraChargeManagerDeleteS2CPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public ChakraChargeManagerDeleteS2CPacket(FriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() -> {
            ClientChakraChargeManagerSubSystem.Remove(uuid);
        });
        return true;

    }
}
