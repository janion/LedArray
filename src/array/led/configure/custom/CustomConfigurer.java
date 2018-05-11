package array.led.configure.custom;

import array.led.writer.PixelWriter;
import array.led.configure.BasicConfigurer;
import array.led.configure.custom.item.Item;
import array.led.configure.custom.validation.ValidationScriptCreator;

import java.util.Map;
import java.util.Set;

public class CustomConfigurer extends BasicConfigurer {

    private static final String HTML_FORMAT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<title>Configure %s</title>\n" +
            "%s\n" +
            "</head>\n" +
            "<body> <h1>Configure %s</h1>\n" +
            "<form name=\"configure\" action=\"/configure\"%s>\n" +
            "<input type=\"hidden\" name=\"name\" value=\"%s\">\n" +
            "%s\n" +
            "<input type=\"submit\" name=\"action\" value=\"Submit\">\n" +
            "<input type=\"submit\" name=\"action\" value=\"Submit and Set\">\n" +
            "<input type=\"submit\" name=\"action\" value=\"Cancel\">\n" +
            "</form>\n" +
            "</body>\n" +
            "</html>\n";

    private static final String SET_REDIRECT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<script type=\"text/javascript\">\n" +
            "window.location.href = \"/setPattern?name=%s\"\n" +
            "</script>\n" +
            "</head>\n" +
            "</html>\n";

    private static final String EMPTY = "";

    private PixelWriter writer;
    private String patternName;
    private Set<Item> configurationItems;
    private String validationScript;
    private String validationAction;

    public CustomConfigurer(PixelWriter writer, String patternName, Set<Item> configurationItems){
        this.patternName = patternName;
        this.configurationItems = configurationItems;
        this.writer = writer;
        validationScript = EMPTY;
        validationAction = EMPTY;
    }

    public void setValidation(ValidationScriptCreator validationCreator) {
        validationScript = validationCreator.createValidationScript();
        validationAction = validationCreator.createValidatingFormAction();
    }

    public String configure(Map<String, String> parameters) {
        String action = parameters.get("action");
        if ("Cancel".equals(action)) {
            return REDIRECT;
        }

        for (Item item : configurationItems) {
            configureItem(item, parameters);
        }

        if (action != null) {
            if (action.equals("Submit and Set")) {
                return String.format(SET_REDIRECT, patternName);
            } else {
                return REDIRECT;
            }
        } else {
            String formContent = "";
            for (Item item : configurationItems) {
                formContent += "\n" + item.createFormEntry();
            }
            return String.format(HTML_FORMAT, patternName, validationScript, patternName,
                    validationAction, patternName, formContent);
        }
    }


    public void configureItem(Item item, Map<String, String> parameters) {
        String value = parameters.get(item.getKey());
        if (value != null) {
            item.setValue(value);
        }
    }
    
}
