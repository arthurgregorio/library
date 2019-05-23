package br.eti.arthurgregorio.library.infrastructure.jsf.primefaces;

import org.primefaces.component.message.Message;
import org.primefaces.component.messages.MessagesRenderer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Customization to make the {@link Message} component able to close itself after some time
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.1.0, 23/05/2019
 */
public class AutoCloseMessages extends MessagesRenderer {

    /**
     * {@inheritDoc}
     *
     * @param context
     * @param component
     * @throws IOException
     */
    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

        super.encodeEnd(context, component);

        final ResponseWriter writer = context.getResponseWriter();

        writer.write('\n');
        writer.startElement("script", null);
        writer.writeText("setTimeout(\"$(\'#" + this.sanitizeId(component.getClientId())
                + "\').slideUp(500)\", 8000)", null);
        writer.endElement("script");
        writer.append('\r');
        writer.append('\n');
    }

    /**
     * Sanitize the component to enable jQuery to find them
     *
     * @param actualId auto generated component id
     * @return the new sanitized id
     */
    private String sanitizeId(String actualId) {
        return actualId.replace(":", "\\\\\\\\:");
    }
}