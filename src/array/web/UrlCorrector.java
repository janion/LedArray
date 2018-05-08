package array.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class UrlCorrector {

    private static final Map<String, String> CORRECTIONS = new LinkedHashMap<>();

    static {
        CORRECTIONS.put("+", " ");
        CORRECTIONS.put("%20", " ");
        CORRECTIONS.put(".", ".");
        CORRECTIONS.put("%2C", ",");
        CORRECTIONS.put("%21", "!");
        CORRECTIONS.put("%3F", "?");
        CORRECTIONS.put("%2F", "/");
        CORRECTIONS.put("%2B", "+");
        CORRECTIONS.put("-", "-");
        CORRECTIONS.put("%3D", "=");
        CORRECTIONS.put("*", "*");
        CORRECTIONS.put("%3A", ",");
        CORRECTIONS.put("%3B", ";");
        CORRECTIONS.put("%27", "'");
        CORRECTIONS.put("%23", "#");
        CORRECTIONS.put("%28", "(");
        CORRECTIONS.put("%29", ")");
    }

    public String correctUrl(String url) {
        String newUrl = url;
        for (Map.Entry<String, String> entry : CORRECTIONS.entrySet()) {
            newUrl = newUrl.replace(entry.getKey(), entry.getValue());
        }
        return newUrl;
    }

}
