package br.eti.arthurgregorio.library.infrastructure.mail;

import br.eti.arthurgregorio.library.domain.model.exception.BusinessLogicException;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple content provider that use Mustache as template processor
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 03/04/2018
 */
public class MustacheProvider implements MailContentProvider {

    private final Mustache mustache;
    
    private final Map<String, Object> data;
    
    /**
     * Constructor
     * 
     * @param template the template file name inside src/java/resources/mail
     */
    public MustacheProvider(String template) {
        
        this.data = new HashMap<>();
        
        final MustacheFactory factory = new DefaultMustacheFactory();
        this.mustache = factory.compile("/mail/" + template);
    }
    
    /**
     * Add some content to the provider
     * 
     * @param key the key to use on the template
     * @param value the value to retrieve through the key in the template
     */
    public void addContent(String key, Object value) {
        this.data.put(key, value);
    }

    /**
     * {@inheritDoc }
     * 
     * @return 
     */
    @Override
    public String getContent() {
        
        final StringWriter writer = new StringWriter();
        
        try {
            this.mustache.execute(writer, this.data)
                .flush();
        } catch (IOException ex) {
            throw BusinessLogicException.create("error.core.email-content-error", ex);
        }
        
        return writer.toString();
    }
}
