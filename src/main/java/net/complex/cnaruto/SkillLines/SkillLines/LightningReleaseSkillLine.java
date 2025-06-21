package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class LightningReleaseSkillLine extends SkillLine {

    public static LightningReleaseSkillLine INSTANCE = new LightningReleaseSkillLine("Lightning Release",
            "A chakra nature that is used most commonly by ninja of Kumogakure. Used offensively",
            new ResourceLocation(CNaruto.MODID, "textures/gui/lightningrelease.png"),
            SkillLineCategories.ELEMENT);

    public LightningReleaseSkillLine(String name, String description, ResourceLocation icon, SkillLineCategories Category) {
        super(name, description, icon, 250, Category);

        // add lightning release jutsu here
    }
}
