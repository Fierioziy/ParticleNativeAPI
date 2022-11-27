package com.github.fierioziy.particlenativebenchmark.command;

import com.github.fierioziy.particlenativebenchmark.spawners.*;

import java.util.function.Function;

public enum BenchmarkCase {
    NMS("nms", ParticleSpawnerNMS::new),
    PARTICLE_NATIVE_API("pna", ParticleSpawnerPNA::new),
    SPIGOT_API("spigot", ParticleSpawnerSpigot::new),
    REFLECTION("ref", ParticleSpawnerReflection::new);

    private static final BenchmarkCase[] VALUES = values();

    private final String shortName;
    private final Function<CommandPNAB.Context, ParticleSpawner> particleSpawnerCtr;

    BenchmarkCase(String shortName,
                  Function<CommandPNAB.Context, ParticleSpawner> particleSpawnerCtr) {
        this.shortName = shortName;
        this.particleSpawnerCtr = particleSpawnerCtr;
    }

    public ParticleSpawner createParticleSpawner(CommandPNAB.Context context) {
        return particleSpawnerCtr.apply(context);
    }

    public static BenchmarkCase getByShortName(String shortName) {
        for (BenchmarkCase benchmarkCase : VALUES) {
            if (benchmarkCase.shortName.equalsIgnoreCase(shortName)) {
                return benchmarkCase;
            }
        }

        throw new IllegalArgumentException("No enum found for short name " + shortName);
    }

}
