package net.complex.cnaruto.client.gui.widgets.draggableWidget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

public class DraggableWidget extends AbstractWidget {


    public boolean dragging;
    private int originX, originY;
    @Nullable
    private ResourceLocation icon = null;
    private final Collection<DropZoneWidget> dropZones;

    public DraggableWidget(int pX, int pY, int pWidth, int pHeight, Collection<DropZoneWidget> dropZones) {
        super(pX, pY, pWidth, pHeight, Component.empty());
        this.originX = pX;
        this.originY = pY;
        this.dropZones = dropZones;
    }

    public void SetOrigin(int pX, int pY)
    {
        this.originX = pX;
        this.originY = pY;
    }

    public void SetIcon(ResourceLocation pIcon)
    {
        this.icon = pIcon;
    }

    public void onDroppedInDropZone() {}

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int i, int i1, float v) {

        System.out.println(dragging);
        if (!dragging)
        {
            setX(originX);
            setY(originY);
        }

        if (icon == null)
        {
            guiGraphics.fill(getX(), getY(), getX() + getWidth(), getY() + getHeight(), 0xFF8888FF);
        } else
        {
            guiGraphics.blit(icon,getX(), getY(),0,0, getWidth(),getHeight(), getWidth(),getHeight() );
        }
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton)
    {
        if (isMouseOver(pMouseX, pMouseY))
        {
            dragging = true;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double pMouseX, double pMouseY, int pButton)
    {
        if (dragging)
        {
            if (!dropZones.isEmpty())
            {
                for (DropZoneWidget dropZone : dropZones)
                {
                    if (dropZone.isPointInBounds((int) pMouseX, (int) pMouseY))
                    {
                        dropZone.wasDroppedOn();
                    }
                }
            }

            dragging = false;

            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double pMouseX, double pMouseY, int pButton, double vX, double vY)
    {
        if (dragging)
        {
            setX((int) pMouseX - getWidth()/2);
            setY((int) pMouseY - getHeight()/2);
            return true;
        }
        return false;
    }
}
