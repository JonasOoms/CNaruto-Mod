package net.complex.cnaruto.networking.packet.c2s.JutsuSystem;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.complex.cnaruto.systems.CNarutoSystemsManager;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Supplier;

public class JutsuSystemCastRequestC2S {

    private final int slot;



    public static CompoundTag CreateRequestTag(JutsuInstance instance)
    {
        return instance.serializeNBT();
    }

    public JutsuSystemCastRequestC2S(int slot) {
        this.slot = slot;
    }

    public JutsuSystemCastRequestC2S(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeInt(slot);
    }


    public boolean handle(Supplier<NetworkEvent.Context> Supplier)
    {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() -> {
            ServerPlayer player = ctx.getSender();

            if (player == null) return;

            EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(player);
            JutsuInstance instanceToCast = equippedJutsu.get(this.slot);
            if (instanceToCast == null) return;

            System.out.println("JutsuSystemCastRequestC2S::handle");

            try {
                CNarutoSystemsManager.getInstance().GetJutsuSchedulerSubSystem().PushJutsuInstance(instanceToCast, player);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        });
        return false;
    }


}
