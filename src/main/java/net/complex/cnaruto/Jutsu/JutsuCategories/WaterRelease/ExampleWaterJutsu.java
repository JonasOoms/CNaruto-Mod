package net.complex.cnaruto.Jutsu.JutsuCategories.WaterRelease;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuProperties;
import net.complex.cnaruto.Jutsu.JutsuTask.JutsuCastData;
import net.complex.cnaruto.Jutsu.JutsuTask.TaskResult;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.LevelJutsuUnlockRequirement;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.SkillLineLevelUnlockRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLines.WaterReleaseSkillLine;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class ExampleWaterJutsu extends Jutsu {

    public class ExampleWaterJutsuTask extends JutsuTask
    {

        public ExampleWaterJutsuTask(JutsuCastData data)
        {
            super(data);
        }

        @Override
        public void OnSchedule() {

        }

        @Override
        public TaskResult Tick() {
            this.getData().GetPlayer().sendSystemMessage(Component.literal("Example water jutsu was ticked!"));
            return TaskResult.COMPLETED;
        }

        @Override
        public void UnSchedule() {

        }
    }

    public static final ExampleWaterJutsu INSTANCE = new ExampleWaterJutsu( () -> JutsuProperties.builder(WaterReleaseSkillLine.INSTANCE)
            .displayName("Water Style: Test Jutsu")
            .description("A test jutsu! This jutsu isn't really going to be used. So if you're somehow seeing this... that's bad!")
            .icon( new ResourceLocation(CNaruto.MODID, "textures/gui/waterrelease.png"))
            .build());

    public ExampleWaterJutsu(Supplier<JutsuProperties> properties) {
        super(properties);
    }

    @Override
    protected void Use(Player player) {
        player.sendSystemMessage(Component.literal("You used water jutsu kablamo").withStyle(ChatFormatting.BLUE));
    }

    @Override
    public JutsuTask createTask(JutsuCastData data) {
        return new ExampleWaterJutsuTask(data);
    }
}
