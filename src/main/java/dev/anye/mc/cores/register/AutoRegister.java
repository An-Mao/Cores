package dev.anye.mc.cores.register;

import dev.anye.core.exception._ClassNotFoundException;
import dev.anye.core.exception._TargetException;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class AutoRegister {
	private final String type;
	private final Class<?> baseClass;
	private final List<String> mods;
	private final Predicate<ModFileScanData.AnnotationData> customCheck;
	private final InstanceAndRegister instanceAndRegister;

	public AutoRegister(Class<? extends Annotation> type, Class<?> baseClass, List<String> mods, Predicate<ModFileScanData.AnnotationData> customCheck, InstanceAndRegister instanceAndRegister) {
		this.type = type.getName();
		this.baseClass = baseClass;
		this.mods = mods;
		this.customCheck = customCheck;
		this.instanceAndRegister = instanceAndRegister;
	}

	public List<IModFileInfo> getModFile() {
		if (mods.isEmpty()) return ModList.getModFiles();
		List<IModFileInfo> modFileInfos = new ArrayList<>();
		mods.forEach(id -> modFileInfos.add(ModList.getModFileById(id)));
		return modFileInfos;
	}


	public void register() {
		getModFile().forEach(iModFileInfo -> {
			ModFileScanData scanData = iModFileInfo.getFile().getScanResult();
			checkAnnotation(scanData.getAnnotations());
		});

	}

	public void checkAnnotation(Set<ModFileScanData.AnnotationData> annotations) {
		annotations.forEach(annotationData -> {
			if (annotationData.annotationType().getClassName().equals(type) && customCheck.test(annotationData)) {
				instantiateClass(annotationData.clazz().getClassName(), annotationData);
			}
		});
	}

	public void instantiateClass(String clazzName, ModFileScanData.AnnotationData annotationData) {
		try {
			Class<?> clazz = Class.forName(clazzName);
			if (baseClass.isAssignableFrom(clazz)) {
				instanceAndRegister.register(clazz, annotationData);
			}
		} catch (ClassNotFoundException e) {
			throw new _ClassNotFoundException(e);
		}
	}


	@SuppressWarnings("unchecked")
	public static <T> T simpleInstance(Class<?> clazz, ModFileScanData.AnnotationData annotationData) {
		try {
			return (T) clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
		         NoSuchMethodException e) {
			throw new _TargetException(e);
		}
	}

	public interface InstanceAndRegister {
		void register(Class<?> c, ModFileScanData.AnnotationData annotation);
	}
}
