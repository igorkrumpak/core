package si.iitech.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DateUtils {

	private static ThreadLocal<Calendar> reusableDate = new ThreadLocal<Calendar>();
	private static ThreadLocal<Calendar> reusableDate2 = new ThreadLocal<Calendar>();

	public DateUtils() {
	}

	private static Calendar icuCalendar(Date date) {
		if (date == null)
			throw new NullPointerException();
		Calendar icuDate = Calendar.getInstance();
		icuDate.setTime(date);
		return icuDate;
	}

	private static Calendar reuseTempIcuCalendar(Date date) {
		if (date == null)
			throw new NullPointerException();
		if (reusableDate.get() == null) {
			reusableDate.set(icuCalendar(date));
			return reusableDate.get();
		}
		reusableDate.get().setTime(date);
		return reusableDate.get();
	}

	private static Calendar reuseTempIcuCalendar2(Date date) {
		if (date == null) {
			throw new NullPointerException();
		}
		if (reusableDate2.get() == null) {
			reusableDate2.set(icuCalendar(date));
			return reusableDate2.get();
		}
		reusableDate2.get().setTime(date);
		return reusableDate2.get();
	}

	public static Date overrideToday = null;

	public static void overrideTimezone(TimeZone timeZone) {
		TimeZone.setDefault(timeZone);
	}

	public static void overrideToday(Date date) {
		overrideToday = date;
	}

	public static Date getNow() {
		if (overrideToday != null)
			return overrideToday;
		return new Date();
	}

	public static boolean isEquals(Date date1, Date date2) {
		return date1.compareTo(date2) == 0;
	}

	public static boolean isAfterOrEquals(Date date1, Date date2) {
		return !isBefore(date1, date2);
	}

	public static boolean isAfter(Date date1, Date date2) {
		return !isBeforeOrEquals(date1, date2);
	}

	public static boolean isBeforeOrEquals(Date date1, Date date2) {
		return date1.compareTo(date2) <= 0;
	}

	public static boolean isBefore(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			throw new RuntimeException("date cannot be null");
		return date1.compareTo(date2) < 0;
	}

	public static Date newDate(int day, int month, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date newDate(int day, int month, int year, int hour, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.YEAR, year);
		return calendar.getTime();
	}

	public static Date getToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getNow());
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		return calendar.getTime();
	}

	public static Date getEndOfTheWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return calendar.getTime();
	}

	public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

	public static Date addWeeks(Date date, int weeks) {
		return addDays(date, weeks * 7);
	}

	public static Date addHours(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hours);
		return calendar.getTime();
	}

	public static String getUnixTimestamp(Date date) {
		return String.valueOf(date.getTime() / 1000);
	}

	public static String getCoinGeckoDateFormat(Date date) {
		String pattern = "MM-dd-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static long getNumberOfDaysBetweenDates(Date date1, Date date2) {
		long diffInMillies = Math.abs(date2.getTime() - date1.getTime());
		return TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	}

	public static Date unitTimestampToDate(String dateInUnixTimeStamp) {
		return new Date(Long.valueOf(dateInUnixTimeStamp));
	}

	public static boolean isSameDate(Date date1, Date date2) {
		if (date1 == null && date2 != null)
			return false;
		if (date1 != null && date2 == null)
			return false;
		if (date1 == null && date2 == null)
			return true;
		Calendar icuFirst = reuseTempIcuCalendar(date1);
		Calendar icuSecond = reuseTempIcuCalendar2(date2);
		return icuFirst.get(Calendar.YEAR) == icuSecond.get(Calendar.YEAR)
				&& icuFirst.get(Calendar.MONTH) == icuSecond.get(Calendar.MONTH)
				&& icuFirst.get(Calendar.DAY_OF_MONTH) == icuSecond.get(Calendar.DAY_OF_MONTH);
	}

	public static Date getYesterday() {
		return addDays(getToday(), -1);
	}

	public static Date getLastWeek() {
		return addDays(getToday(), -7);
	}

	public static Date minDate(Date date1, Date date2) {
		if (isBefore(date1, date2))
			return date1;
		return date2;
	}

	public static List<Interval> getMissingIntervals(List<Date> dates) {
		return getMissingIntervals(dates, null);
	}

	public static List<Interval> getMissingIntervals(List<Date> dates, Date endDate) {
		return getMissingIntervals(dates, endDate, Integer.MAX_VALUE, 0);
	}

	public static List<Interval> getMissingIntervals(List<Date> dates, Date endDate, int maxIntervalDays) {
		return getMissingIntervals(dates, endDate, maxIntervalDays, 0);
	}

	public static List<Interval> getMissingIntervals(List<Date> dates, Date endDate, int maxIntervalDays,
			int minIntervalDays) {
		List<Interval> intervals = new ArrayList<>();
		if (dates.isEmpty()) {
			return intervals;
		}
		List<Date> sortedDates = dates.stream().sorted().collect(Collectors.toList());
		Date from = sortedDates.get(0);
		Date until = endDate != null ? endDate : sortedDates.get(sortedDates.size() - 1);
		List<Date> allDates = getDates(from, until);
		List<Date> missingDates = getMissingDates(allDates, sortedDates);

		Date startOfInterval = null;
		Date endOfInterval = null;
		for (int i = 0; i < missingDates.size(); i++) {
			if (startOfInterval == null) {
				startOfInterval = missingDates.get(i);
			}
			if (i + 1 == missingDates.size()) {
				endOfInterval = missingDates.get(i);
			} else {
				if (!isSameDate(missingDates.get(i + 1), addDays(missingDates.get(i), 1))) {
					endOfInterval = missingDates.get(i);
				}
			}

			if (startOfInterval != null && endOfInterval != null) {
				Date tempEndOfInterval = addDays(endOfInterval, 1);
				long intervalSize = (tempEndOfInterval.getTime() - startOfInterval.getTime()) / (1000 * 60 * 60 * 24);

				if (intervalSize > maxIntervalDays) {
					Date newEndOfInterval = addDays(startOfInterval, maxIntervalDays);
					intervals.add(new Interval(startOfInterval, newEndOfInterval));
					startOfInterval = newEndOfInterval;
					endOfInterval = null;
					i--;
				} else if (intervalSize < minIntervalDays) {
					Date newStartOfInterval = addDays(startOfInterval, -minIntervalDays);
					intervals.add(new Interval(newStartOfInterval, tempEndOfInterval));
					startOfInterval = null;
					endOfInterval = null;
				} else {
					intervals.add(new Interval(startOfInterval, tempEndOfInterval));
					startOfInterval = null;
					endOfInterval = null;
				}
			}
		}
		return intervals;
	}

	public static List<Date> getDates(Date from, Date until) {
		return Stream.iterate(from, date -> DateUtils.addDays(date, 1))
				.limit(getNumberOfDaysBetweenDates(from, until) + 1).collect(Collectors.toList());
	}

	public static List<Date> getMissingDates(List<Date> mainCollection, List<Date> subCollection) {
		List<Date> missingDates = new ArrayList<Date>();
		for (Date eachFromMainCollection :  mainCollection) {
			boolean foundDateInSubCollection = false;
			for (Date eachFromSubCollection : subCollection) {
				if (isSameDate(eachFromSubCollection, eachFromMainCollection)) {
					foundDateInSubCollection = true;
					continue;
				}
			}
			if (!foundDateInSubCollection)
				missingDates.add(eachFromMainCollection);
		}
		return missingDates;
	}

	public static String formatDateTime(Date date) {
		String pattern = "dd.MM.yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static Date parseDateTime(String input) {
		String pattern = "dd.MM.yyyy HH:mm";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		try {
			return simpleDateFormat.parse(input);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formatDate(Date date) {
		String pattern = "dd.MM.yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

	public static boolean isFirstDayInMonth(Date reportDate) {
		return reuseTempIcuCalendar(reportDate).get(Calendar.DAY_OF_MONTH) == 1;
	}

	public static boolean isFirstDayInWeek(Date reportDate) {
		return reuseTempIcuCalendar(reportDate).get(Calendar.DAY_OF_WEEK) == 2;
	}

}
