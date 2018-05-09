package array.web;

import java.util.HashMap;
import java.util.Map;

public class UrlParser {

    private UrlCorrector urlCorrector;

    public UrlParser() {
        urlCorrector = new UrlCorrector();
    }

    public Map<String, String> parseURL(String url) {
        Map<String, String> parameters = new HashMap<>();

        for (String param : url.split(",")) {
            int equalsIndex = param.indexOf("=");
            String key = param.substring(0, equalsIndex);
            String value = param.substring(equalsIndex + 1);
            parameters.put(key, value);
        }

        return parameters;
    }

}
