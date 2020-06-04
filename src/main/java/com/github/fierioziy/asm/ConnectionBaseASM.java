package com.github.fierioziy.asm;

import com.github.fierioziy.api.PlayerConnection;
import com.github.fierioziy.api.PlayerConnectionArray;
import com.github.fierioziy.api.ServerConnection;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
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
     * <p>A <code>Type</code> object representing <code>PlayerConnectionArray</code> class.</p>
     */
    protected Type playerConnArrType =          Type.getType(PlayerConnectionArray.class);

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

    /**
     * <p>A <code>Type</code> object representing implementation
     * of <code>PlayerConnectionArray</code> class.</p>
     */
    protected Type playerConnArrTypeImpl =      getTypeImpl(playerConnArrType);

    public ConnectionBaseASM(String version) {
        super(version);
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
