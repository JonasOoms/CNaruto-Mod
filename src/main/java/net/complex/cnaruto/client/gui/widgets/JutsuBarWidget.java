package net.complex.cnaruto.client.gui.widgets;

import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.complex.cnaruto.Jutsu.JutsuRegister;
import net.complex.cnaruto.Jutsu.JutsuResourceRequirements.IJutsuResourceRequirement;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.client.gui.widgets.draggableWidget.DropZoneWidget;
import net.complex.cnaruto.client.overlay.JutsuBarSystem;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerRequestC2S;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class JutsuBarWidget extends AbstractWidget {

    public Jutsu selectedJutsu;
    public JutsuBarDropZoneWidget[] dropZones = new JutsuBarDropZoneWidget[EquippedJutsu.slots];

    public JutsuBarWidget(int pX, int pY, int pWidth, int pHeight) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        Reload();
    }

    public void Reload()
    {
        ModMessages.SendToServer(new EquippedJutsuSyncWithServerRequestC2S());

        int slotWidth = 16;
        int slotHeight = 16;
        int spacing = 5;
        int totalWidth = (slotWidth + spacing) * dropZones.length - spacing;

        // Center the drop zones inside this widget
        int startX = this.getX() + (this.width - totalWidth) / 2;
        int centerY = this.getY() + (this.height - slotHeight) / 2;

        for (int i = 0; i < dropZones.length; i++) {
            int x = startX + i * (slotWidth + spacing);
            int y = centerY;

            dropZones[i] = new JutsuBarDropZoneWidget(this, x, y, slotWidth, slotHeight, i);
        }

    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
        for (JutsuBarDropZoneWidget dropZone : dropZones)
        {
            dropZone.renderWidget(guiGraphics, i, i1, v);
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    public class JutsuBarDropZoneWidget extends DropZoneWidget
    {

        private final JutsuBarWidget parent;
        private final Jutsu jutsu;
        private JutsuInstance jutsuInstance;
        private final int slot;
        boolean empty = false;

        public JutsuBarDropZoneWidget(JutsuBarWidget parent, int pX, int pY, int pWidth, int pHeight, int slot) {
            super(pX, pY, pWidth, pHeight, Component.empty());
            this.parent = parent;

            EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(Minecraft.getInstance().player);
            this.slot = slot;
            this.jutsuInstance = equippedJutsu.get(slot);
            if (jutsuInstance != null)
            {
                this.jutsu = jutsuInstance.jutsu;
            } else
            {
                this.empty = true;
                this.jutsu = null;
            }
        }

        public void renderJutsuResourceToolTip(GuiGraphics pGuiGraphics, int x, int y)
        {
            if (jutsuInstance != null)
            {
                List<Component> jutsuCastRequirementsComponent = new ArrayList<>(List.of());
                jutsuCastRequirementsComponent.add(Component.literal(this.jutsu.GetDisplayName()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.BLUE));
                for (IJutsuResourceRequirement resourceRequirement: jutsu.GetJutsuResourceRequirements())
                {
                    jutsuCastRequirementsComponent.add(resourceRequirement.GetDisplay(Minecraft.getInstance().player));
                }
                pGuiGraphics.renderComponentTooltip(Minecraft.getInstance().font, jutsuCastRequirementsComponent ,x, y);
            }
        }

        @Override
        public void wasDroppedOn() {
            System.out.println("JutsuBarDropZoneWidget was dropped on");
            EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(Minecraft.getInstance().player);


                if (equippedJutsu.isJutsuEquipped(parent.selectedJutsu))
                {
                    return;
                }

                if (!empty) {
                    if (this.jutsuInstance.HasCooldown()) {
                        return;
                    }
                }

            equippedJutsu.setJutsuInSlot(slot, CUtils.FindAndReturnFromRegistry(JutsuRegister.JUTSU_REGISTER, this.parent.selectedJutsu));
            this.parent.Reload();

        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {
            JutsuBarSystem.drawJutsuSlot(this.slot, guiGraphics, getX(), getY(), getWidth(),getHeight(), false);
            if (isMouseOver(i,i1))
            {
                this.renderJutsuResourceToolTip(guiGraphics, i,i1);
            }
        }

        @Override
        protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

        }
    }

}
