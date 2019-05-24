package br.eti.arthurgregorio.library.domain.logics.registration.author;

import br.eti.arthurgregorio.library.domain.entities.registration.Author;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
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
public class AuthorEmailValidator implements AuthorSavingLogic, AuthorUpdatingLogic {

    @Inject
    private AuthorRepository authorRepository;

    /**
     * {@inheritDoc}
     *
     * @param value
     */
    @Override
    public void run(Author value) {

        if (value.isSaved()) {
            this.validateSaved(value);
        } else {
            this.validateNotSaved(value);
        }
    }

    /**
     * If the {@link Author} is already saved, use this method to run
     *
     * @param value the {@link Author} to run
     */
    private void validateSaved(Author value) {
        this.authorRepository.findByEmail(value.getEmail()).ifPresent(author -> {
            if (!author.equals(value)) {
                throw new BusinessLogicException("error.author.duplicated-email", value.getEmail());
            }
        });
    }

    /**
     * If the {@link Author} is not saved, use this method to run
     *
     * @param value the {@link Author} to run
     */
    private void validateNotSaved(Author value) {
        this.authorRepository.findByEmail(value.getEmail())
                .ifPresent(author -> {
                    throw new BusinessLogicException("error.author.duplicated-email", value.getEmail());
                });
    }
}