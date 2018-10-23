package br.eti.arthurgregorio.library.infrastructure.jsf.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Simple {@link ExceptionHandlerFactory} to customize the exception handling in the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/02/2018
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    /**
     * Constructor...
     * 
     * @param parent the parent factory
     */
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        super(parent);
    }

    /**
     * {@inheritDoc }
     * 
     * @return 
     */
    @Override
    public ExceptionHandler getExceptionHandler() {
        return new CustomExceptionHandler(this.getWrapped().getExceptionHandler());
    }
}
