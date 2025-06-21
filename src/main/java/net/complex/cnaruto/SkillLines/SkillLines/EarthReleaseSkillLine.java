package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class EarthReleaseSkillLine extends SkillLine {

    public static EarthReleaseSkillLine INSTANCE = new EarthReleaseSkillLine("Earth Release",
            "A common chakra nature among ninja of Iwagakure. It is a defensive chakra nature",
            new ResourceLocation(CNaruto.MODID, "textures/gui/earthrelease.png"), SkillLineCategories.ELEMENT);

    public EarthReleaseSkillLine(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, 250, Category);
    }
}
