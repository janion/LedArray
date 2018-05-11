package array.web.handler;

import array.led.PixelUpdater;
import array.led.writer.PixelWriter;
import array.led.PixelWriterFactory;
import array.led.configure.Configurer;
import array.pattern.PatternManager;

import java.util.Map;

public class ConfigureHandler extends Handler {

    public ConfigureHandler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        super(pixelUpdater, writerFactory, patternManager);
    }

    @Override
    protected String createResponse(Map<String, String> parameters) {
        String name = parameters.get("name");
        if (name != null) {
            PixelWriter writer = patternManager.getWriter(name);
            if (writer != null) {
                Configurer config = writer.getConfigurer();
                return config.configure(parameters);
            }
        }
        return EMPTY_REDIRECT;
    }

}
