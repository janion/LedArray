package array.led.configure.custom.validation;

import java.util.Map;
import java.util.Map.Entry;

public class ValidationScriptCreator {

    private static final String VALIDATION_SCRIPT_FORMAT = "<script>\n" +
            "function validateForm() {\n" +
            "%s\n" +
            "if (%s) {\n" +
            "alert(\"%s\");\n" +
            "return false;\n" +
            "}\n" +
            "}\n" +
            "</script>";

    private static final String VARIABLE_ASSIGNMENT_FORMAT = "   var %s = document.forms[\"configure\"][\"%s\"].value;";
    private static final String FORM_ACTION = " onsubmit=\"return validateForm()\"";

    private String errorMessage;
    private String errorCondition;
    private Map<String, String> fieldNames;

    public ValidationScriptCreator(String errorMessage, String errorCondition, Map<String, String> fieldNames) {
        this.errorMessage = errorMessage;
        this.errorCondition = errorCondition;
        this.fieldNames = fieldNames;
    }

    public String createValidationScript() {
        String variables = "";
        for (Entry<String, String> entry : fieldNames.entrySet()) {
            String variable = String.format(VARIABLE_ASSIGNMENT_FORMAT, entry.getKey(), entry.getValue());
            if (!variables.equals("")) {
                variables += "\n";
            }
            variables += variable;
        }
        return String.format(VALIDATION_SCRIPT_FORMAT, variables, errorCondition, errorMessage);
    }

    public String createValidatingFormAction() {
        return FORM_ACTION;
    }

}
