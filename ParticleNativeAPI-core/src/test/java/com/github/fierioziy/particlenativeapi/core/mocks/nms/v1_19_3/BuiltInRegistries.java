package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19_3;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particles_1_13;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.MinecraftKey;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17.Particles_1_17;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.Particles_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.Particles_1_19;

public class BuiltInRegistries {

    public static final IRegistry_1_19_3<Particle<?>> k = paramT -> {
        if (paramT == Particles_1_13.BARRIER)                    return new MinecraftKey("barrier");
        else if (paramT == Particles_1_18.HEART)                 return new MinecraftKey("heart");
        else if (paramT == Particles_1_13.FALLING_DUST)          return new MinecraftKey("falling_dust");
        else if (paramT == Particles_1_13.BLOCK)                 return new MinecraftKey("block");
        else if (paramT == Particles_1_13.ENTITY_EFFECT)         return new MinecraftKey("entity_effect");
        else if (paramT == Particles_1_13.DUST)                  return new MinecraftKey("dust");
        else if (paramT == Particles_1_17.DUST_COLOR_TRANSITION) return new MinecraftKey("dust_color_transition");
        else if (paramT == Particles_1_17.VIBRATION)             return new MinecraftKey("vibration");
        else if (paramT == Particles_1_13.ITEM)                  return new MinecraftKey("item");
        else if (paramT == Particles_1_13.FLAME)                 return new MinecraftKey("flame");
        else if (paramT == Particles_1_13.NOTE)                  return new MinecraftKey("note");
        else if (paramT == Particles_1_18.BLOCK_MARKER)          return new MinecraftKey("block_marker");
        else if (paramT == Particles_1_19.SCULK_CHARGE)          return new MinecraftKey("sculk_charge");
        else if (paramT == Particles_1_19.SHRIEK)                return new MinecraftKey("shriek");

        throw new RuntimeException("Unknown particle in Particles");
    };

}
