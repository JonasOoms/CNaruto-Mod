package net.complex.cnaruto.Jutsu;

import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.IJutsuRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

public class Jutsu {

    boolean isRegistered = false;

    private final String displayName;
    private final String description;
    private final ResourceLocation icon;
    private final Boolean hiddenIfNotUnlocked;
    private final SkillLine BelongsToSkillLine;

    public ArrayList<IJutsuRequirement> jutsuRequirements = new ArrayList<>();
    protected int resourceCost = 1;
    protected int cooldown = 20; // is in milliseconds

    public Jutsu(String displayName, String description ,ResourceLocation Icon, boolean hiddenIfNotUnlocked ,SkillLine SkillLine )
    {
        this.displayName = displayName;
        this.description = description;
        this.icon = Icon;
        this.hiddenIfNotUnlocked = hiddenIfNotUnlocked;
        this.BelongsToSkillLine = SkillLine;
    }

    public boolean Use(Player player) {
        if (CastRequirements(player))
        {

        }
        return false;
    }

    public boolean GetHiddenIfNotUnlocked()
    {
        return hiddenIfNotUnlocked;
    }

    public String GetDisplayName()
    {
        return this.displayName;
    }

    public String GetDescription()
    {
        return this.description;
    }

    public void AddJutsuRequirement(IJutsuRequirement requirement)
    {
        jutsuRequirements.add(requirement);
    }

    public ResourceLocation GetIcon()
    {
        return this.icon;
    }

    // Check if a player has enough to unlock this jutsu in the skill line menu
    public boolean UnlockRequirements(Player player)
    {
        boolean unlocked = true;
        for (int i = 0; i < this.jutsuRequirements.size(); i++)
        {
            IJutsuRequirement requirement = this.jutsuRequirements.get(i);
            if (!requirement.CanUnlock(player))
            {
                unlocked = false;
            }
        }
        return unlocked;
    }

    // Check if player has enough to cast! Personal implementation
    public boolean CastRequirements(Player player)
    {

        return false;
    }

    public RegistryObject<Jutsu> Register(String id, DeferredRegister<Jutsu> JutsuRegister)
    {
        if (!isRegistered)
        {
            BelongsToSkillLine.AddJutsu(id, this);
            RegistryObject<Jutsu> RegisteredJutsu = JutsuRegister.register(id, () -> this);
            this.isRegistered = true;
            return RegisteredJutsu;
        }

        return null;
    }


}
