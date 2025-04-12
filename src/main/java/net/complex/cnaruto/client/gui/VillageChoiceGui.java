package net.complex.cnaruto.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class VillageChoiceGui extends Screen {


    private final ResourceLocation background = new ResourceLocation(CNaruto.MODID, "textures/gui/scrollbackground.png");

    Button ContinueButton;

    private final Player player;
    public VillageChoiceGui() {

        super(Component.empty());
        this.player = Minecraft.getInstance().player;

    }

    @Override
    public void init() {

        //ContinueButton = new Button();

        //this.addRenderableWidget(ContinueButton);



    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {



        int posX = (this.width - 400 ) / 2;
        int posY = (this.height - 256) / 2;

        pGuiGraphics.blit(background, posX, posY, 0,   0, 400, 256, 400, 256);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new VillageChoiceGui());
    }






}
