package dev.anye.mc.cores.am.register;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.data.entity.TheWorld;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DataRegister {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPE_DEFERRED_REGISTER = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Cores.MOD_ID);
    public static final DeferredHolder<AttachmentType<?>, AttachmentType<TheWorld>> THE_WORLD =
            ATTACHMENT_TYPE_DEFERRED_REGISTER.register(
                    "the_world",
                    () -> AttachmentType.builder(() -> new TheWorld()).serialize(TheWorld.CODEC).build());


    public static void register(IEventBus eventBus){
        ATTACHMENT_TYPE_DEFERRED_REGISTER.register(eventBus);
    }
}
