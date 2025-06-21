package net.complex.cnaruto.Jutsu.JutsuResourceRequirements;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public interface IJutsuResourceRequirement
{
    public boolean CanUse(Player player);
    public void SubtractResources(Player player);
    public Component GetDisplay(Player player);

}
