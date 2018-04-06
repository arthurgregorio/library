package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.application.components.MessageSource;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.slf4j.Logger;

/**
 * The base bean of the controllers of this application, this class contains all
 * the basic methods to all the controllers
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/03/2018
 */
public abstract class AbstractBean implements Serializable {

    @Inject
    protected Logger logger;

    @Inject
    protected FacesContext facesContext;
    @Inject
    protected RequestContext requestContext;

    /**
     * 
     * @return 
     */
    protected String getDefaultMessagesComponentId() {
        return "messages";
    }

    /**
     * 
     * @param message
     * @return 
     */
    protected String translate(String message) {
        return MessageSource.get(message);
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
     * 
     * @param widgetVar 
     */
    protected void openDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').show()");
    }

    /**
     * 
     * @param id
     * @param widgetVar 
     */
    protected void updateAndOpenDialog(String id, String widgetVar) {
        this.updateComponent(id);
        this.executeScript("PF('" + widgetVar + "').show()");
    }

    /**
     * 
     * @param widgetVar 
     */
    protected void closeDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').hide()");
    }

    /**
     * 
     */
    protected void updateDefaultMessages() {
        this.temporizeHiding(this.getDefaultMessagesComponentId());
    }

    /**
     * 
     * @param componentId 
     */
    protected void temporizeHiding(String componentId) {
        this.executeScript("setTimeout(\"$(\'#" + componentId + "\').slideUp(300)\", 8000)");
    }

    /**
     * 
     * @param componentId 
     */
    protected void updateComponent(String componentId) {
        PrimeFaces.current().ajax().update(componentId);
    }

    /**
     * 
     * @param script 
     */
    protected void executeScript(String script) {
        PrimeFaces.current().executeScript(script);
    }
}
