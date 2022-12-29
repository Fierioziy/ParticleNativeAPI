package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.SpigotClassRegistry;
import org.objectweb.asm.Opcodes;

public class BaseASM implements Opcodes {

    /*
    Constant names to ease possibly future refactoring
     */

    // API
    public static final String DETACH_COPY_METHOD_NAME = "detachCopy";
    public static final String SEND_TO_METHOD_NAME = "sendTo";
    public static final String IS_PRESENT_METHOD_NAME = "isPresent";

    public static final String PACKET_METHOD_NAME = "packet";

    public static final String OF_METHOD_NAME = "of";
    public static final String COLOR_METHOD_NAME = "color";
    public static final String ROLL_METHOD_NAME = "roll";
    public static final String DELAY_METHOD_NAME = "delay";
    public static final String FLYING_TO_METHOD_NAME = "flyingTo";

    // ASM
    public static final String PACKET_FIELD_NAME = "packet";
    public static final String PACKET_WRAPPER_FIELD_NAME = "packetWrapper";

    public static final String PARTICLE_FIELD_NAME = "particle";
    public static final String PARTICLE_DATA_FIELD_NAME = "data";// for 1.8
    public static final String PARTICLE_WRAPPER_FIELD_NAME = "particleWrapper";

    public static final String SET_PARTICLE_METHOD_NAME = "setParticle";
    public static final String SET_PACKET_METHOD_NAME = "setPacket";

    protected final ContextASM context;
    protected final SpigotClassRegistry refs;

    public BaseASM(ContextASM context) {
        this.context = context;
        this.refs = context.refs;
    }

}
