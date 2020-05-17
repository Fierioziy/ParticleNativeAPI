package me.fierioziy.utils;

/**
 * <p>A basic classloader with public method for class definition.</p>
 */
public class TempClassLoader extends ClassLoader {

    /**
     * <p>Construct a new class loader linked to parameter class loader.</p>
     *
     * @param parent a parent class loader to which this
     *               class loader belongs.
     */
    public TempClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * <p>Defines a class with parameter name and bytecode
     * stored in {@code byte[]} array.</p>
     *
     * @param name name of the class to define.
     * @param b a {@code byte[]} array containing bytecode of class
     *          to define.
     * @return a {@code Class<?>} object made
     * from provided bytecode.
     */
    public Class<?> defineClass(String name, byte[] b) {
        return super.defineClass(name, b, 0, b.length);
    }
}
