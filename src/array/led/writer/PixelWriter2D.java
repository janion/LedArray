package array.led.writer;

import array.led.Colour;

import java.util.LinkedHashSet;
import java.util.Set;

public abstract class PixelWriter2D extends PixelWriter {

    private int ledCountX;
    private int ledCountY;

    public PixelWriter2D(String name, int ledCountX, int ledCountY) {
        super(name);
        this.ledCountX = ledCountX;
        this.ledCountY = ledCountY;
    }

    @Override
    public Set<Colour> calculate(double time) {
        Set<Colour> data = new LinkedHashSet<>();
        for (int x = 0; x < ledCountX; x++) {
            if ((x % 2) == 0) {
                addColumnForwards(x, time, data);
            } else {
                addColumnBackwards(x, time, data);
            }
        }
        return data;
    }

    private void addColumnForwards(int x, double time, Set<Colour> data) {
        for (int y = 0; y < ledCountY; y++) {
            data.add(evaluateCell(x, y, time));
        }
    }

    private void addColumnBackwards(int x, double time, Set<Colour> data) {
        for (int y = ledCountY - 1; y >= 0; y--) {
            data.add(evaluateCell(x, y, time));
        }
    }

    @Override
    public void reset(double time) {
        // Do nothing
    }

    protected abstract Colour evaluateCell(int x, int y, double time);

}
