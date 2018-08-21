package br.eti.arthurgregorio.library.domain.services.validators.user;

import br.eti.arthurgregorio.library.domain.model.entities.tools.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.library.domain.services.validators.BusinessValidator;
import java.util.Optional;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * {@link BusinessValidator} for the username validation logic
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 09/08/2018
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

        final Optional<User> usernameOptional = this.userRepository
                .findOptionalByUsername(value.getUsername());
        
        if (usernameOptional.isPresent()) {
            throw new BusinessLogicException("error.user.username-duplicated");
        }
    }
}