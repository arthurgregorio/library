package br.eti.arthurgregorio.library.application.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * The logger object producer
 *
 * @author Arthur Gregorio
 *
 * @version 1.1.0
 * @since 1.0.0, 18/12/2017
 */
@ApplicationScoped
public class LoggerProducer {

    /**
     * The producer of the logger object
     *
     * @param injectionPoint the injection point for class discovery
     * @return the logger object
     */
    @Produces
    @Dependent
    Logger produce(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}