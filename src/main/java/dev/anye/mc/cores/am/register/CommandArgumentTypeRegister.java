package dev.anye.mc.cores.am.register;

import com.mojang.brigadier.arguments.ArgumentType;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.listen.ListenArgument;
import dev.anye.mc.cores.register.Register;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class CommandArgumentTypeRegister {
	public static final Register<ArgumentTypeInfo<?,?>> ARGUMENT_TYPE_INFO_REGISTER = new Register<>(BuiltInRegistries.COMMAND_ARGUMENT_TYPE, Cores.MOD_ID);
	public static final DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<ListenArgument>> LISTEN = register("listen", ListenArgument.class,ListenArgument::listen);


	public static <A extends ArgumentType<?>> DeferredHolder<ArgumentTypeInfo<?, ?>, SingletonArgumentInfo<A>> register(String name, Class<A> clazz, Supplier<A> constructor){
		return ARGUMENT_TYPE_INFO_REGISTER.register(name,()->
				ArgumentTypeInfos.registerByClass(clazz,
						SingletonArgumentInfo.contextFree(constructor)));
	}

	public static void register(IEventBus eventBus) {
		ARGUMENT_TYPE_INFO_REGISTER.register(eventBus);
	}
}
