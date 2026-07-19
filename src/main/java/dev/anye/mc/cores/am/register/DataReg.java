package dev.anye.mc.cores.am.register;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.data.entity.TheWorld;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class DataReg {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPE_DEFERRED_REGISTER = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, Cores.MOD_ID);

    public static final RegistryObject<DataComponentType<TheWorld>> THE_WORLD = DATA_COMPONENT_TYPE_DEFERRED_REGISTER.register("the_world",
            () -> DataComponentType.<TheWorld>builder().persistent(TheWorld.CODEC).build()
    );
    public static void reg(BusGroup eventBus){
        DATA_COMPONENT_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
