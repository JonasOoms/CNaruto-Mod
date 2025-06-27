package net.complex.cnaruto.client.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuResourceRequirements.IJutsuResourceRequirement;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.IJutsuRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CRenderUtils;
import net.complex.cnaruto.api.CResources;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.client.gui.widgets.JutsuBarWidget;
import net.complex.cnaruto.client.gui.widgets.JutsuScrollList;
import net.complex.cnaruto.client.gui.widgets.OnPress.SkillLineLevelIncreaseOnPress;
import net.complex.cnaruto.client.gui.widgets.TexturableButton;
import net.complex.cnaruto.client.gui.widgets.draggableWidget.DraggableWidget;
import net.complex.cnaruto.client.gui.widgets.draggableWidget.DropZoneWidget;
import net.complex.cnaruto.client.rendering.CNarutoToolTips;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SkillLineScreen extends Screen {

    private SkillLine skillLine;
    private SkillLineData skillLineData;
    private PlayerLevelStats playerLevelStats;

    private List<Jutsu> jutsuList;
    private JutsuScrollList jutsuScrollList;
    private JutsuScrollList.JutsuEntry selected = null;

    private TexturableButton skillLineIncrease;

    private DraggableWidget draggableWidget;
    private JutsuBarWidget jutsuBarWidget;
    private int listWidth;

    int mouseX;
    int mouseY;

    private final static ResourceLocation JutsuInformationBackground = CResources.background;
    private final static ResourceLocation WoodBorder = new ResourceLocation("minecraft", "textures/block/oak_log.png");

    public SkillLineScreen(SkillLine SkillLine) {
        super(Component.empty());
        this.skillLine = SkillLine;
        this.jutsuList = SkillLine.GetJutsuAsList();
        playerLevelStats = PlayerLevelStatsProvider.get(Minecraft.getInstance().player);
        skillLineData = playerLevelStats.GetPlayerSkillLineDataObject(this.skillLine);

    }

    public void init() {

        jutsuList.forEach((jutsu) -> {
            this.listWidth = Math.max(this.listWidth, Minecraft.getInstance().font.width(jutsu.GetDisplayName()) + 16 + 8);
        });

        this.listWidth = Math.max(Math.min(this.listWidth, this.width / 3), 100);
        int y = this.height - 20 - 6;
        this.jutsuScrollList = new JutsuScrollList(this, this.listWidth, 64 , (this.height)/2 + 64 );

        //this.jutsuScrollList.setLeftPos(6);
        this.addRenderableWidget(this.jutsuScrollList);

        jutsuBarWidget = new JutsuBarWidget(this.width/2 - 160/2, this.height - 30, 160, 30);
        this.addRenderableWidget(this.jutsuBarWidget);
        draggableWidget = new DraggableWidget(100, 100, 16, 16, List.of(jutsuBarWidget.dropZones));
        draggableWidget.active = false;
        this.addRenderableWidget(this.draggableWidget);

        skillLineIncrease = new TexturableButton(this.jutsuScrollList.getLeft() + this.jutsuScrollList.getWidth() - 18, 30 + 12, 12,12, new SkillLineLevelIncreaseOnPress(this.skillLineData, 1, Minecraft.getInstance().player), CResources.plus, CResources.plusSelected);
        this.addRenderableWidget(skillLineIncrease);

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

        this.renderBackground(pGuiGraphics);

        this.jutsuScrollList.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.jutsuBarWidget.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        drawSkillLineMastery(pGuiGraphics, this.jutsuScrollList.getLeft(), this.jutsuScrollList.getTop()-30, this.jutsuScrollList.getWidth(), 30, WoodBorder , JutsuScrollList.JutsuScrollListBackground);

        this.skillLineIncrease.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

        if (selected != null)
        {

            Font font = Minecraft.getInstance().font;
            String jutsuDisplayName = selected.GetJutsu().GetDisplayName();
            int displayNameWidth = font.width(jutsuDisplayName);
            RenderSystem.enableBlend();

            // jutsu information scroll background

            int jutsuInformationScrollX, jutsuInformationScrollY;
            int jutsuInformationScrollWidth, jutsuInformationScrollHeight;

            jutsuInformationScrollWidth = (int) (250);
            jutsuInformationScrollHeight = (int) (200);


            if (minecraft.options.guiScale().get() == 4)
            {
                jutsuInformationScrollX = (this.listWidth + 30);
                jutsuInformationScrollY =  32;
            } else
            {

                int screenWidth = minecraft.getWindow().getGuiScaledWidth();
                int screenHeight = minecraft.getWindow().getGuiScaledHeight();

                int listSpacing = 30;
                int listWidth = this.listWidth;

                int jutsuNaturalWidth = 250;
                int jutsuNaturalHeight = 200;

                int maxAllowedJutsuWidth = screenWidth - listWidth - listSpacing;
                int maxAllowedJutsuHeight = screenHeight - 40; // Optional padding


                float widthScale = (float) maxAllowedJutsuWidth / jutsuNaturalWidth;
                float heightScale = (float) maxAllowedJutsuHeight / jutsuNaturalHeight;


                float scale = Math.min(1.0f, Math.min(widthScale, heightScale));


                jutsuInformationScrollWidth = (int) (jutsuNaturalWidth * scale);
                jutsuInformationScrollHeight = (int) (jutsuNaturalHeight * scale);


                    // Center the whole layout
                    int totalWidth = listWidth + listSpacing + jutsuInformationScrollWidth;
                    jutsuInformationScrollX = (screenWidth - totalWidth) / 2 + listWidth + listSpacing;
                    jutsuInformationScrollY = (screenHeight - jutsuInformationScrollHeight) / 2;

            }

            pGuiGraphics.blit(JutsuInformationBackground,jutsuInformationScrollX, jutsuInformationScrollY,0,0, jutsuInformationScrollWidth,jutsuInformationScrollHeight, jutsuInformationScrollWidth, jutsuInformationScrollHeight );



            RenderSystem.disableBlend();
            pGuiGraphics.drawString(font, jutsuDisplayName, jutsuInformationScrollX + 40, jutsuInformationScrollY + 32, Color.GRAY.hashCode());
            int descriptionHeight = drawWrappedText(pGuiGraphics, selected.GetJutsu().GetDescription(), jutsuInformationScrollX + 40, jutsuInformationScrollY + 50, 200, 100);
            pGuiGraphics.drawString(font, "Requirements to equip:", (jutsuInformationScrollX) + 40, jutsuInformationScrollY + 60 + descriptionHeight, Color.DARK_GRAY.hashCode());
            drawRequirements(selected.GetJutsu(), pGuiGraphics, jutsuInformationScrollX + 40 , jutsuInformationScrollY + 70 + descriptionHeight);




            if (this.selected.GetJutsu().UnlockRequirements(minecraft.player))
            {
                this.draggableWidget.active = true;
                this.draggableWidget.SetOrigin((jutsuInformationScrollX + 130), jutsuInformationScrollY + 150);
                this.draggableWidget.SetIcon(this.selected.GetJutsu().GetIcon());
                this.draggableWidget.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);

                if (!this.draggableWidget.dragging && this.draggableWidget.isHovered())
                {
                    pGuiGraphics.renderComponentHoverEffect(this.font, null, pMouseX,pMouseY);

                    List<Component> jutsuCastRequirementsComponent = new ArrayList<>(List.of());
                    jutsuCastRequirementsComponent.add(Component.literal(this.selected.GetJutsu().GetDisplayName()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLUE));
                    jutsuCastRequirementsComponent.add(Component.literal("Hold and drag to jutsu bar to equip!"));
                    for (IJutsuResourceRequirement resourceRequirement: this.selected.GetJutsu().GetJutsuResourceRequirements())
                    {
                        jutsuCastRequirementsComponent.add(resourceRequirement.GetDisplay(this.getMinecraft().player));
                    }
                    pGuiGraphics.renderComponentTooltip(this.font, jutsuCastRequirementsComponent ,pMouseX, pMouseY);
                }

            } else
            {
                this.draggableWidget.active = false;
            }
        }



    }

    public void drawSkillLineMastery(GuiGraphics guiGraphics, int x, int y, int width, int height, ResourceLocation borderTexture, ResourceLocation fillTexture)
    {

        int thickness = 2;

        CRenderUtils.tileTexture(guiGraphics, borderTexture, x, y, width, height, 16);

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

        int points = playerLevelStats.GetPoints();

        Component pointsComponent = Component.literal(String.valueOf(points)).withStyle(ChatFormatting.GOLD).append(
                Component.literal(" Talent Points").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD)
                );

        guiGraphics.drawCenteredString(Minecraft.getInstance().font, pointsComponent
                , barX + barWidth/2, barY - Minecraft.getInstance().font.lineHeight - 5, Color.GRAY.hashCode()
        );

        FormattedCharSequence seq = pointsComponent.getVisualOrderText();
        int textWidth = font.width(seq);


        int centerX = barX + barWidth / 2;
        int textX    = centerX - textWidth / 2;
        int textY    = barY - font.lineHeight - 5;


        boolean isHoveringText = mouseX >= textX &&
                        mouseX <= textX + textWidth &&
                        mouseY >= textY &&
                        mouseY <= textY + font.lineHeight;

        if (isHoveringText)
        {
            CNarutoToolTips.renderTalentPointsTooltip(guiGraphics, mouseX, mouseY);
        }



        int level = playerLevelStats.GetPlayerLevelWithSkillLine(CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, this.skillLine));
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

    private int drawWrappedText(GuiGraphics guiGraphics, String text, int x, int y, int width, int height)
    {
        Font font = Minecraft.getInstance().font;
        Component component = Component.literal(text);
        List<FormattedCharSequence> lines = font.split(component, width);
        int lineHeight = font.lineHeight;
        int maxLines = height/lineHeight;

        int total = lines.size() * lineHeight;

        for (int i = 0; i < Math.min(lines.size(), maxLines); i++)
        {
            guiGraphics.drawString(font, lines.get(i), x, y + i * lineHeight, Color.WHITE.hashCode());
        }

        return total;

    }

    private void drawRequirements(Jutsu jutsu, GuiGraphics guiGraphics, int x, int y)
    {
        int lineHeight = font.lineHeight;

        for (int i = 0; i < jutsu.GetJutsuRequirements().size(); i++)
        {
            Component reqText = jutsu.GetJutsuRequirements().get(i).GetDisplay(getMinecraft().player);
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
    public void onClose() {
        this.minecraft.popGuiLayer();
        Minecraft.getInstance().setScreen(new PlayerStatsMain());
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton)
    {
        this.draggableWidget.mouseReleased(pMouseX, pMouseY, pButton);
        return super.mouseReleased(pMouseX, pMouseY, pButton);
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
        if (this.selected != null)
        {
            this.jutsuBarWidget.selectedJutsu = this.selected.GetJutsu();
            this.jutsuBarWidget.Reload();
        }
    }

}
