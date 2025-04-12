package net.complex.cnaruto.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineCategories;
import net.complex.cnaruto.client.gui.widgets.OnPress.OpenSkillLineMenuForSkillLine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.registries.RegistryObject;
import org.spongepowered.asm.mixin.injection.Desc;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class SkillLineInformationFrame extends Button {

//    private final String SkillLineName;
//    private final String SkillLineDescription;
//    private final SkillLineCategories Category;

    private static final int MaxCharPerLine = 25;

    private final ResourceLocation Icon;
    private final List<Component> ToolTipLines;



    public SkillLineInformationFrame(int pX, int pY, int pWidth, int pHeight, SkillLine SkillLine) {
        super(pX, pY, pWidth, pHeight, Component.empty(), new OpenSkillLineMenuForSkillLine(SkillLine), new CreateNarration() {
            @Override
            public MutableComponent createNarrationMessage(Supplier<MutableComponent> supplier) {
                return Component.empty();
            }
        });
//        this.SkillLineName = SkillLine.GetName();
//        this.SkillLineDescription = SkillLine.GetDescription();
//        this.Category = SkillLine.GetCategory();
        this.Icon = SkillLine.GetIcon();

        ToolTipLines = new ArrayList<>();
        ToolTipLines.add(Component.literal(SkillLine.GetName()).withStyle(NameStyle(SkillLine.GetCategory())));
        //ToolTipLines.add(Component.literal(SkillLine.GetDescription()).withStyle(Style.EMPTY));
        ToolTipLines.addAll(FormatDescription(SkillLine.GetDescription()));




    }

    private List<Component> FormatDescription(String Description)
    {
        List<String> DescriptionStringList = new ArrayList<>();

        int index = 0;

        while (index < Description.length())
        {
            int nextIndex = Math.min(index+MaxCharPerLine, Description.length());

            // for when current segment ends in middle of word
            if (nextIndex < Description.length() && Character.isAlphabetic(Description.charAt(nextIndex))) {
                //backtrack to previous space
                while (nextIndex > index && Character.isAlphabetic(Description.charAt(nextIndex))) {
                    nextIndex--;
                }

                if (nextIndex == index)
                {
                    nextIndex = index + MaxCharPerLine;
                }

            }
           DescriptionStringList.add(Description.substring(index, nextIndex).trim());
            index = nextIndex;
        }


        List<Component> ComponentList = new ArrayList<>();
        DescriptionStringList.forEach( (string) -> {
            ComponentList.add(Component.literal(string).withStyle(Style.EMPTY));
        });

        return ComponentList;
    }

    private Style NameStyle(SkillLineCategories Category)
    {


        switch (Category)
        {
            case ELEMENT:
                return net.minecraft.network.chat.Style.EMPTY.withColor(3381759).withBold(true);
            case KEKKEI:
                return  net.minecraft.network.chat.Style.EMPTY.withColor(16750848).withBold(true);
            case MISC:
                return net.minecraft.network.chat.Style.EMPTY.withColor(39219).withBold(true);
        }

      return Style.EMPTY;
    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int i, int i1, float v) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        pGuiGraphics.blit(Icon, this.getX(), this.getY(), 0,   0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        if (this.isHovered()) pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, ToolTipLines, i, i1);

    }

}
