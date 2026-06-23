package dev.anye.mc.cores.register;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Register<T> {
	private final Identifier key;
	private final ResourceKey<? extends Registry<T>> resourceKey;
	private final Registry<T> registry;
	private final DeferredRegister<T> deferredRegister;

	/**
	 * Usually used to create a new registry, as opposed to using an existing one
	 * 通常用于创建新的注册表，区别于使用已存在的
	 *
	 * @param key         Registry key.注册表的键
	 * @param resourceKey
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
	 *
	 * @param registry
	 * @param modid
	 */
	public Register(Registry<T> registry, String modid) {
		this.key = registry.key().identifier();
		this.resourceKey = registry.key();
		this.registry = registry;
		this.deferredRegister = DeferredRegister.create(registry, modid);
	}

	public Identifier getRegisterKey() {
		return key;
	}

	public ResourceKey<? extends Registry<T>> getResourceKey() {
		return resourceKey;
	}

	public Registry<T> getRegistry() {
		return registry;
	}

	public DeferredRegister<T> getDeferredRegister() {
		return deferredRegister;
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
}
