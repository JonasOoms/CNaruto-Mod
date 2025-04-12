package net.complex.cnaruto.client.gui.widgets.OnPress;

import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.client.gui.SkillLineScreen;
import net.complex.cnaruto.client.gui.widgets.JutsuScrollList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;

public class OpenSkillLineMenuForSkillLine implements Button.OnPress {

    SkillLine SkillLine;

    public OpenSkillLineMenuForSkillLine(SkillLine SkillLineObject)
    {
        this.SkillLine = SkillLineObject;
    }

    @Override
    public void onPress(Button button) {
        Minecraft.getInstance().setScreen(new SkillLineScreen(SkillLine));
    }
}
