package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particle;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_13.Particles_1_13;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_18.Particles_1_18;
import com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_19.Particles_1_19;

public abstract class IRegistry_1_17<T> {

    public static final IRegistry_1_17<Particle<?>> ab = new IRegistry_1_17<Particle<?>>() {
        @Override
        public MinecraftKey getKey_possiblyObfuscated(Particle<?> paramT) {
            if (paramT == Particles_1_13.LAVA)                    return new MinecraftKey("barrier");
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
        }
    };

    public abstract MinecraftKey getKey_possiblyObfuscated(T paramT);

}
