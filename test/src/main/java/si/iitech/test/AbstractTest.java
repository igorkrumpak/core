package si.iitech.test;

import java.util.Date;
import java.util.TimeZone;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import si.iitech.util.DateUtils;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AbstractTest {
	
	private static boolean initFinished = false;

	@BeforeAll
	public void init() {
		if (initFinished)
			return;
		DateUtils.overrideTimezone(TimeZone.getTimeZone("UTC"));
		initFinished = true;
	}

	protected void setToday(Date today) {
		DateUtils.overrideToday(today);
	}

	protected static Date date(int day, int month, int year) {
		return DateUtils.newDate(day, month, year);
	}

	protected static Date date(int day, int month, int year, int hour, int minute) {
		return DateUtils.newDate(day, month, year, hour, minute);
	}

	protected String formatDoubleNoDecimalPlaces(double value) {
		return String.format("%.0f", value);
	}

	protected String formatDouble2DecimalPlaces(double value) {
		return String.format("%.2f", value);
	}

	protected String formatDouble6DecimalPlaces(double value) {
		return String.format("%.6f", value);
	}

	protected String formatDouble10DecimalPlaces(double value) {
		return String.format("%.10f", value);
	}

	
}
