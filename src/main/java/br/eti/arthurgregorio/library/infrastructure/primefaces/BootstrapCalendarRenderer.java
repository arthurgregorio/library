package br.eti.arthurgregorio.library.infrastructure.primefaces;

import org.primefaces.component.calendar.Calendar;
import org.primefaces.component.calendar.CalendarRenderer;

import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Customization for bootstrap 3 compatibility in the {@link Calendar} component
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 03/02/2016
 */
public class BootstrapCalendarRenderer extends CalendarRenderer {

    /**
     * {@inheritDoc}
     *
     * @param context
     * @param calendar
     * @param id
     * @param value
     * @param popup
     * @throws IOException
     */
    @Override
    protected void encodeInput(FacesContext context, Calendar calendar, String id, String value, boolean popup)
            throws IOException {

        final String styleClass = calendar.getInputStyleClass() + " form-control";

        calendar.setInputStyleClass(styleClass);

        super.encodeInput(context, calendar, id, value, popup);
    }
}