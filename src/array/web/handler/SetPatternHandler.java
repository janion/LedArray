package array.web.handler;

import array.led.PixelUpdater;
import array.led.writer.PixelWriter;
import array.led.PixelWriterFactory;
import array.pattern.PatternManager;

import java.util.Map;

public class SetPatternHandler extends Handler {

    public SetPatternHandler(PixelUpdater pixelUpdater, PixelWriterFactory writerFactory, PatternManager patternManager) {
        super(pixelUpdater, writerFactory, patternManager);
    }

    @Override
    protected String createResponse(Map<String, String> parameters) {
        String name = parameters.get("name");
        if (name != null) {
            patternManager.setPattern(name);
            PixelWriter writer = patternManager.getCurrentWriter();
            pixelUpdater.setPixelWriter(writer);
        }
        return EMPTY_REDIRECT;
    }

}
