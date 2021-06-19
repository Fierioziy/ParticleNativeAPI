package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.v1_7.PlayerConnectionASM_1_7;
import com.github.fierioziy.particlenativeapi.core.asm.v1_7.ServerConnectionASM_1_7;
import org.objectweb.asm.Type;

public class ConnectionsASM_1_7 extends ConnectionsASM {

    public ConnectionsASM_1_7(InternalResolver resolver) {
        super(resolver);
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_7(internal).defineClass();
        new ServerConnectionASM_1_7(internal).defineClass();
    }

    @Override
    protected Type getTypeImpl(Type interfaceType) {
        return getTypeImpl(interfaceType, "_1_7");
    }

}
