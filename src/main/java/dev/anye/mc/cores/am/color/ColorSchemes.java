package dev.anye.mc.cores.am.color;

import dev.anye.core.color.scheme._ColorScheme;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Objects;

public class ColorSchemes {
	private static _ColorScheme global = ColorSchemeRegister.DEFAULT.get();

	public static _ColorScheme getGlobal() {
		return global;
	}


	public static void setGlobal(_ColorScheme colorScheme) {
		global = colorScheme;
	}


	public static <I extends _ColorScheme> void setGlobal(DeferredHolder<_ColorScheme, I> colorScheme) {
		setGlobal(Objects.requireNonNullElse(colorScheme, ColorSchemeRegister.DEFAULT).get());
	}
}
