package br.eti.arthurgregorio.library.infrastructure.jaxrs;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Class to activate JAX-RS REST endpoints in the project
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 12/12/2018
 */
@ApplicationPath("api")
public class RestConfiguration extends Application { }