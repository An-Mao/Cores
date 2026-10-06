package dev.anye.mc.cores.am.config.mixin;

public record MixinConfigData(boolean Attributes,boolean PlayerLevel) {
	public static final MixinConfigData DEFAULT = new MixinConfigData(true,true);
}
