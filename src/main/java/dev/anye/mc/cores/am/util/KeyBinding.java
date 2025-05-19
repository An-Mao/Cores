package dev.anye.mc.cores.am.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.anye.mc.cores.Cores;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
@OnlyIn(Dist.CLIENT)
public class KeyBinding {
    public static final String KEY_CATEGORY = "key.category."+Cores.MOD_ID+".mc";
    public static final String KEY_MENU = "key."+ Cores.MOD_ID +".open_menu";

    public static final KeyMapping OPEN_MENU = new KeyMapping(KEY_MENU, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_HOME, KEY_CATEGORY);
}
