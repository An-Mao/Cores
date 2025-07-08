package dev.anye.mc.cores.amlib.config.general;

import dev.anye.core.json._JsonConfig;
import dev.anye.mc.cores.CDT;
import com.google.gson.reflect.TypeToken;

public class GeneralConfig extends _JsonConfig<GeneralConfigData> {
    public GeneralConfig() {
        super(CDT.ConfigDir +"general.json", """
                    {
                      "mixinAttributes": true,
                      "showTipGui": true
                    }""", new TypeToken<>() {
        });
    }
}
