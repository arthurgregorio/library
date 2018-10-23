package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.application.components.MessageSource;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import java.io.Serializable;

/**
 * The basic implementation of a controller/managed bean
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/03/2018
 */
public abstract class AbstractBean implements Serializable {

    @Inject
    protected FacesContext facesContext;

    /**
     * @return the name of the default messages component, by default is <code>messages</code>
     */
    protected String getDefaultMessagesComponentId() {
        return "messages";
    }

    /**
     * Method to translate i18n keys to the corresponding text
     *
     * @param i18nKey the i18n key
     * @return the i18nKey
     */
    protected String translate(String i18nKey) {
        return MessageSource.get(i18nKey);
    }

    /**
     * Add a info message to the {@link FacesContext}
     *
     * @param message the text of the message or the i18n key
     * @param parameters to be used in the message
     * @param updateDefault if the main message component needs to be updated
     */
    protected void addInfo(boolean updateDefault, String message, Object... parameters) {
        Messages.addInfo(null, this.translate(message), parameters);
        if (updateDefault) {
            this.updateDefaultMessages();
        }
    }

    /**
     * Add a info message to the {@link FacesContext} in the {@link Flash} scope
     *
     * @param message the text of the message or the i18n key
     * @param parameters to be used in the message
     */
    protected void addInfoAndKeep(String message, Object... parameters) {
        Messages.addInfo(null, this.translate(message), parameters);
        this.facesContext.getExternalContext().getFlash().setKeepMessages(true);
    }

    /**
     * Add a error message to the {@link FacesContext}
     *
     * @param message the text of the message or the i18n key
     * @param parameters to be used in the message
     * @param updateDefault if the main message component needs to be updated
     */
    protected void addError(boolean updateDefault, String message, Object... parameters) {
        Messages.addError(null, this.translate(message), parameters);
        if (updateDefault) {
            this.updateDefaultMessages();
        }
    }

    /**
     * Convenience method to open dialogs
     *
     * @param widgetVar the name of the dialog to be opened
     */
    protected void openDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').show()");
    }

    /**
     * Same as {@link #openDialog(String)} but before open it, a update by the component ID is performed
     *
     * @param id the dialog component id
     * @param widgetVar the name of the dialog to be opened
     */
    protected void updateAndOpenDialog(String id, String widgetVar) {
        this.updateComponent(id);
        this.executeScript("PF('" + widgetVar + "').show()");
    }

    /**
     * Convenience method to close dialogs
     *
     * @param widgetVar the name of the dialog to close
     */
    protected void closeDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').hide()");
    }

    /**
     * Update the default messages component
     */
    protected void updateDefaultMessages() {
        this.temporizeHiding(this.getDefaultMessagesComponentId());
    }

    /**
     * Method to put a timer in and after the time expires, hide the component
     *
     * @param componentId the component id
     */
    protected void temporizeHiding(String componentId) {
        this.executeScript("setTimeout(\"$(\'#" + componentId + "\').slideUp(300)\", 8000)");
    }

    /**
     * Convenience method to update one component by the client id
     *
     * @param componentId the id of the component
     */
    protected void updateComponent(String componentId) {
        PrimeFaces.current().ajax().update(componentId);
    }

    /**
     * Convenience method to execute scripts on the front-end
     *
     * @param script the script to be executed
     */
    protected void executeScript(String script) {
        PrimeFaces.current().executeScript(script);
    }
}
