package br.eti.arthurgregorio.library.infrastructure.soteria.hash;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 05/11/2018
 */
public class ShaHashGenerator implements HashGenerator {

    private final String algorithmType;

    /**
     *
     * @param algorithmType
     */
    public ShaHashGenerator(String algorithmType) {
        this.algorithmType = algorithmType;
    }

    /**
     * {@inheritDoc}
     *
     * @param plainText
     * @return
     */
    @Override
    public String encode(String plainText) {
        try {
            final MessageDigest messageDigest = MessageDigest.getInstance(this.algorithmType);
            return Base64.getEncoder().encodeToString(messageDigest.digest(plainText.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException("Can't encode the text because an unknown error", ex);
        }
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
        return encrypted.equals(this.encode(plainText));
    }
}
