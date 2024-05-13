package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_20_2;

import com.github.fierioziy.particlenativeapi.core.mocks.nms.common.Packet;

import java.util.Optional;

public abstract class ServerCommonPacketListenerImpl_1_20_2 {

    /*
     * Some spigot/bukkit forks contains lambda method with matching types
     * same as send(Packet) method.
     * Here, this certain behavior is reproduced.
     *
     * Compiler will generate a synthetic method representing lambda
     * with same signature as sendPacket_obf(Packet) method.
     */

    public void doLambdaStuff() {
        Optional.<Packet>empty().ifPresent(packet -> {
            String quack = "Moo";
        });
    }

    // required
    public void sendPacket_obf(Packet packet) {}

}
