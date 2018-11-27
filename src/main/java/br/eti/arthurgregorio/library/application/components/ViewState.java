package br.eti.arthurgregorio.library.application.components;

/**
 * This enum represents the possible view states to control the JSF views/forms
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
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
}
