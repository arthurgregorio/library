/*
 * Copyright 2017 Arthur Gregorio, AG.Software.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.eti.arthurgregorio.library.application.controllers;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import lombok.Getter;
import org.omnifaces.util.Messages;
import org.primefaces.context.RequestContext;

/**
 * Abstract implementation of all managed beans for this application, with this
 * class some of the repetitive operations are shared through the other
 * managed beans
 *
 * @author Arthur Gregorio
 *
 * @since 1.0.0
 * @version 1.0.0, 15/12/2017
 */
public abstract class AbstractBean implements Serializable {

    @Getter
    protected ViewState viewState;
    
    @Inject
    private FacesContext facesContext;
    @Inject
    private RequestContext requestContext;
    
    /**
     * 
     */
    private void updateDefaultMessages() {
        if (this.getDefaultMessagesComponentId() != null 
                && !this.getDefaultMessagesComponentId().isEmpty()) {
            this.temporizeHiding(this.getDefaultMessagesComponentId());
        }
    }
    
    /**
     * 
     * @return 
     */
    protected String getDefaultMessagesComponentId() {
        return "messages";
    }
    
    /**
     * 
     * @param componentId 
     */
    protected void updateComponent(String componentId) {
        this.requestContext.update(componentId);
    }

    /**
     * 
     * @param script 
     */
    protected void executeScript(String script) {
        this.requestContext.execute(script);
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
     * @param componentId 
     */
    protected void temporizeHiding(String componentId) {
        this.updateComponent(componentId);
        this.executeScript("setTimeout(\"$(\'#" + componentId + "\').slideUp(300)\", 8000)");
    }

    /**
     * 
     * @param url 
     */
    protected void redirectTo(String url) {
        try {
            this.facesContext.getExternalContext().redirect(url);
        } catch (IOException ex) {
            throw new RuntimeException(
                    String.format("Can't redirect to url [%s]", url));
        }
    }
    
    /**
     * 
     * @param updateDefault
     * @param message
     * @param parameters 
     */
    protected void addInfo(boolean updateDefault, String message, Object... parameters) {
        Messages.addInfo(null, message, parameters);
        if (updateDefault) this.updateDefaultMessages();
    }
    
    /**
     * 
     * @param updateDefault
     * @param message
     * @param parameters 
     */
    protected void addError(boolean updateDefault, String message, Object... parameters) {
        Messages.addError(null, message, parameters);
        if (updateDefault) this.updateDefaultMessages();
    }
    
    /**
     * 
     * @param updateDefault
     * @param message
     * @param parameters 
     */
    protected void addWarning(boolean updateDefault, String message, Object... parameters) {
        Messages.addWarn(null, message, parameters);
        if (updateDefault) this.updateDefaultMessages();
    }

    /**
     * 
     */
    protected enum ViewState {
        ADDING,
        LISTING,
        INSERTING,
        EDITING,
        DELETING,
        DETAILING;
    }
}
