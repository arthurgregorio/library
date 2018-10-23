package br.eti.arthurgregorio.library.domain.ws.clients;

import br.eti.arthurgregorio.library.domain.ws.dto.BearerToken;
import feign.Body;
import feign.Param;
import feign.RequestLine;

/**
 *
 * @author Arthur Gregório
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public interface AuthenticationClient {

    /**
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestLine("POST /autorizador/oauth/token")
    @Body("grant_type=password&username={username}&password={password}")
    BearerToken createToken(@Param("username") String userName, @Param("password") String password);
}
