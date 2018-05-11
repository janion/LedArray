package array.led.configure.custom.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class NumberItem extends Item {

    private static final String EMPTY_FORMAT = "%s";
    private static final String EXTRA_FORMAT = " %s=\"%s\"";

    private static final String MIN_FORMAT = String.format(EXTRA_FORMAT, "min", EMPTY_FORMAT);
    private static final String MAX_FORMAT = String.format(EXTRA_FORMAT, "max", EMPTY_FORMAT);
    private static final String STEP_FORMAT = String.format(EXTRA_FORMAT, "step", EMPTY_FORMAT);

    private static final String TYPE = "number";

    private NumberItemConfiguration config;
    private String extras;

    public NumberItem(String title, String name, Consumer<String> setAction, Supplier<String> getAction, NumberItemConfiguration config) {
        super(TYPE, title, name, setAction, getAction);
        this.config = config;

        extras = "";
        if (config.hasMin()) {
            extras += String.format(MIN_FORMAT, config.getMin().toString());
        }
        if (config.hasMax()) {
            extras += String.format(MAX_FORMAT, config.getMax().toString());
        }
        if (config.hasStep()) {
            extras += String.format(STEP_FORMAT, config.getStep().toString());
        }
    }

    @Override
    protected String createFormEntryParameters() {
        return extras;
    }
}
