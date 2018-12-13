package br.eti.arthurgregorio.library.infrastructure.i18n;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is a helper class to obtain the i18n messages through the given key
 *
 * @author Arthur Gregorio
 *
 * @version 2.0.0
 * @since 1.0.0, 16/02/2018
 */
public final class MessageSource {

    private static final Logger logger;
    private static final MultiResourceBundle bundles;

    static {
        logger = LoggerFactory.getLogger(MessageSource.class);
        bundles = MultiResourceBundle.combine("i18n.messages", "i18n.webservice", "ValidationMessages");
    }

    /**
     * Give a key and get the message, if the key exists
     *
     * @param key the i18n key
     * @return the message
     */
    public static String get(String key) {
        try {
            return bundles.get(key);
        } catch (MissingResourceException | NullPointerException ex) {
            logger.error("No message found for key {0}", key);
            return "$$" + key + "$$";
        }
    }

    /**
     * Same as {@link #get(String)} but this one permit you to pass parameters to format the message
     *
     * @param key the i18n key
     * @param parameters the parameters to format the message
     * @return the message formatted
     */
    public static String get(String key, Object... parameters) {
        try {
            return bundles.get(key, parameters);
        } catch (MissingResourceException | NullPointerException ex) {
            logger.error("No message found for key {0}", key);
            return "$$" + key + "$$";
        }
    }
}
