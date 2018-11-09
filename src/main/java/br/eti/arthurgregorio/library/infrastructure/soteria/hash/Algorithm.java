package br.eti.arthurgregorio.library.infrastructure.soteria.hash;

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
 * @since 1.0.0, 06/11/2018
 */
@Qualifier
@Documented
@Retention(RUNTIME)
@Target({METHOD, FIELD, PARAMETER, TYPE})
public @interface Algorithm {

    AlgorithmType value() default AlgorithmType.BCRYPT;

    /**
     *
     */
    enum AlgorithmType {
        SHA, BCRYPT, PBKDF2
    }
}
