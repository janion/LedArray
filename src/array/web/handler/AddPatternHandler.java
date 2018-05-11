package array.web.handler;

import array.led.PixelUpdater;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;

import java.util.Map;

public class AddPatternHandler extends Handler {

    private static final String INVALID_PATTERN_REDIRECT = String.format(REDIRECT, 5000, "\n<h1>Invalid pattern functions:</h1><br>\n" +
            "Red = %s<br>\n" +
            "Green = %s<br>\n" +
            "Blue = %s<br>\n" +
            "You will be redirected in 5 seconds.\n");

    private static final String INVALID_NAME_REDIRECT = String.format(REDIRECT, 5000, "\n<h1>Invalid pattern name: %s</h1><br>\n" +
            "This pattern name is already in use.\n" +
            "You will be redirected in 5 seconds.\n");

    public AddPatternHandler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        super(pixelUpdater, writerFactory, patternManager);
    }

    @Override
    protected String createResponse(Map<String, String> parameters) {
        String name = parameters.get("name");
        if (name != null && !name.equals("") && patternManager.isUniqueName(name)) {
            String red = parameters.get("red");
            String green = parameters.get("green");
            String blue = parameters.get("blue");
            if (patternManager.addPattern(name, red, green, blue)) {
                return EMPTY_REDIRECT;
            } else {
                return String.format(INVALID_PATTERN_REDIRECT, red, green, blue);
            }
        } else {
            return String.format(INVALID_NAME_REDIRECT, name);
        }
    }

}
