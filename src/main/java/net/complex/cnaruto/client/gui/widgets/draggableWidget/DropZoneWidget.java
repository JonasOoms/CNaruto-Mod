package net.complex.cnaruto.client.gui.widgets.draggableWidget;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.awt.*;

public abstract class DropZoneWidget extends AbstractWidget {
    public DropZoneWidget(int pX, int pY, int pWidth, int pHeight, Component pMessage) {
        super(pX, pY, pWidth, pHeight, pMessage);
    }

    public abstract void wasDroppedOn();

    public final Rectangle GetBounds()
    {
        return new Rectangle(this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }
    public final boolean isPointInBounds(int x, int y)
    {
        return (this.isMouseOver(x,y));
    }


}
