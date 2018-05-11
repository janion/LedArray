package array.led.configure.custom.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class ColourItem extends Item {

    private static final String TYPE = "color";

    public ColourItem(String title, String name, Consumer<String> setAction, Supplier<String> getAction) {
        super(TYPE, title, name, setAction, getAction);
    }

}
