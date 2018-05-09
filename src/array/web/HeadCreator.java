package array.web;

import array.pattern.PatternManager;

public class HeadCreator {

    private static final String HEAD = "<head>\n" +
            "<title>Table-top patterns</title>\n" +
            "<style>\n" +
            "table {\n" +
            "border-collapse: collapse;\n" +
            "width: 100%%;\n" +
            "white-space: nowrap;\n" +
            "}\n" +
            "th, td {\n" +
            "padding: 2px;\n" +
            "text-align: center;\n" +
            "border-bottom: 1px solid #ddd;\n" +
            "}\n" +
            "th {\n" +
            "background-color: #5050FF;\n" +
            "color: white;\n" +
            "}\n" +
            "tr:hover{background-color:#f5f5f5}\n" +
            "</style>\n" +
            "%s\n" +
            "</head>\n";

    private static final String VALIDATION_SCRIPT_FORMAT = "<script>\n" +
            "function validateName() {\n" +
            "var names = [%s\n" +
            "];\n" +
            "var name = document.forms[\"addPattern\"][\"name\"].value;\n" +
            "if (names.indexOf(name) != -1) {\n" +
            "alert(\"Pattern must have unique name\");\n" +
            "return false;\n" +
            "}\n" +
            "}\n" +
            "</script>";

    private static final String NAMES_ROW_FORMAT = "\"%s\",\n";

    public String createHead(PatternManager patternManager) {
        return String.format(HEAD, createValidationScript(patternManager));
    }

    private String createValidationScript(PatternManager patternManager) {
        String rows = "";
        for (String name : patternManager.getAllPatternNames()) {
            rows += String.format(NAMES_ROW_FORMAT, name);
        }
        String script = String.format(VALIDATION_SCRIPT_FORMAT, rows);
        return script.substring(0, script.length());
    }

}
