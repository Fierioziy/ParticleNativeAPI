package com.github.fierioziy.particlenativeapi.core.mocks.nms.v1_17;

public class MinecraftKey {

    public String namespace = "minecraft";
    public String key;

    public MinecraftKey(String key) {
        this.key = key;
    }

    public String getNamespace_possiblyObfuscated() {
        return namespace;
    }

    public String getKey_possiblyObfuscated() {
        return key;
    }

    @Override
    public String toString() {
        return namespace + ":" + key;
    }

}
