package array.led.configure.custom.item;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SelectItem extends Item {

    private static final String HTML_FORMAT = "<select name=\"%s\">\n%s\n</select>";
    private static final String ROW_FORMAT = "    <option value=\"%s\"%s>%s</option>";
    private static final String SELECTED = " selected";

    private static final String TYPE = "select";

    private Set<String> optionNames;
    private Set<String> optionValues;

    public SelectItem(String title, String name, Consumer<String> setAction, Supplier<String> getAction, Set<String> optionNames, Set<String> optionValues) {
        super(TYPE, title, name, setAction, getAction);
        if (optionNames.size() != optionValues.size()) {
            throw new IllegalArgumentException("Different number of option names and values.");
        }
        this.optionNames = optionNames;
        this.optionValues = optionValues;
    }

    @Override
    public String createFormEntry() {
        return String.format(HTML_FORMAT, name, createOptions());
    }

    private String createOptions() {
        String options = "";

        Iterator<String> names = optionNames.iterator();
        Iterator<String> values = optionValues.iterator();
        while (names.hasNext()) {
            String name = names.next();
            String value = values.next();

            String selected = value.equals(getValue()) ? SELECTED : EMPTY;
            String entry = String.format(ROW_FORMAT, name, selected, value);

            if (options.equals("")) {
                options += entry;
            } else {
                options += "\n";
                options += entry;
            }
        }

        return options;
    }

}
