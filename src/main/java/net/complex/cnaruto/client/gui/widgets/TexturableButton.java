package net.complex.cnaruto.client.gui.widgets;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.function.Supplier;

public class TexturableButton extends Button {



    private final ResourceLocation ButtonTexture;
    private final ResourceLocation HoverButtonTexture;


    public TexturableButton(int pX, int pY, int pWidth, int pHeight, OnPress pOnPress, ResourceLocation ButtonLoc, ResourceLocation HoverButtonTexture) {
        super(pX, pY, pWidth, pHeight, Component.empty(), pOnPress, new CreateNarration() {
            @Override
            public MutableComponent createNarrationMessage(Supplier<MutableComponent> supplier) {
                return Component.empty();
            }
        });
        this.ButtonTexture = ButtonLoc;
        this.HoverButtonTexture = HoverButtonTexture;





    }

    @Override
    protected void renderWidget(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        Minecraft minecraft = Minecraft.getInstance();
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0f);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        ResourceLocation DrawTexture = ButtonTexture;

        if (this.isHovered) DrawTexture = HoverButtonTexture;

       pGuiGraphics.blit(DrawTexture, this.getX(), this.getY(), 0, 0, this.getWidth(), this.getHeight(), this.getWidth(), this.getHeight());
        


        //pGuiGraphics.blitNineSliced(ButtonTexture, this.getX(), this.getY(), this.getWidth(), this.getHeight(), 20, 4, 200, 20, 0, this.getTextureY());
        pGuiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      //  int i = this.getFGColor();
        //this.renderString(pGuiGraphics, minecraft.font, i | Mth.ceil(this.alpha * 255.0F) << 24);
    }

}
