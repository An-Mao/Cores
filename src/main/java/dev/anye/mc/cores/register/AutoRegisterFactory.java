package dev.anye.mc.cores.register;

import com.mojang.logging.LogUtils;
import dev.anye.core.exception._ClassNotFoundException;
import net.minecraft.resources.Identifier;
import net.neoforged.fml.ModList;
import net.neoforged.neoforgespi.language.ModFileScanData;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class AutoRegisterFactory {
	private static final Logger LOGGER = LogUtils.getLogger();
	private static boolean REGISTERED = false;
	private static final Map<Identifier, Data> reg = new HashMap<>();

	public static void register(Identifier identifier, Class<?> annotation, Class<?> baseClass, Predicate<ModFileScanData.AnnotationData> customCheck, AutoRegister.InstanceAndRegister instanceAndRegister){
		reg.put(identifier,new Data(annotation.getName(),baseClass,customCheck,instanceAndRegister));
	}

	public static void register(){
		if (REGISTERED) return;
		REGISTERED = true;
		LOGGER.debug("register start");
		//iModFileInfo.getFile().getId()
		ModList.get().getModFiles().forEach(iModFileInfo -> {
			ModFileScanData scanData = iModFileInfo.getFile().getScanResult();
			//LOGGER.debug("register => {}",iModFileInfo.getFile().getId());
			scanData.getAnnotations().forEach(annotationData -> {
				//LOGGER.debug("register ==> {}",annotationData.memberName());
				reg.forEach((identifier, data) -> {
					if (annotationData.annotationType().getClassName().equals(data.annotation) && data.customCheck.test(annotationData)) {
						//LOGGER.debug("register ===> {}",identifier);
						try {
							Class<?> clazz = Class.forName(annotationData.clazz().getClassName());
							if (data.baseClass.isAssignableFrom(clazz)) {
								//LOGGER.debug("register ====> {}",annotationData.clazz().getClassName());
								data.instanceAndRegister.register(clazz, annotationData);
							}
						} catch (ClassNotFoundException e) {
							throw new _ClassNotFoundException(e);
						}
					}
				});
			});
		});
	}




	public record Data(String annotation, Class<?> baseClass,Predicate<ModFileScanData.AnnotationData> customCheck, AutoRegister.InstanceAndRegister instanceAndRegister){
	}
}
