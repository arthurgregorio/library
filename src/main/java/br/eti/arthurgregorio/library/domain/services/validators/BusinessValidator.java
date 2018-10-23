package br.eti.arthurgregorio.library.domain.services.validators;

import br.eti.arthurgregorio.library.domain.model.entities.PersistentEntity;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;

/**
 * The abstraction of a validator
 * 
 * @param <T> the generic type for this validator. Should extends {@link PersistentEntity}
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/08/2018
 */
public interface BusinessValidator<T extends PersistentEntity> {

    /**
     * Call this to run the validation logic
     * 
     * This method should throw a {@link BusinessLogicException} in his body for
     * validation error cases
     * 
     * @param value the value to be validated
     */
    void validate(T value);
}