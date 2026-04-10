package dev.anye.mc.cores.am.color;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.scheme.*;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Supplier;

public class ColorSchemeRegister {
    public static final Identifier KEY =  Identifier.tryBuild(Cores.MOD_ID, "color_scheme");
    public static final ResourceKey<Registry<_ColorScheme>> REGISTRY_KEY = ResourceKey.createRegistryKey(KEY);
    public static final Registry<_ColorScheme> REGISTRY = new RegistryBuilder<>(REGISTRY_KEY)
            .sync(false)
            .maxId(256)
            .create();
    public static final DeferredRegister<_ColorScheme> COLOR_SCHEME = DeferredRegister.create(REGISTRY, Cores.MOD_ID);


    public static final DeferredHolder<_ColorScheme, ColorSchemeDefault> DEFAULT = reg("default", ColorSchemeDefault::new);

    public static final DeferredHolder<_ColorScheme, TwilightPurple> Twilight_Purple = reg("twilight_purple", TwilightPurple::new);
    public static final DeferredHolder<_ColorScheme, ColdGray> Cold_Gray = reg("cold_gray", ColdGray::new);
    public static final DeferredHolder<_ColorScheme, BlueFresh> Blue_Fresh = reg("blue_fresh", BlueFresh::new);
    public static final DeferredHolder<_ColorScheme, NaturalGreenery> Natural_Greenery = reg("natural_greenery", NaturalGreenery::new);
    public static final DeferredHolder<_ColorScheme, OceanBreeze> Ocean_Breeze = reg("ocean_breeze", OceanBreeze::new);
    public static final DeferredHolder<_ColorScheme, SunsetGlow> Sunset_Glow = reg("sunset_glow", SunsetGlow::new);
    public static final DeferredHolder<_ColorScheme, FreshMint> Fresh_Mint = reg("fresh_mint", FreshMint::new);
    public static final DeferredHolder<_ColorScheme, AutumnLeaves> Autumn_Leaves = reg("autumn_leaves", AutumnLeaves::new);


    public static final DeferredHolder<_ColorScheme, MidnightBlue> Midnight_Blue = reg("midnight_blue", MidnightBlue::new);
    public static final DeferredHolder<_ColorScheme, CoralReef> Coral_Reef = reg("coral_reef", CoralReef::new);
    public static final DeferredHolder<_ColorScheme, EmeraldGreen> Emerald_Green = reg("emerald_green", EmeraldGreen::new);
    public static final DeferredHolder<_ColorScheme, SoftLavender> Soft_Lavender = reg("soft_lavender", SoftLavender::new);
    public static final DeferredHolder<_ColorScheme, GoldenSands> Golden_Sands = reg("golden_sands", GoldenSands::new);
    public static final DeferredHolder<_ColorScheme, CrimsonTide> Crimson_Tide = reg("crimson_tide", CrimsonTide::new);
    public static final DeferredHolder<_ColorScheme, MistyViolet> Misty_Violet = reg("misty_violet", MistyViolet::new);
    public static final DeferredHolder<_ColorScheme, TealWhisper> TealWhisper = reg("teal_whisper", TealWhisper::new);
    public static final DeferredHolder<_ColorScheme, AmberBlaze> Amber_Blaze = reg("amber_blaze", AmberBlaze::new);
    public static final DeferredHolder<_ColorScheme, PeachMelba> Peach_Melba = reg("peach_melba", PeachMelba::new);


    public static void register(IEventBus eventBus){
        COLOR_SCHEME.register(eventBus);
    }

    public static Component getSchemeComponent(_ColorScheme colorScheme){
        return Component.translatable(getSchemeKey(colorScheme));
    }

    public static <I extends _ColorScheme> DeferredHolder<_ColorScheme, I>  reg(String name, Supplier<? extends I> sup) {
        return COLOR_SCHEME.register(name, sup);
    }

    public static String getSchemeKey(_ColorScheme colorScheme){
        return "color_scheme."+REGISTRY.getKey(colorScheme).toLanguageKey();
    }
}
