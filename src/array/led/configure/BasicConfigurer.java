package array.led.configure;

import java.util.Map;

public class BasicConfigurer implements Configurer {

    protected static final String REDIRECT = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<script type=\"text/javascript\">\n" +
            "window.location.href = \"/\"\n" +
            "</script>\n" +
            "</head>\n" +
            "</html>\n";

    private boolean isConfigurable;

    public BasicConfigurer () {
        isConfigurable = !(this instanceof BasicConfigurer);
    }

    @Override
    public String configure(Map<String, String> parameters) {
        return REDIRECT;
    }

    @Override
    public boolean isConfigurable() {
        return isConfigurable;
    }
}
