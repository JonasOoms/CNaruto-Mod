package net.complex.cnaruto.client.rendering.models;

import net.complex.cnaruto.CNaruto;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingPhase;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.event.lifecycle.ModLifecycleEvent;

@Mod.EventBusSubscriber(modid = CNaruto.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelRegister {

    public static PlayerAuraModel PLAYERAURAMODEL;

    @SubscribeEvent
    public static void layerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event)
    {

        event.registerLayerDefinition(PlayerAuraModel.LAYER_LOCATION, PlayerAuraModel::createBodyLayer);

    }

    @SubscribeEvent
    public static void onClientSetup(EntityRenderersEvent.AddLayers evt) {
        ModelPart root = evt.getEntityModels().bakeLayer(PlayerAuraModel.LAYER_LOCATION);
        PLAYERAURAMODEL = new PlayerAuraModel(root);
    }



}
