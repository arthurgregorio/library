package br.eti.arthurgregorio.library.infrastructure.jsf.exception;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;
import java.util.Set;

/**
 * Simple {@link ExceptionHandlerFactory} to customize the exception handling by JSF
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 28/02/2018
 */
public class CustomExceptionHandlerFactory extends ExceptionHandlerFactory {

    /**
     * {@inheritDoc}
     *
     * @param wrapped
     */
    public CustomExceptionHandlerFactory(ExceptionHandlerFactory wrapped) {
        super(wrapped);
    }

    /**
     * {@inheritDoc }
     *
     * @return
     */
    @Override
    public ExceptionHandler getExceptionHandler() {

        final Set<CustomExceptionHandler> handlers = Set.of(
                new BusinessLogicExceptionHandler(),
                new ConstraintViolationExceptionHandler()
        );

        return new CustomExceptionHandlerWrapper(this.getWrapped().getExceptionHandler(), handlers);
    }
}
