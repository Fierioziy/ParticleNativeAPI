package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;

public abstract class ConnectionsProvider extends BaseASM {

    public ConnectionsProvider(InternalResolver resolver, String suffix) {
        super(resolver, suffix);
    }

    public abstract void defineClasses();

}
