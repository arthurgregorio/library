package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.application.cdi.qualifier.UserCreated;
import br.eti.arthurgregorio.library.application.controllers.configuration.ProfileBean.PasswordChangeDTO;
import br.eti.arthurgregorio.library.domain.entities.configuration.Group;
import br.eti.arthurgregorio.library.domain.entities.configuration.StoreType;
import br.eti.arthurgregorio.library.domain.entities.configuration.User;
import br.eti.arthurgregorio.library.domain.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.logics.configuration.user.UserSavingLogic;
import br.eti.arthurgregorio.library.domain.repositories.configuration.UserRepository;
import br.eti.arthurgregorio.library.infrastructure.mail.MailMessage;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetailsProvider;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.UnknownAccountException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Service to take care of some routines about the user account
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.3.0, 20/01/2020
 */
@ApplicationScoped
public class AccountService implements UserDetailsProvider {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    @Inject
    @UserCreated
    private Event<User> userCreatedEvent;

    @Any
    @Inject
    private Instance<UserSavingLogic> userSavingLogics;

    /**
     * Update an {@link User} password
     *
     * @param passwordChangeDTO with the new values
     * @param user the user to change the password
     */
    @Transactional
    public void changePassword(PasswordChangeDTO passwordChangeDTO, User user) {

        final boolean actualMatch = this.passwordEncoder.passwordsMatch(
                passwordChangeDTO.getActualPassword(), user.getPassword());

        if (actualMatch) {
            if (passwordChangeDTO.isNewPassMatching()) {
                final String newPass = this.passwordEncoder.encryptPassword(
                        passwordChangeDTO.getNewPassword());
                user.setPassword(newPass);
                this.userRepository.saveAndFlushAndRefresh(user);
                return;
            }
            throw new BusinessLogicException("error.profile.new-pass-not-match");
        }
        throw new BusinessLogicException("error.profile.actual-pass-not-match");
    }

    /**
     * {@inheritDoc}
     *
     * @param username
     * @return
     * @throws UnknownAccountException
     */
    @Override
    public UserDetails findByUsername(String username) throws UnknownAccountException {
        return this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UnknownAccountException("Can't find account with username " + username));
    }

    /**
     *
     * @param users
     * @param group
     */
    @Transactional
    public void importFromLdap(List<User> users, Group group) {

        if (group == null) {
            throw new BusinessLogicException("error.import-user.no-group");
        }

        users.forEach(user -> {

            if (StringUtils.isBlank(user.getEmail())) {
                throw new BusinessLogicException("error.import-user.no-email", user.getName());
            }

            user.setGroup(group);
            user.setStoreType(StoreType.LDAP);

            this.userSavingLogics.forEach(logic -> logic.run(user));
            this.userCreatedEvent.fire(this.userRepository.save(user));
        });
    }
}
