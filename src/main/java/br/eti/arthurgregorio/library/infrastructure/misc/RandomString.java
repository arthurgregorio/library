package br.eti.arthurgregorio.library.infrastructure.misc;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

/**
 * Random string generator
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 28/09/2019
 */
public final class RandomString {

    private final Random random;
    private final char[] symbols;
    private final char[] buf;

    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER = UPPER.toLowerCase(Locale.ROOT);

    public static final String DIGITS = "0123456789";

    public static final String ALPHANUM = UPPER + LOWER + DIGITS;

    /**
     * Constructor...
     */
    public RandomString() {
        this(8);
    }

    /**
     * Constructor...
     *
     * @param length to limit the amount of chars
     */
    public RandomString(int length) {
        this(length, new SecureRandom());
    }

    /**
     * Constructor...
     *
     * @param length to limit the amount of chars
     * @param random which type of {@link Random} generator we should use
     */
    public RandomString(int length, Random random) {
        this(length, random, ALPHANUM);
    }

    /**
     * Constructor...
     *
     * @param length to limit the amount of chars
     * @param random which type of {@link Random} generator we should use
     * @param symbols which symbols we will include on the generated {@link String}
     */
    public RandomString(int length, Random random, String symbols) {

        if (length < 1) throw new IllegalArgumentException("Length should be > 0");
        if (symbols.length() < 2) throw new IllegalArgumentException("Length should be > 2");

        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Build the random {@link String}
     *
     * @return an {@link Random} {@link String}
     */
    public String nextString() {
        for (int idx = 0; idx < this.buf.length; ++idx) {
            this.buf[idx] = this.symbols[this.random.nextInt(this.symbols.length)];
        }
        return new String(this.buf);
    }
}
