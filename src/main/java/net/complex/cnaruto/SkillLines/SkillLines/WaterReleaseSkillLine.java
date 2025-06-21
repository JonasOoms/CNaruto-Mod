package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class WaterReleaseSkillLine extends SkillLine {

    public static WaterReleaseSkillLine INSTANCE = new WaterReleaseSkillLine("Water Release",
            "A chakra nature that is common among ninja of Kirigakure, used both offensively and defensively",
            new ResourceLocation(CNaruto.MODID, "textures/gui/waterrelease.png"), SkillLineCategories.ELEMENT);

    public WaterReleaseSkillLine(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, 250, Category);

        // Add water release jutsu here!

    }
}
