package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particles_v1_13;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.Particles_v1_18;

public abstract class IRegistry<T> {

    public static final IRegistry<Particle<?>> ab = new IRegistry<Particle<?>>() {
        @Override
        public MinecraftKey getKey_possiblyObfuscated(Particle<?> paramT) {
            if (paramT == Particles_v1_13.BARRIER)                    return new MinecraftKey("barrier");
            else if (paramT == Particles_v1_18.HEART)                 return new MinecraftKey("heart");
            else if (paramT == Particles_v1_13.FALLING_DUST)          return new MinecraftKey("falling_dust");
            else if (paramT == Particles_v1_13.BLOCK)                 return new MinecraftKey("block");
            else if (paramT == Particles_v1_13.ENTITY_EFFECT)         return new MinecraftKey("entity_effect");
            else if (paramT == Particles_v1_13.DUST)                  return new MinecraftKey("dust");
            else if (paramT == Particles_v1_17.DUST_COLOR_TRANSITION) return new MinecraftKey("dust_color_transition");
            else if (paramT == Particles_v1_17.VIBRATION)             return new MinecraftKey("vibration");
            else if (paramT == Particles_v1_13.ITEM)                  return new MinecraftKey("item");
            else if (paramT == Particles_v1_13.FLAME)                 return new MinecraftKey("flame");
            else if (paramT == Particles_v1_13.NOTE)                  return new MinecraftKey("note");
            else if (paramT == Particles_v1_18.BLOCK_MARKER)          return new MinecraftKey("block_marker");

            throw new RuntimeException("Unknown particle in Particles");
        }
    };

    public abstract MinecraftKey getKey_possiblyObfuscated(T paramT);

}
