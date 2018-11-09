package br.eti.arthurgregorio.library.infrastructure.soteria.identity;

import br.eti.arthurgregorio.library.infrastructure.soteria.exceptions.InvalidCredentialsException;
import br.eti.arthurgregorio.library.infrastructure.soteria.exceptions.UserNotFoundException;
import br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm;
import br.eti.arthurgregorio.library.infrastructure.soteria.hash.HashGenerator;
import br.eti.arthurgregorio.library.infrastructure.soteria.validation.AuthenticationValidationStep;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
@ApplicationScoped
public class DatabaseIdentityStore implements IdentityStore {

    @Inject
    @Algorithm
    private HashGenerator hashGenerator;

    @Inject
    private UserDetailsProvider userDetailsProvider;

    @Any
    @Inject
    private Instance<AuthenticationValidationStep> validationSteps;

    /**
     *
     * @param credential
     * @return
     */
    @Override
    public CredentialValidationResult validate(Credential credential) {

        if (credential instanceof UsernamePasswordCredential) {

            final UsernamePasswordCredential usernamePasswordCredential = (UsernamePasswordCredential) credential;

            final UserDetails userDetails = this.userDetailsProvider.findUserDetailsByUsername(
                    usernamePasswordCredential.getCaller()).orElseThrow(UserNotFoundException::new);

            this.validatePassword(userDetails, usernamePasswordCredential.getPasswordAsString());

            return this.evaluate(userDetails);
        }
        return CredentialValidationResult.NOT_VALIDATED_RESULT;
    }

    /**
     *
     * @param userDetails
     * @param plainTextPassword
     */
    private void validatePassword(UserDetails userDetails, String plainTextPassword) {
        if (!this.hashGenerator.isMatching(plainTextPassword, userDetails.getPassword())) {
            throw new InvalidCredentialsException("error.identity.invalid-credentials", userDetails.getUsername());
        }
    }

    /**
     *
     * @param userDetails
     * @return
     */
    private CredentialValidationResult evaluate(UserDetails userDetails) {

        this.validationSteps.forEach(step -> step.validate(userDetails));

        return new CredentialValidationResult(
                new CallerPrincipal(userDetails.getUsername()), userDetails.getPermissions());
    }
}
