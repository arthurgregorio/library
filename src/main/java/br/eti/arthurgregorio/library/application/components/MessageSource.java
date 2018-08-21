package br.eti.arthurgregorio.library.application.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * This is a helper class to obtain the i18n messages through the given key
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 16/02/2018
 */
public final class MessageSource {

    private static final Logger LOG;
    private static final ResourceBundle MESSAGES;
    
    /**
     * Initialize the base properties
     */
    static {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        
        MESSAGES = ResourceBundle.getBundle("i18n.messages", 
                facesContext.getApplication().getDefaultLocale());
        
        LOG = LoggerFactory.getLogger(MessageSource.class);
    }
    
    /**
     * Give a key and get the message, if the key exists
     * 
     * @param key the i18n key
     * @return the message
     */
    public static String get(String key) {
        
        if (MESSAGES.containsKey(checkNotNull(key))) {
            return MESSAGES.getString(key);
        }
        
        LOG.error("No message found for key {0}", key);
        
        return key;
    }
}
