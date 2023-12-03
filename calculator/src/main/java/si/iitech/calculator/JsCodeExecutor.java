package si.iitech.calculator;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JsCodeExecutor {

	private static final String imports = 
			"var MathUtils = Java.type('si.iitech.util.MathUtils');" +
			"var Color = Java.type('java.awt.Color');" +
			"var DateUtils = Java.type('si.iitech.util.DateUtils');" +
			"var Tools = Java.type('si.iitech.calculator.CalculatorObjectTools');";
	
	private static String specificImports;
	
	private static final ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");
	
	public static Double getDoubleValue(String notation, String jsCode, CalculatorObject coinDataObject) throws NoSuchMethodException, ScriptException {
		Double value = getDoubleValuePrivate(jsCode, coinDataObject);
		coinDataObject.addCalculatedValue(notation, value);
		return value;
	}

	private static Double getDoubleValuePrivate(String jsCode, Object... inputs) throws NoSuchMethodException, ScriptException {
		Bindings binding = scriptEngine.createBindings();
		scriptEngine.eval(imports, binding);
		scriptEngine.eval(specificImports, binding);
		scriptEngine.eval(jsCode, binding);
		scriptEngine.setBindings(binding, ScriptContext.ENGINE_SCOPE);
		Invocable invocable = (Invocable) scriptEngine;
		Object result = invocable.invokeFunction("execute", inputs);
		if (result instanceof Number) {
			return ((Number) result).doubleValue();
		}
		return null;
	}

	public static String getStringValue(String notation, String jsCode, CalculatorObject coinDataObject) throws NoSuchMethodException, ScriptException {
		String value = getStringValuePrivate(jsCode, coinDataObject);
		coinDataObject.addCalculatedValue(notation, value);
		return value;
	}

	private static String getStringValuePrivate(String jsCode, Object... inputs) throws NoSuchMethodException, ScriptException {
		Bindings binding = scriptEngine.createBindings();
		scriptEngine.eval(imports, binding);
		scriptEngine.eval(specificImports, binding);
		scriptEngine.eval(jsCode, binding);
		scriptEngine.setBindings(binding, ScriptContext.ENGINE_SCOPE);
		Invocable invocable = (Invocable) scriptEngine;
		Object result = invocable.invokeFunction("execute", inputs);
		if (result instanceof String) {
			return ((String) result);
		}
		return null;
	}

	public static Boolean getBooleanValue(String notation, String jsCode, CalculatorObject coinDataObject) throws NoSuchMethodException, ScriptException {
		Boolean value = getBooleanValuePrivate(jsCode, coinDataObject);
		coinDataObject.addCalculatedValue(notation, value);
		return value;
	}

	private static Boolean getBooleanValuePrivate(String jsCode, Object... inputs) throws NoSuchMethodException, ScriptException {
		Bindings binding = scriptEngine.createBindings();
		scriptEngine.eval(imports, binding);
		scriptEngine.eval(specificImports, binding);
		scriptEngine.eval(jsCode, binding);
		scriptEngine.setBindings(binding, ScriptContext.ENGINE_SCOPE);
		Invocable invocable = (Invocable) scriptEngine;
		Object result = invocable.invokeFunction("execute", inputs);
		if (result instanceof Boolean) {
			return ((Boolean) result);
		}
		return null;
	}

	public static byte[] getByteArrayValue(String notation, String jsCode, CalculatorObject coinDataObject) throws NoSuchMethodException, ScriptException {
		byte[] value = getByteArrayValuePrivate(jsCode, coinDataObject);
		coinDataObject.addCalculatedValue(notation, value);
		return value;
	}

    private static byte[] getByteArrayValuePrivate(String jsCode, Object inputs) throws NoSuchMethodException, ScriptException {
    	Bindings binding = scriptEngine.createBindings();
    	scriptEngine.eval(imports, binding);
    	scriptEngine.eval(specificImports, binding);
    	scriptEngine.eval(jsCode, binding);
    	scriptEngine.setBindings(binding, ScriptContext.ENGINE_SCOPE);
		Invocable invocable = (Invocable) scriptEngine;
		Object result = invocable.invokeFunction("execute", inputs);
		if (result instanceof byte[]) {
			return ((byte[]) result);
		}
		return null;
    }
    
	public static void setSpecificImports(String specificImports) {
		JsCodeExecutor.specificImports = specificImports;
	}

}
