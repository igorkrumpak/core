package si.iitech.util;

public class MathUtils {

    public static double smooth(double number) {
        return Math.round(number * 100000000.0) / 100000000.0;
    }

    public static double round2DecimalPlaces(double number) {
        return Math.round(number * 100.0) / 100.0;
    }
    
    public static double round4DecimalPlaces(double number) {
        return Math.round(number * 10000.0) / 10000.0;
    }

	public static double square(double number) {
		return number * number;
	}

	public static Double squareRoot(double number) {
		return Math.sqrt(number);
	}

    public static double noDecimalRound(double value) {
        return (double) Math.round(value);
    }

    public static double dynamicRound(double value) {
        if (value > 1) {
            return Math.round(value * 100.0) / 100.0;
        } else if (value <= 1 && value > 0.01) {
            return Math.round(value * 1000.0) / 1000.0;
        } else if (value <= 0.01 && value > 0.001) {
            return Math.round(value * 10000.0) / 10000.0;
        } else if (value <= 0.001 && value > 0.0001) {
            return Math.round(value * 100000.0) / 100000.0;
        } else if (value <= 0.0001 && value > 0.00001) {
            return Math.round(value * 1000000.0) / 1000000.0;
        } else if (value <= 0.00001 && value > 0.000001) {
            return Math.round(value * 10000000.0) / 10000000.0;
        } else if (value <= 0.000001 && value > 0.0000001) {
            return Math.round(value * 100000000.0) / 100000000.0;
        } else if (value <= 0.0000001 && value > 0.00000001) {
            return Math.round(value * 1000000000.0) / 1000000000.0;
        } else if (value <= 0.00000001 && value > 0.000000001) {
            return Math.round(value * 10000000000.0) / 10000000000.0;
        } else if (value <= 0.000000001 && value > 0.0000000001) {
            return Math.round(value * 100000000000.0) / 100000000000.0;
        } else if (value <= 0.0000000001 && value > 0.00000000001) {
            return Math.round(value * 1000000000000.0) / 1000000000000.0;
        } else if (value <= 0.00000000001 && value > 0.000000000001) {
            return Math.round(value * 10000000000000.0) / 10000000000000.0;
        } else if (value <= 0.000000000001 && value > 0.0000000000001) {
            return Math.round(value * 100000000000000.0) / 100000000000000.0;
        } else if (value <= 0.0000000000001 && value > 0.00000000000001) {
            return Math.round(value * 1000000000000000.0) / 1000000000000000.0;
        } else if (value <= 0.00000000000001 && value > 0.000000000000001) {
            return Math.round(value * 10000000000000000.0) / 10000000000000000.0;
        } else if (value <= 0.000000000000001 && value > 0.0000000000000001) {
            return Math.round(value * 100000000000000000.0) / 100000000000000000.0;
        }
        return 0.0;
    }
    
}
