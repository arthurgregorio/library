package br.eti.arthurgregorio.library.domain.validators.tools.user;

import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
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
 * @since 2.0.0, 23/10/2018
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
            throw BusinessLogicException.create("error.user.username-duplicated");
        });
    }
}