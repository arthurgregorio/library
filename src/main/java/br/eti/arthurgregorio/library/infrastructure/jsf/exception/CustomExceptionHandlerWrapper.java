package br.eti.arthurgregorio.library.infrastructure.jsf.exception;

import org.omnifaces.util.Exceptions;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import java.util.Iterator;
import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * The customized {@link ExceptionHandlerWrapper} to make handling of exceptions more easy for the managed beans
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 28/02/2018
 */
public class CustomExceptionHandlerWrapper extends ExceptionHandlerWrapper {

    private final Set<CustomExceptionHandler> customExceptionHandlers;

    private final UndefinedExceptionHandler undefinedExceptionHandler;

    /**
     * Constructor...
     *
     * @param exceptionHandler the wrapped handler
     */
    CustomExceptionHandlerWrapper(ExceptionHandler exceptionHandler, Set<CustomExceptionHandler> customHandlers) {
        super(exceptionHandler);
        this.customExceptionHandlers = requireNonNull(customHandlers);
        this.undefinedExceptionHandler = new UndefinedExceptionHandler();
    }

    /**
     * {@inheritDoc }
     *
     * @throws FacesException
     */
    @Override
    public void handle() throws FacesException {
        handleException();
        this.getWrapped().handle();
    }

    /**
     * Method to handle the generic exception and take a decision of witch step to take after identify the type of the
     * exception
     *
     * All non mapped exceptions on the {@link #customExceptionHandlers} will treated as generic exceptions on the
     * {@link UndefinedExceptionHandler}
     */
    @SuppressWarnings("unchecked")
    private void handleException() {

        final Iterator<ExceptionQueuedEvent> unhandled = getUnhandledExceptionQueuedEvents().iterator();

        if (unhandled.hasNext()) {

            final Throwable throwable = unhandled.next()
                    .getContext()
                    .getException();

            unhandled.remove();

            final FacesContext context = FacesContext.getCurrentInstance();
            final Throwable rootCause = Exceptions.unwrap(throwable);

            this.customExceptionHandlers.stream()
                    .filter(handler -> handler.accept(rootCause))
                    .findFirst()
                    .ifPresentOrElse(handler -> handler.handle(context, rootCause),
                            () -> this.undefinedExceptionHandler.handle(context, rootCause));
        }
    }
}