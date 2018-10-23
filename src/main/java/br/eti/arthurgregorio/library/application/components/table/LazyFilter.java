package br.eti.arthurgregorio.library.application.components.table;

import lombok.Getter;
import lombok.Setter;

/**
 * This filter implementation is a helper class to help the lazy loading feature of the data tables
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 21/03/2018
 */
public final class LazyFilter {

    @Getter
    @Setter
    public String value;
    @Getter
    @Setter
    public EntityStatus entityStatus;

    /**
     * Private constructor to prevent misuse 
     */
    private LazyFilter() {
       this.entityStatus = EntityStatus.ACTIVE; 
    }
    
    /**
     * This replace the default constructor to build instances of this filter
     * 
     * @return a instance of this filter
     */
    public static LazyFilter getInstance() {
        return new LazyFilter();
    }

    /**
     * Clear the filter internal parameters
     */
    public void clear() {
        this.value = null;
        this.entityStatus = EntityStatus.ACTIVE;
    }
    
    /**
     * The status value, if the entity to be queried is blocked, unblocked or 
     * if all entities will returned
     * 
     * @return the status value
     */
    public Boolean getEntityStatusValue() {
        return entityStatus.value();
    }
    
    /**
     * @return the values to be used on the selection box of the status
     */
    public EntityStatus[] getEntityStatusValues() {
        return EntityStatus.values();
    }
    
    /**
     * The enum representation of the possible entity status
     */
    public enum EntityStatus {

        ALL("entity-status.all", null),
        ACTIVE("entity-status.active", Boolean.TRUE),
        INACTIVE("entity-status.inactive", Boolean.FALSE);

        private final Boolean value;
        private final String description;

        /**
         * Constructor...
         *
         * @param description the i18n description
         * @param value the value
         */
        private EntityStatus(String description, Boolean value) {
            this.value = value;
            this.description = description;
        }

        /**
         * {@inheritDoc }
         * 
         * @return
         */
        @Override
        public String toString() {
            return this.description;
        }

        /**
         * @return the value of the current enum instance
         */
        public Boolean value() {
            return this.value;
        }
    }
}
