package br.eti.arthurgregorio.library.infrastructure.feign;

import br.eti.arthurgregorio.library.domain.ws.dto.BearerToken;

/**
 * A simple value holder for the bearer token provided by the {@link BearerTokenProvider}
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public final class BearerTokenHolder {

    private static BearerToken authToken;
    private static BearerTokenProvider bearerTokenProvider;

    static {
        bearerTokenProvider = new BearerTokenProvider();
        authToken = bearerTokenProvider.createToken();
    }

    /**
     * Use this method to check if the contained token is valid
     *
     * @return true for a valid token, false otherwise
     */
    public static boolean hasValidToken() {
        return authToken != null && authToken.isValid();
    }

    /**
     * Use this method to get the toke key
     *
     * @return the token key to be used for authentication
     */
    public static String getToken() {
        if (!hasValidToken()) {
            authToken = bearerTokenProvider.createToken();
        }
        return authToken.getAccessToken();
    }
}
