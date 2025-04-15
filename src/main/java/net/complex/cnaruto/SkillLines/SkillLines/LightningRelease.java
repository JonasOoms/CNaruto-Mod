package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class LightningRelease extends SkillLine {

    public static LightningRelease INSTANCE = new LightningRelease("Lightning Release",
            "A chakra nature that is used most commonly by ninja of Kumogakure. Used offensively",
            new ResourceLocation(CNaruto.MODID, "textures/gui/lightningrelease.png"),
            SkillLineCategories.ELEMENT);

    public LightningRelease(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, 250, Category);

        // add lightning release jutsu here
    }
}
