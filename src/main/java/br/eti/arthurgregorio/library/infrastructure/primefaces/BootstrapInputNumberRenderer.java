package br.eti.arthurgregorio.library.infrastructure.primefaces;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputnumber.InputNumberRenderer;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.context.RequestContext;
import org.primefaces.util.HTML;

/**
 * Customization for bootstrap 3 compatibility in the {@link InputNumber} component
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 28/01/2016
 */
public class BootstrapInputNumberRenderer extends InputNumberRenderer {

    /**
     * {@inheritDoc}
     *
     * @param context
     * @param inputNumber
     * @param clientId
     * @param valueToRender
     * @throws IOException
     */
    @Override
    protected void encodeInput(FacesContext context, InputNumber inputNumber, String clientId, String valueToRender)
            throws IOException {

        final String styleClass = inputNumber.getInputStyleClass() + " form-control";

        inputNumber.setInputStyleClass(styleClass);

        super.encodeInput(context, inputNumber, clientId, valueToRender);
    }
}