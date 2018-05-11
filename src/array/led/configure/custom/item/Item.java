package array.led.configure.custom.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Item {

    protected static final String EMPTY = "";
    protected static final String FORM_FORMAT = "%s<br>\n<input type=\"%s\" name=\"%s\" value=\"%s\"%s><br>";

    protected String type;
    protected String title;
    protected String name;
    private Consumer<String> setAction;
    private Supplier<String> getAction;

    protected Item(String type, String title, String name, Consumer<String> setAction, Supplier<String> getAction) {
        this.type = type;
        this.title = title;
        this.name = name;
        this.setAction = setAction;
        this.getAction = getAction;
    }

    public String createFormEntry() {
        return String.format(FORM_FORMAT, title, type, name, getAction.get(), createFormEntryParameters());
    }

    protected String createFormEntryParameters() {
        return EMPTY;
    }

    public String getKey() {
        return name;
    }

    public void setValue(String value) {
        setAction.accept(value);
    }

    public String getValue() {
        return getAction.get();
    }
}
