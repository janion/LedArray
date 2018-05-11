package array.led.writer;

import array.led.Colour;
import array.pattern.Pattern;
import fn.Function;

import java.util.HashMap;
import java.util.Map;

public class MathematicalFunctionPixelWriter extends PixelWriter2D {

    private double startTime = 0;
    private Function redFunction;
    private Function greenFunction;
    private Function blueFunction;

    public MathematicalFunctionPixelWriter(int ledCountX, int ledCountY, Pattern pattern) {
        super(pattern.getName(), ledCountX, ledCountY);
        startTime = 0;

        if (pattern != null) {
            redFunction = pattern.getRedFunction();
            greenFunction = pattern.getGreenFunction();
            blueFunction = pattern.getBlueFunction();
        }
    }

    @Override
    protected Colour evaluateCell(int x, int y, double time) {
        Map<String, Double> parameters = new HashMap<>();
        parameters.put("x", (double) x);
        parameters.put("y", (double) y);
        parameters.put("t", time);
        return new Colour((int) redFunction.evaluate(parameters).get(0).doubleValue(),
                (int) greenFunction.evaluate(parameters).get(0).doubleValue(),
                (int) blueFunction.evaluate(parameters).get(0).doubleValue()
        );
    }

    @Override
    public void reset(double time) {
        startTime = time;
    }
}
