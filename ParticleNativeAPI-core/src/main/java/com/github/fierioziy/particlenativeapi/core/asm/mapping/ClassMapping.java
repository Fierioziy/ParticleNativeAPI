package com.github.fierioziy.particlenativeapi.core.asm.mapping;

public interface ClassMapping {
    String internalName();
    String className();
    String desc();

    ClassMapping impl(String suffix);
}
