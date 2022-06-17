package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Type;

public abstract class ConnectionsASM extends BaseASM {

    private final String suffix;

    public ConnectionsASM(InternalResolver resolver, String suffix) {
        super(resolver);
        this.suffix = suffix;
    }

    /**
     * <p>Defines all classes of particle types.</p>
     */
    public abstract void defineClasses();

    /**
     * <p>Gets a <code>Type</code> object representing
     * class implementation of parameter <code>Type</code> object
     * class representation.</p>
     *
     * @param interfaceType a <code>Type</code> object representing
     * certain class.
     *
     * @return a <code>Type</code> object representing class that implements
     *         class represented by parameter <code>Type</code> object.
     */
    public final Type getTypeImpl(Type interfaceType) {
        return getTypeImpl(interfaceType, suffix);
    }

}
