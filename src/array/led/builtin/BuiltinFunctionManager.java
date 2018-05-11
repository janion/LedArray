package array.led.builtin;

import array.Constants;
import array.led.builtin.rainbow.SolidColourPixelWriter;
import array.led.writer.PixelWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuiltinFunctionManager {

    private List<PixelWriter> writers;
    private List<String> names;

    public BuiltinFunctionManager() {
        writers = Arrays.asList(new SolidColourPixelWriter(Constants.LED_COUNT_X, Constants.LED_COUNT_Y));
        names = new ArrayList<>();

        for (PixelWriter writer : writers) {
            names.add(writer.getName());
        }
    }

    public List<String> getPatternNames() {
        return new ArrayList<>(names);
    }

    public List<PixelWriter> getWriters() {
        return new ArrayList<>(writers);
    }

    public PixelWriter getWriter(String name) {
        for (PixelWriter writer : writers) {
            if (name.equals(writer.getName())) {
                return writer;
            }
        }

        return null;
    }

}
