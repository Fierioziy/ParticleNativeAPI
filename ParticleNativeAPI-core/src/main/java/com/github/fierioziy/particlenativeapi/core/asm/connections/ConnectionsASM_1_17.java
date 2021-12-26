package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.v1_17.PlayerConnectionASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.v1_17.ServerConnectionASM_1_17;
import org.objectweb.asm.Type;

public class ConnectionsASM_1_17 extends ConnectionsASM {

    private String playerConnectionFieldName;
    private String sendPacketMethodName;

    public ConnectionsASM_1_17(InternalResolver resolver) {
        super(resolver);
        playerConnectionFieldName = resolver.getPlayerConnectionFieldName_1_17();
        sendPacketMethodName = resolver.getSendPacketMethodName_1_18();
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_17(internal, playerConnectionFieldName, sendPacketMethodName).defineClass();
        new ServerConnectionASM_1_17(internal, playerConnectionFieldName, sendPacketMethodName).defineClass();
    }

    @Override
    public Type getTypeImpl(Type interfaceType) {
        return getTypeImpl(interfaceType, "_1_17");
    }

}
