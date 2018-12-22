package br.eti.arthurgregorio.library.application.controllers;

/**
 * This enum represents the possible view states to control the JSF views/forms
 *
 * @author Arthur Gregorio
 *
 * @version 2.0.0
 * @since 1.0.0, 11/01/2018
 */
public enum ViewState {
    
    LISTING,
    ADDING,
    EDITING,
    DELETING,
    DETAILING;

    /**
     * If this is an editable state
     *
     * @return true if is, false if not
     */
    public boolean isEditable() {
        return this == EDITING || this == ADDING;
    }

    /**
     * To check if the state is EDITING
     *
     * @return true if is, false otherwise
     */
    public boolean isEditing() {
        return this == EDITING;
    }

    /**
     * To check if the state is ADDING
     *
     * @return true if is, false otherwise
     */
    public boolean isAdding() {
        return this == ADDING;
    }

    /**
     * To check if the state is DETAILING
     *
     * @return true if is, false otherwise
     */
    public boolean isDetailing() {
        return this == DETAILING;
    }

    /**
     * To check if the state is DELETING
     *
     * @return true if is, false otherwise
     */
    public boolean isDeleting() {
        return this == DELETING;
    }
}
