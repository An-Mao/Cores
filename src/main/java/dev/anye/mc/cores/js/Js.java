package dev.anye.mc.cores.js;


import net.minecraftforge.fml.ModList;

public class Js {
    public static boolean CanRun = canRunJsCode();
    public static boolean canRunJsCode() {
        return ModList.get().isLoaded("graaljs");
    }
}
