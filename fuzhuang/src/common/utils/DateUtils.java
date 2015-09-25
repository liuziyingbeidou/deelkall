package utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	public static final long ONE_DAY_MILLIS = 24 * 3600 * 1000L;

	public static String formatDate(long time, String style) {
		Date date = new Date(time);
		SimpleDateFormat outFormat = new SimpleDateFormat(style);
		return outFormat.format(date);
	}

	public static String formatDate(Date date) {
		if (date == null) return "";
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		return outFormat.format(date);
	}

	public static String formatDate(Date date, String fromat) {
		if (date == null) return "";
		SimpleDateFormat outFormat = new SimpleDateFormat(fromat);
		return outFormat.format(date);
	}

	public static String formatDateTime(Date date) {
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return outFormat.format(date);
	}

	public static Date str2Date(String dt, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = df.parse(dt);
			return date;
		}
		catch (ParseException e) {
			return date;
		}
	}
	
	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static int getYear() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour() {
		Calendar cld = Calendar.getInstance();
		cld.setTime(new java.util.Date());
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getYear(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(long t) {
		Calendar cld = Calendar.getInstance();
		if (t > 0) {
			cld.setTime(new java.util.Date(t));
		}
		return cld.get(Calendar.SECOND);
	}

	public static int getYear(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MONTH) + 1;
	}

	public static int getDay(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.DAY_OF_MONTH);
	}

	public static int getHour(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.MINUTE);
	}

	public static int getSecond(Date date) {
		Calendar cld = Calendar.getInstance();
		cld.setTime(date);
		return cld.get(Calendar.SECOND);
	}

	public static String toGMTString(Date dt) {
		DateFormat df = new SimpleDateFormat("EEE, d-MMM-yyyy HH:mm:ss z", Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT")); // modify Time Zone.
		
		return df.format(dt);

	}

	public static void main(String[] args) throws Exception {
		System.out.println(getMonth());
		toGMTString(new Date());
	}

	public static Date addDays(Date date, int days) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, days);
	}

	public static Date addMilliseconds(Date date, int Milliseconds) {
		return org.apache.commons.lang.time.DateUtils.addMilliseconds(date, Milliseconds);
	}

	public static Date addSeconds(Date date, int Seconds) {
		return org.apache.commons.lang.time.DateUtils.addSeconds(date, Seconds);
	}

	public static Date addMinutes(Date date, int Minutes) {
		return org.apache.commons.lang.time.DateUtils.addMinutes(date, Minutes);
	}

	public static Date addHours(Date date, int Hours) {
		return org.apache.commons.lang.time.DateUtils.addHours(date, Hours);
	}

	public static Date addYears(Date date, int years) {
		return org.apache.commons.lang.time.DateUtils.addYears(date, years);
	}

	public static Date addMonths(Date date, int months) {
		return org.apache.commons.lang.time.DateUtils.addMonths(date, months);
	}

	public static Date addWeeks(Date date, int weeks) {
		return org.apache.commons.lang.time.DateUtils.addWeeks(date, weeks);
	}

	public static Date getWeekStart() {
		Calendar cal = new GregorianCalendar();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 2;
		cal.add(Calendar.DATE, -day_of_week);
		return cal.getTime();
	}

	public static Date getWeekStart(Date date) {
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		int day_of_week = calendar.get(Calendar.DAY_OF_WEEK) - 2;
		calendar.add(Calendar.DATE, -day_of_week);
		return calendar.getTime();
		
	}
	
	public static Date getMonthStart() {
		Calendar cal = new GregorianCalendar();
		int dayOfMonth = cal.get(GregorianCalendar.DATE);
		cal.add(GregorianCalendar.DATE, -dayOfMonth + 1);
		return cal.getTime();
	}

	public static Date getMonthStart(Date date) {
		
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date);
		int dayOfMonth = calendar.get(GregorianCalendar.DATE);
		calendar.add(GregorianCalendar.DATE, -dayOfMonth + 1);
		return calendar.getTime();
	}

	public static Date getJdStart() {
		int jd_start_month = 3 * ((DateUtils.getMonth() - 1) / 3) + 1;
		Calendar cal = new GregorianCalendar(DateUtils.getYear(), jd_start_month - 1, 1);

		return cal.getTime();
	}

	public static Date getYearStart() {

		Calendar cal = new GregorianCalendar(DateUtils.getYear(), 0, 1);

		return cal.getTime();
	}
	
	public static Date getYearStart(Date date) {
		
		Calendar cld = Calendar.getInstance(); 
		cld.setTime(date);	
		Calendar cal = new GregorianCalendar(cld.get(Calendar.YEAR), 0, 1);
		return cal.getTime();
		
		
	}
	

	public static Date getTodayStart() {

		Calendar cal = new GregorianCalendar(DateUtils.getYear(), DateUtils.getMonth() - 1, DateUtils.getDay());

		return cal.getTime();
	}

	public static boolean checkLeapyear(int year) {
		boolean isLeapyear = false;
		if (year % 4 == 0 && year % 100 != 0) {
			isLeapyear = true;
		}
		if (year % 400 == 0)
			isLeapyear = true;
		else if (year % 4 != 0) {
			isLeapyear = false;
		}
		return isLeapyear;
	}

	public static int getDaysOfmonth(int month, int year) {

		int Dates = 0;
		if (month < 0 || month > 12) {
			System.out.println("month Error");
		}
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			Dates = 31;
		}
		if (month == 2 && checkLeapyear(year)) {
			Dates = 29;
		}
		if (month == 2 && !checkLeapyear(year)) {
			Dates = 28;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			Dates = 30;
		}
		return Dates;
	}

	/**
	 * HTTP date uses TimeZone GMT
	 */
	static {
	    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);

		format.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public long getTime(String dateString) {
		try {
		    SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
			long longtime = format.parse(dateString).getTime();
			return longtime;
		}
		catch (ParseException e1) {
			Date parsedDate;
			try {
				parsedDate = org.apache.commons.lang.time.DateUtils.parseDate(dateString,
						new String[] { "yyyy/MM/dd", "yyyy.MM.dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy年MM月dd日", "yyyy年MM月dd日HH", "yyyy年MM月dd日HH:mm", "yyyy年MM月dd日HH:mm:ss", "yyyy年MM月dd日HH时", "yyyy年MM月dd日HH时mm分",

						"yyyy年MM月dd日HH时mm分ss秒", "yyyyMMdd", "yyyyMMddHHmmss", "yyyyMMddHHmm", "yyyyMMddHH", "MMM dd yyyy HH:mm:ss. zzz", "MMM dd yyyy HH:mm:ss zzz", "dd.MM.yyyy HH:mm:ss zzz", "dd MM yyyy HH:mm:ss zzz", "dd.MM.yyyy; HH:mm:ss", "dd.MM.yyyy HH:mm:ss", "dd.MM.yyyy zzz", "EEE MMM dd HH:mm:ss yyyy", "EEE MMM dd HH:mm:ss yyyy zzz", "EEE MMM dd HH:mm:ss zzz yyyy", "EEE, MMM dd HH:mm:ss yyyy zzz", "EEE, dd MMM yyyy HH:mm:ss zzz", "EEE,dd MMM yyyy HH:mm:ss zzz", "EEE, dd MMM yyyy HH:mm:sszzz", "EEE, dd MMM yyyy HH:mm:ss", "EEE, dd-MMM-yy HH:mm:ss zzz", "yyyy/MM/dd HH:mm:ss.SSS zzz", "yyyy/MM/dd HH:mm:ss.SSS", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss zzz", });
				long result = parsedDate.getTime();
				return result;
			}
			catch (ParseException e) {
				e.printStackTrace();
				return -1;
			}
		}

	}

	public static long[] getTime(long start, long end) {

		long i = end - start;

		long day = i / (3600 * 1000 * 24);
		long h = (i % (3600 * 1000 * 24)) / (3600 * 1000);
		long m = (i % (3600 * 1000)) / (60 * 1000);
		long s = (i % (3600 * 1000)) / (1000);
		return new long[] { day, h, m, s };
	}

	public static String getTimeDesc(long start, long end) {
		long[] it = getTime(start, end);
		String d = "";
		if (it[0] != 0) {
			d += it[0] + "天";
		}
		if (it[1] != 0) {
			d += it[1] + "时";
		}
		if (it[2] != 0) {
			d += it[2] + "分";
		}
		if (StringUtils.isEmpty(d)) {
			d = it[3] + "秒";
		}

		return d;
	}

	public static String getTimeDesc(Date timing_start, Date timing_end) {
		if (timing_start != null && timing_end != null) return getTimeDesc(timing_start.getTime(), timing_end.getTime());
		return "";
	}
	
	public static Integer getDateDifferenceInMonth(Date oldDate,Date newDate){
	    int years = 0;  
	    int months = 0;
	    Calendar older = Calendar.getInstance();  
	    Calendar newer = Calendar.getInstance();
	    older.setTime(oldDate);  
	    newer.setTime(newDate);


	    years = newer.get(Calendar.YEAR)-older.get(Calendar.YEAR);  
	    if(years>=0){
	        months = newer.get(Calendar.MONTH)-older.get(Calendar.MONTH)+12*years;
	    }
	    return months;
	}
	
	/**
	 * 获得当前时间的字符串  ts格式
	 * @return
	 */
	public static String getCurrentTimeString(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * 获得当前时间的字符串  yyyy-MM-dd格式
	 * @return
	 */
	public static String getCurrentDateString(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 取得当前日期所在周的第一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Sunday
		return calendar.getTime();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Saturday
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的第一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		return calendar.getTime();
	}

	/**
	 * 返回指定日期的月的最后一天
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				1);
		calendar.roll(Calendar.DATE, -1);
		return calendar.getTime();
	}
	
}
