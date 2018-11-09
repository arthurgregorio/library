package br.eti.arthurgregorio.library.infrastructure.soteria.auth;

import lombok.Getter;
import lombok.Setter;

import javax.security.enterprise.credential.UsernamePasswordCredential;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 08/11/2018
 */
public final class CredentialHolder {

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private boolean rememberMe;

    /**
     *
     */
    public CredentialHolder() {
        this.rememberMe = false;
    }

    /**
     *
     * @return
     */
    public UsernamePasswordCredential toCredential() {
        return new UsernamePasswordCredential(this.username, this.password);
    }
}
