package br.eti.arthurgregorio.library.infrastructure.shiro;

import br.eti.arthurgregorio.library.domain.entities.configuration.Permissions;
import br.eti.arthurgregorio.shiroee.config.HttpSecurityConfiguration;
import br.eti.arthurgregorio.shiroee.config.http.HttpSecurityBuilder;
import br.eti.arthurgregorio.shiroee.config.http.PermissionHttpSecurityBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * The implementation of the {@link HttpSecurityConfiguration} for this project
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/03/2018
 */
@ApplicationScoped
public class PathSecurityConfiguration implements HttpSecurityConfiguration {

    @Inject
    private Permissions permissions;

    /**
     * @return the HTTP security configurations for the application path's
     */
    @Override
    public HttpSecurityBuilder configureHttpSecurity() {

        final HttpSecurityBuilder builder = new PermissionHttpSecurityBuilder();

        builder.add("/secured/configuration/users/**", this.permissions.getUSER_ACCESS(), true)
                .add("/secured/configuration/groups/**", this.permissions.getGROUP_ACCESS(), true)
                .add("/secured/registration/authors/**", this.permissions.getAUTHOR_ACCESS(), true)
                .add("/secured/registration/books/**", this.permissions.getBOOK_ACCESS(), true);

        return builder;
    }
}
