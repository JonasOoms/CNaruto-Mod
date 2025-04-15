package net.complex.cnaruto.networking.packet.c2s;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class IncreaseSkillLineLevelC2SPacket
{

    public static CompoundTag CreatePacketCompatibleTag(SkillLineData skillLineData, int levelToIncrease)
    {
        CompoundTag tag = new CompoundTag();
        tag.putInt("level", levelToIncrease);
        tag.putString("id", skillLineData.GetId());
        return tag;
    }

    private CompoundTag nbt;

    IncreaseSkillLineLevelC2SPacket(CompoundTag tag)
    {
        this.nbt = tag;
    }

    public IncreaseSkillLineLevelC2SPacket(SkillLineData skillLineData, int levelToIncrease)
    {
        this(CreatePacketCompatibleTag(skillLineData, levelToIncrease));
    }

    IncreaseSkillLineLevelC2SPacket(FriendlyByteBuf buf)
    {
        this(buf.readNbt());
    }

    public void toBytes(FriendlyByteBuf buf)
    {
        buf.writeNbt(nbt);
    }

    public boolean handle(Supplier<NetworkEvent.Context> Supplier) {
        NetworkEvent.Context ctx = Supplier.get();
        ctx.enqueueWork(() ->
        {

            ServerPlayer player = ctx.getSender();

            if (player != null) {
                PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);

                if (stats.GetPoints() - 1 >= 0) {
                    RegistryObject<SkillLine> object = CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, new ResourceLocation(CNaruto.MODID, nbt.getString("id")));
                    SkillLineData playerSkillLineData = stats.GetPlayerSkillLineDataObject(object);
                    if (playerSkillLineData.GetLevel() + 1 <= object.get().MaxLevel) {
                        playerSkillLineData.SetLevel(playerSkillLineData.GetLevel() + 1);
                        stats.SetPoints(stats.GetPoints() - 1);
                        CompoundTag syncTag = new CompoundTag();
                        stats.saveDataNBT(syncTag);
                        ModMessages.sendToPlayer(new PlayerLevelStatsSyncS2CPacket(syncTag), player);
                    }
                }
            }


        });
        return true;
    }

}
