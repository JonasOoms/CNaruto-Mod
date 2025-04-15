package net.complex.cnaruto.client.gui.widgets.OnPress;

import net.complex.cnaruto.CNaruto;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineData.SkillLineData;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.c2s.IncreaseSkillLineLevelC2SPacket;
import net.minecraft.client.gui.components.Button;
import net.minecraft.world.entity.player.Player;

public class SkillLineLevelIncreaseOnPress implements Button.OnPress {

    private final SkillLineData skillLineData;
    private final int level;
    private final Player player;

    public SkillLineLevelIncreaseOnPress(SkillLineData data, int levelToIncreaseBy, Player player)
    {
        this.skillLineData = data;
        this.level = levelToIncreaseBy;
        this.player = player;
    }

    @Override
    public void onPress(Button button) {
        PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);
        if (stats.GetPoints() - 1 >= 0)
        {
            ModMessages.SendToServer(new IncreaseSkillLineLevelC2SPacket(skillLineData, 1));
        }
    }
}
