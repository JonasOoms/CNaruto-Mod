package net.complex.cnaruto.client.gui.widgets.OnPress;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.PlayerLevelStatsSyncWithServerC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.CompoundTag;
import org.lwjgl.system.linux.Stat;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class StatIncreaseThenSyncToServer implements Button.OnPress {


    private final Function<Integer, Boolean> PointSubtract;
    private final Supplier<Integer> GetSupplier;
    private final Consumer<Integer> SetConsumer;

    public StatIncreaseThenSyncToServer(Supplier<Integer> StatGetMethod, Consumer<Integer> StatSetMethod, Function<Integer, Boolean> pointSubtract ) {
        this.PointSubtract = pointSubtract;
        this.GetSupplier = StatGetMethod;
        this.SetConsumer = StatSetMethod;
    }

    @Override
    public void onPress(Button button) {
        if (PointSubtract.apply(1))
        {
            SetConsumer.accept(GetSupplier.get()+1);
            Minecraft.getInstance().player.getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent((levelstats) ->
            {
                CompoundTag Tag = new CompoundTag();
                PlayerLevelStatsProvider.get(Minecraft.getInstance().player).saveDataNBT(Tag);
                ModMessages.SendToServer(new PlayerLevelStatsSyncWithServerC2SPacket(Tag));
            });
        }

    }
}
