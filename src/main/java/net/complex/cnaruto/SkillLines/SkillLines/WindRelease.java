package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class WindRelease extends SkillLine {

    public static WindRelease INSTANCE = new WindRelease("Wind Release",
            "A balanced chakra nature, used by the ninja of Sunagakure",
            new ResourceLocation(CNaruto.MODID, "textures/gui/windrelease.png"),
            SkillLineCategories.ELEMENT);

    public WindRelease(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, Category);

        // add wind style jutsu!

    }
}
