package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_17.PlayerConnectionASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_17.ServerConnectionASM_1_17;

public class ConnectionsASM_1_17 extends ConnectionsASM {

    private static final String SUFFIX = "_1_17";

    private String playerConnectionFieldName;
    private String sendPacketMethodName;

    public ConnectionsASM_1_17(InternalResolver resolver) {
        super(resolver, SUFFIX);
        playerConnectionFieldName = resolver.getPlayerConnectionFieldName_1_17();
        sendPacketMethodName = resolver.getSendPacketMethodName_1_18();
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_17(internal, SUFFIX, playerConnectionFieldName, sendPacketMethodName).defineClass();
        new ServerConnectionASM_1_17(internal, SUFFIX, playerConnectionFieldName, sendPacketMethodName).defineClass();
    }

}
