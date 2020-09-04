package com.github.fierioziy.particlenativeapi.core.asm;

import com.github.fierioziy.particlenativeapi.core.asm.utils.InternalResolver;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * <p>A base class for all ASM related classes. It holds basic fields
 * and helper methods for manipulating bytecode.</p>
 */
public class BaseASM implements Opcodes {

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

    protected String internalNMS(String classPath) {
        return internal.getNMS(classPath).getInternalName();
    }

    protected String internalOBC(String classPath) {
        return internal.getOBC(classPath).getInternalName();
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
    protected Type getTypeImpl(Type type, String suffix) {
        return Type.getObjectType(type.getInternalName() + suffix);
    }

}
