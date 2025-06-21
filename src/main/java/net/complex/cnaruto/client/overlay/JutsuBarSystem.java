package net.complex.cnaruto.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Config;
import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.complex.cnaruto.api.CRenderUtils;
import net.complex.cnaruto.client.keybinds.Keybindings;
import net.complex.cnaruto.client.rendering.CustomArmRenderer.CustomArmRenderer;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.ChakraManagerSyncWithServerRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.JutsuSystem.HandSignSoundRequestC2S;
import net.complex.cnaruto.networking.packet.c2s.JutsuSystem.JutsuSystemCastRequestC2S;
import net.complex.cnaruto.sounds.ModSounds;
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

    // handsign and charging
    private static boolean isCharging = false;
    private static boolean isJutsuReadyToCast = false;
    private static int handsignCharge = 0;
    private static float handsignTimer = 0;

    // handsign rendering
    private static final ResourceLocation handsignTextures[] =
            {
                    new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/tiger.png"),
                    new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/horse.png"),
                    new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/rat.png"),
                    new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/ram.png"),
                    new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/snake.png"),
                    new ResourceLocation(CNaruto.MODID, "textures/gui/handsigns/hare.png"),
            };

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

            if (e.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT)
            {
                if (e.getAction() == GLFW.GLFW_PRESS)
                {
                    isCharging = true;
                } else if (e.getAction() == GLFW.GLFW_RELEASE)
                {
                    // end jutsu charge and do jutsu check bs
                    isCharging = false;
                    handsignTimer = 0;
                    handsignCharge = 0;

                    if (isJutsuReadyToCast)
                    {
                        isJutsuReadyToCast = false;
                        CastJutsu();
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

           ChargingClientTick(e);
        }
    }


    private static void ChargingClientTick(TickEvent.ClientTickEvent e)
    {
        if (!isOpen)
        {
            isCharging = false;
        }

        JutsuInstance instance = EquippedJutsuProvider.get(Minecraft.getInstance().player).get(selectedSlot);

        if (isCharging) {
            if (instance != null) {
                if (!instance.HasCooldown()) {
                    Jutsu jutsu = instance.jutsu;
                    if (jutsu != null) {

                        if (handsignCharge >= jutsu.GetHandSignRequirement()) {
                            isJutsuReadyToCast = true;
                        } else {

                            // TODO: should become variable, change depending on a stat
                            float timeBetweenHandsigns = 1.f * 20;
                            handsignTimer += Minecraft.getInstance().getPartialTick();


                            if (handsignTimer >= timeBetweenHandsigns) {
                                handsignTimer -= timeBetweenHandsigns;
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

            GuiGraphics guiGraphics = e.getGuiGraphics();
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

            RenderChakraBar(guiGraphics);
        } else if (e.getOverlay() == VanillaGuiOverlay.CROSSHAIR.type())
            {
                if (isCharging)
                {
                    e.setCanceled(true);
                    GuiGraphics guiGraphics = e.getGuiGraphics();

                    int currentHandsign = handsignCharge % handsignTextures.length;
                    ResourceLocation currentHandsignTexture = handsignTextures[currentHandsign];

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
                float partialTicks = e.getPartialTick();

                //stack.translate(0, 0f, -0.1f);

                RenderHandSigns(stack, buffer, e.getPackedLight(), HumanoidArm.LEFT);
                RenderHandSigns(stack, buffer, e.getPackedLight(), HumanoidArm.RIGHT);


            }
        }
    }

    private static void RenderHandSigns(PoseStack poseStack, MultiBufferSource buffer, int packedLight , HumanoidArm arm)
    {
        if (isCharging)
        {
            CustomArmRenderer.ArmPose armPose = CustomArmRenderer.VANILLA_FIRST_PERSON;
            switch (handsignCharge)
            {
                // Tiger seal
                case 0: {

                        armPose = (stack, equip, swing, sign) -> {


                            stack.translate(
                                    sign * (0 + 0.64000005F),
                                    (0 - 0.6F + equip * -0.6F),
                                    (0 - 0.71999997F)
                            );

                            stack.translate(-0.57*sign,-0.5f,-0.3f);

                            stack.mulPose(Axis.YP.rotationDegrees(180*sign));


                            stack.mulPose(Axis.ZP.rotationDegrees(-30*sign));

                        };


                    break;
                }
                // horse seal
                case 1: {
                    armPose = (stack, equip, swing, sign) -> {


                        stack.translate(
                                sign * (0 + 0.64000005F),
                                (0 - 0.6F + equip * -0.6F),
                                (0 - 0.71999997F)
                        );

                        stack.translate(-0.04*sign,-0.3f,0f);

                        stack.mulPose(Axis.YP.rotationDegrees(270*sign));

                        stack.mulPose(Axis.XP.rotationDegrees(50));

                    };
                    break;
                }
                // rat seal
                case 2: {

                    if (arm == HumanoidArm.LEFT)
                    {
                        armPose = (stack, equip, swing, sign) -> {


                            stack.translate(
                                    sign * (0 + 0.64000005F),
                                    (0 - 0.6F + equip * -0.6F),
                                    (0 - 0.71999997F)
                            );

                            stack.translate(-0.7*sign,-0.55f,-0.4f);

                            stack.mulPose(Axis.YP.rotationDegrees(180*sign));


                            stack.mulPose(Axis.ZP.rotationDegrees(-20*sign));

                        };
                    } else
                    {
                        armPose = (stack, equip, swing, sign) -> {


                            stack.translate(
                                    sign * (0 + 0.64000005F),
                                    (0 - 0.6F + equip * -0.6F),
                                    (0 - 0.71999997F)
                            );

                            stack.translate(-0.60 * sign, -0.40f, -0.3f);

                            stack.mulPose(Axis.YP.rotationDegrees(180 * sign));


                            stack.mulPose(Axis.ZP.rotationDegrees(-30 * sign));
                        };
                    }
                    break;
                }
                // ram seal
                case 3:
                {
                    if (arm == HumanoidArm.RIGHT)
                    {
                        armPose = (stack, equip, swing, sign) -> {


                            stack.translate(
                                    sign * (0 + 0.64000005F),
                                    (0 - 0.6F + equip * -0.6F),
                                    (0 - 0.71999997F)
                            );

                            stack.translate(-0.55,-0.55f,-0.4f);

                            stack.mulPose(Axis.YP.rotationDegrees(180*sign));


                            stack.mulPose(Axis.ZP.rotationDegrees(-30*sign));

                        };
                    } else
                    {
                        armPose = (stack, equip, swing, sign) -> {


                            stack.translate(
                                    sign * (0 + 0.64000005F),
                                    (0 - 0.6F + equip * -0.6F),
                                    (0 - 0.71999997F)
                            );

                            stack.translate(0.78, -0.40f, -0.4f);

                            stack.mulPose(Axis.YP.rotationDegrees(180 * sign));


                            stack.mulPose(Axis.ZP.rotationDegrees(-15 * sign));
                        };
                    }
                    break;
                }

            }
            CustomArmRenderer.renderPlayerArm(poseStack, buffer, packedLight, 0, 0, arm, armPose);
        }
        else
        {
            CustomArmRenderer.renderPlayerArm(poseStack, buffer, packedLight, 0,0,arm);
        }
    }

    private static void renderPlayerArm(PoseStack poseStack, MultiBufferSource buffer, int packedLight, float equipProgress, float swingProgress, HumanoidArm p_109352_) {
//        Minecraft mc = Minecraft.getInstance();
//        boolean flag = p_109352_ != HumanoidArm.LEFT;
//        float f = flag ? 1.0F : -1.0F;
//        float f1 = Mth.sqrt(swingProgress);
//        float f2 = -0.3F * Mth.sin(f1 * (float)Math.PI);
//        float f3 = 0.4F * Mth.sin(f1 * ((float)Math.PI * 2F));
//        float f4 = -0.4F * Mth.sin(swingProgress * (float)Math.PI);
//        poseStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);
//        poseStack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
//        float f5 = Mth.sin(swingProgress * swingProgress * (float)Math.PI);
//        float f6 = Mth.sin(f1 * (float)Math.PI);
//        poseStack.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
//        poseStack.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
//        AbstractClientPlayer abstractclientplayer = mc.player;
//        RenderSystem.setShaderTexture(0, abstractclientplayer.getSkinTextureLocation());
//        poseStack.translate(f * -1.0F, 3.6F, 3.5F);
//        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
//        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
//        poseStack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
//        poseStack.translate(f * 5.6F, 0.0F, 0.0F);
//
//
//
//        PlayerRenderer playerrenderer = (PlayerRenderer)mc.getEntityRenderDispatcher().getRenderer(abstractclientplayer);
//        if (flag) {
//            poseStack.pushPose();
//            transformHandSigns(poseStack, HumanoidArm.RIGHT);
//            playerrenderer.renderRightHand(poseStack, buffer, packedLight, abstractclientplayer);
//            poseStack.popPose();
//        } else {
//            poseStack.pushPose();
//            transformHandSigns(poseStack, HumanoidArm.LEFT);
//            playerrenderer.renderLeftHand(poseStack, buffer, packedLight, abstractclientplayer);
//            poseStack.popPose();
//        }


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
