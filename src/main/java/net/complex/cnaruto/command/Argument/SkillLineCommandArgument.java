package net.complex.cnaruto.command.Argument;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.complex.cnaruto.SkillLines.SkillLineRegister;
import net.complex.cnaruto.api.CUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class SkillLineCommandArgument implements ArgumentType<ResourceLocation> {


    public static SkillLineCommandArgument skillLineArgument()
    {
        return new SkillLineCommandArgument();
    }

    public static RegistryObject<SkillLine> getSkillLine(CommandContext<CommandSourceStack> pContext, String pArgument) throws CommandSyntaxException
    {
        ResourceLocation Key = pContext.getArgument(pArgument, ResourceLocation.class);
        return CUtils.FindAndReturnFromRegistry(SkillLineRegister.SKILL_LINE_REGISTER, Key);
    }

    @Override
    public ResourceLocation parse(StringReader stringReader) throws CommandSyntaxException {
        return ResourceLocation.read(stringReader);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Object obj = context.getSource();

        Set<ResourceLocation> KeySet = new HashSet<>();

        SkillLineRegister.SKILL_LINE_REGISTER.getEntries().forEach((RegistryObject)-> {
            KeySet.add(RegistryObject.getId());
        });


        if (obj instanceof SharedSuggestionProvider) {
            SharedSuggestionProvider.suggestResource(KeySet, builder);
        }
        return builder.buildFuture();
    }
}
