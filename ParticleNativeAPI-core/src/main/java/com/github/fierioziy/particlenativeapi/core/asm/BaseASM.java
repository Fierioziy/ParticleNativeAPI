package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.mapping.ClassRegistry;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Opcodes;

public class BaseASM implements Opcodes {

    protected final InternalResolver internal;
    protected final ClassRegistry refs;

    protected final String suffix;

    public BaseASM(InternalResolver resolver, String suffix) {
        this.internal = resolver;
        this.refs = internal.refs;
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }

}
