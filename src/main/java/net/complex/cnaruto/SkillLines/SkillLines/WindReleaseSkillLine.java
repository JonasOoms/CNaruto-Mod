package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class WindReleaseSkillLine extends SkillLine {

    public static WindReleaseSkillLine INSTANCE = new WindReleaseSkillLine("Wind Release",
            "A balanced chakra nature, used by the ninja of Sunagakure",
            new ResourceLocation(CNaruto.MODID, "textures/gui/windrelease.png"),
            SkillLineCategories.ELEMENT);

    public WindReleaseSkillLine(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, 250, Category);

        // add wind style jutsu!

    }
}
