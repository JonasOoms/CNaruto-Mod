package net.complex.cnaruto.Jutsu.JutsuUnlockRequirements;

import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;

public class SkillLineLevelUnlockRequirement implements IJutsuRequirement{

    private final SkillLine skillLine;
    private final int level;

    public SkillLineLevelUnlockRequirement(SkillLine skillLine, int level) {
        this.skillLine = skillLine;
        this.level = level;
    }

    @Override
    public boolean CanUnlock(Player player) {
        PlayerLevelStats stats = PlayerLevelStatsProvider.get(player);
        RegistryObject<SkillLine> skillLineRegistryObject = CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, this.skillLine);
        if (stats.PlayerHasSkillLine(skillLineRegistryObject))
        {
            return stats.GetPlayerLevelWithSkillLine(skillLineRegistryObject) >= this.level;
        }
        return false;
    }

    @Override
    public Component GetDisplay(Player player) {

        Component skillLineNameComponent = Component.literal(this.skillLine.GetName()).withStyle(ChatFormatting.BLUE);
        Component levelComponent = Component.literal(String.valueOf(this.level)).withStyle(ChatFormatting.GOLD);

        return Component.literal("Required " + this.skillLine.GetName() + " Level: " + this.level)
                .withStyle(CanUnlock(player) ? ChatFormatting.GREEN : ChatFormatting.RED)
                .withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("To learn this jutsu, it is required for your ").append(skillLineNameComponent).append(Component.literal(" to be level ").append(levelComponent)))));
    }
}
