package br.eti.arthurgregorio.library.infrastructure.feign;

import br.eti.arthurgregorio.library.domain.ws.clients.AuthenticationClient;
import br.eti.arthurgregorio.library.domain.ws.dto.BearerToken;
import br.eti.arthurgregorio.library.infrastructure.utilities.Configurations;

/**
 * The {@link BearerToken} provider
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public class BearerTokenProvider {

    private final String username;
    private final String password;

    private final AuthenticationClient authenticationClient;

    /**
     * Constructor
     */
    public BearerTokenProvider() {

        this.username = Configurations.get("ws.username");
        this.password = Configurations.get("ws.password");

        final FeignClientBuilder factory = FeignClientBuilder.getInstance();

        this.authenticationClient = factory.usingDefaults()
                .withInterceptor(new BasicAuthInterceptor())
                .build(AuthenticationClient.class);
    }

    /**
     * Create the token using the {@link AuthenticationClient}
     *
     * @return the created token
     */
    public BearerToken createToken() {
        return this.authenticationClient.createToken(this.username, this.password);
    }
}
