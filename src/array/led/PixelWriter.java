package array.led;

import array.led.configure.BasicConfigurer;
import array.led.configure.Configurer;

import java.util.List;

public abstract class PixelWriter {

    private String name;
    private Configurer configurer;

    protected PixelWriter(String name) {
        this(name, new BasicConfigurer());
    }

    protected PixelWriter(String name, Configurer configurer) {
        this.name = name;
        this.configurer = configurer;
    }

    public String getName() {
        return name;
	}

	public Configurer getConfigurer() {
        return configurer;
	}

	protected void tick(double time) {
        // Do nothing
    }
	
	public List<Colour> getPixelData(double time) {
        tick(time);
        return calculate(time);
    }

    protected abstract List<Colour> calculate(double time);
	
	public abstract void reset(double time);

}
