package array.web.handler;

import array.led.PixelUpdater;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;

import java.util.Map;

public class RemovePatternHandler extends Handler {

    public RemovePatternHandler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        super(pixelUpdater, writerFactory, patternManager);
    }

    @Override
    protected String createResponse(Map<String, String> parameters) {
        String name = parameters.get("name");
        if (name != null) {
            patternManager.removePattern(name);
        }
        return EMPTY_REDIRECT;
    }

}
