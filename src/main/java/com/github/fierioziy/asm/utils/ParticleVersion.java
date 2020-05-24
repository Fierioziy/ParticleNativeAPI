package com.github.fierioziy.asm.utils;

import com.github.fierioziy.api.Particles_1_13;
import com.github.fierioziy.api.Particles_1_8;
import org.objectweb.asm.Type;

/**
 * <p>An enum used by <code>ParticleNode</code> class to store
 * particle version.</p>
 *
 * <p>It also provides interface class associated with certain
 * particle version.</p>
 */
public enum ParticleVersion {
    V1_7(Particles_1_8.class),
    V1_8(Particles_1_8.class),
    V1_13(Particles_1_13.class);

    private Class<?> particleTypesClass;
    private Type superType;
    private Type implType;

    ParticleVersion(Class<?> particleTypesClass) {
        this.particleTypesClass = particleTypesClass;

        superType = Type.getType(particleTypesClass);
        implType = Type.getObjectType(superType.getInternalName() + "_Impl");
    }

    /**
     * <p>Gets interface class associated with particle version.</p>
     * @return an interface class.
     */
    public Class<?> getParticleTypesClass() {
        return particleTypesClass;
    }

    /**
     * <p>Gets <code>Type</code> object associated with interface class.</p>
     * @return a <code>Type</code> object representing interface class.
     */
    public Type getSuperType() {
        return superType;
    }

    /**
     * <p>Gets <code>Type</code> object associated with class implementing interface.</p>
     * @return a <code>Type</code> object representing class implementing interface.
     */
    public Type getImplType() {
        return implType;
    }
}
