package br.eti.arthurgregorio.library.infrastructure.soteria.hash;

import lombok.Getter;

import javax.inject.Qualifier;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/11/2018
 */
@Qualifier
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface SHALevel {

    /**
     *
     * @return
     */
    Level value() default Level.SHA512;

    /**
     *
     */
    enum Level {

        SHA256("SHA-256"), SHA512("SHA-512");

        @Getter
        private final String level;

        /**
         *
         * @param algorithmName
         */
        Level(String algorithmName) {
            this.level = algorithmName;
        }
    }
}
