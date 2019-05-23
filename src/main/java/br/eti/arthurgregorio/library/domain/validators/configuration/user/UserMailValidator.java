package br.eti.arthurgregorio.library.domain.validators.configuration.user;

import br.eti.arthurgregorio.library.domain.model.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.library.domain.validators.BusinessLogic;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Optional;

/**
 * {@link BusinessLogic} for the user e-mail validation logic
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
@Dependent
public class UserMailValidator implements UserSavingLogic, UserUpdatingLogic {

    @Inject
    private UserRepository userRepository;

    /**
     * {@inheritDoc }
     *
     * @param value
     */
    @Override
    public void run(User value) {

        final Optional<User> userOptional = this.userRepository.findByEmail(value.getEmail());

        if (userOptional.isPresent()) {

            final User found = userOptional.get();

            if (!found.getUsername().equals(value.getUsername())) {
                throw new BusinessLogicException("error.user.email-duplicated");
            }
        }
    }
}