package net.complex.cnaruto.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.IJutsuRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.client.gui.widgets.JutsuScrollList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;

import java.awt.*;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SkillLineScreen extends Screen {

    private SkillLine skillLine;
    private List<Jutsu> jutsuList;
    private JutsuScrollList jutsuScrollList;
    private JutsuScrollList.JutsuEntry selected = null;
    private int listWidth;

    int mouseX;
    int mouseY;

    private final static ResourceLocation JutsuInformationBackground = new ResourceLocation(CNaruto.MODID, "textures/gui/scrollbackground.png");
    private final static ResourceLocation WoodBorder = new ResourceLocation("minecraft", "textures/block/oak_log.png");

    public SkillLineScreen(SkillLine SkillLine) {
        super(Component.empty());
        this.skillLine = SkillLine;
        this.jutsuList = SkillLine.GetJutsuAsList();


    }

    public void init() {
//        for (Iterator<Jutsu> var1 = this.jutsuList.iterator(); var1.hasNext(); this.listWidth = Math.max(this.listWidth, Minecraft.getInstance().font.width(((Jutsu) var1.next()).GetDisplayName()) + 16 + 8))
//        {
//            if (var1.hasNext())
//            {
//                Jutsu Jutsu = (Jutsu) var1.next();
//                this.listWidth = Math.max(this.listWidth, Minecraft.getInstance().font.width(Jutsu.GetDisplayName()) + 16 + 8);
//            }
//        }

        jutsuList.forEach((jutsu) -> {
            this.listWidth = Math.max(this.listWidth, Minecraft.getInstance().font.width(jutsu.GetDisplayName()) + 16 + 8);
        });

        this.listWidth = Math.max(Math.min(this.listWidth, this.width / 3), 100);
        int y = this.height - 20 - 6;
        this.jutsuScrollList = new JutsuScrollList(this, this.listWidth, 64 , (this.height)/2 + 64 );

        //this.jutsuScrollList.setLeftPos(6);
        this.addRenderableWidget(this.jutsuScrollList);
        this.updateInfo();
    }

    public void tick() {


        this.jutsuScrollList.setSelected(this.selected);

        this.updateInfo();

    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {

        this.mouseX = pMouseX;
        this.mouseY = pMouseY;

        this.jutsuScrollList.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        drawSkillLineMastery(pGuiGraphics, this.jutsuScrollList.getLeft(), this.jutsuScrollList.getTop()-30, this.jutsuScrollList.getWidth(), 30, WoodBorder , JutsuScrollList.JutsuScrollListBackground);

        if (selected != null)
        {

            Font font = Minecraft.getInstance().font;
            String jutsuDisplayName = selected.GetJutsu().GetDisplayName();
            int displayNameWidth = font.width(jutsuDisplayName);
            RenderSystem.enableBlend();
            pGuiGraphics.blit(JutsuInformationBackground,this.listWidth + 30, 32,0,0, 250,200, 250, 200 );
            RenderSystem.disableBlend();
            pGuiGraphics.drawString(font, jutsuDisplayName, (this.listWidth + 30 + 250)/2 - displayNameWidth/2 + 100, 64, Color.GRAY.hashCode());
            drawWrappedText(pGuiGraphics, selected.GetJutsu().GetDescription(), (this.listWidth + 30 + 40), 82, 200, 100);
            pGuiGraphics.drawString(font, "Requirements to equip:", (this.listWidth + 30 + 40), 110, Color.DARK_GRAY.hashCode());
            drawRequirements(selected.GetJutsu(), pGuiGraphics, (this.listWidth + 30 + 40) , 120);


        }

    }

    public void drawSkillLineMastery(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation borderTexture, ResourceLocation fillTexture)
    {

        int thickness = 2;

        CUtils.tileTexture(guiGraphics, borderTexture, x, y, width, height, 16);
        // Inner box (fill)


        int innerX = x + thickness;
        int innerY = y + thickness;
        int innerWidth = width - thickness * 2;
        int innerHeight = height - thickness * 2;

        guiGraphics.blit(fillTexture, innerX, innerY, 0, 0, innerWidth, innerHeight, innerWidth, innerHeight);

        // draw the mastery progression bar

        int barX = (int) (innerX + innerWidth * 0.1f);
        int barY = (int) (innerY + innerHeight - innerHeight * 0.3f);
        int barWidth = innerWidth - (2*barX);
        int barHeight = (int) (innerHeight*0.2f);

        guiGraphics.fill(barX, barY, barX + barWidth, barY + barHeight, new Color(38, 79, 142).hashCode());

        PlayerLevelStats stats = PlayerLevelStatsProvider.get(Minecraft.getInstance().player);
        int level = stats.GetPlayerLevelWithSkillLine(CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, this.skillLine));
        int maxLevel = this.skillLine.MaxLevel;

        float percentage = ((float) level /maxLevel);

        guiGraphics.fill(barX, barY, barX + (int) (barWidth*percentage), barY + barHeight, new Color(89, 193, 255).hashCode());

        Component toolTipText = Component.literal("Your mastery over ").append(
                Component.literal(this.skillLine.GetName()).withStyle(ChatFormatting.BLUE).append(
                        Component.literal(" is at level ").withStyle(ChatFormatting.WHITE).append(
                                Component.literal(String.valueOf(level)).withStyle(ChatFormatting.GOLD).append(
                                        Component.literal("/" + maxLevel).withStyle(ChatFormatting.WHITE)
                                )
                        )
                ));

        boolean isHovering = mouseX >= barX && mouseX <= barX + barWidth
                && mouseY >= barY && mouseY <= barY + barHeight;

        if (isHovering)
        {
            guiGraphics.renderTooltip(font, toolTipText, mouseX, mouseY);
        }

    }

    private void drawWrappedText(GuiGraphics guiGraphics, String text, int x, int y, int width, int height)
    {
        System.out.println(text);
        Font font = Minecraft.getInstance().font;
        Component component = Component.literal(text);
        List<FormattedCharSequence> lines = font.split(component, width);
        int lineHeight = font.lineHeight;
        int maxLines = height/lineHeight;

        for (int i = 0; i < Math.min(lines.size(), maxLines); i++)
        {
            guiGraphics.drawString(font, lines.get(i), x, y + i * lineHeight, Color.WHITE.hashCode());
        }
    }

    private void drawRequirements(Jutsu jutsu, GuiGraphics guiGraphics, int x, int y)
    {
        int lineHeight = font.lineHeight;

        for (int i = 0; i < jutsu.jutsuRequirements.size(); i++)
        {
            Component reqText = jutsu.jutsuRequirements.get(i).GetDisplay(getMinecraft().player);
            guiGraphics.drawString(font, reqText , x, y + i * lineHeight, Color.WHITE.hashCode());
            int textY = y + i * lineHeight;
            int lineWidth = font.width(reqText.getString());
            boolean isHovering = mouseX >= x && mouseX <= x + lineWidth
                    && mouseY >= textY && mouseY <= textY + lineHeight;
            if (isHovering && reqText.getStyle().getHoverEvent() != null)
            {
                var tooltip = reqText.getStyle().getHoverEvent().getValue(HoverEvent.Action.SHOW_TEXT);
                guiGraphics.renderTooltip(font, (Component) tooltip, mouseX, mouseY);
            }

        }
    }

    @Override
    public void renderBackground(GuiGraphics pGuiGraphics) {
        //super.renderBackground(pGuiGraphics);
    }

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        Minecraft.getInstance().setScreen(new PlayerStatsMain());
    }


    public <T extends ObjectSelectionList.Entry<T>> void buildJutsuList(Consumer<T> jutsuListViewConsumer, Function<Jutsu, T> newEntry)
    {
        this.jutsuList.forEach((jutsu) ->
        {
            if (jutsu.GetHiddenIfNotUnlocked())
            {
                if (jutsu.UnlockRequirements(getMinecraft().player))
                {
                    jutsuListViewConsumer.accept((T) newEntry.apply(jutsu));
                }
            } else
            {
                jutsuListViewConsumer.accept((T) newEntry.apply(jutsu));
            }
        });
    }



    public void setSelected(JutsuScrollList.JutsuEntry entry)
    {
        this.selected = entry == this.selected ? null : entry;
        updateInfo();
    }

    public void updateInfo()
    {

    }

}
