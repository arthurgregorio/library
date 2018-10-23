package br.eti.arthurgregorio.library.domain.ws.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
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
     *
     */
    public BearerToken() {
        this.validity = 3500L;
        this.creationTime = System.currentTimeMillis();
    }

    /**
     *
     * @param accessToken
     * @param tokenType
     * @param expiresIn
     * @param scope
     * @param jti
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
     *
     * @return
     */
    public boolean isValid() {
        final long actualTime = System.currentTimeMillis();
        return actualTime - this.creationTime < this.validity;
    }
}
