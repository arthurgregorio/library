package br.eti.arthurgregorio.library.domain.validators;

import br.eti.arthurgregorio.library.domain.entities.PersistentEntity;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;

/**
 * The abstraction of a business logic
 * 
 * @param <T> the generic type for this logic. Should extends {@link PersistentEntity}
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
public interface BusinessLogic<T extends PersistentEntity> {

    /**
     * Call this to run the logic
     * 
     * This method should throw a {@link BusinessLogicException} in his body for validation error cases or any other
     * problem to prevent the subsequent execution
     * 
     * @param value the value to be used in the logic
     */
    void run(T value);
}