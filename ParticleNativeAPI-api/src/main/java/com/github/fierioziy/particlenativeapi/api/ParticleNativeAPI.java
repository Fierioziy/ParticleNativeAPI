package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.particle.Particles_1_13;
import com.github.fierioziy.particlenativeapi.api.particle.Particles_1_8;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public abstract class ParticleNativeAPI {

    /**
     * <p>Instance of class holding particle types prior to 1.13.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from <code>Particles_1_13</code> should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isValid</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isValid</code> defined by all particle types in this interface.</p>
     */
    public final Particles_1_8 LIST_1_8;

    /**
     * <p>Instance of class holding particle types since 1.13.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from <code>Particles_1_13</code> should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isValid</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isValid</code> defined by all particle types in this interface.</p>
     */
    public final Particles_1_13 LIST_1_13;

    protected ParticleNativeAPI(Constructor<?> particles_1_8_ctor,
                                Constructor<?> particles_1_13_ctor)
            throws ReflectiveOperationException {
        LIST_1_8 = (Particles_1_8) particles_1_8_ctor.newInstance(this);
        LIST_1_13 = (Particles_1_13) particles_1_13_ctor.newInstance(this);
    }

}
