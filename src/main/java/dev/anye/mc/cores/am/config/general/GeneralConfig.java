package dev.anye.mc.cores.am.config.general;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.mc.cores.Cores;

public class GeneralConfig extends _JsonConfig<GeneralConfigData> {
    public GeneralConfig() {
        super(Cores.CONFIG_DIR +"general.json", """
                    {
                      "showTipGui": true
                    }""", new TypeToken<>() {
        });
    }
}
