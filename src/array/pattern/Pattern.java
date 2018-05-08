package array.pattern;

import fn.Function;
import fn.FunctionParser;

public class Pattern {
	
	private static final FunctionParser parser = new FunctionParser();
	
	private String name;
	private String redFunctionString;
	private String greenFunctionString;
	private String blueFunctionString;
	private Function redFunction;
	private Function greenFunction;
	private Function blueFunction;
	
	public Pattern(String name, String redFunctionString, String greenFunctionString, String blueFunctionString) {
		this.name = name;
		this.redFunctionString = redFunctionString;
		this.greenFunctionString = greenFunctionString;
		this.blueFunctionString = blueFunctionString;

		this.redFunction = createFunctionAndValidate(redFunctionString);
		this.greenFunction = createFunctionAndValidate(greenFunctionString);
		this.blueFunction = createFunctionAndValidate(blueFunctionString);
	}

    public boolean isValid() {
		return redFunction == null || greenFunction == null || blueFunction == null;
	}

	public String getName() {
		return name;
	}

	public String getRedFunctionString() {
		return redFunctionString;
	}

	public String getGreenFunctionString() {
		return greenFunctionString;
	}

	public String getBlueFunctionString() {
		return blueFunctionString;
	}

	public Function getRedFunction() {
		return redFunction;
	}

	public Function getGreenFunction() {
		return greenFunction;
	}


	public Function getBlueFunction() {
		return blueFunction;
	}

	private Function createFunctionAndValidate(String functionString) {
        try {
            return parser.parseFunction(functionString);
        } catch (RuntimeException exptn) {
        	return null;
        }
    }
    
}
