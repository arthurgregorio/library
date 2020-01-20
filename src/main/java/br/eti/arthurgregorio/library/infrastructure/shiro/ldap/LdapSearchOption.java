package br.eti.arthurgregorio.library.infrastructure.shiro.ldap;

import lombok.Getter;

import java.text.MessageFormat;

/**
 * Options for LDAP/AD search
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.3.0, 20/01/2020
 */
public enum LdapSearchOption {

    ALL_ACTIVE("ldap-search-option.all-active") {
        /**
         * {@inheritDoc}
         *
         * @param parameter
         * @return
         */
        @Override
        public String build(String parameter) {
            return MessageFormat.format(FILTER, "mail=*");
        }
    },
    BY_EMAIL("ldap-search-option.by-email") {
        /**
         * {@inheritDoc}
         *
         * @param parameter
         * @return
         */
        @Override
        public String build(String parameter) {
            return MessageFormat.format(FILTER, "mail=" + parameter + "*");
        }
    },
    BY_NAME("ldap-search-option.by-name") {
        /**
         * {@inheritDoc}
         *
         * @param parameter
         * @return
         */
        @Override
        public String build(String parameter) {
            return MessageFormat.format(FILTER, "displayName=" + parameter + "*");
        }
    },
    BY_DEPARTMENT("ldap-search-option.by-department") {
        /**
         * {@inheritDoc}
         *
         * @param parameter
         * @return
         */
        @Override
        public String build(String parameter) {
            return MessageFormat.format(FILTER, "department=" + parameter + "*");
        }
    };

    @Getter
    private String description;

    /**
     * Constructor...
     *
     * @param description i18n key for this enum
     */
    LdapSearchOption(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String toString() {
        return this.description;
    }

    /**
     * Hook method to build the search query
     *
     * @param parameter to be used inside the query
     * @return the query ready to use
     */
    public abstract String build(String parameter);

    private static final String FILTER = "(&(objectClass=person)({0})(|(UserAccountControl=512)(UserAccountControl=544)" +
            "(UserAccountControl=66048)(UserAccountControl=66080))(!(memberOf=OU=Colaboradores,DC=fpti,DC=pti,DC=org,DC=br)))";
}
