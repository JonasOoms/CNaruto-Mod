package net.complex.cnaruto.Jutsu.JutsuUnlockRequirements;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class LevelJutsuUnlockRequirement implements IJutsuRequirement {

    private final int level;

    public LevelJutsuUnlockRequirement(int level)
    {
        this.level = level;
    }

    @Override
    public boolean CanUnlock(Player player) {
        PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);
        int level = stats.GetLevel();
        return (level >= this.level);
    }

    @Override
    public Component GetDisplay(Player player) {
        return Component.literal("Required Level: " + this.level)
                .withStyle(CanUnlock(player) ? ChatFormatting.GREEN : ChatFormatting.RED)
                .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("For this jutsu, it is required for you to be level " + this.level))));
    }
}
