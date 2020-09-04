package com.github.fierioziy.particlenativeapi.api;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

public interface ParticleNativeAPI {

    /**
     * <p>Gets instance of interface holding particle types
     * prior to 1.13.</p>
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
     *
     * <p>This method might throw <code>ParticleException</code> if class
     * generation failed (by a plugin).</p>
     *
     * @return a valid <code>Particles_1_8</code> instance.
     * @throws ParticleException if error occurred during class generation (by a plugin).
     */
    Particles_1_8 getParticles_1_8() throws ParticleException;

    /**
     * <p>Gets instance of interface holding particle types
     * since 1.13.</p>
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
     *
     * <p>This method might throw <code>ParticleException</code> if class
     * generation failed (by a plugin).</p>
     *
     * @return a valid <code>Particles_1_13</code> instance.
     * @throws ParticleException if error occurred during class generation (by a plugin).
     */
    Particles_1_13 getParticles_1_13() throws ParticleException;

    /**
     * <p>Gets instance of <code>ServerConnection</code>.</p>
     *
     * <p>You should check if API has been successfully generated
     * using <code>isValid</code> method.
     *
     * This method will throw <code>ParticleException</code> if class
     * generation failed (by a plugin).</p>
     *
     * @return a valid <code>ServerConnection</code> instance.
     * @throws ParticleException if error occurred during class generation (by a plugin).
     * @deprecated use any particle list instead, it contains exact same functionality.
     */
    @Deprecated
    ServerConnection getServerConnection() throws ParticleException;

}
