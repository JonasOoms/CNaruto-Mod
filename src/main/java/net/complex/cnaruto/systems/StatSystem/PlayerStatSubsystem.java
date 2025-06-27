package net.complex.cnaruto.systems.StatSystem;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.Data.attributes.ModAttributes;
import net.complex.cnaruto.systems.Subsystem;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.UUID;

public class PlayerStatSubsystem extends Subsystem {

    private static UUID TAIJUTSU_ADD_UUID = UUID.randomUUID();
    private static UUID AGILITY_ADD_UUID = UUID.randomUUID();
    private static UUID NINJUTSU_HANDSIGN_SPEED_MODIFIER = UUID.randomUUID();



    @SubscribeEvent
    public void tickEvent(TickEvent.PlayerTickEvent event)
    {
        Player player = event.player;
        PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);

        ApplyTaijutsu(player, stats);
        ApplyAgility(player, stats);
        ApplyNinjutsu(player, stats);

    }

    public void ApplyTaijutsu(Player player, PlayerLevelStats playerLevelStats)
    {
        int taijutsuStat = playerLevelStats.GetTaijutsu();
        AttributeInstance playerStrength = player.getAttribute(Attributes.ATTACK_DAMAGE);


        AttributeModifier existingModifier = playerStrength.getModifier(TAIJUTSU_ADD_UUID);
        if (player.getMainHandItem().isEmpty())
        {
            double damageModifier = playerLevelStats.GetAddedDamageTaijutsu();
            if (existingModifier == null)
            {
                playerStrength.addTransientModifier(new AttributeModifier(TAIJUTSU_ADD_UUID, "taijutsuAdd", playerLevelStats.GetAddedDamageTaijutsu()  , AttributeModifier.Operation.ADDITION));
            } else if (existingModifier.getAmount() != damageModifier)
            {
                playerStrength.removeModifier(TAIJUTSU_ADD_UUID);
                playerStrength.addTransientModifier(new AttributeModifier(TAIJUTSU_ADD_UUID, "taijutsuAdd", playerLevelStats.GetAddedDamageTaijutsu()  , AttributeModifier.Operation.ADDITION));
            }

        } else
        {
            if (existingModifier != null)
            {
                playerStrength.removeModifier(TAIJUTSU_ADD_UUID);
            }
        }
    }

    public void ApplyAgility(Player player, PlayerLevelStats playerLevelStats) {
        int agilityStat = playerLevelStats.GetAgility();
        AttributeInstance playerSpeedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier existingModifier = playerSpeedAttribute.getModifier(AGILITY_ADD_UUID);
        double agilityModifier = playerLevelStats.getAgilitySpeedModifier();
        if (existingModifier == null) {
            playerSpeedAttribute.addTransientModifier(new AttributeModifier(AGILITY_ADD_UUID, "agilityAdd", agilityModifier , AttributeModifier.Operation.MULTIPLY_BASE));
        } else if (existingModifier.getAmount() != agilityModifier)
        {
            playerSpeedAttribute.removeModifier(AGILITY_ADD_UUID);
            playerSpeedAttribute.addTransientModifier(new AttributeModifier(AGILITY_ADD_UUID, "agilityAdd", agilityModifier , AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    public void ApplyNinjutsu(Player player, PlayerLevelStats playerLevelStats)
    {
        int ninjutsuStat = playerLevelStats.GetNinjutsu();

        // Handsign speed
        AttributeInstance playerHandsignSpeedAttribute = player.getAttribute(ModAttributes.HANDSIGN_SPEED.get());
        AttributeModifier existingHandsignSpeedAttribute = playerHandsignSpeedAttribute.getModifier(NINJUTSU_HANDSIGN_SPEED_MODIFIER);

        // Function to have logarithmic decrease of time. X = 1 => Y= 1.2, X = 400 => Y = 0.35, X = 999, Y = 0.3
        double playerHandsignSpeedTarget =  1.79089 - 0.325591*Math.log(0.181859*ninjutsuStat+5.89527);
        double playerHandsignSpeedModifier =  (playerHandsignSpeedAttribute.getBaseValue() - playerHandsignSpeedTarget);
        if (existingHandsignSpeedAttribute == null)
        {
            playerHandsignSpeedAttribute.addTransientModifier(new AttributeModifier(NINJUTSU_HANDSIGN_SPEED_MODIFIER, "ninjutsuHandsignSpeedModifier", -playerHandsignSpeedModifier, AttributeModifier.Operation.ADDITION));
        } else if (existingHandsignSpeedAttribute.getAmount() != playerHandsignSpeedModifier)
        {
            playerHandsignSpeedAttribute.removeModifier(NINJUTSU_HANDSIGN_SPEED_MODIFIER);
            playerHandsignSpeedAttribute.addTransientModifier(new AttributeModifier(NINJUTSU_HANDSIGN_SPEED_MODIFIER, "ninjutsuHandsignSpeedModifier", -playerHandsignSpeedModifier, AttributeModifier.Operation.ADDITION));
        }



    }


    @Override
    public void Register(IEventBus eventBus) {
        eventBus.register(this);
        System.out.println("[CNARUTO]: PlayerStatSubsystem was loaded!");
    }
}


