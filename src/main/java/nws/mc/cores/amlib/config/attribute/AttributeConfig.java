package nws.mc.cores.amlib.config.attribute;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.cores.CDT;

import java.util.Map;

public class AttributeConfig extends _JsonConfig<Map<String, AttributeData>> {
    public AttributeConfig() {
        super(CDT.ConfigDir +"attribute-config.json", """
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
    public AttributeData getConfig(String key){
        return getDatas().get(key);
    }
}
