package net.complex.cnaruto.networking.packet.s2c.ExplosionEffects;

import net.complex.cnaruto.client.rendering.ExplosionHandler.CExplosionEffectInstance;
import net.complex.cnaruto.client.rendering.ExplosionHandler.ClientExplosionHandler;
import net.complex.cnaruto.networking.packet.s2c.ClientSideChakraManagerSyncWithClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SendExplosionEffectToClientS2CPacket {

    private CExplosionEffectInstance instance;

    public  SendExplosionEffectToClientS2CPacket(CExplosionEffectInstance instance)
    {
        this.instance = instance;
    }

    public SendExplosionEffectToClientS2CPacket(FriendlyByteBuf buf)
    {
        this(new CExplosionEffectInstance(buf.readNbt()));
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeNbt(instance.Serialize());
    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() -> ClientExplosionHandler.PushExplosion(instance));
        return false;
    }



}
