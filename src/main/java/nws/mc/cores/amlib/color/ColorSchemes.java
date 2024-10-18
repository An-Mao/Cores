package nws.mc.cores.amlib.color;

import net.minecraftforge.registries.RegistryObject;
import nws.dev.core.color.scheme._ColorScheme;

public class ColorSchemes{
    private static _ColorScheme global = ColorSchemeRegister.DEFAULT.get();
    public static _ColorScheme getGlobal() {
        return global;
    }


    public static void setGlobal(_ColorScheme colorScheme) {
        global = colorScheme;
    }


    public static void setGlobal(RegistryObject<_ColorScheme> colorScheme) {
        if (colorScheme == null){
            setGlobal(ColorSchemeRegister.DEFAULT.get());
        }else {
            setGlobal(colorScheme.get());
        }
    }
}
