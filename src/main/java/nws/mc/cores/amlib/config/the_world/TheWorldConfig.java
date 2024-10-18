package nws.mc.cores.amlib.config.the_world;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.cores.CDT;

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
