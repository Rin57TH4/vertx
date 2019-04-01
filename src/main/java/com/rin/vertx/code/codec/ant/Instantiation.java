package com.rin.vertx.code.codec.ant;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by duongittien
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Instantiation {
    Instantiation.InstantiationMode value() default Instantiation.InstantiationMode.NEW_INSTANCE;

    public static enum InstantiationMode {
        NEW_INSTANCE,
        SINGLE_INSTANCE;

        private InstantiationMode() {
        }
    }
}
