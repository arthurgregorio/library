package br.eti.arthurgregorio.library.domain.validators.registration.author;

import br.eti.arthurgregorio.library.domain.model.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.registration.AuthorRepository;

import javax.inject.Inject;

/**
 * Validator for the {@link Author} e-mail
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 18/12/2018
 */
public class AuthorEmailValidator implements AuthorSavingValidator, AuthorUpdatingValidator {

    @Inject
    private AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     *
     * @param value
     */
    @Override
    public void validate(Author value) {

        if (value.isSaved()) {
            this.validateSaved(value);
        } else {
            this.validateNotSaved(value);
        }
    }

    /**
     * If the {@link Author} is already saved, use this method to validate
     *
     * @param value the {@link Author} to validate
     */
    private void validateSaved(Author value) {
        this.authorRepository.findByEmail(value.getEmail()).ifPresent(author -> {
            if (!author.equals(value)) {
                throw new BusinessLogicException("error.author.duplicated-email", value.getEmail());
            }
        });
    }

    /**
     * If the {@link Author} is not saved, use this method to validate
     *
     * @param value the {@link Author} to validate
     */
    private void validateNotSaved(Author value) {
        this.authorRepository.findByEmail(value.getEmail())
                .ifPresent(author -> {
                    throw new BusinessLogicException("error.author.duplicated-email", value.getEmail());
                });
    }
}
