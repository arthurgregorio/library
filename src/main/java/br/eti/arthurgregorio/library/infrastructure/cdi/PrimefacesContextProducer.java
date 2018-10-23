package br.eti.arthurgregorio.library.infrastructure.cdi;

import org.primefaces.context.RequestContext;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;

/**
 * The faces resources producer 
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
@ApplicationScoped
public class PrimefacesContextProducer {

    /**
     * @return the request context instance for injection
     */
    @Produces
    @RequestScoped
    RequestContext produceRequestContext() {
        return RequestContext.getCurrentInstance();
    }
}
