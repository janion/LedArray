package array.led.configure.custom.item;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class CheckboxItem extends Item {

    private static final String FORM_NAME = "{FORM_NAME}";
    private static final String ITEM_NAME = "{ITEM_NAME}";
    private static final String CHECKBOX_NAME = "{CHECKBOX_NAME}";
    private static final String MAYBE_NOT = "{MAYBE_NOT}";

    private static final String DISABLE_SCRIPT_FORMAT = "\n<script>\n" +
            "document.forms[\"%s\"][\"%s\"].onchange = function() {\n" +
            "document.forms[\"%s\"][\"%s\"].disabled = %sthis.checked;\n" +
            "};\n" +
            "document.forms[\"%s\"][\"%s\"].disabled = %sdocument.forms[\"%s\"][\"%s\"].checked\n" +
            "</script>";

    private static final String DISABLE_SCRIPT = String.format(DISABLE_SCRIPT_FORMAT, FORM_NAME, CHECKBOX_NAME, FORM_NAME, ITEM_NAME, MAYBE_NOT, FORM_NAME,
            ITEM_NAME, MAYBE_NOT, FORM_NAME, CHECKBOX_NAME);
    private static final String NOT = "!";
    private static final String CHECKED = " checked";
    private static final String UNCHECKED = " unchecked";

    private static final String TYPE = "checkbox";

    private String valueName;
    private String formName;
    private String disabledItemName;
    private boolean checkToEnable;
    private Supplier<Boolean> getBooleanAction;

    public CheckboxItem(String title, String name, Runnable setTrueAction, Supplier<Boolean> getBooleanAction, String valueName, String formName, String disabledItemName, boolean checkToEnable) {
        super(TYPE, title, name, ignored -> setTrueAction.run(), () -> valueName);
        this.valueName = valueName;
        this.formName = formName;
        this.disabledItemName = disabledItemName;
        this.checkToEnable = checkToEnable;
        this.getBooleanAction = getBooleanAction;
    }

    public String createFormEntry() {
        String basic = super.createFormEntry().replaceFirst("<br>", "");

        String maybeNot = checkToEnable ? NOT : EMPTY;
        String script = DISABLE_SCRIPT.replace(FORM_NAME, formName)
                .replace(CHECKBOX_NAME, name)
                .replace(ITEM_NAME, disabledItemName)
                .replace(MAYBE_NOT, maybeNot);

        return basic + script;
    }

    @Override
    protected String createFormEntryParameters() {
        return getBooleanAction.get() ? CHECKED : UNCHECKED;
    }

}
