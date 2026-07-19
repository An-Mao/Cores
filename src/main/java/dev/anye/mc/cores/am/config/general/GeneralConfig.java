package dev.anye.mc.cores.am.config.general;

import com.google.gson.reflect.TypeToken;
import dev.anye.core.json._JsonConfig;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;

public class GeneralConfig extends _JsonConfig<GeneralConfigData> {
    public GeneralConfig() {
        super( _File.getFilePath(Cores.CONFIG_DIR, "general.json"), """
                    {
                      "showTipGui": true
                    }""", new TypeToken<>() {
        });
    }
}
