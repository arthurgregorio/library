package br.eti.arthurgregorio.library.infrastructure.jsf;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;

/**
 * The class used to activate the JSF 2.3 implementation on the project
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 27/09/2018
 */
@ApplicationScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class FacesActivator { }
