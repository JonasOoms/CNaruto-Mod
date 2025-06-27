package net.complex.cnaruto.systems.ChakraChargeManagerSubSystem;

import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT)
public class ClientChakraChargeManagerSubSystem {
    

    private static List<UUID> chargeLookup = new ArrayList<>();


    public static void Put(UUID uuid) {
        chargeLookup.add(uuid);
    }

    public static void Remove(UUID uuid) {
        chargeLookup.remove(uuid);
    }

    @SubscribeEvent
    public static void OnUnload(LevelEvent.Unload event) {
       chargeLookup.clear();
    }


    @SubscribeEvent
    public static void OnPlayerInput(MovementInputUpdateEvent event) {
        if (chargeLookup.contains(Minecraft.getInstance().player.getUUID())) {
            Input input = event.getInput();
            input.down = false;
            input.up = false;
            input.left = false;
            input.right = false;
            input.leftImpulse = 0.f;
            input.forwardImpulse = 0.f;
            input.shiftKeyDown = false;
            input.jumping = false;
        }
    }

    @SubscribeEvent
    public static void PlayerRenderEvent(RenderPlayerEvent event) {

    }



    
}
