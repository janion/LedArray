package array.led;

import array.led.writer.PixelWriter;

public interface PixelUpdater {
    
    public void setPixelWriter(PixelWriter writer);

    public void setBrightness(int brightness);

    public void stop();

    public void updateLoop();

    public int getBrightness();
}
