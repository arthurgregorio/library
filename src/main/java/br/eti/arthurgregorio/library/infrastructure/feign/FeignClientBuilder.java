package br.eti.arthurgregorio.library.infrastructure.feign;

import br.eti.arthurgregorio.library.infrastructure.utilities.Configurations;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.Client;
import feign.Feign;
import feign.RequestInterceptor;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

import javax.net.ssl.SSLSession;

import static feign.Logger.Level.FULL;

/**
 * A fluent interface to create Feing clients
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public final class FeignClientBuilder {

    private final String baseUrl;

    private final Feign.Builder builder;

    /**
     * Private constructor to force clients to use {@link #getInstance()}
     */
    private FeignClientBuilder() {
        this(Configurations.get("ws.base-url"));
    }

    /**
     * Private constructor to force clients to use {@link #getInstance(String)}
     *
     * @param baseUrl the base URL for the target connection
     */
    private FeignClientBuilder(String baseUrl) {
        this.baseUrl = baseUrl;
        this.builder = Feign.builder().logLevel(FULL);
    }

    /**
     * Static method to get a new instance for this builder
     *
     * @return this builder
     */
    public static FeignClientBuilder getInstance() {
        return new FeignClientBuilder();
    }

    /**
     * Static method to get a new instance for this builder
     *
     * @param baseUrl the base URL for the target connection
     * @return this builder
     */
    public static FeignClientBuilder getInstance(String baseUrl) {
        return new FeignClientBuilder(baseUrl);
    }

    /**
     * Use this method to get a pre-configured version of this {@link FeignClientBuilder} with the basics already
     * configured
     * <p>
     * This method already set the Jackson to encode and decode the JSON
     *
     * @return this builder
     */
    public FeignClientBuilder usingDefaults() {
        this.builder
                .decode404()
                .encoder(new JacksonEncoder(this.configureMapper()))
                .decoder(new JacksonDecoder(this.configureMapper()));
        return this;
    }

    /**
     * Use this method to provide a interceptor to be executed before every request
     *
     * @param interceptor the {@link RequestInterceptor} implementation
     * @return this builder
     */
    public FeignClientBuilder withInterceptor(RequestInterceptor interceptor) {
        this.builder.requestInterceptor(interceptor);
        return this;
    }

    /**
     * If you try to use this Feing {@link Client}  implementation with a untrusted target (domain with a self signed
     * SSL cert) you need to use this method to ignore the fact of the target doesn't have a valid SSL certificate
     *
     * @return a totally untrusted version of the Feing {@link Client}
     */
    public FeignClientBuilder withUntrustedClient() {
        this.builder.client(new Client.Default(null, (String host, SSLSession sslSession) -> true));
        return this;
    }

    /**
     * The build method, return the instance of the client with all the configurations made in this builder
     *
     * @param <T>   the type of the client
     * @param clazz the class attribute of the client
     * @return the client type by the generic type
     */
    public <T> T build(Class<T> clazz) {
        return this.builder.target(clazz, this.baseUrl);
    }

    /**
     * Internal method to configure the Jackson {@link ObjectMapper} an his features
     *
     * @return a configured version of the {@link ObjectMapper}
     */
    private ObjectMapper configureMapper() {
        return new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
