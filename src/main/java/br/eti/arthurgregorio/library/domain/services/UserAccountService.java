package br.eti.arthurgregorio.library.domain.services;

import br.eti.arthurgregorio.library.application.controllers.ProfileBean.PasswordChangeDTO;
import br.eti.arthurgregorio.library.domain.model.entities.security.Authorization;
import br.eti.arthurgregorio.library.domain.model.entities.security.Grant;
import br.eti.arthurgregorio.library.domain.model.entities.security.Group;
import br.eti.arthurgregorio.library.domain.model.entities.security.StoreType;
import br.eti.arthurgregorio.library.domain.model.entities.security.User;
import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import br.eti.arthurgregorio.library.domain.repositories.tools.AuthorizationRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.GrantRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.GroupRepository;
import br.eti.arthurgregorio.library.domain.repositories.tools.UserRepository;
import br.eti.arthurgregorio.shiroee.auth.PasswordEncoder;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetails;
import br.eti.arthurgregorio.shiroee.config.jdbc.UserDetailsProvider;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;

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
    private AuthorizationRepository authorizationRepository;

    /**
     *
     * @param user
     * @return
     */
    @Transactional
    public User save(User user) {
        
        // validate the email
        final Optional<User> emailOptional = this.userRepository
                .findOptionalByEmail(user.getEmail());
        
        if (emailOptional.isPresent()) {
            throw new BusinessLogicException("user.email-duplicated");
        }
        
        // validate the username
        final Optional<User> usernameOptional = this.userRepository
                .findOptionalByUsername(user.getUsername());
        
        if (usernameOptional.isPresent()) {
            throw new BusinessLogicException("user.username-duplicated");
        }

        // if the user is local...
        if (user.getStoreType() == StoreType.LOCAL) {
            
            if (!user.isPasswordValid()) {
                throw new BusinessLogicException("user.password-not-match-or-invalid");
            }

            user.setPassword(this.passwordEncoder
                    .encryptPassword(user.getPassword()));
        }
        
        // save
        return this.userRepository.save(user);
    }

    /**
     *
     * @param user
     */
    @Transactional
    public void update(User user) {
        
        // validate the email
        final Optional<User> userOptional = this.userRepository
                .findOptionalByEmail(user.getEmail());
        
        if (userOptional.isPresent()) {
            
            final User found = userOptional.get();
            
            if (!found.getUsername().equals(user.getUsername())) {
                throw new BusinessLogicException("user.email-duplicated");
            }
        }
        
        // if the user is local...
        if (user.getStoreType() == StoreType.LOCAL) {

            if (user.hasChangedPasswords()) {

                // check if passwords match
                if (!user.isPasswordValid()) {
                    throw new BusinessLogicException("user.password-not-match");
                }

                // crypt the user password
                user.setPassword(this.passwordEncoder.encryptPassword(
                        user.getPassword()));            
            } else {
                user.setPassword(userOptional.get().getPassword());
            }
        }
        
        this.userRepository.saveAndFlushAndRefresh(user);
    }

    /**
     *
     * @param user
     */
    @Transactional
    public void delete(User user) {
        
        final String principal = String.valueOf(SecurityUtils
                .getSubject().getPrincipal());
        
        // prevent to delete you own user 
        if (principal.equals(user.getUsername())) {
            throw new BusinessLogicException("user.delete-principal");
        }
        
        // prevent to delete the main admin
        if (user.isAdministrator()) {
            throw new BusinessLogicException("user.delete-administrator");
        }
        
        this.userRepository.attachAndRemove(user);
    }
    
    /**
     * 
     * @param passwordChangeDTO
     * @param user 
     */
    @Transactional
    public void changePasswordForCurrentUser(PasswordChangeDTO passwordChangeDTO, User user) {
        
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
            throw new BusinessLogicException("profile.new-pass-not-match");        
        }
        throw new BusinessLogicException("profile.actual-pass-not-match");        
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
        
        // prevent to delete the main admin
        if (group.isAdministrator()) {
            throw new BusinessLogicException("group.delete-administrator");
        }
        
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
     * @param grant
     */
    @Transactional
    public void update(Grant grant) {
        this.grantRepository.saveAndFlushAndRefresh(grant);
    }

    /**
     *
     * @param grant
     */
    @Transactional
    public void remove(Grant grant) {
        this.grantRepository.attachAndRemove(grant);
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
     *
     * @param username
     * @return
     */
    public User findUserByUsername(String username) {
        return this.userRepository.findOptionalByUsername(username)
                .orElse(null);
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
