package net.complex.cnaruto.command.Argument;

import net.complex.cnaruto.CNaruto;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

public class CNarutoCommandsArguments {

    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> COMMAND_ARGUMENT_TYPES = DeferredRegister.create(ForgeRegistries.COMMAND_ARGUMENT_TYPES, CNaruto.MODID);

    public static final RegistryObject<ArgumentTypeInfo<?,?>> SKILLARGUMENT = COMMAND_ARGUMENT_TYPES.register("skill_argument", () ->
            ArgumentTypeInfos.registerByClass(SkillLineCommandArgument.class, SingletonArgumentInfo.contextFree(SkillLineCommandArgument::skillLineArgument)));


}
