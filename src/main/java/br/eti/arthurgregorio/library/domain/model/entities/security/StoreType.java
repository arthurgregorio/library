package br.eti.arthurgregorio.library.domain.model.entities.security;

/**
 * The enum to hold the type of the authentication to be selected on the form
 * to create new users
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/03/2018
 */
public enum StoreType {

    LDAP("store-type.ldap"),
    LOCAL("store-type.database");
    
    private final String description;

    /**
     * Constructor...
     *
     * @param description i18n description
     */
    private StoreType(String description) {
        this.description = description;
    }

    /**
     * @return the i18n key
     */
    @Override
    public String toString() {
        return this.description;
    }
}

