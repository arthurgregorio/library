package br.eti.arthurgregorio.library.domain.validators.configuration.user;

import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.library.domain.validators.BusinessLogic;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

/**
 * {@link BusinessLogic} for the username validation logic
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.3.1, 09/08/2018
 */
@Dependent
public class UsernameValidator implements UserSavingLogic {

    @Inject
    private UserRepository userRepository;

    /**
     * {@inheritDoc }
     *
     * @param value
     */
    @Override
    public void run(User value) {
        this.userRepository.findByUsername(value.getUsername())
                .ifPresent(user -> {
                    throw new BusinessLogicException("error.user.username-duplicated");
                });
    }
}