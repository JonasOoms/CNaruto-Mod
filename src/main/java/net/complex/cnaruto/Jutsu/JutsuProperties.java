package net.complex.cnaruto.Jutsu;

import net.complex.cnaruto.Jutsu.JutsuResourceRequirements.IJutsuResourceRequirement;
import net.complex.cnaruto.Jutsu.JutsuUnlockRequirements.IJutsuRequirement;
import net.complex.cnaruto.SkillLines.SkillLine;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Collections;

public class JutsuProperties {
    private String displayName = "A jutsu";
    private String description = "You forgot to set a description!";
    private ResourceLocation icon;
    private Boolean hiddenIfNotUnlocked;
    private SkillLine belongsToSkillLine;
    private int requiredHandsigns = 3;

    private ArrayList<IJutsuRequirement> jutsuRequirements = new ArrayList<>();
    private ArrayList<IJutsuResourceRequirement> jutsuResourceRequirements = new ArrayList<>();
    protected int cooldown = 1000;

    private JutsuProperties(Builder builder) {
        this.displayName = builder.displayName;
        this.description = builder.description;
        this.icon = builder.icon;
        this.hiddenIfNotUnlocked = builder.hiddenIfNotUnlocked;
        this.belongsToSkillLine = builder.belongsToSkillLine;
        this.requiredHandsigns = builder.requiredHandsigns;
        this.jutsuRequirements = (builder.jutsuRequirements);
        this.jutsuResourceRequirements = (builder.jutsuResourceRequirements);
        this.cooldown = builder.cooldown;
    }

    public static Builder builder(SkillLine skillLine) {
        return new Builder(skillLine);
    }

    // Getters
    public String getDisplayName() { return displayName; }
    public String getDescription() { return description; }
    public ResourceLocation getIcon() { return icon; }
    public boolean isHiddenIfNotUnlocked() { return hiddenIfNotUnlocked; }
    public SkillLine getBelongsToSkillLine() { return belongsToSkillLine; }
    public int getRequiredHandsigns() { return requiredHandsigns; }
    public ArrayList<IJutsuRequirement> getJutsuRequirements() { return jutsuRequirements; }
    public ArrayList<IJutsuResourceRequirement> getJutsuResourceRequirements() { return jutsuResourceRequirements; }
    public int getCooldown() { return cooldown; }


    public static class Builder {
        private String displayName = "A jutsu";
        private String description = "You forgot to set a description!";
        private ResourceLocation icon;
        private boolean hiddenIfNotUnlocked = false;
        private final SkillLine belongsToSkillLine;
        private int requiredHandsigns = 3;
        private ArrayList<IJutsuRequirement> jutsuRequirements = new ArrayList<>();
        private ArrayList<IJutsuResourceRequirement> jutsuResourceRequirements = new ArrayList<>();
        private int cooldown = 1000;


        public Builder(SkillLine skillLine) {
            if (skillLine == null) {
                throw new IllegalArgumentException("SkillLine must not be null");
            }
            this.belongsToSkillLine = skillLine;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder icon(ResourceLocation icon) {
            this.icon = icon;
            return this;
        }

        public Builder hiddenIfNotUnlocked(boolean hidden) {
            this.hiddenIfNotUnlocked = hidden;
            return this;
        }

        public Builder requiredHandsigns(int handsigns) {
            this.requiredHandsigns = handsigns;
            return this;
        }

        public Builder addRequirement(IJutsuRequirement requirement) {
            this.jutsuRequirements.add(requirement);
            return this;
        }

        public Builder addResourceRequirement(IJutsuResourceRequirement requirement) {
            this.jutsuResourceRequirements.add(requirement);
            return this;
        }

        public Builder cooldown(int cooldown) {
            this.cooldown = cooldown;
            return this;
        }

        public JutsuProperties build() {
            if (icon == null) {
                throw new IllegalStateException("Icon must be set");
            }
            return new JutsuProperties(this);
        }
    }
}

