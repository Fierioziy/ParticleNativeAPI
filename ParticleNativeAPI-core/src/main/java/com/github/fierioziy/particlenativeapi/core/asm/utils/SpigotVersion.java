package com.github.fierioziy.particlenativeapi.core.asm.utils;

public enum SpigotVersion {

    V1_7("_1_7"),
    V1_8("_1_8"),
    V1_13("_1_13"),
    V1_15("_1_15"),
    V1_17("_1_17"),
    V1_18("_1_18"),
    V1_19("_1_19"),
    V1_19_3("_1_19_3"),
    V1_20_2("_1_20_2"),
    V1_20_5("_1_20_5"),
    V1_21_3("_1_21_3"),
    V1_21_4("_1_21_4"),
    V1_21_10("_1_21_10"),
    ;

    private final String suffix;

    SpigotVersion(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

    public boolean between(SpigotVersion lower, SpigotVersion higher) {
        return ordinal() >= lower.ordinal() && ordinal() <= higher.ordinal();
    }

}
