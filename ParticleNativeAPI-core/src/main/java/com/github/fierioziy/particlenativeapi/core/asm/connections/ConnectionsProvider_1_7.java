package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_7.PlayerConnectionASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_7.ServerConnectionASM_1_7;

public class ConnectionsProvider_1_7 extends ConnectionsProvider {


    public ConnectionsProvider_1_7(InternalResolver resolver) {
        super(resolver, "_1_7");
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_7(internal, suffix).defineClass();
        new ServerConnectionASM_1_7(internal, suffix).defineClass();
    }

}
