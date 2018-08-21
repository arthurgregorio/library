package br.eti.arthurgregorio.library.infrastructure.shiro;

import br.eti.arthurgregorio.shiroee.config.HttpSecurityConfiguration;
import br.eti.arthurgregorio.shiroee.config.http.HttpSecurityBuilder;
import br.eti.arthurgregorio.shiroee.config.http.PermissionHttpSecurityBuilder;

import javax.enterprise.context.ApplicationScoped;

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

    /**
     * @return the HTTP security configurations for the application path's
     */
    @Override
    public HttpSecurityBuilder configureHttpSecurity() {
        
        final HttpSecurityBuilder builder = new PermissionHttpSecurityBuilder();
        
        builder.add("/secured/tools/users/**", "user:access", true)
                .add("/secured/tools/groups/**", "group:access", true);
        
        return builder;
    }    
}
