package net.complex.cnaruto.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.CommandNode;
import net.complex.cnaruto.Data.PlayerLevelStats;
import net.complex.cnaruto.Data.PlayerLevelStatsProvider;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.command.Argument.SkillLineCommandArgument;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.PlayerLevelStatsSyncS2CPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.RegistryObject;

public class PointCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher)
    {
//        CommandNode<CommandSourceStack> node = dispatcher.register(Commands.literal("skill").requires((commandSource) -> commandSource.hasPermission(2))
//                .then(Commands.argument("add/remove", StringArgumentType.string()).then(
//                        Commands.argument("player", EntityArgument.player()).then(
//                                Commands.argument("skillline", SkillLineCommandArgument.skillLineArgument())
//                                        .executes( ctx -> {
//                                            if (StringArgumentType.getString(ctx, "add/remove").equals("add"))
//                                            {
//                                                return addSkill(EntityArgument.getPlayer(ctx, "player"), SkillLineCommandArgument.getSkillLine(ctx, "skillline"));
//                                            } else if (StringArgumentType.getString(ctx, "add/remove").equals("remove"))
//                                            {
//                                                return removeSkill(EntityArgument.getPlayer(ctx, "player"), SkillLineCommandArgument.getSkillLine(ctx, "skillline"));
//                                            }
//                                            return 0;
//
//                                        })
//                        ))));
//        dispatcher.register(Commands.literal("cnarutoskill").requires((ctx) -> ctx.hasPermission(2)).redirect(node));


        CommandNode<CommandSourceStack> node = dispatcher.register(
                Commands.literal("point").requires((commandSource) -> commandSource.hasPermission(2))
                        .then(Commands.argument("add/set", StringArgumentType.string()).then(
                                Commands.argument("amount", IntegerArgumentType.integer(0, 999)).then(
                                        Commands.argument("player", EntityArgument.player()).executes(
                                                ctx -> {
                                                    if (StringArgumentType.getString(ctx, "add/set").equals("add"))
                                                    {
                                                        return AddPoints(EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount"));
                                                    } else if (StringArgumentType.getString(ctx, "add/set").equals("set"))
                                                    {
                                                        return SetPoints(EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount"));
                                                    }
                                                    return 0;
                                                }

                                        )
                                )
                        ))
        );

        dispatcher.register(Commands.literal("cnarutopoints").requires((commandSource) -> commandSource.hasPermission(2)).redirect(node));

    }

   public static int AddPoints(Player player, int points)
   {
       PlayerLevelStats playerLevelStats = PlayerLevelStatsProvider.get(player);
       playerLevelStats.AddPoints(points);

       if (player instanceof ServerPlayer)
       {
           CompoundTag Tag = new CompoundTag();
           PlayerLevelStats Stats = PlayerLevelStatsProvider.get(player);
           Stats.saveDataNBT(Tag);

           ModMessages.sendToPlayer(new PlayerLevelStatsSyncS2CPacket(Tag), (ServerPlayer) player);
       }

       player.sendSystemMessage(Component.literal("Added ").withStyle(ChatFormatting.GREEN).append(
               Component.literal(String.valueOf(points)).withStyle(ChatFormatting.GOLD).append(
                       Component.literal(" Points to ").withStyle(ChatFormatting.GREEN).append(
                               (player.getDisplayName())
                       )
               ))
       );

       return 1;

   }

   public static int SetPoints(Player player, int points)
   {
       PlayerLevelStats playerLevelStats = PlayerLevelStatsProvider.get(player);
       playerLevelStats.SetPoints(points);

       if (player instanceof ServerPlayer)
       {
           CompoundTag Tag = new CompoundTag();
           PlayerLevelStats Stats = PlayerLevelStatsProvider.get(player);
           Stats.saveDataNBT(Tag);

           ModMessages.sendToPlayer(new PlayerLevelStatsSyncS2CPacket(Tag), (ServerPlayer) player);
       }

       player.sendSystemMessage(Component.literal("Set points to ").withStyle(ChatFormatting.GREEN).append(
               Component.literal(String.valueOf(points)).withStyle(ChatFormatting.GOLD).append(
                       Component.literal(" for player ").withStyle(ChatFormatting.GREEN).append(
                               (player.getDisplayName())
                       )
               ))
       );

       return 1;
   }


}
