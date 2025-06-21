package net.complex.cnaruto.SkillLines.SkillLines;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.minecraft.resources.ResourceLocation;

public class SageJutsuSkillLine extends SkillLine {

    public static SageJutsuSkillLine INSTANCE = new SageJutsuSkillLine("Senjutsu", "Senjutsu is the practice of drawing natural energy inside of the body, using the power of the natural world to strengthen oneself.", new ResourceLocation(CNaruto.MODID, "textures/gui/senjutsu.png"), 300, SkillLineCategories.MISC);

    public SageJutsuSkillLine(String name, String description, ResourceLocation icon, int maxLevel, SkillLineCategories Category) {
        super(name, description, icon, maxLevel, Category);
    }
}
