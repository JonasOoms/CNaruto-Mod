package net.complex.cnaruto.api;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public class CUtils {

    public static <T> RegistryObject<T> FindAndReturnFromRegistry(DeferredRegister<T> Register, ResourceLocation Id)
    {
        RegistryObject<T> RegistryObjectToBeFound = null;

        Collection<RegistryObject<T>> RegisterObjectCollection = Register.getEntries();

        for (int RegistryIndex = 0; RegistryIndex < RegisterObjectCollection.size(); RegistryIndex++)
        {
            RegistryObject<T> Object = (RegistryObject<T>) RegisterObjectCollection.toArray()[RegistryIndex];
            if (Object.getId().getPath().equals(Id.getPath()))
            {
                return Object;
            }
        }

        return null;
    }

    public static <T> RegistryObject<T> FindAndReturnFromRegistry(DeferredRegister<T> Register, T item ) {
        RegistryObject<T> RegistryObjectToBeFound = null;

        Collection<RegistryObject<T>> RegisterObjectCollection = Register.getEntries();

        for (int RegistryIndex = 0; RegistryIndex < RegisterObjectCollection.size(); RegistryIndex++) {
            RegistryObject<T> Object = (RegistryObject<T>) RegisterObjectCollection.toArray()[RegistryIndex];
            if (Object.get().equals(item)) {
                return Object;
            }
        }

        return null;
    }

    public static void tileTexture(GuiGraphics guiGraphics, ResourceLocation texture, int x, int y, int width, int height, int textureResolution) {
        for (int dx = 0; dx < width; dx += textureResolution) {
            for (int dy = 0; dy < height; dy += textureResolution) {
                int tileWidth = Math.min(textureResolution, width - dx);
                int tileHeight = Math.min(textureResolution, height - dy);
                guiGraphics.blit(texture, x + dx, y + dy, 0, 0, tileWidth, tileHeight, textureResolution, textureResolution);
            }
        }
    }

    public static void drawFittedComponentText(GuiGraphics guiGraphics, Font font, Component component, int x, int y, int width, int height, int color) {
        String text = component.getString(); // Flatten to plain string for measuring

        int textWidth = font.width(text);
        int textHeight = 8; // Default font height

        // Determine uniform scale that fits within the box
        float scaleX = (float) width / (float) textWidth;
        float scaleY = (float) height / (float) textHeight;
        float scale = Math.min(scaleX, scaleY);

        // Clamp scale to avoid weird cases
        scale = Math.max(0.1f, Math.min(scale, 4.0f));

        float scaledWidth = textWidth * scale;
        float scaledHeight = textHeight * scale;

        float drawX = x + (width - scaledWidth) / 2f;
        float drawY = y + (height - scaledHeight) / 2f;

        PoseStack poseStack = guiGraphics.pose();
        poseStack.pushPose();
        poseStack.translate(drawX, drawY, 0);
        poseStack.scale(scale, scale, 1.0f);

        guiGraphics.drawString(font, component, 0, 0, color, false);

        poseStack.popPose();
    }

}
