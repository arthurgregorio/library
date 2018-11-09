package br.eti.arthurgregorio.library.domain.validators.configurations.user;

import br.eti.arthurgregorio.library.domain.model.entities.configurations.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configurations.UserRepository;
import br.eti.arthurgregorio.library.domain.validators.BusinessValidator;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Optional;

/**
 * {@link BusinessValidator} for the username validation logic
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
@Dependent
public class UsernameValidator implements UserSavingValidator {

    @Inject
    private UserRepository userRepository;

    /**
     * {@inheritDoc }
     * 
     * @param value 
     */
    @Override
    public void validate(User value) {

        final Optional<User> usernameOptional = this.userRepository.findOptionalByUsername(value.getUsername());

        usernameOptional.ifPresent(user -> {
            throw new BusinessLogicException("error.user.username-duplicated");
        });
    }
}