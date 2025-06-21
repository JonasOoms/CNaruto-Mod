package net.complex.cnaruto.Jutsu;

import com.google.common.collect.ImmutableList;
import net.complex.cnaruto.Jutsu.JutsuResourceRequirements.IJutsuResourceRequirement;
import net.complex.cnaruto.Jutsu.JutsuTask.JutsuCastData;
import net.complex.cnaruto.Jutsu.JutsuTask.TaskResult;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.IJutsuRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Supplier;

/// TODO: rework with a builder with JutsuProperties
public abstract class Jutsu {

    public class JutsuTask
    {

        private final JutsuCastData data;

        public JutsuTask(JutsuCastData data) {
            this.data = data;
        }

        public void OnSchedule() {};
        public TaskResult Tick() { return TaskResult.COMPLETED;}; // TODO: cascade event down
        public void UnSchedule() {};

        public JutsuCastData getData() {return data;}

    }


    boolean isRegistered = false;
   private final JutsuProperties jutsuProperties;

    public Jutsu(Supplier<JutsuProperties> props)
    {
        jutsuProperties = props.get();
    }

    public final boolean Cast(Player player) {
        if (CastRequirements(player))
        {
            Use(player);
            return true;
        }
        return false;
    }

    protected abstract void Use(Player player);

    public final JutsuTask CreateTask(JutsuCastData data) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return createTask(data);
    }

    public abstract JutsuTask createTask(JutsuCastData data);

    public final boolean GetHiddenIfNotUnlocked()
    {
        return jutsuProperties.isHiddenIfNotUnlocked();
    }

    public final String GetDisplayName()
    {
        return jutsuProperties.getDisplayName();
    }

    public final String GetDescription()
    {
        return jutsuProperties.getDescription();
    }

    public final int GetHandSignRequirement() {return jutsuProperties.getRequiredHandsigns();}

    public float GetCooldown() {return jutsuProperties.getCooldown();}

    public final ResourceLocation GetIcon()
    {
        return jutsuProperties.getIcon();
    }

    public ImmutableList<IJutsuRequirement> GetJutsuRequirements() {
        return ImmutableList.copyOf(this.jutsuProperties.getJutsuRequirements());
    }

    public ImmutableList<IJutsuResourceRequirement> GetJutsuResourceRequirements() {
        return ImmutableList.copyOf(this.jutsuProperties.getJutsuResourceRequirements());
    }
    // Check if a player has enough to unlock this jutsu in the skill line menu
    public final boolean UnlockRequirements(Player player)
    {
        boolean unlocked = true;
        for (int i = 0; i < jutsuProperties.getJutsuRequirements().size(); i++)
        {
            IJutsuRequirement requirement = jutsuProperties.getJutsuRequirements().get(i);
            if (!requirement.CanUnlock(player))
            {
                unlocked = false;
            }
        }
        return unlocked;
    }

    public final boolean CastRequirements(Player player)
    {
        boolean unlocked = true;
        for (int i = 0; i < this.jutsuProperties.getJutsuResourceRequirements().size(); i++)
        {
            IJutsuResourceRequirement requirement = this.jutsuProperties.getJutsuResourceRequirements().get(i);
            if (!requirement.CanUse(player))
            {
                unlocked = false;
            }
        }
        return unlocked;
    }

    public final RegistryObject<Jutsu> Register(String id, DeferredRegister<Jutsu> JutsuRegister)
    {
        if (!isRegistered)
        {
            this.jutsuProperties.getBelongsToSkillLine().AddJutsu(id, this);
            RegistryObject<Jutsu> RegisteredJutsu = JutsuRegister.register(id, () -> this);
            this.isRegistered = true;
            return RegisteredJutsu;
        }

        return null;
    }


}
