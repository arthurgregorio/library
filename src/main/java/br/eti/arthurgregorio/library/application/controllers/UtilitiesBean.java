package br.eti.arthurgregorio.library.application.controllers;

import br.eti.arthurgregorio.library.infrastructure.misc.Configurations;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
     * @return the application version from the configurations
     */
    public String getApplicationVersion() {
        return Configurations.get("application.version");
    }
    
    /**
     * @return the current year for the copyright text
     */
    public String getCurrentYear() {
        return DateTimeFormatter.ofPattern("yyyy").format(LocalDate.now());
    }
}
