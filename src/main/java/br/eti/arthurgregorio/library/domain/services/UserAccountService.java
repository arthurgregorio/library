package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.application.controllers.ProfileBean.PasswordChangeDTO;
import br.eti.arthurgregorio.library.domain.model.entities.tools.*;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.*;
import br.eti.arthurgregorio.library.domain.validators.tools.group.GroupDeletingValidator;
import br.eti.arthurgregorio.library.domain.validators.tools.user.UserDeletingValidator;
import br.eti.arthurgregorio.library.domain.validators.tools.user.UserSavingValidator;
import br.eti.arthurgregorio.library.domain.validators.tools.user.UserUpdatingValidator;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetailsProvider;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * The user account service
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 27/12/2017
 */
@ApplicationScoped
public class UserAccountService implements UserDetailsProvider {

    @Inject
    private PasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;
    @Inject
    private GrantRepository grantRepository;
    @Inject
    private GroupRepository groupRepository;
    @Inject
    private ProfileRepository profileRepository;
    @Inject
    private AuthorizationRepository authorizationRepository;

    @Any
    @Inject
    private Instance<UserSavingValidator> userSavingValidators;
    @Any
    @Inject
    private Instance<UserUpdatingValidator> userUpdatingValidators;
    @Any
    @Inject
    private Instance<UserDeletingValidator> userDeletingValidators;

    @Any
    @Inject
    private Instance<GroupDeletingValidator> groupDeletingValidators;

    /**
     * Persist a new {@link User}
     *
     * @param user the {@link User} to be persisted
     * @return the persisted {@link User}
     */
    @Transactional
    public User save(User user) {
        this.userSavingValidators.forEach(validator -> validator.validate(user));
        return this.userRepository.save(user);
    }

    /**
     * Update an already persisted {@link User}
     *
     * @param user the {@link User} to be updated
     */
    @Transactional
    public void update(User user) {
        this.userUpdatingValidators.forEach(validator -> validator.validate(user));
        this.userRepository.saveAndFlushAndRefresh(user);
    }

    /**
     * Delete a persisted {@link User}
     *
     * @param user the {@link User} to be deleted
     */
    @Transactional
    public void delete(User user) {
        this.userDeletingValidators.forEach(validator -> {
            validator.validate(user);
        });
        this.userRepository.attachAndRemove(user);
    }

    /**
     * Use this method to change the password of a given {@link User}
     *
     * @param passwordChangeDTO the {@link PasswordChangeDTO} with the new values
     * @param user the {@link User} to be updated
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
            throw BusinessLogicException.create("profile.new-pass-not-match");
        }
        throw BusinessLogicException.create("profile.actual-pass-not-match");
    }

    /**
     * Persist a new {@link Group}
     *
     * @param group the {@link Group} to be persisted
     * @return the persisted {@link Group}
     */
    @Transactional
    public Group save(Group group) {
        return this.groupRepository.save(group);
    }

    /**
     * Persist a new {@link Group} along with his {@link Authorization}
     *
     * @param group the {@link Group}
     * @param authorizations the list of {@link Authorization} of this group
     */
    @Transactional
    public void save(Group group, List<Authorization> authorizations) {
        this.groupRepository.save(group);
        authorizations.forEach(auth -> this.authorizationRepository
                .findOptionalByFunctionalityAndPermission(auth.getFunctionality(), auth.getPermission())
                .ifPresent(authorization -> this.grantRepository.save(new Grant(group, authorization)))
        );
    }

    /**
     * Update an already persisted {@link Group}
     *
     * @param group the {@link Group} to be updated
     */
    @Transactional
    public void update(Group group) {
        this.groupRepository.saveAndFlushAndRefresh(group);
    }

    /**
     * Update an already persisted {@link Group} and his {@link Authorization}
     *
     * @param group the {@link Group} to be update
     * @param authorizations the new {@link List} of {@link Authorization} of this {@link Group}
     */
    @Transactional
    public void update(Group group, List<Authorization> authorizations) {

        this.groupRepository.saveAndFlushAndRefresh(group);

        // list all old grants
        final List<Grant> oldGrants = this.grantRepository.findByGroup(group);

        oldGrants.forEach(grant -> this.grantRepository.remove(grant));

        // save the new ones
        authorizations.forEach(auth ->
            this.authorizationRepository
                    .findOptionalByFunctionalityAndPermission(auth.getFunctionality(), auth.getPermission())
                    .ifPresent(authorization -> this.grantRepository.save(new Grant(group, authorization)))
        );
    }

    /**
     * Delete an already persisted {@link Group}
     *
     * @param group the {@link Group} to be deleted
     */
    @Transactional
    public void delete(Group group) {
        this.groupDeletingValidators.forEach(validator -> validator.validate(group));
        this.groupRepository.attachAndRemove(group);
    }

    /**
     * Update the {@link User} {@link Profile}
     *
     * @param profile the {@link Profile} to be updated
     * @return the update {@link Profile}
     */
    @Transactional
    public Profile updateUserProfile(Profile profile) {
        return this.profileRepository.saveAndFlushAndRefresh(profile);
    }

    /**
     * Find the {@link UserDetails} of a given username from an {@link User}
     *
     * @param username the username to search for the details
     * @return an {@link Optional} of the {@link UserDetails} for the username
     */
    @Override
    public Optional<UserDetails> findUserDetailsByUsername(String username) {
        final Optional<User> user = this.userRepository.findOptionalByUsername(username);
        return Optional.ofNullable(user.orElse(null));
    }
}