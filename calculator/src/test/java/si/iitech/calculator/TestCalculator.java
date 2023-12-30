package si.iitech.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.junit.jupiter.api.Test;

public class TestCalculator {

	@Test
	public void simpleCalculatorTest() throws NoSuchMethodException, ScriptException {
//		JsCodeExecutor.setSpecificImports(null);
		Map<String, Object> inputValues = new HashMap<String, Object>();
		inputValues.put("Value", 40.0);
		List<ICalculator> calculators = new ArrayList<ICalculator>();
		calculators.add(new ICalculator() {
			@Override
			public String getNotation() {
				return "GetValue";
			}

			@Override
			public String getCode() {
				return JsCodeExecutor.createExecuteFunction("return o.getDouble('Value');");
			}
		});

		calculators.add(new ICalculator() {
			@Override
			public String getNotation() {
				return "Test1";
			}

			@Override
			public String getCode() {
				return JsCodeExecutor.createExecuteFunction("return 'Test1'");
			}
		});
		
		calculators.add(new ICalculator() {
			@Override
			public String getNotation() {
				return "Test2";
			}

			@Override
			public String getCode() {
				return JsCodeExecutor.createExecuteFunction("return o.getString('Test1') + ' Test2'");
			}
		});

		CalculatorObject object = new CalculatorObject(inputValues, calculators);
		assertEquals(object.getDouble("GetValue"), 40.0);
		assertEquals(object.getString("Test2"), "Test1 Test2");
	}

}
