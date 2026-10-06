package dev.anye.mc.cores.am.color.scheme;


import dev.anye.core.color._StateColors;
import dev.anye.core.color.scheme._ColorScheme;

public final class ColdGray extends _ColorScheme {
	public ColdGray() {
		super(
				new _StateColors(0x80CCCCCC, 0xA6999999, 0xCC666666),
				new _StateColors(0x99333333, 0xB2000000, 0xCC000000),
				new _StateColors(0x4DFFFFFF, 0x66E6E6E6, 0x80CCCCCC),
				new _StateColors(0xA6999999, 0xCC666666, 0xFF333333),
				new _StateColors(0xB2666666, 0xCC333333, 0xFF000000),
				new _StateColors(0x66F2F2F2, 0x80D9D9D9, 0x99BFBFBF));
	}
}
