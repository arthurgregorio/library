package br.eti.arthurgregorio.library.infrastructure.jsf.exception;

import javax.faces.context.FacesContext;

/**
 * The contract to create new exception handlers to be used with the {@link CustomExceptionHandlerWrapper}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 20/11/2018
 */
public interface CustomExceptionHandler<T extends Throwable> {

    /**
     * This is the method responsible to handle the exception itself
     *
     * @param context current {@link FacesContext}
     * @param exception the exception to be handled
     */
    void handle(FacesContext context, T exception);

    /**
     * To check if the throwable passed as parameter can be handled by this handler
     *
     * @param throwable the throwable to test
     * @return true if is acceptable or  false if not
     */
    boolean accept(Throwable throwable);
}
