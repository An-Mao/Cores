package dev.anye.mc.cores.helper.effect;

import dev.anye.core.math._Math;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;

import java.util.ArrayList;
import java.util.List;

public class EffectHelper {
	private static List<MobEffect> DeBuff = null;
	private static int DeBuffNumber = 0;

	public static Registry<MobEffect> getRegister() {
		return BuiltInRegistries.MOB_EFFECT;
	}

	public static List<MobEffect> getDeBuffs() {
		if (DeBuff == null) {
			DeBuff = new ArrayList<>();
			getRegister().forEach(mobEffect -> {
				if (!mobEffect.isBeneficial()) {
					DeBuff.add(mobEffect);
				}
			});
			DeBuffNumber = DeBuff.size() - 1;
		}
		return DeBuff;
	}

	public static MobEffect getRandomDeBuff() {
		return getDeBuffs().get(_Math.RD.getIntRandomNumber(0, DeBuffNumber));
	}

	public static MobEffectInstance getDeBuffInstance(int time, int lvl) {
		return new MobEffectInstance(Holder.direct(getRandomDeBuff()), time, lvl);
	}
}
