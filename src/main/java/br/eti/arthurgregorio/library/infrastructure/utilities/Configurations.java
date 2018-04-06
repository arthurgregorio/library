package br.eti.arthurgregorio.library.infrastructure.utilities;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * The class that hold the configurations of the application reading the default 
 * properties file under the resources folder in the classpath
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 11/01/2018
 */
public final class Configurations {

    private static final ResourceBundle CONFIG_PROPERTIES;
    
    static {
        CONFIG_PROPERTIES = ResourceBundle.getBundle("application");
    }
    
    /**
     * For a given configuration key return his value if the key exists 
     * 
     * @param configurationKey the key to the configuration
     * @return the configuration
     */
    public static String get(String configurationKey) {
        try {
            return CONFIG_PROPERTIES.getString(configurationKey);
        } catch (MissingResourceException ex) {
            return null;
        }
    }

    /**
     * 
     * 
     * @param configurationKey
     * @return 
     */
    public static boolean getAsBoolean(String configurationKey) {
        return Boolean.valueOf(get(configurationKey));
    }
    
    /**
     * 
     * @param configurationKey
     * @return 
     */
    public static int getAsInteger(String configurationKey) {
        return Integer.valueOf(get(configurationKey));
    }
}
