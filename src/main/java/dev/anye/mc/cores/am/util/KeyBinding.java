package dev.anye.mc.cores.am.util;

import com.mojang.blaze3d.platform.InputConstants;
import dev.anye.mc.cores.Cores;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;
@OnlyIn(Dist.CLIENT)
public class KeyBinding {
    public static final ResourceLocation KEY_CATEGORY_RES=ResourceLocation.fromNamespaceAndPath(Cores.MOD_ID,"cores");
    public static final String KEY_CATEGORY = "key.category."+Cores.MOD_ID+".mc";
    public static final String KEY_MENU = "key."+ Cores.MOD_ID +".open_menu";

    public static final KeyMapping OPEN_MENU = new KeyMapping(KEY_MENU, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_HOME,new KeyMapping.Category(KEY_CATEGORY_RES));
}
