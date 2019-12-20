package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.application.components.ui.AbstractBean;
import br.eti.arthurgregorio.library.infrastructure.misc.Configurations;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * Utilities controller to provide common data to all the pages in the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 11/01/2018
 */
@Named
@RequestScoped
public class UtilitiesBean extends AbstractBean {

    /**
     * Used to retrieve the application version
     *
     * @return application version text from configurations
     */
    public String getApplicationVersion() {
        return Configurations.get("application.version");
    }
}
