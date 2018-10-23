package br.eti.arthurgregorio.library.infrastructure.jpa;

import br.eti.arthurgregorio.library.application.components.Color;
import javax.persistence.AttributeConverter;

/**
 * A simple JPA converter to use in conjunction of {@link Color} for persistent purpose
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 22/01/2017
 */
public class ColorConverter implements AttributeConverter<Color, String>{

    /**
     * {@inheritDoc }
     * 
     * @param attribute
     * @return 
     */
    @Override
    public String convertToDatabaseColumn(Color attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    /**
     * {@inheritDoc }
     * 
     * @param dbData
     * @return 
     */
    @Override
    public Color convertToEntityAttribute(String dbData) {
        return dbData != null ? Color.parse(dbData) : null;
    }
}
