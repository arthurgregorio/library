package br.eti.arthurgregorio.library.domain.ws.clients;

import br.eti.arthurgregorio.library.domain.ws.dto.BearerToken;
import feign.Body;
import feign.Param;
import feign.RequestLine;

/**
 * A {@link BearerToken} authentication client sample
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public interface AuthenticationClient {

    /**
     * Create a simple client to authenticate a request to the REST server
     *
     * @param username the username
     * @param password the password
     * @return the bearer token with the authorization token
     */
    @RequestLine("POST /oauth/token")
    @Body("grant_type=password&username={username}&password={password}")
    BearerToken createToken(@Param("username") String username, @Param("password") String password);
}
