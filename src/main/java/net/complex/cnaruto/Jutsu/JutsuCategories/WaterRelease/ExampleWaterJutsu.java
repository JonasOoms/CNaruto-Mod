package net.complex.cnaruto.Jutsu.JutsuCategories.WaterRelease;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.LevelJutsuUnlockRequirement;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.SkillLineLevelUnlockRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.SkillLines.SkillLines.WaterRelease;
import net.minecraft.resources.ResourceLocation;

public class ExampleWaterJutsu extends Jutsu {

    public static final ExampleWaterJutsu INSTANCE = new ExampleWaterJutsu("Water Style: Test Jutsu", "A test jutsu! This jutsu isn't really going to be used. So if you're somehow seeing this... that's bad!",
            new ResourceLocation(CNaruto.MODID, "textures/gui/waterrelease.png"), WaterRelease.INSTANCE);

    public ExampleWaterJutsu(String displayName, String description, ResourceLocation Icon, SkillLine SkillLine) {
        super(displayName, description ,Icon, true  ,SkillLine);
        AddJutsuRequirement(new LevelJutsuUnlockRequirement(1));
        AddJutsuRequirement(new SkillLineLevelUnlockRequirement(WaterRelease.INSTANCE, 10));
    }
}
