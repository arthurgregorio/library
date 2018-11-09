package br.eti.arthurgregorio.library.infrastructure.soteria.hash;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 05/11/2018
 */
public class BCryptHashGenerator implements HashGenerator {

    private final int logRounds;

    /**
     *
     */
    public BCryptHashGenerator() {
        this(10);
    }

    /**
     *
     * @param logRounds
     */
    public BCryptHashGenerator(int logRounds) {
        this.logRounds = logRounds;
    }

    /**
     * {@inheritDoc}
     *
     * @param plainPassword
     * @return
     */
    @Override
    public String encode(String plainPassword) {
        return BCrypt.hashpw(String.valueOf(plainPassword), BCrypt.gensalt(this.logRounds));
    }

    /**
     * {@inheritDoc}
     *
     * @param plainPassword
     * @param encryptedPassword
     * @return
     */
    @Override
    public boolean isMatching(String plainPassword, String encryptedPassword) {
        return BCrypt.checkpw(String.valueOf(plainPassword), encryptedPassword);
    }
}
