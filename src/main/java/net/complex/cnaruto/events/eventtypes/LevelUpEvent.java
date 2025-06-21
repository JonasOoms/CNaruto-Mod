package net.complex.cnaruto.events.eventtypes;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class LevelUpEvent extends Event
{
    private final Player player;
    private int level;

    public LevelUpEvent(Player player, int level) {
        this.player = player;
        this.level = level;
    }

    public Player GetPlayer() {return this.player;}
    public int GetLevel() {return this.level;}

}
