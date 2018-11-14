package br.eti.arthurgregorio.library.domain.validators.tools.user;

import br.eti.arthurgregorio.library.domain.model.entities.tools.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.library.domain.validators.BusinessValidator;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;

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
    private PasswordEncoder passwordEncoder;
    
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

        final Optional<User> optionalUser = this.userRepository.findOptionalByUsername(value.getUsername());

        if (value.hasChangedPasswords()) {
            if (!value.isPasswordValid()) {
                throw new BusinessLogicException("error.user.password-not-match");
            }
            value.setPassword(this.cryptPassword(value.getPassword()));
        } else {
            value.setPassword(optionalUser.get().getPassword());
        }
    }

    /**
     * Vaslidate the user password for unsaved users
     */
    private void validateUnsavedUserPassword(User value) {
        if (!value.isPasswordValid()) {
            throw new BusinessLogicException("error.user.password-not-match");
        }
        value.setPassword(this.passwordEncoder.encryptPassword(value.getPassword()));
    }

    /**
     * Crypt the user password to protect it
     * 
     * @param plainPassword the plain password to be crypted
     * @return the crypted password
     */
    private String cryptPassword(String plainPassword) {
        return this.passwordEncoder.encryptPassword(plainPassword);
    }
}
