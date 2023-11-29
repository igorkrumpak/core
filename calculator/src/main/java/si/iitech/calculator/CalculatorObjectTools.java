package si.iitech.calculator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import si.iitech.util.MathUtils;

public class CalculatorObjectTools {

	public static Double avg(CalculatorObject calculatorObject, int size, String definition) {
		validate(calculatorObject, size);
		return calculatorObject.getCoinDataObjects().stream().limit(size)
				.mapToDouble(each -> each.getDoubleOrNull(definition)).average().stream()
				.map(MathUtils::dynamicRound).findFirst().orElse(0.0);
	}
	
	public static Double max(CalculatorObject calculatorObject, String definition) {
		Double maxPrice = calculatorObject.getCoinDataObjects().stream()
				.mapToDouble(each -> each != null ? each.getDoubleOrNull(definition) : 0.0).max()
				.stream()
				.findFirst().orElse(0.0);
		return maxPrice;
	}
	
	public static Double standardDeviation(CalculatorObject calculatorObject, int size, String definition) {
		validate(calculatorObject, size);
		Double avg = avg(calculatorObject, size, definition);
		double sumOfSquares = 0.0;
		List<CalculatorObject> temp = getCalculatorObjectsReverse(calculatorObject, size);
		for (CalculatorObject each : temp) {
			sumOfSquares = sumOfSquares + MathUtils.square(each.getDoubleOrNull(definition) - avg);
		}
		return MathUtils.round2DecimalPlaces(MathUtils.squareRoot(sumOfSquares / size));
	}
	
	public static List<CalculatorObject> getCalculatorObjectsReverse(CalculatorObject calculatorObject, int size) {
		List<CalculatorObject> temp = calculatorObject.getCoinDataObjects().stream().limit(size).collect(Collectors.toList());
		Collections.reverse(temp);
		return temp;
	}
	
	public static Double lowestLow(CalculatorObject calculatorObject, int size, String definition) {
		Double lowestLow = getCalculatorObjectsReverse(calculatorObject, size).stream()
				.mapToDouble(each -> each != null ? each.getDoubleOrNull(definition) : 0.0).min()
				.stream()
				.findFirst().orElse(0.0);
		return lowestLow;
	}

	public static Double highestHigh(CalculatorObject calculatorObject, int size, String definition) {
		Double highestHigh = getCalculatorObjectsReverse(calculatorObject, size).stream()
				.mapToDouble(each -> each != null ? each.getDoubleOrNull(definition) : 0.0).max()
				.stream()
				.findFirst().orElse(0.0);
		return highestHigh;
	}
	
	public static CalculatorObject getPreviousCoinDataObject(CalculatorObject calculatorObject, int size) {
		validate(calculatorObject, size);
		return calculatorObject.getCoinDataObjects().get(size);
	}
	
	public static void validate(CalculatorObject calculatorObject, int size) {
		if (size > calculatorObject.getCoinDataObjects().size()) {
			throw new IndexOutOfBoundsException("size is out of bounds");
		}
	
		for (int i = 0; i < size; i++) {
			CalculatorObject each = calculatorObject.getCoinDataObjects().get(i);
			if (each == null) {
				throw new RuntimeException("missing calculator objects are present");
			}
		}
	}
}
