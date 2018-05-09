package array.led;

import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.diozero.ws281xj.LedDriverInterface;
import com.diozero.ws281xj.StripType;
import com.diozero.ws281xj.spi.WS281xSpi;

import static array.Constants.LED_BRIGHTNESS;
import static array.Constants.LED_COUNT_TOTAL;
import static array.Constants.LED_PIN;


public class PixelUpdater {
	
	private PixelWriter writer;
	private LedDriverInterface strip;
	private Lock writerLock = new ReentrantLock();
	private double startTime;
	private boolean stopped;
	
	public PixelUpdater(PixelWriter writer) {
		this.writer = writer;
		
		strip = new WS281xSpi(LED_PIN, 0, StripType.WS2812, LED_COUNT_TOTAL, LED_BRIGHTNESS);
		strip.allOff();
		
		startTime = time();
	}
    
    public void setPixelWriter(PixelWriter writer) {
        writerLock.lock();
        try {
            this.writer = writer;
            startTime = time();
            writer.reset(startTime);
        } finally {
        	writerLock.unlock();
        }
        System.out.println("Pixel writer set");
    }

    public void setBrightness(int val) {
        writerLock.lock();
        try {
        	// TODO: find a way around this
        	// Might be able to close old strip and instantiate a new one
        	strip.close();
            strip = new WS281xSpi(LED_PIN, 0, StripType.WS2812, LED_COUNT_TOTAL, LED_BRIGHTNESS);
    		strip.allOff();
        } finally {
        	writerLock.unlock();
        }
    }

    public void stop() {
        stopped = true;
    }

    public void updateLoop() {
        while (!stopped) {
            writerLock.lock();
            Set<Colour> data = null;
            try {
                data = writer.getPixelData(time() - startTime);
            } catch (Exception exptn) {
            	continue;
            } finally {
            	writerLock.unlock();
            }
            int i = 0;
            for (Colour colour : data) {
                Colour datum = correctColour(colour);
                strip.setPixelColourRGB(i++, datum.getRed(), datum.getGreen(), datum.getBlue());
            }
            strip.render();
        }
    }

	private Colour correctColour(Colour colour) {
		return new Colour(colour.getGreen(), colour.getRed(), colour.getBlue());
	}
	
	private double time() {
		return System.currentTimeMillis() / 1000.0;
	}
	
}
