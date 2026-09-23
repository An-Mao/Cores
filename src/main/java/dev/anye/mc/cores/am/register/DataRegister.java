package dev.anye.mc.cores.am.register;

import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.data.entity.TheWorld;
import dev.anye.mc.cores.register.Register;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class DataRegister {
	private DataRegister() {
	}

	public static final Register<AttachmentType<?>> ATTACHMENT_TYPE_REGISTER = new Register<>(NeoForgeRegistries.ATTACHMENT_TYPES, Cores.MOD_ID);

	public static final DeferredHolder<AttachmentType<?>, AttachmentType<TheWorld>> THE_WORLD =
			ATTACHMENT_TYPE_REGISTER.register(
					"the_world",
					() -> AttachmentType.builder(() -> new TheWorld()).serialize(TheWorld.MAP_CODEC).build());


	public static void register(IEventBus eventBus) {
		ATTACHMENT_TYPE_REGISTER.register(eventBus);
	}
}
