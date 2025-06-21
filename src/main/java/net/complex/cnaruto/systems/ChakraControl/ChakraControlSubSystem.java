package net.complex.cnaruto.systems.ChakraControl;

import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.complex.cnaruto.systems.Subsystem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ChakraControlSubSystem extends Subsystem {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;


        Player player = event.player;
        Level level = player.level();

        if (!true || player.isCrouching()) return;

        BlockPos posFeet = player.blockPosition();
        BlockState stateAtFeet = level.getBlockState(posFeet);
        BlockState stateBelowFeet = level.getBlockState(posFeet.below());

        boolean insideWater = stateAtFeet.getBlock() == Blocks.WATER;
        boolean aboveWater = stateBelowFeet.getBlock() == Blocks.WATER;

        if (insideWater && ChakraManagerProvider.get(player).isChakraControlOn()) {
            player.setDeltaMovement(player.getDeltaMovement().x, 0.1, player.getDeltaMovement().z);
            player.fallDistance = 0.0F;
        }
    }

    @SubscribeEvent
    public static void ParticleTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        Level level = player.level();

        if (player.isSpectator()) return; // no effects for spectators


        BlockPos posFeet = player.blockPosition();
        BlockState stateBelowFeet = level.getBlockState(posFeet.below());

        boolean onWater = stateBelowFeet.getBlock() == Blocks.WATER;

        if (ChakraManagerProvider.get(player).isChakraControlOn() && onWater && !player.isInWater()) { // <-- or your own boolean check
            if (level.isClientSide) {
                double x = player.getX();
                double y = player.getY() - 0.1; // a little below feet
                double z = player.getZ();

                level.addParticle(ParticleTypes.CLOUD,
                        x + (player.getRandom().nextDouble() - 0.5) * 0.4,
                        y,
                        z + (player.getRandom().nextDouble() - 0.5) * 0.4,
                        0, 0, 0);



            }
        }
    }

    @Override
    public void Register(IEventBus eventBus) {
        eventBus.register(this);
        System.out.println("[CNARUTO]: ChakraControlSubsystem was loaded!");
    }
}
