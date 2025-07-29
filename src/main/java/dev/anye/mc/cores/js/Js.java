package dev.anye.mc.cores.js;

import net.neoforged.fml.ModList;

public class Js {
    public static boolean CanRun = canRunJsCode();

    public static boolean canRunJsCode() {
        return ModList.get().isLoaded("graaljs");
    }
}
