package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_13;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_19_Part;
import com.github.fierioziy.particlenativeapi.api.particle.ParticleList_1_8;

import java.lang.reflect.Constructor;

/**
 * <p>Instance holding particle lists.</p>
 */
public abstract class ParticleNativeAPI {

    /**
     * <p>Instance of class holding particle types prior to 1.13.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from {@link ParticleList_1_13} should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isPresent</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isPresent</code> defined by all particle types in this class.</p>
     */
    public final ParticleList_1_8 LIST_1_8;

    /**
     * <p>Instance of class holding particle types since 1.13.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from {@link ParticleList_1_13} should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isPresent</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isPresent</code> defined by all particle types in this class.</p>
     */
    public final ParticleList_1_13 LIST_1_13;

    /**
     * <p>Instance of class holding particle types that have changed since 1.19.</p>
     *
     * <p>All particle lists attempt to provide same particle types between
     * renames or merges. They also attempt to provide cross-version
     * compatibility (for ex. usage of <code>ENCHANTED_HIT</code> effect name
     * from {@link ParticleList_1_13} should work on MC 1.8), however this is
     * not always possible.</p>
     *
     * <p>Use <code>isPresent</code> method on particle type to handle such cases.</p>
     *
     * <p>Before accessing any particle type, you should check if it exists on server by
     * an <code>isPresent</code> defined by all particle types in this class.</p>
     */
    public final ParticleList_1_19_Part LIST_1_19_PART;

    protected ParticleNativeAPI(Constructor<?> particleList_1_8_ctor,
                                Constructor<?> particleList_1_13_ctor,
                                Constructor<?> particleList_1_19_part_ctor)
            throws ReflectiveOperationException {
        LIST_1_8 = (ParticleList_1_8) particleList_1_8_ctor.newInstance(this);
        LIST_1_13 = (ParticleList_1_13) particleList_1_13_ctor.newInstance(this);
        LIST_1_19_PART = (ParticleList_1_19_Part) particleList_1_19_part_ctor.newInstance(this);
    }

}
