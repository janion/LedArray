package array.web.handler;

import array.led.PixelUpdater;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;
import array.web.HomePageCreator;

import java.util.Map;

public class SetBrightnessHandler extends Handler {

    public SetBrightnessHandler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        super(pixelUpdater, writerFactory, patternManager);
    }

    @Override
    protected String createResponse(Map<String, String> parameters) {
        String val = parameters.get("brightness");
        if (val != null) {
            pixelUpdater.setBrightness(Integer.parseInt(val));
        }
        return EMPTY_REDIRECT;
    }

}
