package array.led;

import array.led.writer.PixelWriter;
import com.diozero.ws281xj.LedDriverInterface;
import com.diozero.ws281xj.StripType;
import com.diozero.ws281xj.spi.WS281xSpi;

import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static array.Constants.*;


public class MockPixelUpdater implements PixelUpdater {

	private int brightness;

	public MockPixelUpdater() {
        brightness = LED_BRIGHTNESS;
	}

    @Override
    public void setPixelWriter(PixelWriter writer) {
        System.out.println("Pixel writer set");
    }

    @Override
    public void setBrightness(int brightness) {
	    this.brightness = brightness;
    }

    @Override
    public void stop() {
    }

    @Override
    public void updateLoop() {
    }

    @Override
    public int getBrightness() {
        return brightness;
    }
}
