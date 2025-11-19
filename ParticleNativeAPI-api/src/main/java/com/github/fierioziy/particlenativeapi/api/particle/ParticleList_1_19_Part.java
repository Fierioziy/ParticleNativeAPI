package com.github.fierioziy.particlenativeapi.api.particle;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.github.fierioziy.particlenativeapi.api.particle.type.*;

/**
 * <p>A class declaring fields for particle types.
 * It contains only those particle types that changed since MC 1.19.</p>
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
 * an <code>isPresent</code> defined by all particle types in this interface.</p>
 */
@SuppressWarnings("unused")
public abstract class ParticleList_1_19_Part extends ParticleSupplier_1_19_Part {

    private final ParticleNativeAPI api;

    // 1.19
    public final ParticleTypeVibration VIBRATION = VIBRATION();

    // 1.20.5
    public final ParticleTypeColor ENTITY_EFFECT = ENTITY_EFFECT();

    // 1.21.10
    public final ParticleTypeColor FLASH = FLASH();
    public final ParticleTypePowerMotion DRAGON_BREATH = DRAGON_BREATH();
    public final ParticleTypeSpell EFFECT = EFFECT();
    public final ParticleTypeSpell INSTANT_EFFECT = INSTANT_EFFECT();

    protected ParticleList_1_19_Part(ParticleNativeAPI api) {
        this.api = api;
    }

    public ParticleNativeAPI getAPI() {
        return api;
    }

}
