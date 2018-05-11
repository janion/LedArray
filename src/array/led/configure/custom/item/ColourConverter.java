package array.led.configure.custom.item;

import array.led.Colour;

import java.util.Arrays;
import java.util.List;

public class ColourConverter {

    private static final String HASH = "#";
    private static final List<String> CHARACTERS = Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f");

    public Colour convertFromHtmlToColour(String colourString) {
        String red = colourString.substring(1, 3);
        String green = colourString.substring(3, 5);
        String blue = colourString.substring(5, 7);
        return new Colour(convertByteCodeToNumber(red), convertByteCodeToNumber(green), convertByteCodeToNumber(blue));
    }

    private int convertByteCodeToNumber(String byteString) {
        return (CHARACTERS.indexOf(byteString.substring(0, 1)) * 16) + CHARACTERS.indexOf(byteString.substring(1, 2));
    }

    public String convertFromColourToHtml(Colour colour) {
        int red = colour.getRed();
        int green = colour.getGreen();
        int blue = colour.getBlue();
        return HASH + convertNumberToByteCode(red) + convertNumberToByteCode(green) + convertNumberToByteCode(blue);
    }

    private String convertNumberToByteCode(int colourValue) {
        return CHARACTERS.get((int) (colourValue / 16)) + CHARACTERS.get(colourValue % 16);
    }

}
