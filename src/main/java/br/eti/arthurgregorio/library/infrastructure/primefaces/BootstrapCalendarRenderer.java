package br.eti.arthurgregorio.library.infrastructure.primefaces;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.calendar.CalendarRenderer;
import org.primefaces.context.RequestContext;
import org.primefaces.util.HTML;

/**
 * Renderizador customizado para o calendar dentro da aplicacao
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 03/02/2016
 */
public class BootstrapCalendarRenderer extends CalendarRenderer {

    private static final String CUSTOM_CLASSES = "form-control";

    /**
     * Novamente mesma treta do CustomInputNumberRenderer so que para o calendar
     * 
     * @param context
     * @param calendar
     * @param id
     * @param value
     * @param popup
     * 
     * @throws IOException
     */
    @Override
    protected void encodeInput(FacesContext context, Calendar calendar, 
            String id, String value, boolean popup) throws IOException {

        ResponseWriter writer = context.getResponseWriter();
        String type = popup ? "text" : "hidden";
        String labelledBy = calendar.getLabelledBy();

        writer.startElement("input", null);
        writer.writeAttribute("id", id, null);
        writer.writeAttribute("name", id, null);
        writer.writeAttribute("type", type, null);

        if (calendar.isRequired()) {
            writer.writeAttribute("aria-required", "true", null);
        }

        if (!isValueBlank(value)) {
            writer.writeAttribute("value", value, null);
        }

        if (popup) {
            String inputStyleClass = Calendar.INPUT_STYLE_CLASS + " " + CUSTOM_CLASSES;
            if (calendar.isDisabled()) {
                inputStyleClass = inputStyleClass + " ui-state-disabled";
            }
            if (!calendar.isValid()) {
                inputStyleClass = inputStyleClass + " ui-state-error";
            }

            writer.writeAttribute("class", inputStyleClass, null);

            if (calendar.isReadonly() || calendar.isReadonlyInput()) {
                writer.writeAttribute("readonly", "readonly", null);
            }
            if (calendar.isDisabled()) {
                writer.writeAttribute("disabled", "disabled", null);
            }

            renderPassThruAttributes(context, calendar, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
            renderDomEvents(context, calendar, HTML.INPUT_TEXT_EVENTS);
        }

        if (labelledBy != null) {
            writer.writeAttribute("aria-labelledby", labelledBy, null);
        }

        if (RequestContext.getCurrentInstance().getApplicationContext()
                .getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, calendar);
        }

        writer.endElement("input");
    }
}