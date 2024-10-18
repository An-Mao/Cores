package nws.mc.cores.amlib.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nws.mc.cores.Cores;
import nws.mc.cores.amlib.util.KeyBinding;
import nws.mc.cores.screen.SettingScreen;

@Mod.EventBusSubscriber(modid = Cores.MOD_ID, value = Dist.CLIENT)
public class ForgeClientEvent {


    public static void onKeyInpu(InputEvent.Key event) {
        /*
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            if (player.isAlive()) {
                System.out.println("event.getAction() ::" + event.getAction());
                if (KeyBinding.OPEN_MENU.consumeClick()) {
                    Minecraft.getInstance().setScreen(new TestScreen());
                }
            }
        }

        System.out.println("serverData:"+Minecraft.getInstance().isLocalServer());
        ServerData serverData = Minecraft.getInstance().getCurrentServer();
        if (serverData == null){
            System.out.println("serverData"+Minecraft.getInstance().getSingleplayerServer());
            return;
        }
        String ip = serverData.ip;
        System.out.println("serverData:" + ip);

         */
    }
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (event.getKey() == KeyBinding.OPEN_MENU.getKey().getValue()) {
            Screen screen = Minecraft.getInstance().screen;
            if (event.getAction() == 1) {
                if (screen == null) {
                    Minecraft.getInstance().setScreen(new SettingScreen());
                }
            }
        }

    }
}
