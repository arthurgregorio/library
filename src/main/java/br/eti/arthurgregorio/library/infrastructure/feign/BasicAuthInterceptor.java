package br.eti.arthurgregorio.library.infrastructure.feign;

import br.eti.arthurgregorio.library.infrastructure.utilities.Configurations;
import com.google.common.io.BaseEncoding;
import feign.RequestInterceptor;
import feign.RequestTemplate;

import static feign.Util.UTF_8;

/**
 * Feign {@link RequestInterceptor} for Basic authentication
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public class BasicAuthInterceptor implements RequestInterceptor {

    private final String headerValue;

    /**
     * Constructor...
     */
    public BasicAuthInterceptor() {
        final String clientName = Configurations.get("ws.client-name") + ":";
        this.headerValue = "Basic " + this.base64Encode(clientName.getBytes(UTF_8));
    }

    /**
     * The base64 encoder to transform the plain string in some more secured
     *
     * @param bytes the bytes to be processed and transformed
     * @return the base64 encoded {@link String}
     */
    private String base64Encode(byte[] bytes) {
        return BaseEncoding.base64().encode(bytes);
    }

    /**
     * {@inheritDoc}
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header("Authorization", this.headerValue);
    }
}
