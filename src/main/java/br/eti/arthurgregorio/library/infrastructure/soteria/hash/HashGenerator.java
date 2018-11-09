package br.eti.arthurgregorio.library.infrastructure.soteria.hash;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/11/2018
 */
public interface HashGenerator {

    /**
     *
     * @param plainText
     * @return
     */
    String encode(String plainText);

    /**
     *
     * @param plainText
     * @param encrypted
     * @return
     */
    boolean isMatching(String plainText, String encrypted);

    /**
     *
     * @param plainText
     * @return
     */
    default String encode(char[] plainText) {
        return this.encode(String.valueOf(plainText));
    }

    /**
     *
     * @param plainText
     * @param encrypted
     * @return
     */
    default boolean isMatching(char[] plainText, String encrypted) {
        return this.isMatching(String.valueOf(plainText), encrypted);
    }
}
