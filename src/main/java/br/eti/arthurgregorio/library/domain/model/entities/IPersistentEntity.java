package br.eti.arthurgregorio.library.domain.model.entities;

import java.io.Serializable;

/**
 * The basic entity definition for all the classes in the application
 * 
 * @param <T> the type of
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 04/04/2018
 */
public interface IPersistentEntity<T extends Serializable> {

    /**
     * @return the ID of the entity
     */
    T getId();

    /**
     * @return if the entity is saved or not
     */
    boolean isSaved();
    
    /**
     * helper method to call validation on the entity
     */
    void validate();
    
    /**
     * @return ther inverse of {@link #isSaved()}
     */
    default boolean isNotSaved() {
        return !this.isSaved();
    }
}
