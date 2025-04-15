package net.complex.cnaruto.mixin;

import com.mojang.math.Axis;
import net.complex.cnaruto.CNaruto;
import net.minecraft.Util;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashRenderer.class)
public class SplashTextMixin {

    private static ResourceLocation LOGO = new ResourceLocation(CNaruto.MODID, "textures/logo.png");

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void render(GuiGraphics pGuiGraphics, int pScreenWidth, Font pFont, int pColor, CallbackInfo ci) {

        int width = 64;
        int height = 32;

        pGuiGraphics.pose().pushPose();
        pGuiGraphics.pose().translate((float)pScreenWidth / 2.0F + 123.0F, 69.0F, 0.0F);
        pGuiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
        float $$4 = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 5000L) / 5000.0F * ((float)Math.PI * 2F)) * 0.1F);
        $$4 = $$4 * 100.0F / ((float)(width) + 32);
        pGuiGraphics.pose().scale($$4, $$4, $$4);
        pGuiGraphics.blit(LOGO, -32,-16, 0,0, 64,32,64,32);
        pGuiGraphics.pose().popPose();
        ci.cancel();
    }

}
