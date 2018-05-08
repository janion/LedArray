package array.led;

import array.led.builtin.Configurer;

import java.awt.Color;
import java.util.List;

public interface PixelWriter {
	
	public List<Color> getPixelData(double time);
	
	public void reset(double time);

	public String getName();

	public Configurer getConfigurer();

}
