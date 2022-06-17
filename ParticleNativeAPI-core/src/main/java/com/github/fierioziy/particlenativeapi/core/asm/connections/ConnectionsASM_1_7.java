package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_7.PlayerConnectionASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_7.ServerConnectionASM_1_7;

public class ConnectionsASM_1_7 extends ConnectionsASM {

    private static final String SUFFIX = "_1_7";

    public ConnectionsASM_1_7(InternalResolver resolver) {
        super(resolver, SUFFIX);
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_7(internal, SUFFIX).defineClass();
        new ServerConnectionASM_1_7(internal, SUFFIX).defineClass();
    }

}
