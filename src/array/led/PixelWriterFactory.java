package array.led;

import array.pattern.Pattern;
import fn.Function;

public interface PixelWriterFactory {
	
	public PixelWriter createPixelWriter(Pattern pattern);

}
