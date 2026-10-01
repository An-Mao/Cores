package dev.anye.mc.cores.am.color.scheme;

import dev.anye.core.color._StateColors;
import dev.anye.core.color.scheme._ColorScheme;

public final class ColorSchemeDefault extends _ColorScheme {

	public ColorSchemeDefault() {
		super(
				new _StateColors(0xFF000000, 0xFF000000),
				new _StateColors(0x77000000, 0x77000000),
				new _StateColors(0xFFFFFFFF, 0xFF0000FF),
				new _StateColors(0xFF000000, 0xFF000000),
				new _StateColors(0x77000000, 0x77000000),
				new _StateColors(0xFFFFFFFF, 0xFF0000FF, 0xff000000));
	}

}
