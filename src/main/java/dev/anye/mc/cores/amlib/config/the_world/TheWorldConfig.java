package dev.anye.mc.cores.amlib.config.the_world;

import dev.anye.core.json._JsonConfig;
import dev.anye.mc.cores.CDT;
import com.google.gson.reflect.TypeToken;

public class TheWorldConfig extends _JsonConfig<TheWorldConfigData> {
    public TheWorldConfig() {
        super(CDT.ConfigDir +"TheWorld.json", """
                {
                  "enable": true,
                  "type": 1,
                  "stopTime": 600
                }""", new TypeToken<>() {
        });
    }
}
