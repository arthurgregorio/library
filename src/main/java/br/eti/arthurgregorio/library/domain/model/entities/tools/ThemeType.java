package br.eti.arthurgregorio.library.domain.model.entities.tools;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

/**
 * Enum to represent the themes of the application
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 18/07/2018
 */
public enum ThemeType {

    BLACK("theme-type.black", "skin-black"), BLACK_LIGHT("theme-type.black-light", "skin-black-light"),
    BLUE("theme-type.blue", "skin-blue"), BLUE_LIGHT("theme-type.blue-light", "skin-blue-light"),
    GREEN("theme-type.green", "skin-green"), GREEN_LIGHT("theme-type.green-light", "skin-green-light"),
    PURPLE("theme-type.purple", "skin-purple"), PURPLE_LIGHT("theme-type.purple-light", "skin-purple-light"),
    RED("theme-type.red", "skin-red"), RED_LIGHT("theme-type.red-light", "skin-red-light"),
    YELLOW("theme-type.yellow", "skin-yellow"), YELLOW_LIGHT("theme-type.yellow-light", "skin-yellow-light");

    @Getter
    private final String value;
    private final String description;

    /**
     * Constructor
     *
     * @param description the description for this enum, also is the i18n key
     * @param value the value of this enum type
     */
    ThemeType(String description, String value) {
        this.value = value;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String toString() {
        return this.description;
    }

    /**
     * Get only the color name of the skin
     * 
     * @return the color name
     */
    public String getColorName() {
        return this.value.replace("skin-", "");
    }
    
    /**
     * This method is used to parse a theme from his value to his type
     *
     * @param value the value of the theme
     * @return the type of the theme
     */
    public static ThemeType parseFromValue(String value) {

        final List<ThemeType> themes = Arrays.asList(ThemeType.values());

        return themes.stream()
                .filter(theme -> theme.matchValue(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Can't parse value ["+ value +"] for ThemeType enum"));
    }

    /**
     * Helper method to check if the value matches the instance value
     *
     * @param value the value to be tested
     * @return if the value matches or not
     */
    private boolean matchValue(String value) {
        return this.value.equals(value);
    }
}