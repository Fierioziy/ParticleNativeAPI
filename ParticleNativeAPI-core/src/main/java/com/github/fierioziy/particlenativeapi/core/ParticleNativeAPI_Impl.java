package com.github.fierioziy.particlenativeapi.core;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class ParticleNativeAPI_Impl extends ParticleNativeAPI {

    public ParticleNativeAPI_Impl(Constructor<?> particleList_1_8_ctor,
                                  Constructor<?> particleList_1_13_ctor,
                                  Constructor<?> particleList_1_19_part_ctor)
            throws ReflectiveOperationException {

        super(particleList_1_8_ctor, particleList_1_13_ctor, particleList_1_19_part_ctor);
    }

}
