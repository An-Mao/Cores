package dev.anye.mc.cores.register;

import com.mojang.logging.LogUtils;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Register<T> {
	private static final Logger LOGGER = LogUtils.getLogger();
	private final Identifier key;
	private final ResourceKey<? extends Registry<T>> resourceKey;
	private final DeferredRegister.RegistryHolder<T> registry;
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
		this.deferredRegister = DeferredRegister.create(key, key.getNamespace());
		RegistryBuilder<T> builder = new RegistryBuilder<>();

		builder.setName(key);
		consumer.accept(builder);
		this.registry = deferredRegister.makeRegistry(()->builder);
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
	public Register(DeferredRegister.RegistryHolder<T> registry, String modid) {
		this.key = registry.getKey().identifier();
		this.resourceKey = registry.getKey();
		this.registry = registry;
		this.deferredRegister = DeferredRegister.create(registry, modid);
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

	public DeferredRegister.RegistryHolder<T> registry() {
		return registry;
	}

	public DeferredRegister<T> deferredRegister() {
		return deferredRegister;
	}
	@Deprecated(since = "2.0.5")
	public DeferredRegister<T> getDeferredRegister() {
		return deferredRegister();
	}


	public void register(BusGroup eventBus) {
		deferredRegister.register(eventBus);
	}
	public <I extends T> RegistryObject<? extends I> register(String name, Supplier<? extends I> sup) {
		return deferredRegister.register(name, sup);
	}

	public <I extends T> Identifier getMemberKey(I member) {
		return registry.get().getKey(member);
	}
	public T getMemberValue(Identifier member) {
		return registry.get().getValue(member);
	}

	/**
	 * Pay attention to the timing of use; if used too early, nothing will appear in the registry.
	 * 注意使用时机，过早的使用，注册表中不会有任何东西。
	 * @param action action
	 */
	public void foreach(Consumer<? super T> action){
		registry.get().forEach(action);
	}
}
