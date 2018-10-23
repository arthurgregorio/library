package br.eti.arthurgregorio.library.infrastructure.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * The authorization interceptor to be used with Bearer Token implementation methods
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public class BearerTokenAuthInterceptor implements RequestInterceptor {

    private static final String BEARER_TOKEN_TYPE = "Bearer %s";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * {@inheritDoc}
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        final String token = BearerTokenHolder.getToken();
        template.header(AUTHORIZATION_HEADER, String.format(BEARER_TOKEN_TYPE, token));
    }
}
