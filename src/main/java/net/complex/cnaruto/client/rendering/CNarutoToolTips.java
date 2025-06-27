package net.complex.cnaruto.client.rendering;

import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class CNarutoToolTips {

    public static void renderTalentPointsTooltip(GuiGraphics graphics, int x, int y)
    {
        Component whatAreTalentPoints = Component.literal("What are Talent Points?").withStyle(ChatFormatting.BLUE).withStyle(ChatFormatting.BOLD);

        Component explanation1 = Component.literal("Talent Points").withStyle(ChatFormatting.BLUE).append(
                Component.literal(" are expendable points you get when you").withStyle(ChatFormatting.WHITE).append(
                        Component.literal(" level up ").withStyle(ChatFormatting.GOLD).append(
                                Component.literal("as a shinobi!").withStyle(ChatFormatting.WHITE)
                        )
                )
        );

        Component explanation2 = Component.literal("these talent points can be spent on\neither leveling up a main stat,\nor investing in a skill line");
        Component explanation3 = Component.literal("Choose where to spend your points carefully!").withStyle(ChatFormatting.BOLD);
        Component explanation4 = Component.literal(" You only get a limited amount \n of these points as you level up. \n This way, you must pick and choose which talent \n you want to master.");


        graphics.renderComponentTooltip(Minecraft.getInstance().font, List.of(
                whatAreTalentPoints,
                Component.literal("Talent Points").withStyle(ChatFormatting.BLUE).append(
                        Component.literal(" are expendable points")),
                Component.literal("you get when you").withStyle(ChatFormatting.WHITE).append(
                Component.literal(" level up ").withStyle(ChatFormatting.GOLD).append(
                        Component.literal("as a shinobi!").withStyle(ChatFormatting.WHITE)
                    )
                ),
                Component.literal("these talent points can be spent on"),
                Component.literal("either leveling up a main stat or"),
                Component.literal("investing in a skill line"),
                Component.literal("Choose where to spend your points carefully!").withStyle(ChatFormatting.BOLD),
                Component.literal("You only get a limited amount of"),
                Component.literal("these points as you level up."),
                Component.literal("this way, you must pick and choose which"),
                Component.literal("talent you want to master")
        ), x, y);
    }

}
