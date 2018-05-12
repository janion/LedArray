import array.led.Colour;
import com.diozero.ws281xj.LedDriverInterface;
import com.diozero.ws281xj.StripType;
import com.diozero.ws281xj.spi.WS281xSpi;

import static array.Constants.*;

public class LedTest {

    public static void main(String[] args) {
        LedDriverInterface strip = new WS281xSpi(LED_PIN, 0, StripType.WS2812, LED_COUNT_TOTAL, LED_BRIGHTNESS);
        strip.allOff();

        boolean even = true;
        while (true) {
            for (int x = 0; x < LED_COUNT_X; x++) {
                for (int y = 0; y < LED_COUNT_Y; y++) {
                    int index = (x * LED_COUNT_Y) + y;
                    int green = 0;
                    if ((index % 2 == 0 && even) || (index % 2 == 1 && !even)) {
                        green = 255;
                    }
                    strip.setPixelColourRGB(index, 0, green, 0);
                }
            }
            strip.render();
            even = !even;
        }
    }

}
