package com.github.fierioziy.particlenativeapi.core.asm.particle;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.connections.ConnectionsProvider;
import com.github.fierioziy.particlenativeapi.core.asm.connections.ConnectionsProvider_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.connections.ConnectionsProvider_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.particle.types.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;

public class ParticlesProvider extends BaseASM {

    private final ConnectionsProvider connectionsProvider;
    private final ParticleTypesProvider particleTypesProvider;

    private final SpigotVersion chosenVersion;

    private Class<?> particles_1_8_class;
    private Class<?> particles_1_13_class;

    public ParticlesProvider(InternalResolver resolver) {
        super(resolver, "_Impl");

        if (internal.isVersion_1_7()) {
            connectionsProvider = new ConnectionsProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_7(resolver);
            chosenVersion = SpigotVersion.V1_7;
        }
        else if (internal.isVersion_1_8()) {
            connectionsProvider = new ConnectionsProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_8(resolver);
            chosenVersion = SpigotVersion.V1_8;
        }
        else if (internal.isVersion_1_13()) {
            connectionsProvider = new ConnectionsProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_13(resolver);
            chosenVersion = SpigotVersion.V1_13;
        }
        else if (internal.isVersion_1_15()) {
            connectionsProvider = new ConnectionsProvider_1_7(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_15(resolver);
            chosenVersion = SpigotVersion.V1_15;
        }
        else if (internal.isVersion_1_17()) {
            connectionsProvider = new ConnectionsProvider_1_17(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_17(resolver);
            chosenVersion = SpigotVersion.V1_17;
        }
        else if (internal.isVersion_1_18()) {
            connectionsProvider = new ConnectionsProvider_1_17(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_18(resolver);
            chosenVersion = SpigotVersion.V1_18;
        }
        else if (internal.isVersion_1_19()) {
            connectionsProvider = new ConnectionsProvider_1_17(resolver);
            particleTypesProvider = new ParticleTypesProvider_1_19(resolver);
            chosenVersion = SpigotVersion.V1_19;
        }
        else throw new ParticleException("Error: this server version is not supported!");
    }

    public void defineClasses() {
        connectionsProvider.defineClasses();
        particleTypesProvider.defineClasses();

        ClassMapping serverConnImplType = refs.serverConnType.impl(connectionsProvider.getSuffix());

        particles_1_8_class = new ParticlesASM(
                internal, SpigotParticleVersion.V1_8,
                particleTypesProvider, serverConnImplType
        ).defineClass();

        particles_1_13_class = new ParticlesASM(
                internal, SpigotParticleVersion.V1_13,
                particleTypesProvider, serverConnImplType
        ).defineClass();
    }

    public Class<?> getParticles_1_8_class() {
        return particles_1_8_class;
    }

    public Class<?> getParticles_1_13_class() {
        return particles_1_13_class;
    }

    public SpigotVersion getChosenVersion() {
        return chosenVersion;
    }

}
