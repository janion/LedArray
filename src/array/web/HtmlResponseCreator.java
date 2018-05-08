package array.web;

import array.led.PixelUpdater;
import array.led.PixelWriter;
import array.led.PixelWriterFactory;
import array.led.configure.Configurer;
import array.pattern.PatternManager;

import java.util.Map;

public class HtmlResponseCreator {

    private static final String REDIRECT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<script type=\"text/javascript\">\n" +
            "setTimeout(\"location.href = '/';\",%d);\n" +
            "</script>\n" +
            "</head>\n" +
            "<body>\n" +
            "%s\n" +
            "</body>\n" +
            "</html>\n";

    private static final String EMPTY_REDIRECT = String.format(REDIRECT, 0, "");

    private static final String INVALID_PATTERN_REDIRECT = String.format(REDIRECT, 5000, "<h1>Invalid pattern functions:</h1><br>\n" +
            "Red = %s<br>\n" +
            "Green = %s<br>\n" +
            "Blue = %s<br>\n" +
            "You will be redirected in 5 seconds.\n");

    private static final String INVALID_NAME_REDIRECT = String.format(REDIRECT, 5000, "<h1>Invalid pattern name: %s</h1><br>\n" +
            "This pattern name is already in use.\n" +
            "You will be redirected in 5 seconds.\n");

    private static final String INVALID_REQUEST_REDIRECT = String.format(REDIRECT, 5000, "<h1>Invalid request</h1><br>\n" +
            "You will be redirected in 5 seconds.\n");

    private PixelUpdater pixelUpdater;
    private PixelWriterFactory writerFactory;
    private PatternManager patternManager;
    private UrlParser urlParser;
    private HomePageCreator homePageCreator;

    public HtmlResponseCreator(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        this.pixelUpdater = pixelUpdater;
        this.writerFactory = writerFactory;
        this.patternManager = patternManager;
        urlParser = new UrlParser();
        homePageCreator = new HomePageCreator();
    }

//    public String createResponse(String request) {
//    path, parameters = self.urlParser.parseURL(request);
//            if path.startswith("/setPattern"):
//    name = parameters.get("name", None)
//            self._setPattern(name)
//            return self._buildResponse(self.EMPTY_REDIRECT)
//
//    elif path.startswith("/addPattern"):
//    name = parameters.get("name", None)
//            if self.patterns.isUniqueName(name):
//    red = parameters.get("red", None)
//    green = parameters.get("green", None)
//    blue = parameters.get("blue", None)
//            if self.patterns.addPattern(name, red, green, blue):
//            return self._buildResponse(self.EMPTY_REDIRECT)
//            else:
//            return self._buildResponse(self.INVALID_PATTERN_REDIRECT %(red, green, blue))
//            else:
//            return self._buildResponse(self.INVALID_NAME_REDIRECT % name)
//
//    elif path.startswith("/removePattern"):
//    name = parameters.get("name", None)
//            self.patterns.removePattern(name)
//            return self._buildResponse(self.EMPTY_REDIRECT)
//
//    elif path.startswith("/setBrightness"):
//    val = int(parameters.get("brightness", 255))
//            self.updater.setBrightness(val)
//            return self._buildResponse(self.EMPTY_REDIRECT)
//
//    elif path.startswith("/configure"):
//            return self._buildResponse(self._configurePattern(parameters))
//
//    elif path == "/":
//            return self._buildResponse(self.homePageCreator.buildHomePage(self.patterns))
//
//            return self._buildResponse(self.INVALID_REQUEST_REDIRECT)

    private void setPattern(String name) {
        patternManager.setPattern(name);
        PixelWriter writer = patternManager.getCurrentWriter();
        pixelUpdater.setPixelWriter(writer);
    }

    public String configurePattern(Map<String, String> parameters) {
        String name = parameters.getOrDefault("name", null);
        Configurer config = patternManager.getWriter(name).getConfigurer();
        return config.configure(parameters);
    }

}
