package com.github.fierioziy.particlenativeapi.api.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Indicates that returned object is one and the same instance with changed state
 * and is shared between method calls.</p>
 *
 * <p>Generally, any element annotated with this annotation provides a <code>detachCopy</code> method
 * to create an independent copy of that element.</p>
 */
@Target({ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Shared {

}
