package si.iitech.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

public class DateUtilsTest {

    @Test
    public void testFirstDay() {
        assertTrue(DateUtils.isFirstDayInMonth(date(1, 8, 2019)));
        assertTrue(DateUtils.isFirstDayInWeek(date(5, 8, 2019)));
    }

    @Test
    public void testGetMissingIntervals() {

        List<Date> dates = Arrays.asList(
            date(1, 1, 2000),
            date(2, 1, 2000),
            date(3, 1, 2000),
            date(4, 1, 2000),
            date(5, 1, 2000),
            date(7, 1, 2000),
            date(8, 1, 2000),
            date(9, 1, 2000),
            date(15, 1, 2000)
        );

        List<Interval> missingIntervals = DateUtils.getMissingIntervals(dates);

        assertEquals(2, missingIntervals.size());
        assertEquals(date(6, 1, 2000), missingIntervals.get(0).dateFrom);
        assertEquals(date(7, 1, 2000), missingIntervals.get(0).dateUntil);

        assertEquals(date(10, 1, 2000), missingIntervals.get(1).dateFrom);
        assertEquals(date(15, 1, 2000), missingIntervals.get(1).dateUntil);
    }

    @Test
    public void testGetMissingIntervalsWithEndDate() {

        List<Date> dates = Arrays.asList(
            date(1, 1, 2000),
            date(2, 1, 2000),
            date(3, 1, 2000),
            date(4, 1, 2000),
            date(5, 1, 2000),
            date(6, 1, 2000),
            date(7, 1, 2000),
            date(8, 1, 2000),
            date(9, 1, 2000)
        );

        List<Interval> missingIntervals = DateUtils.getMissingIntervals(dates, date(10, 1, 2000));

        assertEquals(1, missingIntervals.size());
        assertEquals(date(10, 1, 2000), missingIntervals.get(0).dateFrom);
        assertEquals(date(11, 1, 2000), missingIntervals.get(0).dateUntil);
    }

    @Test
    public void testGetMissingIntervalsWithEndDateAndMaxIntervalDaysAndMinIntervalDays() {

        List<Date> dates = Arrays.asList(
            date(20, 6, 2023),
            date(24, 6, 2023)
        );

        List<Interval> missingIntervals = DateUtils.getMissingIntervals(dates, date(24, 6, 2023), 45, 45);

        assertEquals(1, missingIntervals.size());
        assertEquals(date(7, 5, 2023), missingIntervals.get(0).dateFrom);
        assertEquals(date(24, 6, 2023), missingIntervals.get(0).dateUntil);

       // assertEquals(date(6, 1, 2000), missingIntervals.get(1).dateFrom);
       // assertEquals(date(8, 1, 2000), missingIntervals.get(1).dateUntil);

       // assertEquals(date(10, 1, 2000), missingIntervals.get(2).dateFrom);
       // assertEquals(date(11, 1, 2000), missingIntervals.get(2).dateUntil);
    }

    @Test
    public void testGetMissingIntervalsWithEndDateAndMaxIntervalDays() {

        List<Date> dates = Arrays.asList(
            date(1, 1, 2000),
            date(2, 1, 2000),
            date(8, 1, 2000),
            date(9, 1, 2000)
        );

        List<Interval> missingIntervals = DateUtils.getMissingIntervals(dates, date(10, 1, 2000), 3);

        assertEquals(3, missingIntervals.size());
        assertEquals(date(3, 1, 2000), missingIntervals.get(0).dateFrom);
        assertEquals(date(6, 1, 2000), missingIntervals.get(0).dateUntil);

        assertEquals(date(6, 1, 2000), missingIntervals.get(1).dateFrom);
        assertEquals(date(8, 1, 2000), missingIntervals.get(1).dateUntil);

        assertEquals(date(10, 1, 2000), missingIntervals.get(2).dateFrom);
        assertEquals(date(11, 1, 2000), missingIntervals.get(2).dateUntil);
    }
    
    @Test
    public void testGetMissingDates() {
    	List<Date> mainCollection = Arrays.asList(
            date(1, 1, 2000),
            date(2, 1, 2000),
            date(3, 1, 2000),
            date(4, 1, 2000),
            date(5, 1, 2000),
            date(6, 1, 2000),
            date(7, 1, 2000),
            date(8, 1, 2000),
            date(9, 1, 2000)
        );
    	
    	List<Date> subCollection = Arrays.asList(
            date(1, 1, 2000),
            date(3, 1, 2000),
            date(4, 1, 2000),
            date(5, 1, 2000),
            date(6, 1, 2000),
            date(7, 1, 2000),
            date(9, 1, 2000)
        );
    	List<Date> missingDates = DateUtils.getMissingDates(mainCollection, subCollection);
    	 assertEquals(2, missingDates.size());
         assertEquals(date(2, 1, 2000), missingDates.get(0));
         assertEquals(date(8, 1, 2000), missingDates.get(1));
    	
    }
    
    private Date date(int day, int month, int year) {
		return DateUtils.newDate(day, month, year);
	}
}
