package dev.anye.mc.cores.am.color;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.scheme.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.bus.BusGroup;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class ColorSchemeRegister {

    public static final ResourceLocation KEY =  ResourceLocation.tryBuild(Cores.MOD_ID, "color_scheme");
    public static final DeferredRegister<_ColorScheme> COLOR_SCHEME = DeferredRegister.create(KEY, Cores.MOD_ID);
    public static final Supplier<IForgeRegistry<_ColorScheme>> REGISTRY = COLOR_SCHEME.makeRegistry(()->new RegistryBuilder<_ColorScheme>().disableSync());

    public static final RegistryObject<_ColorScheme> DEFAULT = reg("default", ColorSchemeDefault::new);

    public static final RegistryObject<_ColorScheme> Twilight_Purple = reg("twilight_purple", TwilightPurple::new);
    public static final RegistryObject<_ColorScheme> Cold_Gray = reg("cold_gray", ColdGray::new);
    public static final RegistryObject<_ColorScheme> Blue_Fresh = reg("blue_fresh", BlueFresh::new);
    public static final RegistryObject<_ColorScheme> Natural_Greenery = reg("natural_greenery", NaturalGreenery::new);
    public static final RegistryObject<_ColorScheme> Ocean_Breeze = reg("ocean_breeze", OceanBreeze::new);
    public static final RegistryObject<_ColorScheme> Sunset_Glow = reg("sunset_glow", SunsetGlow::new);
    public static final RegistryObject<_ColorScheme> Fresh_Mint = reg("fresh_mint", FreshMint::new);
    public static final RegistryObject<_ColorScheme> Autumn_Leaves = reg("autumn_leaves", AutumnLeaves::new);


    public static final RegistryObject<_ColorScheme> Midnight_Blue = reg("midnight_blue", MidnightBlue::new);
    public static final RegistryObject<_ColorScheme> Coral_Reef = reg("coral_reef", CoralReef::new);
    public static final RegistryObject<_ColorScheme> Emerald_Green = reg("emerald_green", EmeraldGreen::new);
    public static final RegistryObject<_ColorScheme> Soft_Lavender = reg("soft_lavender", SoftLavender::new);
    public static final RegistryObject<_ColorScheme> Golden_Sands = reg("golden_sands", GoldenSands::new);
    public static final RegistryObject<_ColorScheme> Crimson_Tide = reg("crimson_tide", CrimsonTide::new);
    public static final RegistryObject<_ColorScheme> Misty_Violet = reg("misty_violet", MistyViolet::new);
    public static final RegistryObject<_ColorScheme> TealWhisper = reg("teal_whisper", TealWhisper::new);
    public static final RegistryObject<_ColorScheme> Amber_Blaze = reg("amber_blaze", AmberBlaze::new);
    public static final RegistryObject<_ColorScheme> Peach_Melba = reg("peach_melba", PeachMelba::new);


    public static void register(BusGroup eventBus){
        COLOR_SCHEME.register(eventBus);
    }

    public static Component getSchemeComponent(_ColorScheme colorScheme){
        return Component.translatable(getSchemeKey(colorScheme));
    }

    public static RegistryObject<_ColorScheme> reg(String name , Function<String , _ColorScheme> function){
        return COLOR_SCHEME.register(name, () -> function.apply(name));

    }
    public static RegistryObject<_ColorScheme> reg(String name, Supplier<? extends _ColorScheme> sup) {
        return COLOR_SCHEME.register(name, sup);

    }

    public static String getSchemeKey(_ColorScheme colorScheme){
        return "color_scheme."+REGISTRY.get().getKey(colorScheme).toLanguageKey();
    }
}
