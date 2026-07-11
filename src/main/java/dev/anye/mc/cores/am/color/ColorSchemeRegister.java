package dev.anye.mc.cores.am.color;

import dev.anye.core.color.scheme._ColorScheme;
import dev.anye.core.system._File;
import dev.anye.mc.cores.Cores;
import dev.anye.mc.cores.am.color.scheme.*;
import dev.anye.mc.cores.register.Register;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Supplier;

public class ColorSchemeRegister {
	private ColorSchemeRegister() {
	}

	public static final Register<_ColorScheme> COLOR_SCHEME_REGISTER = new Register<>(Cores.MOD_ID, "color_scheme", builder -> builder.sync(false).maxId(256));


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

	static {
		regFromConfig();
	}

	public static void regFromConfig() {
		_File.getFiles(ColorSchemeIO.filePath, ".json").forEach(path -> {
			String k = path.getFileName().toString();
			ColorSchemeIO.Data colorD = new ColorSchemeIO(k).getData();
			k = k.substring(0, k.length() - 5);
			if (colorD != null && !COLOR_SCHEME_REGISTER.getRegistry().containsKey(Identifier.tryBuild(Cores.MOD_ID, k)))
				reg(k, () -> new ColorSchemeConfigLoad(colorD));
		});
	}

	public static void register(IEventBus eventBus) {
		COLOR_SCHEME_REGISTER.register(eventBus);
	}

	public static Component getSchemeComponent(_ColorScheme colorScheme) {
		return Component.translatable(getSchemeKey(colorScheme));
	}

	public static <I extends _ColorScheme> DeferredHolder<_ColorScheme, I> reg(String name, Supplier<? extends I> sup) {
		return COLOR_SCHEME_REGISTER.register(name, sup);
	}

	public static String getSchemeKey(_ColorScheme colorScheme) {
		return "color_scheme." + COLOR_SCHEME_REGISTER.getRegistry().getKey(colorScheme).toLanguageKey();
	}
}
