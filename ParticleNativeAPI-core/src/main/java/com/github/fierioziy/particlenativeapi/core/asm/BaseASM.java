package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.api.PlayerConnection;
import com.github.fierioziy.particlenativeapi.api.ServerConnection;
import com.github.fierioziy.particlenativeapi.api.types.*;
import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * <p>A base class for all ASM related classes. It holds basic fields
 * and helper methods for manipulating bytecode.</p>
 */
public class BaseASM implements Opcodes {

    protected static Type particleType =                Type.getType(ParticleType.class);
    protected static Type particleTypeBlock =           Type.getType(ParticleTypeBlock.class);
    protected static Type particleTypeBlockMotion =     Type.getType(ParticleTypeBlockMotion.class);
    protected static Type particleTypeColorable =       Type.getType(ParticleTypeColorable.class);
    protected static Type particleTypeMotion =          Type.getType(ParticleTypeMotion.class);
    protected static Type particleTypeDust =            Type.getType(ParticleTypeDust.class);
    protected static Type particleTypeDustTransition =  Type.getType(ParticleTypeDustTransition.class);
    protected static Type particleTypeItemMotion =      Type.getType(ParticleTypeItemMotion.class);
    protected static Type particleTypeNote =            Type.getType(ParticleTypeNote.class);
    protected static Type particleTypeRedstone =        Type.getType(ParticleTypeRedstone.class);
    protected static Type particleTypeVibration =       Type.getType(ParticleTypeVibration.class);

    protected static Type serverConnType =              Type.getType(ServerConnection.class);
    protected static Type playerConnType =              Type.getType(PlayerConnection.class);

    /**
     * <p>An internal class data resolver.</p>
     */
    protected InternalResolver internal;

    /**
     * <p>Constructs and instantiate helper fields.</p>
     * @param resolver an internal class data resolver.
     */
    public BaseASM(InternalResolver resolver) {
        this.internal = resolver;
    }

    protected String internalOther(String classPath) {
        return internal.getOther(classPath).getInternalName();
    }

    protected String internalNMS(String classPath) {
        return internal.getNMS(classPath).getInternalName();
    }

    protected String internalOBC(String classPath) {
        return internal.getOBC(classPath).getInternalName();
    }

    protected String descOther(String classPath) {
        return internal.getOther(classPath).getDescriptor();
    }

    protected String descNMS(String classPath) {
        return internal.getNMS(classPath).getDescriptor();
    }

    protected String descOBC(String classPath) {
        return internal.getOBC(classPath).getDescriptor();
    }


    protected String classNameNMS(String classPath) {
        return internal.getNMS(classPath).getClassName();
    }

    protected String classNameOBC(String classPath) {
        return internal.getOBC(classPath).getClassName();
    }

    /**
     * <p>Return a <code>Type</code> object representing a class name
     * with suffix added.</p>
     *
     * @param type a <code>Type</code> object of which
     *             new <code>Type</code> object should be created.
     * @param suffix a suffix which should be added to class name.
     * @return a <code>Type</code> object representing class with
     * added suffix to its name.
     */
    protected static Type getTypeImpl(Type type, String suffix) {
        return Type.getObjectType(type.getInternalName() + suffix);
    }

}
