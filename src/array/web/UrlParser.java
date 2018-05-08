package array.web;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlParser {

    private static final Pattern REQUEST_ROOT_PATTERN = Pattern.compile("(.*?)?");
    private static final Pattern PARAMETER_PATTERN = Pattern.compile("(([a-zA-Z0-9]+)=([a-zA-Z0-9.+%\\-]*))&?");

    private UrlCorrector urlCorrector;

    public UrlParser() {
        urlCorrector = new UrlCorrector();
    }

    public Map<String, String> parseURL(String url) {
        // PARSE THE URL AND RETURN THE PATH AND GET PARAMETERS
        Map<String, String> parameters = new HashMap<>();
        Matcher root = REQUEST_ROOT_PATTERN.matcher(url);
        url.replace(root.group(1) + "?", "");

        while (true) {
            Matcher params = PARAMETER_PATTERN.matcher(url);
            if (params.find()) {
                parameters.put(params.group(1), urlCorrector.correctUrl(params.group(2)));
                url = url.replace(params.group(0), "");
            } else {
                break;
            }
        }

        return parameters;
    }

}
