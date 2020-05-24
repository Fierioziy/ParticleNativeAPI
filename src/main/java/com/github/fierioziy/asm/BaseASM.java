package com.github.fierioziy.asm;

import org.objectweb.asm.*;

/**
 * <p>A base class for all ASM related classes. It holds basic fields
 * and helper methods for manipulating bytecode.</p>
 */
public class BaseASM implements Opcodes {

    /**
     * <p>Package version of NMS and OBC classes.</p>
     */
    protected String version;

    /**
     * <p>An internal path of NMS package.</p>
     */
    protected String NMS;

    /**
     * <p>An internal path of OBC package.</p>
     */
    protected String OBC;

    /**
     * <p>A class path of NMS package.</p>
     */
    protected String classNMS;

    /**
     * <p>A class path of OBC package.</p>
     */
    protected String classOBC;


    /**
     * <p>Constructs and instantiate helper fields.</p>
     * @param version a package version string.
     */
    public BaseASM(String version) {
        this.version = version;
        NMS = "net/minecraft/server/" + version;
        OBC = "org/bukkit/craftbukkit/" + version;

        classNMS = "net.minecraft.server." + version;
        classOBC = "org.bukkit.craftbukkit." + version;
    }

    /**
     * <p>Returns descriptor of parameter s internal name.</p>
     * <p>It's just a helper method for some NMS and OBC internal names.</p>
     *
     * @param s an internal name of a class.
     * @return a descriptor of parameter internal class name.
     */
    protected String desc(String s) {
        return "L" + s + ";";
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
