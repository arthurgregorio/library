package br.eti.arthurgregorio.library.infrastructure.cdi;

import org.primefaces.context.RequestContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;

/**
 * The faces resources producer 
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 18/12/2017
 */
@ApplicationScoped
public class FacesResourcesProducer {

    /**
     * @return the request context instance for injection
     */
    @Produces
    @RequestScoped
    RequestContext produceRequestContext() {
        return RequestContext.getCurrentInstance();
    }
    
    /**
     * @return the faces context instance for injection
     */
    @Produces
    @RequestScoped
    FacesContext produceFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}
