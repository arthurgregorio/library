package br.eti.arthurgregorio.library.infrastructure.cdi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

/**
 * The logger object producer
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 18/12/2017
  */
@Dependent
public class LoggerProducer {

    /**
     * The producer of the logger object
     * 
     * @param injectionPoint the injection point for class dicovery
     * @return the logger object
     */
    @Produces
    Logger produce(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMember().getDeclaringClass());
    }
}