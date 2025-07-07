package net.complex.cnaruto.Jutsu.JutsuCategories.FireRelease;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuProperties;
import net.complex.cnaruto.Jutsu.JutsuResourceRequirements.ChakraJutsuResourceRequirement;
import net.complex.cnaruto.Jutsu.JutsuTask.JutsuCastData;
import net.complex.cnaruto.Jutsu.JutsuTask.TaskResult;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.SkillLineLevelUnlockRequirement;
import net.complex.cnaruto.SkillLines.SkillLines.FireReleaseSkillLine;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns.*;
import net.complex.cnaruto.entities.jutsu_entities.FireBallJutsuFireballProjectile;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.function.Supplier;

public class FireBallJutsu extends Jutsu {

    public class FireBallJutsuTask extends JutsuTask {



        public FireBallJutsuTask(JutsuCastData data) {
            super(data);
        }

        public void OnSchedule() {};
        public TaskResult Tick() {

            JutsuCastData data = getData();
            Player player = data.GetPlayer();
            Level world = player.level();

            Vec3 start = player.getEyePosition();
            Vec3 dir = player.getLookAngle();

            int count    = 25;
            double step  = 0.2;
            double speed = 0.1;

            if (!world.isClientSide && world instanceof ServerLevel server) {
                for (int i = 0; i < count; i++) {
                    Vec3 pos = start.add(dir.scale(step * i));
                    server.sendParticles(
                            ParticleTypes.FLAME,
                            pos.x, pos.y, pos.z,
                            1,    // 1 particle per step
                            0, 0, 0, // no random offset
                            speed
                    );
                    server.sendParticles(
                            ParticleTypes.SMOKE,
                            pos.x, pos.y, pos.z,
                            1,    // 1 particle per step
                            0, 0, 0, // no random offset
                            speed
                    );
                }

                FireBallJutsuFireballProjectile fireball = new FireBallJutsuFireballProjectile(
                        world, player, dir.x,dir.y,dir.z
                );

                fireball.setPos(start.x + dir.x, start.y + dir.y, start.z + dir.z);
                world.addFreshEntity(fireball);

                fireball.triggerAnim("pop_controller", "pop");


            }


            return TaskResult.COMPLETED;
        };
        public void UnSchedule() {};



    }

    public static FireBallJutsu INSTANCE = new FireBallJutsu(() ->
            JutsuProperties.builder(FireReleaseSkillLine.INSTANCE)
                    .displayName("Fire Release: Fire Ball Jutsu")
                    .description("The user kneads chakra in their body and turns it into fire, which they then expel from their mouth; the more chakra they use, the more flames that are produced.")
                    .icon(new ResourceLocation(CNaruto.MODID, "textures/gui/firestyle_fireballjutsu.png"))
                    .addRequirement(new SkillLineLevelUnlockRequirement(FireReleaseSkillLine.INSTANCE, 10))
                    .addResourceRequirement(new ChakraJutsuResourceRequirement(150))
                    .addHandsign(new SnakeSealHandsign())
                    .addHandsign(new RamSealHandsign())
                    .addHandsign(new MonkeySealHandsign())
                    .addHandsign(new BoarSealHandsign())
                    .addHandsign(new HorseSealHandsign())
                    .addHandsign(new TigerSealHandsign())
                    .build()
            );

    public FireBallJutsu(Supplier<JutsuProperties> props) {
        super(props);
    }

    @Override
    protected void Use(Player player) {

    }

    @Override
    public JutsuTask createTask(JutsuCastData data) {
        return new FireBallJutsuTask(data);
    }
}
