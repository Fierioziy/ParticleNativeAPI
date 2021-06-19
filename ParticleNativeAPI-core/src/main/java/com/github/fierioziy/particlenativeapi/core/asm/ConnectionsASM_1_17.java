package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.v1_17.PlayerConnectionASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.v1_17.ServerConnectionASM_1_17;
import org.objectweb.asm.Type;

public class ConnectionsASM_1_17 extends ConnectionsASM {

    private String playerConnectionFieldName;

    public ConnectionsASM_1_17(InternalResolver resolver) {
        super(resolver);
        playerConnectionFieldName = resolver.getPlayerConnectionFieldName_1_17();
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_17(internal, playerConnectionFieldName).defineClass();
        new ServerConnectionASM_1_17(internal, playerConnectionFieldName).defineClass();
    }

    @Override
    protected Type getTypeImpl(Type interfaceType) {
        return getTypeImpl(interfaceType, "_1_17");
    }

}
