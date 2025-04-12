package net.complex.cnaruto.client.keybinds;

import com.mojang.blaze3d.platform.InputConstants;
import net.complex.cnaruto.CNaruto;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import javax.swing.text.Keymap;

public class Keybindings {

    public static final Keybindings NARUTO_KEYBINDINGS = new Keybindings();

    private Keybindings() {}

    private static final String NARUTO_CATEGORY = "key.categories." + CNaruto.MODID;

    public final KeyMapping DataScrollKey = new KeyMapping(
            "key." + CNaruto.MODID + ".datascroll_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_P, -1),
            NARUTO_CATEGORY
    );

    public final KeyMapping JutsuBarKey = new KeyMapping(
            "key." + CNaruto.MODID + ".jutsubar_key",
            KeyConflictContext.IN_GAME,
            InputConstants.getKey(InputConstants.KEY_R, -1),
            NARUTO_CATEGORY
    );



}
