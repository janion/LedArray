package array.led.builtin.rainbow;

import array.led.Colour;
import array.led.ColourWheel;
import array.led.configure.custom.CustomConfigurer;
import array.led.configure.custom.item.ColourConverter;
import array.led.configure.custom.item.ColourItem;
import array.led.writer.PixelWriter2D;

import java.util.Collections;

public class SolidColourPixelWriter extends PixelWriter2D {

    private static final String NAME = "Solid colour";

    private static final Colour DEFAULT_COLOUR = new Colour(255, 255, 255);
    private static final String COLOUR_KEY = "colour";
    private static final String COLOUR_TITLE = "Colour";

    private ColourConverter colourConverter;
    private ColourWheel colourWheel;
    private Colour colour;

    public SolidColourPixelWriter(int ledCountX, int ledCountY) {
        super(NAME, ledCountX, ledCountY);
        colourConverter = new ColourConverter();
        colourWheel = new ColourWheel();
        colour = DEFAULT_COLOUR;

        ColourItem colourItem = new ColourItem(COLOUR_TITLE, COLOUR_KEY, this::setColourFromString, this::getColourString);
        setConfigurer(new CustomConfigurer(this, NAME, Collections.singleton(colourItem)));
    }

    @Override
    protected Colour evaluateCell(int x, int y, double time) {
        return colour;
    }

    private String getColourString() {
        return colourConverter.convertFromColourToHtml(colour);
    }

    public void setColourFromString(String colourString) {
        colour = colourConverter.convertFromHtmlToColour(colourString);
    }

}
