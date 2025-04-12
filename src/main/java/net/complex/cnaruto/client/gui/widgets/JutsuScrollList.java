package net.complex.cnaruto.client.gui.widgets;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.client.gui.SkillLineScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;
import java.util.List;
import java.util.Objects;

public class JutsuScrollList extends ObjectSelectionList<JutsuScrollList.JutsuEntry> {

    private final ResourceLocation JutsuScrollListBackground = new ResourceLocation(CNaruto.MODID, "textures/gui/jutsulistbackground.png");
    private SkillLineScreen parent;
    private final int listWidth;

    public JutsuScrollList(SkillLineScreen parent, int listWidth, int top, int bottom) {
        super(Minecraft.getInstance(), listWidth, parent.height, top, bottom, 9* 2 + 8);
        this.parent = parent;
        this.listWidth = listWidth;
        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
        this.refreshList();

    }

    protected int getScrollbarPosition() { return this.listWidth;}

    public int getRowWidth() { return this.listWidth;};

    public void refreshList() {
        this.clearEntries();
        this.parent.buildJutsuList((jutsuEntry) ->
        {
            this.addEntry((JutsuEntry) jutsuEntry);
        }, (jutsu) -> {
            return new JutsuEntry(jutsu, this.parent);
        });
    }

    protected void renderBackground(GuiGraphics guiGraphics) {
        guiGraphics.blit(JutsuScrollListBackground, this.x0, this.y0, 0, 0, this.width, this.y1 - this.y0, 512, 512);
    }


    public class JutsuEntry extends ObjectSelectionList.Entry<JutsuEntry> {
        private final Jutsu Jutsu;
        private final SkillLineScreen parent;

        List<Component> JutsuInfo;

        JutsuEntry(Jutsu Jutsu, SkillLineScreen parent) {
            this.Jutsu = Jutsu;
            this.parent = parent;

        }

//        public String GetId()
//        {
//            return this.id;
//        }

        private void BuildJutsuInfo(Jutsu Jutsu)
        {

        }

        @Override
        public Component getNarration() {
            return Component.empty();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int entryIdx, int top, int left, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean isMouseOver, float partialTick) {
            Component name = Component.literal(Jutsu.GetDisplayName()).withStyle(Style.EMPTY.withBold(true).withColor(Color.YELLOW.getRGB()));
            guiGraphics.blit(Jutsu.GetIcon(), JutsuScrollList.this.getLeft() + 4, top + 2, 0, 0, 16, 16, 16, 16);

            int Color = java.awt.Color.black.getRGB();
            if (JutsuScrollList.this.getSelected() == this)
            {
                Color = java.awt.Color.WHITE.getRGB();
            }

            guiGraphics.drawString(Minecraft.getInstance().font, Jutsu.GetDisplayName(), JutsuScrollList.this.getLeft()+16+6, top + 6, Color, false);
        }

        public boolean mouseClicked(double mouseClicked1, double mouseClicked2, int mouseClicked3) {
            this.parent.setSelected(this);
            JutsuScrollList.this.setSelected(this);
            return false;
        }

        public Jutsu GetJutsu()
        {
            return this.Jutsu;
        }

        public List<Component> ReturnDescriptionComponentList() {
            return this.JutsuInfo;
        }
    }


}
