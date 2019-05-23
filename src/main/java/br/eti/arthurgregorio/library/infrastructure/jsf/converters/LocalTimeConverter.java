package br.eti.arthurgregorio.library.infrastructure.jsf.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple JSF converter to provide {@link LocalTime} support to the UI
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 13/12/2017
 */
@FacesConverter("localTimeConverter")
public class LocalTimeConverter implements Converter {

    /**
     * {@inheritDoc }
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        return value == null ? null : LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * {@inheritDoc }
     *
     * @param context
     * @param component
     * @param value
     * @return
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        final LocalTime time = (LocalTime) value;
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
