package net.complex.cnaruto.Jutsu.JutsuUnlockRequirements;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public interface IJutsuRequirement {

    public boolean CanUnlock(Player player);
    public Component GetDisplay(Player player);

}
