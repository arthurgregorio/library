package br.eti.arthurgregorio.library.domain.validators.configurations.user;

import br.eti.arthurgregorio.library.domain.model.entities.configurations.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.configurations.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configurations.UserRepository;
import br.eti.arthurgregorio.library.domain.validators.BusinessValidator;
import br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm;
import br.eti.arthurgregorio.library.infrastructure.soteria.hash.HashGenerator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Optional;

/**
 * {@link BusinessValidator} for the user password validation logic
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
@Dependent
public class PasswordValidator implements UserSavingValidator, UserUpdatingValidator {

    @Inject
    private UserRepository userRepository;

    @Inject
    @Algorithm
    private HashGenerator hashGenerator;
    
    /**
     * {@inheritDoc }
     *
     * @param value
     */
    @Override
    public void validate(User value) {
        if (value.getStoreType() == StoreType.LOCAL) {
            if (value.isSaved()) {
                this.validateSavedUserPassword(value);
            } else {
                this.validateUnsavedUserPassword(value);
            }
        }
    }

    /**
     * Validate the user password for already saved users
     */
    private void validateSavedUserPassword(User value) {

        if (value.passwordsHasChanged()) {
            if (value.isPasswordInvalid()) {
                throw new BusinessLogicException("error.user.password-not-match");
            }
            value.setPassword(this.cryptPassword(value.getPassword()));
        } else {

            final Optional<User> optionalUser = this.userRepository.findOptionalByUsername(value.getUsername());

            value.setPassword(optionalUser.get().getPassword());
        }
    }

    /**
     * Vaslidate the user password for unsaved users
     */
    private void validateUnsavedUserPassword(User value) {
        if (value.isPasswordInvalid()) {
            throw new BusinessLogicException("error.user.password-not-match");
        }
        value.setPassword(this.hashGenerator.encode(value.getPassword()));
    }

    /**
     * Crypt the user password to protect it
     * 
     * @param plainPassword the plain password to be crypted
     * @return the crypted password
     */
    private String cryptPassword(String plainPassword) {
        return this.hashGenerator.encode(plainPassword);
    }
}
