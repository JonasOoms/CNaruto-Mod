package net.complex.cnaruto.mixin;

import net.complex.cnaruto.sounds.ModSounds;
import net.minecraft.Optionull;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.core.Holder;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Minecraft.class)
public class MenuMusicMixin {

    @Nullable
    @Shadow public LocalPlayer player;

    @Nullable
    @Shadow public Screen screen;

    @Final @Shadow public Gui gui;

    @Final @Shadow private MusicManager musicManager;

    /**
     * @author GodCompleks
     * @reason replacing the main menu music with a more fitting, naruto themed music
     */
    @Overwrite
    public Music getSituationalMusic() {

        Music menuMusic = new Music(ModSounds.MENU_MUSIC.getHolder().get(), 20, 600, true);

        Music music = (Music) Optionull.map(screen, Screen::getBackgroundMusic);
        if (music != null) {
            return music;
        } else if (player != null) {
            if (player.level().dimension() == Level.END) {
                return gui.getBossOverlay().shouldPlayMusic() ? Musics.END_BOSS : Musics.END;
            } else {
                Holder<Biome> holder = player.level().getBiome(player.blockPosition());
                if (!musicManager.isPlayingMusic(Musics.UNDER_WATER) && (!player.isUnderWater() || !holder.is(BiomeTags.PLAYS_UNDERWATER_MUSIC))) {
                    return player.level().dimension() != Level.NETHER && player.getAbilities().instabuild && player.getAbilities().mayfly ? Musics.CREATIVE : (Music)((Biome)holder.value()).getBackgroundMusic().orElse(Musics.GAME);
                } else {
                    return Musics.UNDER_WATER;
                }
            }
        } else {
            return menuMusic;
        }
    }

}
