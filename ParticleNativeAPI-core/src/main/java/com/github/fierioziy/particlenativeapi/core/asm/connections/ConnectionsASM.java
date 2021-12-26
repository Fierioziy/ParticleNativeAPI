package com.github.fierioziy.particlenativeapi.core.asm.connections;

import com.github.fierioziy.particlenativeapi.core.asm.BaseASM;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Type;

public abstract class ConnectionsASM extends BaseASM {

    public ConnectionsASM(InternalResolver resolver) {
        super(resolver);
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
    public abstract Type getTypeImpl(Type interfaceType);

}
