package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefUtils {

    public static Class<?> tryGetClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ParticleException(String.format(
                    "Class %s could not be found", className
            ));
        }
    }

    public static Method tryGetMethod(Class<?> clazz, String name, Class<?>... classes) {
        try {
            return clazz.getDeclaredMethod(name, classes);
        } catch (NoSuchMethodException e) {
            throw new ParticleException(String.format(
                    "Method %s of %s could not be found.",
                    name, clazz.getName()
            ));
        }
    }

    public static Object tryInvoke(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        } catch (InvocationTargetException e) {
            throw new ParticleException(String.format(
                    "Error occurred when invoking method %s on object of type %s",
                    method.getName(), obj.getClass().getName()
            ));
        } catch (IllegalAccessException e) {
            throw new ParticleException(String.format(
                    "Tried to invoke method %s on %s, however access is denied.",
                    method.getName(), obj.getClass().getName()
            ));
        }
    }

    public static Object tryGet(Object obj, Field f) {
        try {
            return f.get(obj);
        } catch (IllegalAccessException e) {
            throw new ParticleException(String.format(
                    "Tried to get field %s from %s, however access is denied.",
                    f.getName(), obj.getClass().getName()
            ));
        }
    }

}
