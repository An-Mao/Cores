package dev.anye.mc.cores.register;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.slf4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Register<T> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final Identifier key;
	private final ResourceKey<? extends Registry<T>> resourceKey;
	private final Registry<T> registry;
	private final DeferredRegister<T> deferredRegister;

	/**
	 * Usually used to create a new registry, as opposed to using an existing one
	 * 通常用于创建新的注册表，区别于使用已存在的
	 * @param key         Registry key.注册表的键
	 * @param resourceKey resourceKey
	 * @param consumer    For custom registration build.用于自定义注册构建
	 */
	public Register(Identifier key, ResourceKey<Registry<T>> resourceKey, Consumer<RegistryBuilder<T>> consumer) {
		this.key = key;
		this.resourceKey = resourceKey;
		RegistryBuilder<T> builder = new RegistryBuilder<>(resourceKey);
		consumer.accept(builder);
		this.registry = builder.create();
		this.deferredRegister = DeferredRegister.create(registry, key.getNamespace());
	}

	public Register(String modid, String key, Consumer<RegistryBuilder<T>> consumer) {
		this(Identifier.fromNamespaceAndPath(modid, key), consumer);
	}

	public Register(Identifier key, Consumer<RegistryBuilder<T>> consumer) {
		this(key, ResourceKey.createRegistryKey(key), consumer);
	}

	/**
	 * Use an existing registry to register
	 * 使用已存在的注册表进行注册
	 * @param registry registry
	 * @param modid modid
	 */
	public Register(Registry<T> registry, String modid) {
		this.key = registry.key().identifier();
		this.resourceKey = registry.key();
		this.registry = registry;
		this.deferredRegister = DeferredRegister.create(registry, modid);
	}

	@Deprecated(since = "2.0.5")
	@SuppressWarnings("unchecked")
	public Register(ResourceKey<? extends Registry<T>> resourceKey, String modid) {
		this.key = resourceKey.identifier();
		this.resourceKey = resourceKey;
		Registry<T> r;
		try {
			r = (Registry<T>) BuiltInRegistries.REGISTRY.getValue(this.key);
		} catch (RuntimeException _) {
			r = null;
		}
		if (r != null) this.registry = r;
		else this.registry = new RegistryBuilder<>(resourceKey).create();
		this.deferredRegister = DeferredRegister.create(this.registry, modid);
	}


	public Identifier registerKey() {
		return key;
	}

	@Deprecated(since = "2.0.5")
	public Identifier getRegisterKey() {
		return registerKey();
	}

	public ResourceKey<? extends Registry<T>> resourceKey() {
		return resourceKey;
	}

	@Deprecated(since = "2.0.5")
	public ResourceKey<? extends Registry<T>> getResourceKey() {
		return resourceKey();
	}

	public Registry<T> registry() {
		return registry;
	}
	@Deprecated(since = "2.0.5")
	public Registry<T> getRegistry() {
		return registry();
	}
	public DeferredRegister<T> deferredRegister() {
		return deferredRegister;
	}
	@Deprecated(since = "2.0.5")
	public DeferredRegister<T> getDeferredRegister() {
		return deferredRegister();
	}


	public void register(IEventBus eventBus) {
		deferredRegister.register(eventBus);
	}
	public <I extends T> DeferredHolder<T, I> register(String name, Supplier<? extends I> sup) {
		return deferredRegister.register(name, sup);
	}

	public <I extends T> Identifier getMemberKey(I member) {
		return registry.getKey(member);
	}
	public T getMemberValue(Identifier member) {
		return registry.getValue(member);
	}


	public void foreach(Consumer<? super T> action){
		registry.forEach(action);
	}
}
