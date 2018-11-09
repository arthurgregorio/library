package br.eti.arthurgregorio.library.infrastructure.soteria.hash;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/11/2018
 */
public class PbkdfHashGenerator implements HashGenerator {

    @Inject
    private Pbkdf2PasswordHash pbkdf2PasswordHash;

    /**
     * Initialize the hash algorithm
     */
    @PostConstruct
    public void initialize() {

        final Map<String, String> parameters = new HashMap<>();

        parameters.put("Pbkdf2PasswordHash.Iterations", "3072");
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA512");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "64");

        this.pbkdf2PasswordHash.initialize(parameters);
    }

    /**
     * {@inheritDoc}
     *
     * @param plainText
     * @return
     */
    @Override
    public String encode(String plainText) {
        return this.pbkdf2PasswordHash.generate(plainText.toCharArray());
    }

    /**
     * {@inheritDoc}
     *
     * @param plainText
     * @param encrypted
     * @return
     */
    @Override
    public boolean isMatching(String plainText, String encrypted) {
        return this.pbkdf2PasswordHash.verify(plainText.toCharArray(), encrypted);
    }
}
