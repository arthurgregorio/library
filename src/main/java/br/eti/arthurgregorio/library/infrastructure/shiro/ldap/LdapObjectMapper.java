package br.eti.arthurgregorio.library.infrastructure.shiro.ldap;

import org.apache.commons.lang3.reflect.FieldUtils;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Object mapper to map {@link Attributes} from LDAP to a given domain object
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.3.0, 20/01/2020
 */
public class LdapObjectMapper<T> {

    /**
     * Map the values from {@link Attributes} list to the given domain object
     *
     * @param attributes {@link List} with all attributes of the LDAP object
     * @param clazz to receive the values from the LDAP object
     * @return {@link List} of domain objects with the mapped values
     */
    public List<T> map(List<Attributes> attributes, Class<T> clazz) {

        final List<T> values = new ArrayList<>();

        attributes.forEach(attribute -> {
            this.extractValue(clazz, attribute).ifPresent(values::add);
        });

        return values;
    }

    /**
     * Method used to extract the values from a given attribute and put it into the domain model object
     *
     * @param clazz to instantiate the domain object
     * @param attributes to extract the values
     * @return an {@link Optional} of the domain object
     */
    private Optional<T> extractValue(Class<T> clazz, Attributes attributes) {

        try {
            final T instance = clazz.getDeclaredConstructor().newInstance();

            final List<Field> fields = FieldUtils.getFieldsListWithAnnotation(instance.getClass(), LdapAttribute.class);

            for (Field field : fields) {
                final LdapAttribute annotation = field.getAnnotation(LdapAttribute.class);

                final Attribute attribute = attributes.get(annotation.value());

                if (attribute != null) {
                    FieldUtils.writeField(field, instance, attribute.get(), true);
                }
            }
            return Optional.of(instance);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }
}