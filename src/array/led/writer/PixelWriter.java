package array.led.writer;

import array.led.Colour;
import array.led.configure.BasicConfigurer;
import array.led.configure.Configurer;

import java.util.Set;

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

	protected void setConfigurer(Configurer configurer) {
        this.configurer = configurer;
    }

	public Configurer getConfigurer() {
        return configurer;
	}

	protected void tick(double time) {
        // Do nothing
    }
	
	public Set<Colour> getPixelData(double time) {
        tick(time);
        return calculate(time);
    }

    protected abstract Set<Colour> calculate(double time);
	
	public abstract void reset(double time);

}
