package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.api.PlayerConnection;
import com.github.fierioziy.particlenativeapi.api.ServerConnection;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Type;

/**
 * <p>A base class for all Connection ASM related classes. It holds basic fields
 * and helper methods for manipulating bytecode.</p>
 */
public class ConnectionBaseASM extends BaseASM {

    /**
     * <p>A <code>Type</code> object representing <code>ServerConnection</code> class.</p>
     */
    protected Type serverConnType =             Type.getType(ServerConnection.class);

    /**
     * <p>A <code>Type</code> object representing <code>PlayerConnection</code> class.</p>
     */
    protected Type playerConnType =             Type.getType(PlayerConnection.class);

    /**
     * <p>A <code>Type</code> object representing implementation
     * of <code>ServerConnection</code> class.</p>
     */
    protected Type serverConnTypeImpl =         getTypeImpl(serverConnType);

    /**
     * <p>A <code>Type</code> object representing implementation
     * of <code>PlayerConnection</code> class.</p>
     */
    protected Type playerConnTypeImpl =         getTypeImpl(playerConnType);

    public ConnectionBaseASM(InternalResolver resolver) {
        super(resolver);
    }

    /**
     * <p>Gets a <code>Type</code> object representing an implementation
     * of class represented by parameter <code>Type object</code>.</p>
     *
     * @param superType a <code>Type</code> object associated
     *                  with base class.
     * @return a <code>Type</code> object representing class
     * extending or implementing parameter's class represented
     * by <code>Type</code> object.
     */
    protected Type getTypeImpl(Type superType) {
        return getTypeImpl(superType, "_Impl");
    }

}
