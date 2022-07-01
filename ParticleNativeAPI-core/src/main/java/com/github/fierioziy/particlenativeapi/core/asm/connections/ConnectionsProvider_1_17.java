package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_17.PlayerConnectionASM_1_17;
import com.github.fierioziy.particlenativeapi.core.asm.connections.v1_17.ServerConnectionASM_1_17;

public class ConnectionsProvider_1_17 extends ConnectionsProvider {

    private final String playerConnectionFieldName;
    private final String sendPacketMethodName;

    public ConnectionsProvider_1_17(InternalResolver resolver) {
        super(resolver, "_1_17");
        playerConnectionFieldName = resolver.getPlayerConnectionFieldName_1_17();
        sendPacketMethodName = resolver.getSendPacketMethodName_1_18();
    }

    @Override
    public void defineClasses() {
        new PlayerConnectionASM_1_17(internal, suffix, playerConnectionFieldName, sendPacketMethodName).defineClass();
        new ServerConnectionASM_1_17(internal, suffix, playerConnectionFieldName, sendPacketMethodName).defineClass();
    }

}
