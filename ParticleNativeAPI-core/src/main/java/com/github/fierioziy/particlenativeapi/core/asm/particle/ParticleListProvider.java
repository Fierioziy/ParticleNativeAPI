package com.github.fierioziy.particlenativeapi.core.asm.particle;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.ContextASM;
import com.github.fierioziy.particlenativeapi.core.asm.packet.ParticlePacketProvider;
import com.github.fierioziy.particlenativeapi.core.asm.particle.type.*;
import com.github.fierioziy.particlenativeapi.core.asm.skeleton.ClassSkeleton;
import com.github.fierioziy.particlenativeapi.core.asm.utils.SpigotParticleVersion;

public class ParticleListProvider extends BaseASM {

    private final ParticlePacketProvider particlePacketProvider;
    private final ParticleTypesProvider particleTypesProvider;

    private ParticleListASM particleListASM_1_8;
    private ParticleListASM particleListASM_1_13;
    private ParticleListASM particleListASM_1_19_part;

    public ParticleListProvider(ContextASM context) {
        super(context);
        particlePacketProvider = context.particlePacketProvider;
        particleTypesProvider = context.particleTypesProvider;
    }

    public void registerClasses() {
        particlePacketProvider.registerClasses();
        particleTypesProvider.registerClasses();

        particleListASM_1_8 = new ParticleListASM(context,
                SpigotParticleVersion.V1_8,
                ClassSkeleton.PARTICLE_LIST_1_8
        );
        particleListASM_1_8.registerClass();

        particleListASM_1_13 = new ParticleListASM(context,
                SpigotParticleVersion.V1_13,
                ClassSkeleton.PARTICLE_LIST_1_13
        );
        particleListASM_1_13.registerClass();

        particleListASM_1_19_part = new ParticleListASM(context,
                SpigotParticleVersion.V1_18,
                ClassSkeleton.PARTICLE_LIST_1_19_PART
        );
        particleListASM_1_19_part.registerClass();
    }

    public ParticleListASM getParticleListASM_1_8() {
        return particleListASM_1_8;
    }

    public ParticleListASM getParticleListASM_1_13() {
        return particleListASM_1_13;
    }

    public ParticleListASM getParticleListASM_1_19_part() {
        return particleListASM_1_19_part;
    }

}
