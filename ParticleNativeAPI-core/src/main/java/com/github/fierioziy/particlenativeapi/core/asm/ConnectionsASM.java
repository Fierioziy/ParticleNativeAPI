package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Type;

public abstract class ConnectionsASM extends BaseASM {

    /**
     * <p>Constructs and instantiate helper fields.</p>
     *
     * @param resolver an internal class data resolver.
     */
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
         *                  certain class.
     *
     * @return a <code>Type</code> object representing class that implements
     *         class represented by parameter <code>Type</code> object.
     */
    protected abstract Type getTypeImpl(Type interfaceType);

}
