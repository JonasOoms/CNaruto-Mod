package net.complex.cnaruto.systems.JutsuSystem;

import net.complex.cnaruto.Data.ChakraManager;
import net.complex.cnaruto.Data.ChakraManagerProvider;
import net.complex.cnaruto.Data.EquippedJutsu;
import net.complex.cnaruto.Data.EquippedJutsuProvider;
import net.complex.cnaruto.Jutsu.Jutsu;
import net.complex.cnaruto.Jutsu.JutsuResourceRequirements.IJutsuResourceRequirement;
import net.complex.cnaruto.Jutsu.JutsuInstance;
import net.complex.cnaruto.Jutsu.JutsuTask.JutsuCastData;
import net.complex.cnaruto.Jutsu.JutsuTask.TaskResult;
import net.complex.cnaruto.networking.ModMessages;
import net.complex.cnaruto.networking.packet.s2c.ChakraManagerSyncWithClientS2CPacket;
import net.complex.cnaruto.networking.packet.s2c.EquippedJutsuSyncWithClientS2CPacket;
import net.complex.cnaruto.sounds.ModSounds;
import net.complex.cnaruto.systems.Subsystem;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/// Receives jutsu activation signals from players, validates them, and schedules their JutsuTask
public class JutsuSchedulerSubSystem extends Subsystem
{

    List<Jutsu.JutsuTask> taskList = new ArrayList<>();

    public void PushJutsuInstance(JutsuInstance instance, @NotNull ServerPlayer player) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (player.isAlive())
        {
            // add an instance of the jutsus task to the taskList if everything is valid (cooldown and jutsu requirements)

            if (instance != null)
            {
                if (!instance.HasCooldown()) // might seem like redundant check, but this is to prevent cheating
                {
                    Jutsu jutsu = instance.jutsu;
                    if (instance.jutsu.CastRequirements(player))
                    {

                        player.level().playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.JUTSU_ACTIVATION.get(), SoundSource.PLAYERS, 1.f, 1.f);

                        // Create the task to be scheduled after resource transaction
                        JutsuCastData data = new JutsuCastData(player, player.position());
                        Jutsu.JutsuTask task = jutsu.CreateTask(data);



                        // take resources from player
                        for (IJutsuResourceRequirement requirement : jutsu.GetJutsuResourceRequirements())
                        {
                            requirement.SubtractResources(player);
                        }

                        instance.Cast();

                        ChakraManager playerChakra = ChakraManagerProvider.get(player);
                        EquippedJutsu playerJutsu = EquippedJutsuProvider.get(player);

                        // after resources are taken, sync new resources with client
                        CompoundTag syncDataChakra = new CompoundTag();
                        playerChakra.serializeNBT(syncDataChakra);
                        ModMessages.sendToPlayer(new ChakraManagerSyncWithClientS2CPacket(syncDataChakra), player);

                        CompoundTag syncDataEquippedJutsu = new CompoundTag();
                        playerJutsu.serializeNBT(syncDataEquippedJutsu);
                        ModMessages.sendToPlayer(new EquippedJutsuSyncWithClientS2CPacket(syncDataEquippedJutsu), player);

                        // Schedule task
                        PushTask(task);


                    }
                }
            }


        }
    }

    private void PushTask(Jutsu.JutsuTask task)
    {
        taskList.add(task);
        task.OnSchedule();
    }

    private void UnSchedule(Jutsu.JutsuTask task)
    {
        if (taskList.contains(task))
        {
            task.UnSchedule();

        }
    }

    @SubscribeEvent
    public void ServerTick(TickEvent.ServerTickEvent event)
    {
        List<Jutsu.JutsuTask> toRemove = new ArrayList<>();
        for (Jutsu.JutsuTask task : taskList)
        {
            TaskResult result = task.Tick();
            if (result == TaskResult.COMPLETED || result == TaskResult.FAILED)
            {
                UnSchedule(task);
                toRemove.add(task);
            }
        }
        taskList.removeAll(toRemove);

    }


    @Override
    public void Register(IEventBus eventBus) {
        eventBus.register(this);
        System.out.println("[CNARUTO]: JutsuSchedulerSubsystem was loaded!");
    }
}
