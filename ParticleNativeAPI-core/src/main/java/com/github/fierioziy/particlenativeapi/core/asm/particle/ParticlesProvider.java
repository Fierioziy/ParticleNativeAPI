package com.github.fierioziy.particlenativeapi.core.asm.particle;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;

public class ParticlesProvider extends BaseASM {

    private final ParticlePacketProvider particlePacketProvider;
    private final ParticleTypesProvider particleTypesProvider;

    private final SpigotVersion chosenVersion;

    private ParticlesASM particlesASM_1_8;
    private ParticlesASM particlesASM_1_13;

    public ParticlesProvider(InternalResolver resolver) {
        super(resolver, "_Impl");

        if (internal.isVersion_1_7()) {
            particlePacketProvider = new ParticlePacketProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_7(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_7;
        }
        else if (internal.isVersion_1_8()) {
            particlePacketProvider = new ParticlePacketProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_8(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_8;
        }
        else if (internal.isVersion_1_13()) {
            particlePacketProvider = new ParticlePacketProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_13(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_13;
        }
        else if (internal.isVersion_1_15()) {
            particlePacketProvider = new ParticlePacketProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_15(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_15;
        }
        else if (internal.isVersion_1_17()) {
            particlePacketProvider = new ParticlePacketProvider_1_17(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_17(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_17;
        }
        else if (internal.isVersion_1_18()) {
            particlePacketProvider = new ParticlePacketProvider_1_17(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_18(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_18;
        }
        else if (internal.isVersion_1_19()) {
            particlePacketProvider = new ParticlePacketProvider_1_17(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_19(resolver, particlePacketProvider);
            chosenVersion = SpigotVersion.V1_19;
        }
        else throw new ParticleException("Error: this server version is not supported!");
    }

    public void registerClasses() {
        particlePacketProvider.registerClasses();
        particleTypesProvider.registerClasses();

        particlesASM_1_8 = new ParticlesASM(
                internal, SpigotParticleVersion.V1_8,
                particleTypesProvider
        );
        particlesASM_1_8.registerClass();

        particlesASM_1_13 = new ParticlesASM(
                internal, SpigotParticleVersion.V1_13,
                particleTypesProvider
        );
        particlesASM_1_13.registerClass();
    }

    public SpigotVersion getChosenVersion() {
        return chosenVersion;
    }

    public ParticlesASM getParticlesASM_1_8() {
        return particlesASM_1_8;
    }

    public ParticlesASM getParticlesASM_1_13() {
        return particlesASM_1_13;
    }
}
