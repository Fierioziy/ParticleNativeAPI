package com.github.fierioziy.particlenativeapi.core.asm.utils;

import com.github.fierioziy.particlenativeapi.api.utils.ParticleException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefUtils {

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
                    method.getName(), obj != null ? obj.getClass().getName() : "null"
            ), e);
        } catch (IllegalAccessException e) {
            throw new ParticleException(String.format(
                    "Tried to invoke method %s on %s, however access is denied.",
                    method.getName(), obj != null ? obj.getClass().getName() : "null"
            ));
        }
    }

    public static Object tryGet(Object obj, Field f) {
        try {
            return f.get(obj);
        } catch (IllegalAccessException e) {
            throw new ParticleException(String.format(
                    "Tried to get field %s from %s, however access is denied.",
                    f.getName(), obj != null ? obj.getClass().getName() : "null"
            ));
        }
    }

    public static String tryInferFieldName(Class<?> clazz, Class<?> fieldType) {
        String fieldName = null;
        for (Field field : clazz.getDeclaredFields()) {
            // avoid compiler-generated fields
            if (field.isSynthetic()) {
                continue;
            }

            // match type
            if (!fieldType.equals(field.getType())) {
                continue;
            }

            // we assume that there will be only one matching field
            if (fieldName == null) {
                fieldName = field.getName();
            }
            else {
                throw new ParticleException(String.format(
                        "Ambiguous names (%s) for field %s in %s class.",
                        fieldName + ", " + field.getName(), fieldType.getSimpleName(), clazz.getName()
                ));
            }
        }

        if (fieldName == null) {
            throw new ParticleException(String.format(
                    "Could not find name for field %s in %s class.",
                    fieldType.getName(), clazz.getName()
            ));
        }

        return fieldName;
    }

    public static String tryInferMethodName(Class<?> clazz, Class<?> returnType, Class<?>... paramTypes) {
        String methodName = null;

        methodsLabel:
        for (Method method : clazz.getDeclaredMethods()) {
            // avoid non-bridge compiler-generated methods
            if (!method.isBridge() && method.isSynthetic()) {
                continue;
            }

            // match return type
            if (!returnType.equals(method.getReturnType())) {
                continue;
            }

            // match parameter count
            Class<?>[] methodParamTypes = method.getParameterTypes();
            if (methodParamTypes.length != paramTypes.length) {
                continue;
            }

            // match parameter types
            for (int i = 0; i < paramTypes.length; ++i) {
                if (!paramTypes[i].equals(methodParamTypes[i])) {
                    continue methodsLabel;
                }
            }

            // we assume that there will be only one matching method
            if (methodName == null) {
                methodName = method.getName();
            }
            else {
                throw new ParticleException(String.format(
                        "Ambiguous names (%s) for %s method(%s) in %s class.",
                        methodName + ", " + method.getName(),
                        returnType.getSimpleName(), joinedSimpleNames(paramTypes), clazz.getName()
                ));
            }
        }

        if (methodName == null) {
            throw new ParticleException(String.format(
                    "Could not find name for %s method(%s) in %s class.",
                    returnType.getSimpleName(), joinedSimpleNames(paramTypes), clazz.getName()
            ));
        }

        return methodName;
    }

    private static String joinedSimpleNames(Class<?>... paramTypes) {
        StringBuilder classNames = new StringBuilder();
        for (int i = 0; i < paramTypes.length - 1; ++i) {
            classNames.append(paramTypes[i].getSimpleName()).append(", ");
        }
        if (paramTypes.length > 0) {
            classNames.append(paramTypes[paramTypes.length - 1].getSimpleName());
        }
        return classNames.toString();
    }

}
