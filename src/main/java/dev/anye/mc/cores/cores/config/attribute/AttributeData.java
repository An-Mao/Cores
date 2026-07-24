package dev.anye.mc.cores.cores.config.attribute;

import java.util.HashMap;
import java.util.Map;

public record AttributeData (double def, double min, double max) {
	public static final Map<String, AttributeData> DEFAULT = Default();

	private static Map<String, AttributeData> Default (){
		Map<String, AttributeData> map = new HashMap<>();
		map.put("attribute.name.generic.max_health",new AttributeData(20d,1d,999999999d));
		map.put("attribute.name.generic.attack_damage",new AttributeData(2d,0d,999999999d));
		map.put("attribute.name.generic.armor",new AttributeData(0d,0d,999999999d));
		map.put("attribute.name.generic.armor_toughness",new AttributeData(0d,0d,999999999d));
		return map;
	}
}
