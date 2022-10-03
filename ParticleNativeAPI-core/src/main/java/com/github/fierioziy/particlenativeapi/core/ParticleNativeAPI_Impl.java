package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class ParticleNativeAPI_Impl extends ParticleNativeAPI {

    public ParticleNativeAPI_Impl(Constructor<?> particles_1_8_ctor,
                                  Constructor<?> particles_1_13_ctor)
            throws ReflectiveOperationException {

        super(particles_1_8_ctor, particles_1_13_ctor);
    }

}
