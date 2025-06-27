package net.complex.cnaruto.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Config;
import net.complex.cnaruto.Data.*;
import net.complex.cnaruto.Data.attributes.ModAttributes;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.complex.cnaruto.api.CRenderUtils;
import net.complex.cnaruto.client.keybinds.Keybindings;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.CustomArmRenderer;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns.IHandsign;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.Handsigns.RamSealHandsign;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.ChakraChargeManagerSystem.ChakraChargeManagerDeleteC2SPacket;
import net.complex.cnaruto.networking.packet.c2s.ChakraChargeManagerSystem.ChakraChargeManagerPostC2SPacket;
import net.complex.cnaruto.networking.packet.c2s.ChakraManagerSyncWithServerRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.JutsuSystem.HandSignSoundRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.JutsuSystem.JutsuSystemCastRequestC2S;
import net.complex.cnaruto.sounds.ModSounds;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT)
public class JutsuBarSystem {

    private static ResourceLocation emptyJutsuSlot = new ResourceLocation(CNaruto.MODID, "textures/gui/jutsuslotwhite.png");
    private static ResourceLocation FillBar = new ResourceLocation(CNaruto.MODID, "textures/gui/fillbar.png");
    private static ResourceLocation FillBarChakra = new ResourceLocation(CNaruto.MODID, "textures/gui/fillbarchakra.png");
    private static ResourceLocation FillBarBackground = new ResourceLocation(CNaruto.MODID, "textures/gui/fillbarbackground.png");
    private static boolean isOpen = false;
    private static boolean jutsuBarOpenDebounce = false;
    private static int selectedSlot = 0;
    private static JutsuInstance selectedJutsu = null;

    // handsign and charging
    private static boolean isCharging = false;
    private static boolean isJutsuReadyToCast = false;
    private static int handsignCharge = 0;
    private static float handsignTimer = 0;

    private static IHandsign defaultHandsign = new RamSealHandsign();

    // chakra charging
    private static boolean isChakraCharging = false;


    public static void SyncResourcesRequest()
    {
        ModMessages.SendToServer(new EquippedJutsuSyncWithServerRequestC2S());
        ModMessages.SendToServer(new ChakraManagerSyncWithServerRequestC2S());
    }


    @SubscribeEvent
    public static void onJoin(PlayerEvent.PlayerLoggedInEvent event)
    {
//        SyncResourcesRequest();
    }

    @SubscribeEvent
    public static void OnScroll(InputEvent.MouseScrollingEvent e) {
        if (isOpen) {
            e.setCanceled(true);

            if (e.getScrollDelta() < 0)
            {
                selectedSlot = selectedSlot - 1;
            } else
            {
                selectedSlot = selectedSlot + 1;
            }

            selectedSlot = clampSlot(selectedSlot);
            selectedJutsu = EquippedJutsuProvider.get(Minecraft.getInstance().player).get(selectedSlot);

            isCharging = false;

        }
    }

    public static int clampSlot(int slot)
    {
        if (slot < 0)
        {
            return EquippedJutsu.slots -1;
        } else if (slot >= EquippedJutsu.slots)
        {
            return 0;
        }
        return slot;
    }

    @SubscribeEvent
    public static void onMouseButton(InputEvent.MouseButton e) {
        if (isOpen)
        {
            e.setCanceled(true);

            if (!isChakraCharging) {
                if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    if (e.getAction() == GLFW.GLFW_PRESS) {
                        isCharging = true;
                    } else if (e.getAction() == GLFW.GLFW_RELEASE) {
                        // end jutsu charge and do jutsu check bs
                        isCharging = false;
                        handsignTimer = 0;
                        handsignCharge = 0;

                        if (isJutsuReadyToCast) {
                            isJutsuReadyToCast = false;
                            CastJutsu();
                        }

                    }
                }
            }
        }
    }

    private static void CastJutsu()
    {
        Player player = Minecraft.getInstance().player;
        JutsuInstance instance = EquippedJutsuProvider.get(player).get(selectedSlot);
        if (instance != null)
        {
            if (instance.jutsu.CastRequirements(player))
            {
                //Minecraft.getInstance().player.playSound(ModSounds.JUTSU_ACTIVATION.get(), 1.0f, 1.0f);
                ModMessages.SendToServer(new JutsuSystemCastRequestC2S(selectedSlot));
            }
        }
    }

    @SubscribeEvent
    public static void chakraChargeCheck(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().player != null)
        {
            if (isChakraCharging)
            {
                {
                    if (!Keybindings.NARUTO_KEYBINDINGS.ChargeChakraKey.isDown())
                    {
                        isChakraCharging = false;
                        ModMessages.SendToServer(new ChakraChargeManagerDeleteC2SPacket(Minecraft.getInstance().player.getUUID()));
                    }
                }
            } else
            {
                if (Keybindings.NARUTO_KEYBINDINGS.ChargeChakraKey.isDown() && isOpen && !isCharging)
                {
                    isChakraCharging = true;
                    ModMessages.SendToServer(new ChakraChargeManagerPostC2SPacket(Minecraft.getInstance().player.getUUID()));
                }
            }
        }
    }

    @SubscribeEvent
    public static void clientTickEvent(TickEvent.ClientTickEvent e) {
        if (Minecraft.getInstance().player != null) {
            if (Config.toggleJutsuBar) {
                if (Keybindings.NARUTO_KEYBINDINGS.JutsuBarKey.consumeClick() && !jutsuBarOpenDebounce) {
                    jutsuBarOpenDebounce = true;
                    isOpen = !isOpen;
                    SyncResourcesRequest();
                }
                if (!Keybindings.NARUTO_KEYBINDINGS.JutsuBarKey.isDown())
                {
                    jutsuBarOpenDebounce = false;
                }
            } else {
                if (Keybindings.NARUTO_KEYBINDINGS.JutsuBarKey.isDown()) {
                    isOpen = true;
                } else {
                    isOpen = false;
                }
            }

            if (Minecraft.getInstance().screen != null)
            {
                isOpen = false;

            }

            selectedJutsu = EquippedJutsuProvider.get(Minecraft.getInstance().player).get(selectedSlot);

           ChargingClientTick(e);
        }
    }


    private static void ChargingClientTick(TickEvent.ClientTickEvent e)
    {
        if (!isOpen)
        {
            isCharging = false;
        }

        JutsuInstance instance = selectedJutsu;

        if (isCharging) {
            if (instance != null) {
                if (!instance.HasCooldown()) {
                    Jutsu jutsu = instance.jutsu;
                    if (jutsu != null) {

                        if (handsignCharge >= jutsu.GetHandSignRequirement() - 1) {
                            isJutsuReadyToCast = true;
                        } else {

                            // TODO: should become variable, change depending on a stat


                            assert Minecraft.getInstance().player != null;
                            double timeBetweenHandsigns =  Minecraft.getInstance().player.getAttribute(ModAttributes.HANDSIGN_SPEED.get()).getValue() * 20;
                            System.out.println(timeBetweenHandsigns);
                            handsignTimer += Minecraft.getInstance().getPartialTick();


                            if (handsignTimer >= timeBetweenHandsigns) {
                                handsignTimer -= (float) timeBetweenHandsigns;
                                handsignCharge += 1;
                                Minecraft.getInstance().player.playSound(ModSounds.JUTSU_HAND_SIGN.get(), 1.0f, 1.0f);
                                ModMessages.SendToServer(new HandSignSoundRequestC2S());
                            }
                        }
                    }
                }

            }

        } else {
            handsignCharge = 0;
            handsignTimer = 0;
            isJutsuReadyToCast = false;
        }
    }

    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent.Pre e)
    {
        GuiGraphics guiGraphics = e.getGuiGraphics();
        RenderChakraBar(guiGraphics);
        if (isOpen)
        {
            if (e.getOverlay() == VanillaGuiOverlay.PLAYER_HEALTH.type() ||
                    e.getOverlay() == VanillaGuiOverlay.ARMOR_LEVEL.type() ||
                    e.getOverlay() == VanillaGuiOverlay.HOTBAR.type() ||
                    e.getOverlay() == VanillaGuiOverlay.FOOD_LEVEL.type() ||
                    e.getOverlay() == VanillaGuiOverlay.EXPERIENCE_BAR.type() ||
                    e.getOverlay() == VanillaGuiOverlay.AIR_LEVEL.type() ||
                    e.getOverlay() == VanillaGuiOverlay.MOUNT_HEALTH.type()
            ) {

            e.setCanceled(true);


            if (!isChakraCharging)
            {
                PoseStack stack = e.getGuiGraphics().pose();

                int screenWidth = e.getGuiGraphics().guiWidth();
                int screenHeight = e.getGuiGraphics().guiHeight();

                int screenMiddle = screenWidth / 2;


                drawJutsuSlot(selectedSlot, guiGraphics, screenMiddle - 10, screenHeight - 40, 20, 20, true);
                guiGraphics.blit(emptyJutsuSlot, screenMiddle - 10, screenHeight - 40, 0, 0, 20, 20, 20, 20);

                // draw slot to the left
                drawJutsuSlot(clampSlot(selectedSlot - 1), guiGraphics, screenMiddle - 10 - 22, screenHeight - 25, 16, 16, false);
                // draw slot to the right
                drawJutsuSlot(clampSlot(selectedSlot + 1), guiGraphics, screenMiddle - 10 + 25, screenHeight - 25, 16, 16, false);

            }

        } else if (e.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type())
            {
                if (isCharging)
                {
                    e.setCanceled(true);

                    ResourceLocation currentHandsignTexture = defaultHandsign.GetHandsignIcon();
                    if (selectedJutsu != null) {
                        currentHandsignTexture = selectedJutsu.jutsu.GetHandSigns().get(handsignCharge).GetHandsignIcon();
                    }


                    int x = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
                    int y = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;

                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1f,1f,1f,0.5f);
                    guiGraphics.blit(currentHandsignTexture, x - 16, y - 16, 0, 0, 0, 32, 32, 32, 32 );
                    RenderSystem.setShaderColor(1f,1f,1f,1f);
                } else if (isChakraCharging)
                {
                    e.setCanceled(true);

                    ResourceLocation currentHandsignTexture = defaultHandsign.GetHandsignIcon();

                    int x = Minecraft.getInstance().getWindow().getGuiScaledWidth() / 2;
                    int y = Minecraft.getInstance().getWindow().getGuiScaledHeight() / 2;

                    RenderSystem.enableBlend();
                    RenderSystem.setShaderColor(1f,1f,1f,0.5f);
                    guiGraphics.blit(currentHandsignTexture, x - 16, y - 16, 0, 0, 0, 32, 32, 32, 32 );
                    RenderSystem.setShaderColor(1f,1f,1f,1f);
                }
            }
        }
    }
    public static void drawJutsuSlot(int slot, GuiGraphics guiGraphics, int x, int y, int width, int height, boolean drawName)
    {
        
        EquippedJutsu equippedJutsu = EquippedJutsuProvider.get(Minecraft.getInstance().player);
        JutsuInstance jutsuInstance = equippedJutsu.get(slot);
        if (jutsuInstance == null)
        {
            guiGraphics.blit(emptyJutsuSlot, x, y, 0,0, width, height,width,height);
            return;
        }

        Jutsu jutsu = jutsuInstance.jutsu;
        if (jutsu != null)
        {
            if (jutsuInstance.HasCooldown())
            {
                RenderSystem.setShaderColor(0.5f,0.5f,0.5f,1f);
            }
            guiGraphics.blit(jutsu.GetIcon(), x, y, 0,0, width, height,width,height);
            RenderSystem.setShaderColor(1f,1f,1f,1f);
            if (drawName)
            {

                Font font = Minecraft.getInstance().font;
                String jutsuDisplayName = jutsu.GetDisplayName();
                int displayNameWidth = font.width(jutsuDisplayName);
                guiGraphics.drawString(font, jutsuDisplayName, x + 8 - displayNameWidth/2, (int) (y - height), Color.WHITE.hashCode());
            }
        } else
        {
            guiGraphics.blit(emptyJutsuSlot, x, y, 0,0, width, height,width,height);
        }
    }

    @SubscribeEvent
    public static void renderHand(RenderHandEvent e) {
        if (isOpen) {
            e.setCanceled(true);
            if (e.getHand() == InteractionHand.MAIN_HAND) {

                PoseStack stack = new PoseStack();
                MultiBufferSource buffer = e.getMultiBufferSource();

                RenderHandSigns(stack, buffer, e.getPackedLight(), HumanoidArm.LEFT);
                RenderHandSigns(stack, buffer, e.getPackedLight(), HumanoidArm.RIGHT);


            }
        }
    }

    private static void RenderHandSigns(PoseStack poseStack, MultiBufferSource buffer, int packedLight , HumanoidArm arm)
    {
        if (isCharging)
        {

            IHandsign currentHandsign = defaultHandsign;
            if (selectedJutsu != null) {
                currentHandsign = selectedJutsu.jutsu.GetHandSigns().get(handsignCharge );
            }

            CustomArmRenderer.renderPlayerArm(poseStack, buffer, packedLight, 0, 0, arm, currentHandsign.GetHandsignArmPose(arm));

        }
        else if (isChakraCharging)
        {
            IHandsign currentHandsign = defaultHandsign;
            CustomArmRenderer.renderPlayerArm(poseStack, buffer, packedLight, 0, 0, arm, currentHandsign.GetHandsignArmPose(arm));
        }
        else
        {
            CustomArmRenderer.renderPlayerArm(poseStack, buffer, packedLight, 0,0,arm);
        }
    }

    private static void RenderChakraBar(GuiGraphics guiGraphics)
    {
        final int x = 10;
        final int y = guiGraphics.guiHeight() - 20;

        guiGraphics.blit(FillBarBackground, x, y, 0,0,128,16,128,16);

        ChakraManager chakraManager = ChakraManagerProvider.get(Minecraft.getInstance().player);

        int chakra = chakraManager.GetChakra();
        int maxChakra = chakraManager.GetMaxChakra(Minecraft.getInstance().player);
        float fillRatio = Mth.clamp((float) chakra /maxChakra, 0.0f, 1.0f);
        int fillWidth = (int)(128*fillRatio);

        guiGraphics.blit(FillBarChakra, x, y, 0,0,fillWidth,16,128,16);
        //guiGraphics.blit(FillBarChakra, x, y, 128,16,0,0,128,16,128,16);

        guiGraphics.blit(FillBar, x, y, 0,0,128,16,128,16);

        CRenderUtils.drawFittedComponentText(guiGraphics, Minecraft.getInstance().font, Component.literal(String.valueOf(chakra) + "/" + String.valueOf(maxChakra)), x,y + 3,128,16-6, Color.WHITE.getRGB());



    }

}
