package net.complex.cnaruto.events.common;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.events.eventtypes.LevelUpEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = CNaruto.MODID)
public class NinjaXPEvents {

    @SubscribeEvent
    public static void onLivingEntityDeath(LivingDeathEvent event)
    {
        if (event.getSource().getEntity() instanceof Player player)
        {
            PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);
            stats.AddXP((int) event.getEntity().getMaxHealth());
        }
    }

    @SubscribeEvent
    public static void onLevelUp(LevelUpEvent event)
    {
        Player player = event.GetPlayer();

        Component levelUpMessage = Component.literal("You learned more about being a shinobi! You're now level ").withStyle(ChatFormatting.BLUE).append(Component.literal(String.valueOf(event.GetLevel())).withStyle(ChatFormatting.GOLD)).append(Component.literal("!").withStyle(ChatFormatting.BLUE));

        player.displayClientMessage(levelUpMessage, false);

    }

}
