package br.eti.arthurgregorio.library.application.components;

import com.google.common.base.Preconditions;
import java.util.concurrent.ThreadLocalRandom;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * RGB color implementation to use with color pickers on the UI
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 22/01/2017
 */
@NoArgsConstructor
public class Color {

    @Getter
    private int red;
    @Getter
    private int green;
    @Getter
    private int blue;
    @Getter
    private double alpha;

    /**
     * Creates a new color with values provided by the user
     *
     * @param red red value (0-255)
     * @param green green value (0-255)
     * @param blue blue value (0-255)
     */
    public Color(int red, int green, int blue) {
        this(red, green, blue, 1.0f);
    }

    /**
     * Creates a new color with values provided by the user
     *
     * @param red red value (0-255)
     * @param green green value (0-255)
     * @param blue blue value (0-255)
     * @param alpha alpha value to make the color more transparent or brighter
     */
    public Color(int red, int green, int blue, double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    /**
     * @return a litle darker tone of this color
     */
    public Color darker() {
        return new Color(this.getRed(), this.getGreen(), this.getBlue());
    }
    
    /**
     * @return a litle lighter tone fo this color
     */
    public Color lighter() {
        return new Color(this.getRed(), this.getGreen(), this.getBlue(), 0.6);
    }

    /**
     * @return the rgba(255,255,255,1) string for this color
     */
    @Override
    public String toString() {
        return "rgba(" + this.getRed() + "," + this.getGreen() + "," + this.getBlue() + "," + this.getAlpha() + ")";
    }

    /**
     * This method randomize a color and return
     * 
     * @return a random color
     */
    public static Color randomize() {

        int rgb[] = new int[3];

        for (int i = 0; i < 3; i++) {
            rgb[i] = ThreadLocalRandom.current().nextInt(1, 255 + 1);
        }
        return new Color(rgb[0], rgb[1], rgb[2]);
    }
    
    /**
     * Parse the color from RGB(A) string to the color object
     *
     * @param data the RGB(A) string, must not be null
     * @return the color object
     */
    public static Color parse(String data) {
        
        Preconditions.checkNotNull(data);
        
        final String numbers = data
                .replace(" ", "")
                .replace("rgba", "")
                .replace("rgb", "")
                .replace("(", "")
                .replace(")", "");
        
        final String color[] = numbers.split(",");
        
        if (color.length < 4) {
            return new Color(Integer.valueOf(color[0]), 
                    Integer.valueOf(color[1]), 
                    Integer.valueOf(color[2]));
        } else {
            return new Color(Integer.valueOf(color[0]), 
                    Integer.valueOf(color[1]), 
                    Integer.valueOf(color[2]), 
                    Double.valueOf(color[3]));
        }
    }
}
