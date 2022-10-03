package com.github.fierioziy.particlenativeapi.core.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>A basic classloader with public method for class definition.</p>
 */
public class ParticleNativeClassLoader extends ClassLoader {

    private Map<String, byte[]> classBytecodeMap = new HashMap<>();

    /**
     * <p>Construct a new class loader linked to parameter class loader.</p>
     *
     * @param parent a parent class loader to which this
     *               class loader belongs.
     */
    public ParticleNativeClassLoader(ClassLoader parent) {
        super(parent);
    }

    /**
     * <p>Defines a class with parameter name and bytecode
     * stored in {@code byte[]} array.</p>
     *
     * @param name name of the class to define.
     * @param bytecode a {@code byte[]} array containing bytecode of class
     *          to define.
     */
    public void registerClass(String name, byte[] bytecode) {
        classBytecodeMap.put(name, bytecode);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        if (classBytecodeMap.containsKey(name)) {
            byte[] bytecode = classBytecodeMap.remove(name);
            return super.defineClass(name, bytecode, 0, bytecode.length);
        }
        return super.findClass(name);
    }

}
