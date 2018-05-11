package array.web;

import array.led.PixelUpdater;
import array.led.builtin.BuiltinFunctionManager;
import array.led.configure.Configurer;
import array.pattern.Pattern;
import array.pattern.PatternManager;

import java.util.LinkedHashSet;
import java.util.Set;

public class HomePageCreator {


    private static final String HTML_FORMAT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "%s\n" +
            "<body>\n" +
            "<h1>Table-top patterns</h1>\n" +
            "<b>Current pattern:</b> %s<br>\n" +
            "<form action=\"/setBrightness\">\n"+
            "<input type=\"number\" name=\"brightness\" min=\"0\" max=\"255\" value=\"%d\"> <input type=\"submit\" value=\"Set Brightness(0-255)\">\n"+
            "</form>\n"+
            "<br>\n"+
            "<div style=\"overflow-x:auto;\">\n"+
            "<table border=\"1\">\n"+
            "<tr><th></th><th>Name</th><th></th></tr> %s\n"+
            "</table>\n"+
            "</div>\n"+
            "<br>\n"+
            "<div style=\"overflow-x:auto;\">\n"+
            "<table border=\"1\">\n"+
            "<tr><th></th><th></th><th>Name</th><th>Red Function</th><th>Green Function</th><th>Blue Function</th></tr> %s\n"+
            "</table>\n"+
            "</div>\n"+
            "<br>\n"+
            "<br>\n"+
            "<form name=\"addPattern\" action=\"/addPattern\" onsubmit=\"return validateName()\">\n"+
            "<b><u>Add Pattern</u></b><br>\n"+
            "Pattern name:<br>\n"+
            "<input type=\"text\" name=\"name\" required><br>\n"+
            "Red function:<br>\n"+
            "<input type=\"text\" name=\"red\" required><br>\n"+
            "Green function:<br>\n"+
            "<input type=\"text\" name=\"green\" required><br>\n"+
            "Blue function:<br>\n"+
            "<input type=\"text\" name=\"blue\" required><br>\n"+
            "<br>\n"+
            "<input type=\"submit\" value= \"Add pattern\">\n"+
            "</form>\n"+
            "</body>\n"+
            "</html>";

    private static final String CUSTOM_PATTERN_ROW_FORMAT = "<tr><td><a href=\"/setPattern?name=%s\">Set</a></td><td>" +
            "<a href=\"/removePattern?name=%s\">Remove</a></td>" +
            "<td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";

    private static final String BUILTIN_PATTERN_ROW_FORMAT = "<tr><td><a href=\"/setPattern?name=%s\">Set</a></td><td>%s</td><td>%s</td></tr>";

    private static final String LINK_FORMAT = "<a href=\"/configure?name=%s\">Configure</a>";

    private PixelUpdater pixelUpdater;

    public HomePageCreator(PixelUpdater pixelUpdater) {
        this.pixelUpdater = pixelUpdater;
    }

    public String buildHomePage(PatternManager patternManager) {
        StringBuilder customRows = new StringBuilder();
        buildCustomPatternHtmlTable(patternManager).forEach(row -> customRows.append(row + "\n"));
        StringBuilder builtinRows = new StringBuilder();
        buildBuiltinPatternHtmlTable(patternManager).forEach(row -> builtinRows.append(row + "\n"));
        String response = String.format(HTML_FORMAT, new HeadCreator().createHead(patternManager), patternManager.getCurrentPatternName(),
                pixelUpdater.getBrightness(), builtinRows.toString(), customRows.toString());
        return response;
    }

    private Set<String> buildCustomPatternHtmlTable (PatternManager patternManager){
        Set<String> customRows = new LinkedHashSet<>();
        for (Pattern pattern : patternManager.getPatterns()) {
            customRows.add(String.format(CUSTOM_PATTERN_ROW_FORMAT, pattern.getName(), pattern.getName(), pattern.getName(),
                    pattern.getRedFunctionString(), pattern.getGreenFunctionString(),
                    pattern.getBlueFunctionString()
            ));
        }
        return customRows;
    }

    private Set<String> buildBuiltinPatternHtmlTable(PatternManager patternManager) {
        Set<String> builtinRows = new LinkedHashSet<>();
        BuiltinFunctionManager builtinManager = patternManager.getBuiltinPatternsManager();
        for (String name : builtinManager.getPatternNames()) {
            Configurer configurer = builtinManager.getWriter(name).getConfigurer();
            String finalColumn = configurer.isConfigurable() ? String.format(LINK_FORMAT, name) : "";
            builtinRows.add(String.format(BUILTIN_PATTERN_ROW_FORMAT, name, name, finalColumn));
        }
        return builtinRows;
    }

}
