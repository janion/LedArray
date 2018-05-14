import arduino.Arduino;

import static array.Constants.*;

public class LedSerialTest {

    private static final String RENDER = "" + (char) 254 + (char) 254 + (char) 254 + (char) 254;
    //private static final String BRIGHTNESS = "" + (char) 255 + (char) 255 + (char) 255;

    public static void main(String[] args) {
        Arduino arduino = new Arduino("/dev/ttyUSB0", 9600);
        arduino.openConnection();

        boolean even = true;
        while (true) {
            for (int x = 0; x < LED_COUNT_X; x++) {
                for (int y = 0; y < LED_COUNT_Y; y++) {
                    int index = (x * LED_COUNT_Y) + y;
                    int green = 0;
                    if ((index % 2 == 0 && even) || (index % 2 == 1 && !even)) {
                        green = 255;
                    }
                    setPixel(arduino, index, 0, green, 0);
                }
            }
            render(arduino);
            even = !even;
        }
    }

    private static void setPixel(Arduino arduino, int index, int r, int g, int b) {
        String str = "";
        str += (char) index;
        str += (char) r;
        str += (char) g;
        str += (char) b;

        arduino.serialWrite(str);
    }

    private static void render(Arduino arduino) {
        arduino.serialWrite(RENDER);
    }

}
