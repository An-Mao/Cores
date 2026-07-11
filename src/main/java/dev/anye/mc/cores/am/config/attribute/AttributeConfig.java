package dev.anye.mc.cores.am.config.attribute;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

import java.util.Map;

public class AttributeConfig extends _JsonConfig<Map<String, AttributeData>> {
	public static final String file = _File.getFilePath(Cores.CONFIG_DIR, "attribute-config.json");

	public AttributeConfig() {
		super(file, """
				{
				  "attribute.name.generic.max_health": {
				    "min": 1.0,
				    "def": 20.0,
				    "max": 999999999999999.0
				  },
				  "attribute.name.generic.attack_damage": {
				    "min": 0.0,
				    "def": 2.0,
				    "max": 999999999999999.0
				  },
				  "attribute.name.generic.armor": {
				    "min": 0.0,
				    "def": 0.0,
				    "max": 999999999999999.0
				  },
				  "attribute.name.generic.armor_toughness": {
				    "min": 0.0,
				    "def": 0.0,
				    "max": 999999999999999.0
				  }
				}""", new TypeToken<>() {
		});
	}

	public AttributeData getConfig(String key) {
		return getData().get(key);
	}
}
