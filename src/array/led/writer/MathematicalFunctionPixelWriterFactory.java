package array.led.writer;

import array.led.PixelWriterFactory;
import array.pattern.Pattern;

public class MathematicalFunctionPixelWriterFactory implements PixelWriterFactory {

    private int ledCountX;
    private int ledCountY;

    public MathematicalFunctionPixelWriterFactory(int ledCountX, int ledCountY) {
        this.ledCountX = ledCountX;
        this.ledCountY = ledCountY;
    }

    @Override
    public PixelWriter createPixelWriter(Pattern pattern) {
        return new MathematicalFunctionPixelWriter(ledCountX, ledCountY, pattern);
    }

}
