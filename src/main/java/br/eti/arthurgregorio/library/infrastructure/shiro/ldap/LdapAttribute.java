package br.eti.arthurgregorio.library.infrastructure.shiro.ldap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * LDAP attribute annotation binder
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.3.0, 20/01/2020
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LdapAttribute {

    /**
     * The value for this annotation
     *
     * @return {@link String} value for this LDAP Attribute
     */
    String value() default "";
}
