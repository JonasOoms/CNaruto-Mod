package net.complex.cnaruto.networking.packet.c2s.ChakraChargeManagerSystem;

import net.complex.cnaruto.systems.CNarutoSystemsManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

public class ChakraChargeManagerDeleteC2SPacket {

    UUID uuid;

    public ChakraChargeManagerDeleteC2SPacket(UUID uuid) {
        this.uuid = uuid;
    }

    public ChakraChargeManagerDeleteC2SPacket(FriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() -> {
            CNarutoSystemsManager.getInstance().GetChakraChargeManagerSubSystem().Remove(uuid);
        });
        return true;

    }




}
