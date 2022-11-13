package com.github.fierioziy.particlenativeapi.core.asm.mapping;

import org.objectweb.asm.Type;

public class RegisteredClassMapping implements ClassMapping {

    private final String internalName;
    private final String className;
    private final String desc;

    public RegisteredClassMapping(String internalName) {
        this.internalName = internalName;

        Type type = Type.getObjectType(internalName);
        this.className = type.getClassName();
        this.desc = type.getDescriptor();
    }

    public RegisteredClassMapping(Class<?> clazz) {
        Type type = Type.getType(clazz);
        this.internalName = type.getInternalName();
        this.className = type.getClassName();
        this.desc = type.getDescriptor();
    }

    @Override
    public String internalName() {
        return internalName;
    }

    @Override
    public String className() {
        return className;
    }

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RegisteredClassMapping))
            return false;

        RegisteredClassMapping that = (RegisteredClassMapping) o;

        return internalName.equals(that.internalName);
    }

    @Override
    public int hashCode() {
        return internalName.hashCode();
    }

    @Override
    public String toString() {
        return className;
    }
}
