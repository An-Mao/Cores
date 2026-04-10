package dev.anye.mc.cores.mixin;

import dev.anye.mc.cores.am.config.MixinConfigs;
import dev.anye.mc.cores.helper.entity.player.PlayerHelper;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {
    @Unique
    private boolean cores$notRunGiveLevel = false;
    @Shadow
    private int lastSentExp;
    @Shadow public abstract void giveExperiencePoints(int pXpPoints);

    @Inject(method = "giveExperienceLevels", at = @At("HEAD"), cancellable = true)
    private void cores$giveExperienceLevels$fix(int pLevel, CallbackInfo ci) {
        if (MixinConfigs.EnableFixLevel ) {
            boolean skip = false;
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            for (StackTraceElement stackTraceElement : stackTrace)
                if (stackTraceElement != null && stackTraceElement.getMethodName().equals("giveExperiencePoints")) {
                    skip = true;
                    break;
                }
            if (!skip) {
                //cores$notRunGiveLevel = true;
                int points;
                if (pLevel < 0) points = -PlayerHelper.getExperienceForLevel(-pLevel);
                else points = PlayerHelper.getExperienceForLevel(pLevel);
                giveExperiencePoints(points);
                lastSentExp = -1;
                //cores$notRunGiveLevel = false;
                ci.cancel();
            }
        }
    }
}
