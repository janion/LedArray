package array.led.configure;

import java.util.Map;

public interface Configurer {

    public String configure(Map<String, String> parameters);

    public boolean isConfigurable();

}
