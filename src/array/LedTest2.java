package array;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.util.Objects;

import array.led.Colour;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import com.pi4j.io.i2c.I2CFactory.UnsupportedBusNumberException;

import static array.Constants.LED_COUNT_X;
import static array.Constants.LED_COUNT_Y;

public class LedTest2 implements AutoCloseable {

    public static final int ROWS = 8;
    public static final int COLS = 8;

    public static final int WS2812B_ADDR = 0x20;

    public static final int SET_ALL_LEDS_CMD = 0xfe;
    public static final int ALL_LEDS_OFF_CMD = 0xff;

    public static class LEDField {

        private Color[][] field = new Color[ROWS][COLS];

        public LEDField(Color[][] field) {
            this.field = field;
        }

        byte[] getColorField() {
            byte[] retVal = new byte[3*ROWS*COLS];
            int idx = 0;
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    retVal[idx++] = (byte)Math.min(field[j][i].getRed(),0xfd);
                    retVal[idx++] = (byte)Math.min(field[j][i].getGreen(),0xfd);
                    retVal[idx++] = (byte)Math.min(field[j][i].getBlue(),0xfd);
                }
            }
            return retVal;
        }
    }

    private I2CBus i2c;
    private I2CDevice device;

    public LedTest2() throws UnsupportedBusNumberException, IOException {
        i2c = I2CFactory.getInstance(I2CBus.BUS_0);

        device = i2c.getDevice(WS2812B_ADDR);
    }

    public void setLED(int index, Colour colour) throws IOException {
        device.write(index, new byte[]{(byte)colour.getRed(),(byte)colour.getGreen(),(byte)colour.getBlue()});
    }

//    public void setLED(LEDField field) throws IOException {
//        Objects.requireNonNull(field, "LEDField must not be null");
//        byte[] colorField = field.getColorField();
//
//        int offset = 0;
//        int size = Math.min(offset + 30,colorField.length);
//        device.write(SET_ALL_LEDS_CMD, colorField,offset,size);
//
//        while (offset + size < colorField.length) {
//            offset = offset + size;
//            size = Math.min(30,colorField.length - offset);
//            device.write(colorField,offset,size);
//        }
//    }

    public void clear() throws IOException {
        device.write(ALL_LEDS_OFF_CMD,new byte[0]);
    }

    @Override
    public void close() throws IOException {
        if (i2c != null) {
            clear();
            i2c.close();
        }
    }

    public static void main(String[] args) throws Exception {
        LedTest2 leds = new LedTest2();

        leds.clear();


        boolean even = true;
        while (true) {
            for (int x = 0; x < LED_COUNT_X; x++) {
                for (int y = 0; y < LED_COUNT_Y; y++) {
                    int index = (x * LED_COUNT_Y) + y;
                    int green = 0;
                    if ((index % 2 == 0 && even) || (index % 2 == 1 && !even)) {
                        green = 255;
                    }
                    leds.setLED(index, new Colour(0, green, 0));
                }
            }
            even = !even;
        }
    }
}
