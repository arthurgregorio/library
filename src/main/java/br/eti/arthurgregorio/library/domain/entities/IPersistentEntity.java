package br.eti.arthurgregorio.library.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

/**
 * The basic entity definition for all the classes in the application
 * 
 * @param <T> type of this entity
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 04/04/2018
 */
public interface IPersistentEntity<T extends Serializable> {

    /**
     * Get the current entity ID
     *
     * @return the ID of the entity
     */
    T getId();

    /**
     * Use this method to check if this entity is saved or not
     *
     * @return if the entity is saved or not
     */
    @JsonIgnore
    boolean isSaved();
}
