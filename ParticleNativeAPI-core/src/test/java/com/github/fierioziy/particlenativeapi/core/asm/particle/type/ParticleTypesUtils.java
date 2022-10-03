package com.github.fierioziy.particlenativeapi.core.asm.particle.type;

import com.github.fierioziy.particlenativeapi.api.packet.ParticlePacket;
import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;

import java.lang.reflect.Field;

public class ParticleTypesUtils {

    public static Object unwrapPacket(ParticlePacket wrappedPacket) {
        String packetFieldName = BaseASM.PACKET_FIELD_NAME;
        try {
            // runtime class type that holds packet field depends on server version
            // so it cannot be reliably stored for continuous calls
            // it doesn't matter anyway, it is just an unit test
            Field declaredField = wrappedPacket.getClass().getDeclaredField(packetFieldName);
            declaredField.setAccessible(true);

            return declaredField.get(wrappedPacket);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(String.format(
                    "Particle wrapper runtime class type probably does not contain \"%s\" field",
                    packetFieldName
            ), e);
        }
    }

}
