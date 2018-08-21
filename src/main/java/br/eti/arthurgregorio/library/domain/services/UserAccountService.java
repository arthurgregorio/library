package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.application.controllers.ProfileBean.PasswordChangeDTO;
import br.eti.arthurgregorio.library.domain.model.entities.tools.*;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.*;
import br.eti.arthurgregorio.library.domain.services.validators.group.GroupDeletingValidator;
import br.eti.arthurgregorio.library.domain.services.validators.user.UserDeletingValidator;
import br.eti.arthurgregorio.library.domain.services.validators.user.UserSavingValidator;
import br.eti.arthurgregorio.library.domain.services.validators.user.UserUpdatingValidator;
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
     *
     * @param user
     * @return
     */
    @Transactional
    public User save(User user) {

        this.userSavingValidators.forEach(validator -> {
            validator.validate(user);
        });

        return this.userRepository.save(user);
    }

    /**
     *
     * @param user
     */
    @Transactional
    public void update(User user) {

        this.userUpdatingValidators.forEach(validator -> {
            validator.validate(user);
        });

        this.userRepository.saveAndFlushAndRefresh(user);
    }

    /**
     *
     * @param user
     */
    @Transactional
    public void delete(User user) {

        this.userDeletingValidators.forEach(validator -> {
            validator.validate(user);
        });

        this.userRepository.attachAndRemove(user);
    }

    /**
     *
     * @param passwordChangeDTO
     * @param user
     */
    @Transactional
    public void changePassword(PasswordChangeDTO passwordChangeDTO, User user) {

        final boolean actualsMatch = this.passwordEncoder.passwordsMatch(
                passwordChangeDTO.getActualPassword(), user.getPassword());

        if (actualsMatch) {
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
     *
     * @param group
     * @return
     */
    @Transactional
    public Group save(Group group) {
        return this.groupRepository.save(group);
    }

    /**
     *
     * @param group
     * @param authorizations
     */
    @Transactional
    public void save(Group group, List<Authorization> authorizations) {

        this.groupRepository.save(group);

        authorizations.stream().forEach(authz -> {
            Authorization authorization = this.authorizationRepository
                    .findOptionalByFunctionalityAndPermission(
                            authz.getFunctionality(), authz.getPermission())
                    .get();
            this.grantRepository.save(new Grant(group, authorization));
        });
    }

    /**
     *
     * @param group
     */
    @Transactional
    public void update(Group group) {
        this.groupRepository.saveAndFlushAndRefresh(group);
    }

    /**
     *
     * @param group
     * @param authorizations
     */
    @Transactional
    public void update(Group group, List<Authorization> authorizations) {

        this.groupRepository.saveAndFlushAndRefresh(group);

        // list all old grants
        final List<Grant> oldGrants = this.grantRepository.findByGroup(group);

        oldGrants.stream().forEach(grant -> {
            this.grantRepository.remove(grant);
        });

        // save the new ones
        authorizations.stream().forEach(authz -> {
            Authorization authorization = this.authorizationRepository
                    .findOptionalByFunctionalityAndPermission(
                            authz.getFunctionality(), authz.getPermission())
                    .get();
            this.grantRepository.save(new Grant(group, authorization));
        });
    }

    /**
     *
     * @param group
     */
    @Transactional
    public void delete(Group group) {

        this.groupDeletingValidators.forEach(validator -> {
            validator.validate(group);
        });

        this.groupRepository.attachAndRemove(group);
    }

    /**
     *
     * @param authorization
     */
    @Transactional
    public void save(Authorization authorization) {
        this.authorizationRepository.saveAndFlush(authorization);
    }

    /**
     *
     * @param grant
     * @return
     */
    @Transactional
    public Grant save(Grant grant) {
        return this.grantRepository.save(grant);
    }

    /**
     *
     * @param authorizations
     * @param group
     */
    @Transactional
    public void grantAll(List<Authorization> authorizations, Group group) {
        authorizations.stream().forEach(authz -> {
            this.grantRepository.save(new Grant(group, authz));
        });
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
     *
     * @param username
     * @return
     */
    @Override
    public Optional<UserDetails> findUserDetailsByUsername(String username) {
        final Optional<User> user =
                this.userRepository.findOptionalByUsername(username);
        return Optional.ofNullable(user.orElse(null));
    }
}
