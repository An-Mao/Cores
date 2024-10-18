package nws.mc.cores.amlib.config.general;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.cores.CDT;

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
