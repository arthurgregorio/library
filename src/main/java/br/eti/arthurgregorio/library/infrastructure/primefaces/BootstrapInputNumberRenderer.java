package br.eti.arthurgregorio.library.infrastructure.primefaces;

import org.primefaces.component.inputnumber.InputNumber;
import org.primefaces.component.inputnumber.InputNumberRenderer;

import javax.faces.context.FacesContext;
import java.io.IOException;

/**
 * Customization for bootstrap 3 compatibility in the {@link InputNumber} component
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 28/01/2016
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