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



}
