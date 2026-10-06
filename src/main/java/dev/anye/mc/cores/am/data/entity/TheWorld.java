package dev.anye.mc.cores.am.data.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record TheWorld(int time) {
    public static final Codec<TheWorld> CODEC = RecordCodecBuilder.create(
            theWorldInstance -> theWorldInstance.group(
                    Codec.INT.fieldOf("time").forGetter(TheWorld::time)
            ).apply(theWorldInstance, TheWorld::new)
    );
    public TheWorld() {
        this(0);
    }
}
