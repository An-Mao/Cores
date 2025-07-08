package dev.anye.mc.cores.amlib.network.easy_net;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.amlib.network.tip$gui.TipGuiNetSTC;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class EasyNetRegister {
    public static final ResourceLocation KEY = new ResourceLocation(Cores.MOD_ID, "easy_net");
    public static final DeferredRegister<EasyNet> EASY_NET = DeferredRegister.create(KEY, Cores.MOD_ID);
    public static final Supplier<IForgeRegistry<EasyNet>> REGISTRY = EASY_NET.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<EasyNet> TipGui = EASY_NET.register("tip_gui", TipGuiNetSTC::new);



    public static void register(IEventBus eventBus){
        EASY_NET.register(eventBus);
    }
}
