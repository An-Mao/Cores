package dev.anye.mc.cores.amlib.config.color;

import dev.anye.core.json._JsonConfig;
import dev.anye.mc.cores.CDT;
import com.google.gson.reflect.TypeToken;

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
