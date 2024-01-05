package si.iitech.calculator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.script.ScriptException;

import si.iitech.util.DateUtils;

public class CalculatorObject {

	private Map<String, Object> calculatedValues = new HashMap<String, Object>();
	private List<CalculatorObject> coinDataObjects = new ArrayList<>();
	private Map<String, String> metadataCalculators = new HashMap<>();

	public CalculatorObject(Map<String, Object> calculatedValues, List<CalculatorObject> coinDataObjects,
			List<? extends ICalculator> metadataCalculators) {
		this.calculatedValues = calculatedValues;
		this.coinDataObjects = new ArrayList<>();
		this.coinDataObjects.add(this);
		addPreviousCoinDataObjects(coinDataObjects);
		this.metadataCalculators = metadataCalculators.stream()
		.collect(Collectors.toMap(ICalculator::getNotation, ICalculator::getCode));
	}

	public void addPreviousCoinDataObjects(List<CalculatorObject> coinDataObjects) {
		this.coinDataObjects.addAll(coinDataObjects);
	}

	// coin data object created from calculated values
	public CalculatorObject(Map<String, Object> calculatedValues, List<? extends ICalculator> metadataCalculators) {
		this.calculatedValues = calculatedValues;
		this.coinDataObjects = new ArrayList<>();
		this.coinDataObjects.add(this);
		this.metadataCalculators = metadataCalculators.stream()
		.collect(Collectors.toMap(ICalculator::getNotation, ICalculator::getCode));
	}

	public CalculatorObject(Map<String, Object> calculatedValues) {
		this.calculatedValues = calculatedValues;
		this.coinDataObjects = new ArrayList<>();
		this.coinDataObjects.add(this);
	}

	public List<CalculatorObject> getCoinDataObjects() {
		return coinDataObjects;
	}

	public void addCalculatedValue(String notation, Object value) {
		calculatedValues.put(notation, value);
	}

	public boolean isFirstDayInWeek(String currentDate) {
		return DateUtils.isFirstDayInWeek(DateUtils.parseDateTime(currentDate));
	}

	public boolean isFirstDayInMonth(String currentDate) {
		return DateUtils.isFirstDayInMonth(DateUtils.parseDateTime(currentDate));
	}

	public Double getDoublePrivate(String notation, CalculatorObject coinDataObject)
			throws NoSuchMethodException, ScriptException {
		if (coinDataObject.calculatedValues.containsKey(notation)) {
			return Double.class.cast(coinDataObject.calculatedValues.get(notation));
		}
		return JsCodeExecutor.getDoubleValue(notation, metadataCalculators.get(notation), coinDataObject);
	}

	public Double getDouble(String notation, int daysInThePast) throws NoSuchMethodException, ScriptException {
		validateDaysInThePast(daysInThePast);
		return getDoublePrivate(notation, coinDataObjects.get(daysInThePast));
	}

	private void validateDaysInThePast(int days) {
		if (days > coinDataObjects.size())
			throw new IndexOutOfBoundsException("days are out of bounds");
	}

	public Double getDouble(String notation) throws NoSuchMethodException, ScriptException {
		return getDouble(notation, 0);
	}

	public Double getDoubleOrNull(String notation) {
		try {
			return getDouble(notation);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (ScriptException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		} catch (RuntimeException e) {
			return null;
		}
	}

	public String getStringPrivate(String notation, CalculatorObject coinDataObject)
			throws NoSuchMethodException, ScriptException {
		String value = JsCodeExecutor.getStringValue(notation, metadataCalculators.get(notation), coinDataObject);
		return value;
	}

	public String getString(String notation, int daysInThePast) throws NoSuchMethodException, ScriptException {
		validateDaysInThePast(daysInThePast);
		if (daysInThePast == 0 && calculatedValues.containsKey(notation)) {
			return String.class.cast(calculatedValues.get(notation));
		}
		return getStringPrivate(notation, coinDataObjects.get(daysInThePast));
	}
	
	public Date getDate(String notation) throws NoSuchMethodException, ScriptException {
		return DateUtils.parseDateTime(getString(notation, 0));
	}

	public String getString(String notation) throws NoSuchMethodException, ScriptException {
		return getString(notation, 0);
	}

	public String getStringOrNull(String notation) {
		try {
			return getString(notation);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (ScriptException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public Boolean getBooleanPrivate(String notation, CalculatorObject coinDataObject)
			throws NoSuchMethodException, ScriptException {
		Boolean value = JsCodeExecutor.getBooleanValue(notation, metadataCalculators.get(notation), coinDataObject);
		return value;
	}

	public Boolean getBoolean(String notation, int daysInThePast) throws NoSuchMethodException, ScriptException {
		validateDaysInThePast(daysInThePast);
		if (daysInThePast == 0 && calculatedValues.containsKey(notation)) {
			return Boolean.class.cast(calculatedValues.get(notation));
		}
		return getBooleanPrivate(notation, coinDataObjects.get(daysInThePast));
	}

	public Boolean getBoolean(String notation) throws NoSuchMethodException, ScriptException {
		return getBoolean(notation, 0);
	}

	public Boolean getBooleanOrNull(String notation) {
		try {
			return getBoolean(notation);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (ScriptException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public byte[] getByteArrayOrNull(String notation) {
		try {
			return getByteArray(notation);
		} catch (NoSuchMethodException e) {
			return null;
		} catch (ScriptException e) {
			return null;
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}

	public byte[] getByteArray(String notation) throws NoSuchMethodException, ScriptException {
		byte[] value = JsCodeExecutor.getByteArrayValue(notation, metadataCalculators.get(notation), this);
		return value;
	}

}
