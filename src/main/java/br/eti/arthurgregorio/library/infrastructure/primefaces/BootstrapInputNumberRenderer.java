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
 * Customizacao da renderer do inputNumber para acertar sua classe CSS no 
 * layout da aplicacao
 * 
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.2.0, 28/01/2016
 */
public class BootstrapInputNumberRenderer extends InputNumberRenderer {

    private static final String CUSTOM_CLASSES = " form-control";

    /**
     * GAMBIARRA NECESSARIA! Zeus tenha piedade de mim e do programador que fez
     * a merda do inputnumber, o class deve ser escrito no input de saida e nao
     * na span que envolve o componente. Alias, pq tem um span la? :P
     * 
     * @param context
     * @param inputNumber
     * @param clientId
     * @param valueToRender 
     * @throws IOException 
     */
    @Override
    protected void encodeInput(FacesContext context, InputNumber inputNumber, String clientId, String valueToRender) throws IOException {
        
        ResponseWriter writer = context.getResponseWriter();
        String inputId = clientId + "_input";

        String inputStyle = inputNumber.getInputStyle();
        String inputStyleClass = inputNumber.getInputStyleClass();

        String style = inputStyle;
        
        String styleClass = InputText.STYLE_CLASS + CUSTOM_CLASSES;
        styleClass = inputNumber.isValid() ? styleClass : styleClass + " ui-state-error";
        styleClass = !inputNumber.isDisabled() ? styleClass : styleClass + " ui-state-disabled";
        if (!isValueBlank(inputStyleClass)) {
            styleClass += " " + inputStyleClass;
        }

        writer.startElement("input", null);
        writer.writeAttribute("id", inputId, null);
        writer.writeAttribute("name", inputId, null);
        writer.writeAttribute("type", inputNumber.getType(), null);
        writer.writeAttribute("value", valueToRender, null);

        renderPassThruAttributes(context, inputNumber, HTML.INPUT_TEXT_ATTRS_WITHOUT_EVENTS);
        renderDomEvents(context, inputNumber, HTML.INPUT_TEXT_EVENTS);

        if (inputNumber.isReadonly()) {
            writer.writeAttribute("readonly", "readonly", "readonly");
        }
        if (inputNumber.isDisabled()) {
            writer.writeAttribute("disabled", "disabled", "disabled");
        }

        if (!isValueBlank(style)) {
            writer.writeAttribute("style", style, null);
        }
        
        writer.writeAttribute("class", styleClass, null);

        if(RequestContext.getCurrentInstance().getApplicationContext().getConfig().isClientSideValidationEnabled()) {
            renderValidationMetadata(context, inputNumber);
        }

        writer.endElement("input");
    }
}