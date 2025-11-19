package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassMapping;
import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider_1_20_2;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.*;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotVersion;

public class ContextASM {

    public final InternalResolver internal;
    public final SpigotClassRegistry refs;

    public final SpigotVersion currentVersion;
    public final SpigotParticleVersion currentParticleVersion;
    public final String suffix;

    public final ParticlePacketProvider particlePacketProvider;
    public final ParticleTypesProvider particleTypesProvider;

    public final ClassMapping particlePacketImpl_X;

    public ContextASM(InternalResolver resolver) {
        this.internal = resolver;
        refs = internal.refs;

        if (internal.isVersion_1_7()) {
            currentVersion = SpigotVersion.V1_7;
            currentParticleVersion = SpigotParticleVersion.V1_7;
            particlePacketProvider = new ParticlePacketProvider_1_7(this);
            particleTypesProvider = new ParticleTypesProvider_1_7(this);
        }
        else if (internal.isVersion_1_8()) {
            currentVersion = SpigotVersion.V1_8;
            currentParticleVersion = SpigotParticleVersion.V1_8;
            particlePacketProvider = new ParticlePacketProvider_1_7(this);
            particleTypesProvider = new ParticleTypesProvider_1_8(this);
        }
        else if (internal.isVersion_1_13()) {
            currentVersion = SpigotVersion.V1_13;
            currentParticleVersion = SpigotParticleVersion.V1_13;
            particlePacketProvider = new ParticlePacketProvider_1_7(this);
            particleTypesProvider = new ParticleTypesProvider_1_13(this);
        }
        else if (internal.isVersion_1_15()) {
            currentVersion = SpigotVersion.V1_15;
            currentParticleVersion = SpigotParticleVersion.V1_13;
            particlePacketProvider = new ParticlePacketProvider_1_7(this);
            particleTypesProvider = new ParticleTypesProvider_1_15(this);
        }
        else if (internal.isVersion_1_17()) {
            currentVersion = SpigotVersion.V1_17;
            currentParticleVersion = SpigotParticleVersion.V1_13;
            particlePacketProvider = new ParticlePacketProvider_1_17(this);
            particleTypesProvider = new ParticleTypesProvider_1_17(this);
        }
        else if (internal.isVersion_1_18()) {
            currentVersion = SpigotVersion.V1_18;
            currentParticleVersion = SpigotParticleVersion.V1_18;
            particlePacketProvider = new ParticlePacketProvider_1_17(this);
            particleTypesProvider = new ParticleTypesProvider_1_18(this);
        }
        else if (internal.isVersion_1_19()) {
            currentVersion = SpigotVersion.V1_19;
            currentParticleVersion = SpigotParticleVersion.V1_18;
            particlePacketProvider = new ParticlePacketProvider_1_17(this);
            particleTypesProvider = new ParticleTypesProvider_1_19(this);
        }
        else if (internal.isVersion_1_19_3()) {
            currentVersion = SpigotVersion.V1_19_3;
            currentParticleVersion = SpigotParticleVersion.V1_18;
            particlePacketProvider = new ParticlePacketProvider_1_17(this);
            particleTypesProvider = new ParticleTypesProvider_1_19_3(this);
        }
        else if (internal.isVersion_1_20_2()) {
            currentVersion = SpigotVersion.V1_20_2;
            currentParticleVersion = SpigotParticleVersion.V1_18;
            particlePacketProvider = new ParticlePacketProvider_1_20_2(this);
            particleTypesProvider = new ParticleTypesProvider_1_19_3(this);
        }
        else if (internal.isVersion_1_20_5()) {
            currentVersion = SpigotVersion.V1_20_5;
            currentParticleVersion = SpigotParticleVersion.V1_20_5;
            particlePacketProvider = new ParticlePacketProvider_1_20_2(this);
            particleTypesProvider = new ParticleTypesProvider_1_20_5(this);
        }
        else if (internal.isVersion_1_21_3()) {
            currentVersion = SpigotVersion.V1_21_3;
            currentParticleVersion = SpigotParticleVersion.V1_20_5;
            particlePacketProvider = new ParticlePacketProvider_1_20_2(this);
            particleTypesProvider = new ParticleTypesProvider_1_21_3(this);
        }
        else if (internal.isVersion_1_21_4()) {
            currentVersion = SpigotVersion.V1_21_4;
            currentParticleVersion = SpigotParticleVersion.V1_20_5;
            particlePacketProvider = new ParticlePacketProvider_1_20_2(this);
            particleTypesProvider = new ParticleTypesProvider_1_21_4(this);
        }
        else if (internal.isVersion_1_21_10()) {
            currentVersion = SpigotVersion.V1_21_10;
            currentParticleVersion = SpigotParticleVersion.V1_20_5;
            particlePacketProvider = new ParticlePacketProvider_1_20_2(this);
            particleTypesProvider = new ParticleTypesProvider_1_21_10(this);
        }
        else throw new ParticleException("Error: this server version is not supported!");

        suffix = currentVersion.getSuffix();
        particlePacketImpl_X = ClassSkeleton.PARTICLE_PACKET.getImpl(suffix);
    }

}
