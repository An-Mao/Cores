package dev.anye.mc.cores.am.config.attribute;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

import java.util.HashMap;
import java.util.Map;

public class AttributeConfig extends _JsonConfig<Map<String, AttributeData>> {
	public static final String FILE_PATH = _File.getFilePath(Cores.CONFIG_DIR, "attribute-config.json");
	protected static final Map<String, AttributeData> DEFAULT = new HashMap<>();
	static {
		DEFAULT.put("attribute.name.generic.max_health",new AttributeData(1d,999999999d,20d));
		DEFAULT.put("attribute.name.generic.attack_damage",new AttributeData(0d,999999999d,2d));
		DEFAULT.put("attribute.name.generic.armor",new AttributeData(0d,999999999d,0d));
		DEFAULT.put("attribute.name.generic.armor_toughness",new AttributeData(0d,999999999d,0d));
	}

	public AttributeConfig() {
		super(FILE_PATH, DEFAULT, new TypeToken<>() {});
	}
}
