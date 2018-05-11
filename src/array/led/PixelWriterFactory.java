package array.led;

import array.led.writer.PixelWriter;
import array.pattern.Pattern;

public interface PixelWriterFactory {
	
	public PixelWriter createPixelWriter(Pattern pattern);

}
