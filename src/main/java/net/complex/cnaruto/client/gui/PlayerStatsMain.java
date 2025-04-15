package net.complex.cnaruto.client.gui;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.client.gui.widgets.OnPress.OpenSkillLineMenuForSkillLine;
import net.complex.cnaruto.client.gui.widgets.OnPress.StatIncreaseThenSyncToServer;
import net.complex.cnaruto.client.gui.widgets.SkillLineInformationFrame;
import net.complex.cnaruto.client.gui.widgets.TexturableButton;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.PlayerLevelStatsSyncRequestC2SPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PlayerStatsMain extends Screen {

    private final Player player;
    private final ResourceLocation background = new ResourceLocation(CNaruto.MODID, "textures/gui/scrollbackground.png");
    private final ResourceLocation elementlabel = new ResourceLocation(CNaruto.MODID, "textures/gui/chakranature.png");

    private final ResourceLocation plus = new ResourceLocation(CNaruto.MODID, "textures/gui/levelup.png");
    private final ResourceLocation plusSelected = new ResourceLocation(CNaruto.MODID, "textures/gui/levelupselect.png");

    private final ResourceLocation LevelingFrame = new ResourceLocation(CNaruto.MODID, "textures/gui/levelingframe.png");
    private final ResourceLocation LevelingBar = new ResourceLocation(CNaruto.MODID, "textures/gui/levelingbar.png");

    private TexturableButton SpiritButton;
    private TexturableButton DexterityButton;
    private TexturableButton AgilityButton;
    private TexturableButton NinjutsuButton;

    private TexturableButton TaijutsuButton;
    private TexturableButton KenjutsuButton;
    private TexturableButton MedicalButton;
    private TexturableButton SealingButton;
    private TexturableButton SummoningButton;

    int KekkeiPositionY;
    boolean ShowKekkei = false;

    public PlayerStatsMain() {

        super(Component.empty());
        this.player = Minecraft.getInstance().player;

    }






    @Override
    public void init() {

        int posX = (this.width - 400 ) / 2;
        int posY = (this.height - 256) / 2;

        ModMessages.SendToServer(new PlayerLevelStatsSyncRequestC2SPacket());

        player.getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent((store) -> {

            // StatButtons
            SpiritButton = new TexturableButton(posX + 65, posY + 78, 10, 10, new StatIncreaseThenSyncToServer(store::GetSpirit, store::SetSpirit, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(SpiritButton);
            DexterityButton = new TexturableButton(posX + 65, posY + 89, 10, 10, new StatIncreaseThenSyncToServer(store::GetDexterity, store::SetDexterity, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(DexterityButton);
            AgilityButton = new TexturableButton(posX + 65, posY + 100, 10, 10, new StatIncreaseThenSyncToServer(store::GetAgility, store::SetAgility, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(AgilityButton);
            NinjutsuButton = new TexturableButton(posX + 65, posY + 111, 10, 10, new StatIncreaseThenSyncToServer(store::GetNinjutsu, store::SetNinjutsu, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(NinjutsuButton);

            TaijutsuButton = new TexturableButton(posX + 65, posY + 138, 10, 10, new StatIncreaseThenSyncToServer(store::GetTaijutsu, store::SetTaijutsu, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(TaijutsuButton);
            KenjutsuButton = new TexturableButton(posX + 65, posY + 149, 10, 10, new StatIncreaseThenSyncToServer(store::GetKenjutsu, store::SetKenjutsu, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(KenjutsuButton);
            MedicalButton = new TexturableButton(posX + 65, posY + 160, 10, 10, new StatIncreaseThenSyncToServer(store::GetMedical, store::SetMedical, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(MedicalButton);
            SealingButton = new TexturableButton(posX + 65, posY + 171, 10, 10, new StatIncreaseThenSyncToServer(store::GetSealing, store::SetSealing, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(SealingButton);
            SummoningButton = new TexturableButton(posX + 65, posY + 182, 10, 10, new StatIncreaseThenSyncToServer(store::GetSummoning, store::SetSummoning, store::RemovePointsIfEnough), plus, plusSelected );
            this.addRenderableWidget(SummoningButton);

            // Rendering unlocked Skill Lines
            ArrayList<SkillLineData> ElementList = store.GetElementReleases();
            ArrayList<SkillLineData> KekkeiList = store.GetKekkeiReleases();

            int ElementPosX = posX + 220;
            int ElementPosY = posY + 50;

            int columns = 3;

            for (int i = 0; i < ElementList.size(); i++)
            {
                int ColumnIndex = i % columns;
                int RowIndex = i / columns;

                int eX = (ElementPosX + 20) + (22)*ColumnIndex;
                int eY = (ElementPosY + 20) + (16)*RowIndex;

                this.addRenderableWidget(new SkillLineInformationFrame(
                        eX,
                        eY,
                        16,
                        16,
                        CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, ElementList.get(i).GetId())).get()
                ));
            }

            int KekkeiPosX = ElementPosX;
            int KekkeiPosY = (ElementPosY + 20) + (16)*(ElementList.size()/columns) + 20;
            KekkeiPositionY = KekkeiPosY;

            if (KekkeiList.size() > 0)
            {
                ShowKekkei = true;
                for (int i = 0; i < KekkeiList.size(); i++)
                {
                    int ColumnIndex = i % columns;
                    int RowIndex = i / columns;

                    int eX = (KekkeiPosX + 20) + (22)*ColumnIndex;
                    int eY = (KekkeiPosY + 20) + (16)*RowIndex;

                    this.addRenderableWidget(new SkillLineInformationFrame(
                            eX,
                            eY,
                            16,
                            16,
                            CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, KekkeiList.get(i).GetId())).get()
                    ));
                }
            }

            //this.addRenderableWidget(new SkillLineInformationFrame(posX, posY, 32, 32, CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, store.ElementReleases.get(0).GetId())).get()));

        });


    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick)
    {

        int posX = (this.width - 400 ) / 2;
        int posY = (this.height - 256) / 2;

        pGuiGraphics.blit(background, posX, posY, 0,   0, 400, 256, 400, 256);
        //RenderElements((this.width)/2+ 75, (this.height)/2 - 90, pGuiGraphics);

        Font SelectedFont = minecraft.font;

        pGuiGraphics.drawString(SelectedFont, "Level", posX + 80, posY + 50 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Spirit", posX + 80, posY + 80 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Dexterity", posX + 80, posY + 91 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Agility", posX + 80, posY + 102 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Ninjutsu", posX + 80, posY + 112 , Color.BLACK.getRGB(), false);

        pGuiGraphics.drawString(SelectedFont, "Taijutsu", posX + 80, posY + 140 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Kenjutsu", posX + 80, posY + 151 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Medical Ninjutsu", posX + 80, posY + 162 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Sealing Jutsu", posX + 80, posY + 172 , Color.BLACK.getRGB(), false);
        pGuiGraphics.drawString(SelectedFont, "Summoning Jutsu", posX + 80, posY + 184 , Color.BLACK.getRGB(), false);

        pGuiGraphics.drawString(SelectedFont, "Talent Points", posX + 65, posY + 200 , Color.BLACK.getRGB(), false);

        pGuiGraphics.blit(LevelingFrame, posX + 220, posY + 190, 0, 0, 128, 32, 128 , 32);

        pGuiGraphics.drawString(SelectedFont, "Nature", posX + 255, posY + 60, Color.BLACK.getRGB(), false );
        pGuiGraphics.drawString(SelectedFont, "Kekkei Genkai", posX + 255, KekkeiPositionY, Color.BLACK.getRGB(), false);

        player.getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent((store) ->
        {
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetLevel()), posX + 110, posY + 50 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetSpirit()), posX + 170, posY + 80 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetDexterity()), posX + 170, posY + 91 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetAgility()), posX + 170, posY + 102 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetNinjutsu()), posX + 170, posY + 112 , GetColorRGBBasedOnLevel(store.GetLevel()), false);

            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetTaijutsu()), posX + 170, posY + 140 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetKenjutsu()), posX + 170, posY + 151 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetMedical()), posX + 170, posY + 162 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetSealing()), posX + 170, posY + 172 , GetColorRGBBasedOnLevel(store.GetLevel()), false);
            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetSummoning()), posX + 170, posY + 184 , GetColorRGBBasedOnLevel(store.GetLevel()), false);

            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetPoints()), posX + 135, posY + 200 , Color.black.getRGB(), false);
            pGuiGraphics.blit(LevelingBar, posX + 220, posY + 190, 0, 0, (int) (128.f * store.GetXP()/store.GetMaxXP()), 32,  128, 32);

            pGuiGraphics.drawString(SelectedFont, Integer.toString(store.GetXP()) + "/" + Integer.toString(store.GetMaxXP()), posX + 270, posY + 202 , Color.BLUE.getRGB(), false);



        });

        Iterator<Renderable> it = renderables.iterator();

        while (it.hasNext())
        {
            Renderable next = it.next();
            next.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        }





    }

    private void RenderElements(int beginX, int beginY, GuiGraphics pGuiGraphics)
    {

        pGuiGraphics.blit(elementlabel, beginX, beginY, 0,   0, 88, 32, 88, 32);

        player.getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent((store) -> {

            int columns = 2;

            for (int i = 0; i < store.GetElementReleasesSize(); i++)
            {

                ResourceLocation Icon = CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, store.ElementReleases.get(i).GetId())).get().GetIcon();

                int ColumnIndex = i % columns;
                int RowIndex = i / columns;

                int X = (beginX + 20) + (16)*ColumnIndex;
                int Y = (beginY + 40) + (16)*RowIndex;

                pGuiGraphics.blit(Icon, X, Y, 0,   0, 16, 16, 16, 16);


            }
        });
    }

    private int GetColorRGBBasedOnLevel(int level)
    {
        return Color.GRAY.getRGB();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new PlayerStatsMain());
    }



}
