package array.led.builtin;

import array.led.PixelWriter;

import java.util.ArrayList;
import java.util.List;

public class BuiltinFunctionManager {

    private List<PixelWriter> writers;
    private List<String> names;

    public BuiltinFunctionManager() {
        writers = new ArrayList<>();
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
