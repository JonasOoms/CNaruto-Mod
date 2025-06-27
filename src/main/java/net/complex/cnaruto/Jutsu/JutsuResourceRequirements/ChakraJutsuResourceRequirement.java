package net.complex.cnaruto.Jutsu.JutsuResourceRequirements;

import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ChakraJutsuResourceRequirement implements IJutsuResourceRequirement {

    private int cost;

    public ChakraJutsuResourceRequirement(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean CanUse(Player player) {
        ChakraManager chakraManager = ChakraManagerProvider.get(player);
        return (chakraManager.GetChakra() - cost >= 0);
    }

    @Override
    public void SubtractResources(Player player) {
        ChakraManager chakraManager = ChakraManagerProvider.get(player);
        chakraManager.SetChakra(chakraManager.GetChakra() - cost, player);
    }

    @Override
    public Component GetDisplay(Player player) {
        return Component.literal("This jutsu costs ").append(
                Component.literal(String.valueOf(cost)).withStyle(ChatFormatting.AQUA).append(
                        Component.literal(" chakra to use").withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD)
                )
        );
    }
}
