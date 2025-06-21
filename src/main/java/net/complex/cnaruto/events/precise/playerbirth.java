package net.complex.cnaruto.events.precise;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;


import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.swing.text.AttributeSet;
import javax.swing.text.Style;
import java.util.Random;

@Mod.EventBusSubscriber(modid = CNaruto.MODID)
public class playerbirth {


    @SubscribeEvent
    public static void EntityJoinWorldEvent(EntityJoinLevelEvent Event)
    {
        if (Event.getEntity() instanceof ServerPlayer)
        {
            ServerPlayer player = (ServerPlayer) Event.getEntity();

            player.getCapability(PlayerLevelStatsProvider.PLAYER_LEVELSTATS).ifPresent((store) ->
            {
                if (store.ElementReleases.isEmpty())
                {

                    Random random = new Random();

                    int selectedElement = Math.abs(random.nextInt() % 5);

                    player.sendSystemMessage(Component.literal(Integer.toString(selectedElement)));

                    switch (selectedElement)
                    {
                        case 0:
                            store.AddSkillLineToPlayer(SkillLineRegister.FIRE_RELEASE);
                            break;
                        case 1:
                            store.AddSkillLineToPlayer(SkillLineRegister.WATER_RELEASE);
                            break;
                        case 2:
                            store.AddSkillLineToPlayer(SkillLineRegister.WIND_RELEASE);
                            break;
                        case 3:
                            store.AddSkillLineToPlayer(SkillLineRegister.LIGHTNING_RELEASE);
                            break;
                        case 4:
                            store.AddSkillLineToPlayer(SkillLineRegister.EARTH_RELEASE);
                            break;

                    }


                }
            });

            CompoundTag Tag = new CompoundTag();
            PlayerLevelStatsProvider.get(player).saveDataNBT(Tag);

            ModMessages.sendToPlayer(new PlayerLevelStatsSyncS2CPacket(Tag), player);
        }
    }

}
