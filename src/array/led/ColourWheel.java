package array.led;

import java.awt.Color;

public class ColourWheel {

	private static final double CONVERSION = 1.0 / 120;

	public Color getColour(double intensity, double angle) {
		angle %= 360;
		double r;
		double g;
		double b;
		if (angle < 120) {
			// Red -> yellow -> green
			r = intensity * ((120 - angle) * CONVERSION);
			g = intensity * (angle * CONVERSION);
			b = 0;
		} else if (angle < 240) {
			// Green -> cyan -> blue
			angle = angle - 120;
			r = 0;
			g = intensity * ((120 - angle) * CONVERSION);
			b = intensity * (angle * CONVERSION);
		} else {
			// Blue -> magenta -> red
			angle = angle - 240;
			r = intensity * (angle * CONVERSION);
			g = 0;
			b = intensity * ((120 - angle) * CONVERSION);
		}

		return new Color((int) r, (int) g, (int) b);
	}
}
