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
     * Caso o nome do componente default de mensagens tenha sido setado, este
     * metodo invocado apos adicionar mensagens faz com que ele seja atualizado
     * automaticamente
     */
    private void updateDefaultMessages() {
        if (this.getDefaultMessagesComponentId() != null 
                && !this.getDefaultMessagesComponentId().isEmpty()) {
            this.temporizeHiding(this.getDefaultMessagesComponentId());
        }
    }
    
    /**
     * @return o nome do componente default de mensagens da view
     */
    protected String getDefaultMessagesComponentId() {
        return "messages";
    }
    
    /**
     * Atualiza um componente pelo seu id no contexto atual
     *
     * @param componentId o id do componente
     */
    protected void updateComponent(String componentId) {
        this.requestContext.update(componentId);
    }

    /**
     * Executa um JavaScript na pagina pelo FacesContext atual
     *
     * @param script o script a ser executado
     */
    protected void executeScript(String script) {
        this.requestContext.execute(script);
    }

    /**
     * Apenas abre uma dialog pelo seu widgetvar
     * 
     * @param widgetVar o widgetvar para abri-la
     */
    protected void openDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').show()");
    }
    
    /**
     * Dado o id de um dialog, atualiza a mesma e depois abre pelo widgetvar
     * 
     * @param id o id da dialog para atualiza-la
     * @param widgetVar o widgetvar para abri-la
     */
    protected void updateAndOpenDialog(String id, String widgetVar) {
        this.updateComponent(id);
        this.executeScript("PF('" + widgetVar + "').show()");
    }

    /**
     * Fecha uma dialog aberta previamente
     *
     * @param widgetVar o widgetvar da dialog
     */
    protected void closeDialog(String widgetVar) {
        this.executeScript("PF('" + widgetVar + "').hide()");
    }

    /**
     * Dado um componente, atualiza o mesmo e depois temporiza o seu fechamento
     * 
     * @param componentId o id do componente
     */
    protected void temporizeHiding(String componentId) {
        this.updateComponent(componentId);
        this.executeScript("setTimeout(\"$(\'#" + componentId + "\').slideUp(300)\", 8000)");
    }

    /**
     * Redireciona o usuario para um determinada URL, caso haja um erro, loga 
     * 
     * @param url a url para o cara ser redirecionado
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
     * Adiciona uma mensagem de informacao na tela
     * 
     * @param message a mensagem
     * @param parameters os parametros da mensagem
     * @param updateDefault se devemos ou nao atualizar o componente default
     */
    protected void addInfo(boolean updateDefault, String message, Object... parameters) {
        Messages.addInfo(null, message, parameters);
        if (updateDefault) this.updateDefaultMessages();
    }
    
    /**
     * Adiciona uma mensagem de erro na tela
     * 
     * @param message a mensagem
     * @param parameters os parametros da mensagem
     * @param updateDefault se devemos ou nao atualizar o componente default
     */
    protected void addError(boolean updateDefault, String message, Object... parameters) {
        Messages.addError(null, message, parameters);
        if (updateDefault) this.updateDefaultMessages();
    }
    
    /**
     * Adiciona uma mensagem de aviso na tela
     * 
     * @param message a mensagem
     * @param parameters os parametros da mensagem
     * @param updateDefault se devemos ou nao atualizar o componente default
     */
    protected void addWarning(boolean updateDefault, String message, Object... parameters) {
        Messages.addWarn(null, message, parameters);
        if (updateDefault) this.updateDefaultMessages();
    }

    /**
     * Enum para controle do estado de execucao da tela
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
