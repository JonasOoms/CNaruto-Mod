package net.complex.cnaruto.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Config;
import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuRegister;
import net.complex.cnaruto.SkillLines.SkillLines.FireRelease;
import net.complex.cnaruto.client.keybinds.Keybindings;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.EquippedJutsuSyncWithServerRequestC2S;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT)
public class EquippedJutsuOverlay {

    private static ResourceLocation emptyJutsuSlot = new ResourceLocation(CNaruto.MODID, "textures/gui/jutsuslotwhite.png");
    private static boolean isOpen = false;
    private static boolean jutsuBarOpenDebounce = false;
    private static int selectedSlot = 0;

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
                EquippedJutsuProvider.get(Minecraft.getInstance().player).setJutsuInSlot(0, JutsuRegister.EXAMPLE_WATER_JUTSU);
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
                    ModMessages.SendToServer(new EquippedJutsuSyncWithServerRequestC2S());
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

        }
    }


    @SubscribeEvent
    public static void renderOverlay(RenderGuiOverlayEvent e)
    {
        if (isOpen)
        {
            e.setCanceled(true);

            GuiGraphics guiGraphics = e.getGuiGraphics();
            PoseStack stack = e.getGuiGraphics().pose();

            int screenWidth = e.getGuiGraphics().guiWidth();
            int screenHeight = e.getGuiGraphics().guiHeight();

            int screenMiddle = screenWidth / 2;


            drawJutsuSlot(selectedSlot, guiGraphics ,screenMiddle - 10, screenHeight - 40,20,20, true);
            guiGraphics.blit(emptyJutsuSlot, screenMiddle - 10, screenHeight - 40, 0,0, 20, 20,20,20);

            // draw slot to the left
            drawJutsuSlot(clampSlot(selectedSlot-1), guiGraphics, screenMiddle - 10 - 22, screenHeight - 25, 16, 16, false);
            // draw slot to the right
            drawJutsuSlot(clampSlot(selectedSlot+1), guiGraphics, screenMiddle - 10 + 25, screenHeight - 25, 16, 16, false);




        }
    }

    public static void drawJutsuSlot(int slot, GuiGraphics guiGraphics, int x, int y, int width, int height, boolean drawName)
    {
        Jutsu jutsu = EquippedJutsuProvider.get(Minecraft.getInstance().player).getJutsuInSlot(slot);
        if (jutsu != null)
        {
            guiGraphics.blit(jutsu.GetIcon(), x, y, 0,0, width, height,width,height);
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
                stack.pushPose();
                renderPlayerArm(stack, buffer,  e.getPackedLight(),0,0, HumanoidArm.LEFT);
                stack.popPose();
                stack.pushPose();
                renderPlayerArm(stack,buffer,  e.getPackedLight(),0,0, HumanoidArm.RIGHT);
                stack.popPose();
        
            }
        }
    }


    private static void renderPlayerArm(PoseStack poseStack, MultiBufferSource buffer, int packedLight, float equipProgress, float swingProgress, HumanoidArm p_109352_) {
        Minecraft mc = Minecraft.getInstance();
        boolean flag = p_109352_ != HumanoidArm.LEFT;
        float f = flag ? 1.0F : -1.0F;
        float f1 = Mth.sqrt(swingProgress);
        float f2 = -0.3F * Mth.sin(f1 * (float)Math.PI);
        float f3 = 0.4F * Mth.sin(f1 * ((float)Math.PI * 2F));
        float f4 = -0.4F * Mth.sin(swingProgress * (float)Math.PI);
        poseStack.translate(f * (f2 + 0.64000005F), f3 + -0.6F + equipProgress * -0.6F, f4 + -0.71999997F);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * 45.0F));
        float f5 = Mth.sin(swingProgress * swingProgress * (float)Math.PI);
        float f6 = Mth.sin(f1 * (float)Math.PI);
        poseStack.mulPose(Axis.YP.rotationDegrees(f * f6 * 70.0F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * f5 * -20.0F));
        AbstractClientPlayer abstractclientplayer = mc.player;
        RenderSystem.setShaderTexture(0, abstractclientplayer.getSkinTextureLocation());
        poseStack.translate(f * -1.0F, 3.6F, 3.5F);
        poseStack.mulPose(Axis.ZP.rotationDegrees(f * 120.0F));
        poseStack.mulPose(Axis.XP.rotationDegrees(200.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(f * -135.0F));
        poseStack.translate(f * 5.6F, 0.0F, 0.0F);

        PlayerRenderer playerrenderer = (PlayerRenderer)mc.getEntityRenderDispatcher().getRenderer(abstractclientplayer);
        if (flag) {
            playerrenderer.renderRightHand(poseStack, buffer, packedLight, abstractclientplayer);
        } else {
            playerrenderer.renderLeftHand(poseStack, buffer, packedLight, abstractclientplayer);
        }

    }

}
