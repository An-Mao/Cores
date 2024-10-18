package nws.mc.cores.amlib.config.color;

import com.google.gson.reflect.TypeToken;
import nws.dev.core.json._JsonConfig;
import nws.mc.cores.CDT;

public class ColorConfig extends _JsonConfig<ColorConfigData> {
    public static String filePath = CDT.ConfigDir +"color.json";
    public static ColorConfig instance = new ColorConfig();
    public ColorConfig() {
        super(filePath, """
                {
                  "colorScheme": "cores:default"
                }
                """, new TypeToken<>() {});
    }
}
