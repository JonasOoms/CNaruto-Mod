package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class FireRelease extends SkillLine {

    public static FireRelease INSTANCE = new FireRelease("Fire Release",
            "An offensive chakra nature, associated with the ninja of Konohagakure",
            new ResourceLocation(CNaruto.MODID, "textures/gui/firerelease.png"), SkillLineCategories.ELEMENT);

    public FireRelease(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, Category);

        // Add fire style jutsu to this!
    }


}
