package br.eti.arthurgregorio.library.domain.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * A value holder for a {@link BearerToken} authentication/authorization process
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
public class BearerToken {

    @Getter
    @Setter
    @JsonProperty("access_token")
    private String accessToken;
    @Getter
    @Setter
    @JsonProperty("token_type")
    private String tokenType;
    @Getter
    @Setter
    @JsonProperty("expires_in")
    private long expiresIn;
    @Getter
    @Setter
    private String scope;
    @Getter
    @Setter
    private String jti;

    @Setter
    private long validity;

    private final long creationTime;

    /**
     * Constructor
     */
    public BearerToken() {
        this.validity = 3500L;
        this.creationTime = System.currentTimeMillis();
    }

    /**
     * Constructor
     *
     * @param accessToken the access token
     * @param tokenType the type of the token
     * @param expiresIn when it will expire
     * @param scope the scope of this authentication token
     * @param jti the jti value
     */
    public BearerToken(String accessToken, String tokenType, long expiresIn, String scope, String jti) {
        this();
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.scope = scope;
        this.jti = jti;
    }

    /**
     * To check if this token is valid or not
     *
     * @return true if this token is valid, false otherwise
     */
    public boolean isValid() {
        final long actualTime = System.currentTimeMillis();
        return actualTime - this.creationTime < this.validity;
    }
}
