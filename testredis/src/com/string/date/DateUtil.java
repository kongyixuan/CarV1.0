package com.string.date;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;
public class DateUtil {
	   private DateUtil() {
		   
	    }
	 
	    /**
	     * yyyy-MM-dd
	     */
	    public static final String FORMAT1 = "yyyy-MM-dd";
	 
	    /**
	     * yyyy.MM.dd
	     */
	    public static final String FORMAT2 = "yyyy.MM.dd";
	 
	    /**
	     * yyyy/MM/dd
	     */
	    public static final String FORMAT3 = "yyyy/MM/dd";
	 
	    /**
	     * yyyy-MM-dd HH:mm
	     */
	    public static final String FORMAT4 = "yyyy-MM-dd HH:mm";
	 
	    /**
	     * yyyy.MM.dd HH:mm
	     */
	    public static final String FORMAT5 = "yyyy.MM.dd HH:mm";
	 
	    /**
	     * yyyy/MM/dd HH:mm
	     */
	    public static final String FORMAT6 = "yyyy/MM/dd HH:mm";
	 
	    /**
	     * yyyy-MM-dd HH:mm:ss
	     */
	    public static final String FORMAT7 = "yyyy-MM-dd HH:mm:ss";
	 
	    /**
	     * YYYY-MM-dd HH-mm-ss
	     */
	    public static final String FORMAT15 = "YYYY-MM-dd HH-mm-ss";
	 
	    /**
	     * yyyy.MM.dd HH:mm:ss
	     */
	    public static final String FORMAT8 = "yyyy.MM.dd HH:mm:ss";
	 
	    /**
	     * yyyy/MM/dd HH:mm:ss
	     */
	    public static final String FORMAT9 = "yyyy/MM/dd HH:mm:ss";
	 
	    /**
	     * yyyy_MM_dd_HH_mm_ss
	     */
	    public static final String FORMAT10 = "yyyy_MM_dd_HH_mm_ss";
	 
	    /**
	     * yy-MM-dd
	     */
	    public static final String FORMAT11 = "yy-MM-dd";
	 
	    /**
	     * yyyyMMdd
	     */
	    public static final String FORMAT12 = "yyyyMMdd";
	 
	    /**
	     * yyyyMMddHHmmss
	     */
	    public static final String FORMAT13 = "yyyyMMddHHmmss";
	 
	    /**
	     * yyyyMM
	     */
	    public static final String FORMAT14 = "yyyyMM";
	 
	    public static Date getCurrentDate() {
	 
	        return new Date(System.currentTimeMillis());
	    }
	 
	    public static Date getYesterDay() {
	 
	        return addDay(new Date(System.currentTimeMillis()), -1);
	    }
	 
	    public static String getYesterDayString() {
	 
	        return parseDateToString(addDay(new Date(System.currentTimeMillis()), -1), FORMAT1);
	    }
	 
	    /**
	     * �õ������յ�·��
	     * @return
	     */
	    public static String getThisYearMonthDay(String dateString) {
	 
	        Date date = parseStringToDate(dateString, FORMAT12);
	 
	        return DateUtil.getYear(date) + "/" + DateUtil.getMonth(date) + "/" + DateUtil.getDay(date) + "/";
	    }
	 
	    /**
	     * ���ص�ǰ���ڻ�ʱ��
	     * 
	     * @param format
	     * @return
	     */
	    public static String getCurrentDate(String format) {
	 
	        if (StringUtils.isBlank(format)) {
	            format = FORMAT1;
	        }
	 
	        Date date = new Date();
	 
	        SimpleDateFormat formatter = new SimpleDateFormat(format);
	 
	        String currentTime = formatter.format(date);
	 
	        return currentTime;
	    }
	 
	    /**
	     * ���ص�ǰʱ��
	     * 
	     * @return
	     */
	    public static String getCurrentTime() {
	 
	        String datetime = getCurrentDate(FORMAT7);
	        String time = datetime.substring(datetime.indexOf(" ") + 1);
	 
	        return time;
	    }
	 
	    /**
	     * ���ص�ǰʱ��������
	     * 
	     * @return
	     */
	    public static String getCurrentDateTimeRandom() {
	 
	        String datetime = getCurrentDate(DateUtil.FORMAT10) + "_" + Math.random();
	 
	        return datetime;
	    }
	 
	    /**
	     * ���ص�ǰʱ�������
	     * @return
	     */
	    public static String getCurrentDateTimeString() {
	 
	        return getCurrentDate(DateUtil.FORMAT7);
	 
	    }
	 
	    /**
	     * ���ص�ǰ����
	     * 
	     * @return
	     */
	    public static Date getCurrentDateTime() {
	 
	        String datetime = getCurrentDate(FORMAT7);
	 
	        return parseStringToDate(datetime, "");
	    }
	 
	    public static Timestamp getCurrentTimestamp() {
	 
	        String datetime = getCurrentDate(FORMAT7);
	 
	        return parseStringToTimestamp(datetime, "");
	    }
	 
	    public static Timestamp getCurrentTimestamp(String format) {
	 
	        String datetime = getCurrentDate(format);
	 
	        return parseStringToTimestamp(datetime, "");
	    }
	 
	    /**
	     * ����ת��Ϊ�ַ���
	     * 
	     * @param date ����
	     * @param format ��ʽ
	     * @return �����ַ�������
	     */
	    public static String parseDateToString(Date date, String format) {
	 
	        String result = "";
	        DateFormat formatter = null;
	        try {
	            if (date != null) {
	                if (StringUtils.isBlank(format)) {
	                    formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                }
	                else {
	                    formatter = new SimpleDateFormat(format);
	                }
	                result = formatter.format(date);
	            }
	        }
	        catch (Exception e) {
	        }
	 
	        return result;
	    }
	 
	    /**
	     * ʱ��1-ʱ��2�ĺ���
	     * 
	     * @param t1
	     * @param t2
	     * @return
	     */
	    public static long between(Timestamp t1, Timestamp t2) {
	 
	        if ((t1 != null) && (t2 != null)) {
	            return t1.getTime() - t2.getTime();
	        }
	 
	        return 0;
	    }
	 
	    /**
	     * ��������date1-date2�������������
	     * 
	     * @param date1
	     *             ����
	     * @param date2
	     *             ����
	     * @return ��������������
	     */
	    public static int betweenTwoDates(Date date1, Date date2) {
	 
	        return (int) ((getMillis(date1) - getMillis(date2)) / (24 * 3600 * 1000));
	    }
	 
	    /**   
	     * ���ַ���ת��Ϊ����   
	     *    
	     * @param str   
	     * @return   
	     * @throws ParseException 
	     */
	    public static Date parseStringToDate(String str, String format) {
	 
	        DateFormat formatter = null;
	        Date date = null;
	        if (StringUtils.isNotBlank(str)) {
	 
	            if (StringUtils.isBlank(format)) {
	                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            }
	            else {
	                formatter = new SimpleDateFormat(format);
	            }
	 
	            try {
	                date = formatter.parse(str);
	            }
	            catch (ParseException e) {
	                e.printStackTrace();
	            }
	        }
	 
	        return date;
	    }
	 
	    /**
	     * ����2007����������
	     * 
	     * @return
	     */
	    public static List<Integer> get2007ToThisYear() {
	 
	        // ��ǰʱ��
	        Calendar c = Calendar.getInstance();
	        c.setTime(new Date());
	 
	        // ���ص����
	        List<Integer> the2007ToThisYearList = new ArrayList<Integer>();
	        // ��ǰ��
	        int thisYear = c.get(Calendar.YEAR);
	 
	        for (int i = thisYear; i >= 2007; i--) {
	            the2007ToThisYearList.add(i);
	        }
	 
	        return the2007ToThisYearList;
	    }
	 
	    /**
	     *  ��ȡ��ǰ�µ�ǰ�����·ݵ�ʱ��
	     * @param months 
	     * @return
	     */
	    public static Date getDateBeforeMonths(int months) {
	 
	        // ��ǰʱ��
	        Calendar c = Calendar.getInstance();
	 
	        c.add(Calendar.MONTH, -months);
	 
	        return c.getTime();
	    }
	 
	    /**
	     * ��ȡ��ǰ���ڵ�ǰ�����ʱ��
	     * @param days
	     * @return
	     */
	    public static Date getDateBeforeDays(int days) {
	 
	        // ��ǰʱ��
	        Calendar c = Calendar.getInstance();
	 
	        c.add(Calendar.DATE, -days);
	        return c.getTime();
	    }
	 
	    /**
	     * ����1-12�·�
	     * 
	     * @return
	     */
	    public static List<String> getAllMonth() {
	 
	        List<String> theMonthList = new ArrayList<String>();
	 
	        for (int i = 1; i < 13; i++) {
	            if (i < 10) {
	                theMonthList.add("0" + i);
	            }
	            else {
	                theMonthList.add("" + i);
	            }
	        }
	 
	        return theMonthList;
	    }
	 
	    /**
	     * ���������е����
	     * 
	     * @param date
	     *             ����
	     * @return �������
	     */
	    public static int getYear(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.get(Calendar.YEAR);
	    }
	 
	    /**
	     * ���������е��·�
	     * 
	     * @param date
	     *             ����
	     * @return �����·�
	     */
	    public static int getMonth(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.get(Calendar.MONTH) + 1;
	    }
	 
	    /**
	     * ���������е���
	     * 
	     * @param date
	     *             ����
	     * @return ������
	     */
	    public static int getDay(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.get(Calendar.DAY_OF_MONTH);
	    }
	 
	    /**
	     * ���������е�Сʱ
	     * 
	     * @param date
	     *             ����
	     * @return ����Сʱ
	     */
	    public static int getHour(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.get(Calendar.HOUR_OF_DAY);
	    }
	 
	    /**
	     * ���������еķ���
	     * 
	     * @param date
	     *             ����
	     * @return ���ط���
	     */
	    public static int getMinute(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.get(Calendar.MINUTE);
	    }
	 
	    /**
	     * ���������е�����
	     * 
	     * @param date
	     *             ����
	     * @return ��������
	     */
	    public static int getSecond(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.get(Calendar.SECOND);
	    }
	 
	    /**
	     * �������ڴ���ĺ���
	     * 
	     * @param date
	     *             ����
	     * @return ���غ���
	     */
	    public static long getMillis(Date date) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	 
	        return c.getTimeInMillis();
	    }
	 
	    /**
	     * ���ص�ǰ���ڴ���ĺ���
	     * 
	     * @return
	     */
	    public static long getCurrentTimeMillis() {
	 
	        return System.currentTimeMillis();
	    }
	 
	    public static Date addMonth(Date date, int month) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        c.add(c.MONTH, month);
	 
	        return c.getTime();
	    }
	 
	    /**
	     * ���ڼ�����,������ǰ�ӣ�����
	     * 
	     * @param date
	     *             ����
	     * @param day
	     *             ����
	     * @return ������Ӻ������
	     */
	    public static Date addDay(Date date, int day) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
	 
	        return c.getTime();
	    }
	 
	    /**
	     * ���ڼ�����,������ǰ�ӣ�����
	     * 
	     * @param date
	     *             ����
	     * @param second
	     *             ����
	     * @return ������Ӻ������
	     */
	    public static Date addSecond(Date date, long second) {
	 
	        Calendar c = Calendar.getInstance();
	        c.setTimeInMillis(getMillis(date) + second * 1000);
	 
	        return c.getTime();
	    }
	 
	    /**   
	     * ����һ�����ڣ����������ڼ�   
	     *    
	     * @param sdate   
	     * @return   
	     */
	    public static String getWeekByDate(String sdate) {
	 
	        // ��ת��Ϊʱ��
	        Date date = parseStringToDate(sdate, "");
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        int day = c.get(Calendar.DAY_OF_WEEK);
	        // day�д�ľ������ڼ��ˣ��䷶Χ 1~7
	        // 1=������ 7=����������������
	        return day + "";
	    }
	     
	     
	    /**   
	     * ����һ�����ڣ����������ڼ�
	     * 1=������ 7=����������������   
	     *    
	     * @param sdate   
	     * @return   
	     */
	    public static int getWeekByDate(Date date) {
	 
	        // ��ת��Ϊʱ��
	        Calendar c = Calendar.getInstance();
	        c.setTime(date);
	        // day�д�ľ������ڼ��ˣ��䷶Χ 1~7
	        // 1=������ 7=����������������
	        return c.get(Calendar.DAY_OF_WEEK);
	    }
	     
	 
	    /**
	     * ����Ϊһ���еĵڼ���
	     * @return
	     */
	    public static String getWeekNum(Date date) {
	 
	        Calendar c = Calendar.getInstance(Locale.CHINA);
	        c.setTime(date);
	        String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
	 
	        return week;
	 
	    }
	 
	    public static java.sql.Date parseUtilDateToSqlDate(Date date) {
	 
	        if (date != null) {
	            return new java.sql.Date(date.getTime());
	        }
	        else {
	            return null;
	        }
	    }
	 
	    public static java.sql.Date parseStringToSqlDate(String dateStr, String format) {
	 
	        Date date = null;
	        if (StringUtils.isBlank(format)) {
	            date = parseStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
	        }
	        else {
	            date = parseStringToDate(dateStr, format);
	        }
	 
	        return parseUtilDateToSqlDate(date);
	    }
	 
	    public static Timestamp parseUtilDateToTimestamp(Date date, String format) {
	 
	        return parseStringToTimestamp(parseDateToString(date, format), format);
	    }
	 
	    public static Date parseTimestampToUtilDate(Timestamp date, String format) {
	 
	        return parseStringToDate(parseDateToString(date, format), format);
	    }
	 
	    public static Timestamp parseStringToTimestamp(String dateStr, String format) {
	 
	        if (StringUtils.isBlank(dateStr)) {
	            return null;
	        }
	 
	        Date date = null;
	        if (StringUtils.isBlank(format)) {
	            date = parseStringToDate(dateStr, "yyyy-MM-dd HH:mm:ss");
	        }
	        else {
	            date = parseStringToDate(dateStr, format);
	        }
	 
	        if (date != null) {
	            long t = date.getTime();
	            return new Timestamp(t);
	        }
	        else {
	            return null;
	        }
	    }
	 
	    /**
	     * ��ȡʱ��2099-12-31��23:59:59
	     * 
	     * @return
	     */
	    public static Timestamp getMaxTimestamp() {
	 
	        return DateUtil.parseStringToTimestamp("2099-12-31 23:59:59", DateUtil.FORMAT7);
	    }
	 
	    /**
	     * ���������е������գ���ʽ����yyyyMMdd
	     * @param date
	     * @return
	     */
	    public static String getYearMonthDay(Date date) {
	 
	        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT12);
	 
	        String currentTime = formatter.format(date);
	 
	        return currentTime;
	    }
	 
	    /**
	     * ȡ��ָ���·ݵĵ�һ��
	     *
	     * @param strdate String
	     * @return String
	     */
	    public static String getMonthBegin(String strdate) {
	 
	        return parseDateToString(parseStringToDate(strdate, "yyyy-MM"), "");
	    }
	 
	    /**
	     * ȡ��ָ���·ݵ����һ��
	     *
	     * @param strdate String
	     * @return String
	     */
	    public static String getMonthEnd(String strdate) {
	 
	        Date date = parseStringToDate(getMonthBegin(strdate), "");
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH, 1);
	        calendar.add(Calendar.DAY_OF_YEAR, -1);
	 
	        return parseDateToString(calendar.getTime(), "");
	    }
	 
	    public static String getPreviousMonthBegin() {
	 
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
	        Date date = new Date(cal.getTimeInMillis());
	 
	        return parseDateToString(parseStringToDate(parseDateToString(date, FORMAT1), "yyyy-MM"), FORMAT1);
	    }
	 
	    public static String getPreviousMonthEnd() {
	 
	        Date date = parseStringToDate(getPreviousMonthBegin(), FORMAT1);
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.MONTH, 1);
	        calendar.add(Calendar.DAY_OF_YEAR, -1);
	 
	        return parseDateToString(calendar.getTime(), FORMAT1);
	    }
	 
	    /**
	     * �ϸ���
	     * 
	     * @return
	     */
	    public static String getPreviousMonth() {
	 
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
	        Date date = new Date(cal.getTimeInMillis());
	 
	        return parseDateToString(parseStringToDate(parseDateToString(date, FORMAT1), "yyyy-MM"), FORMAT14);
	    }
	 
	    /**
	     * ��������
	     * 
	     * @return
	     */
	    public static String getPreviousTwoMonth() {
	 
	        Calendar cal = Calendar.getInstance();
	        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 2);
	        Date date = new Date(cal.getTimeInMillis());
	 
	        return parseDateToString(parseStringToDate(parseDateToString(date, FORMAT1), "yyyy-MM"), FORMAT14);
	    }
	 
	    /**
	     * �ж����������Ƿ���ͬһ��
	     * @param date1
	     * @param date2
	     * @return
	     */
	    public static boolean isSameWeekDates(Date date1, Date date2) {
	 
	        Calendar cal1 = Calendar.getInstance();
	        Calendar cal2 = Calendar.getInstance();
	        cal1.setTime(date1);
	        cal2.setTime(date2);
	        int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
	        if (0 == subYear) {
	            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
	                return true;
	            }
	        }
	        else if ((1 == subYear) && (11 == cal2.get(Calendar.MONTH))) {
	            // ���12�µ����һ�ܺ�������һ�ܵĻ������һ�ܼ���������ĵ�һ��
	            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
	                return true;
	            }
	        }
	        else if ((-1 == subYear) && (11 == cal1.get(Calendar.MONTH))) {
	            if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR)) {
	                return true;
	            }
	        }
	        return false;
	    }
	 
	    public static void main(String[] args) {
	 
	        System.out.println(getPreviousMonthBegin());
	        System.out.println(getPreviousMonthEnd());
	        System.out.println(getYearMonthDay(new Date()));
	        System.out.println(getYearMonthDay(parseStringToDate("2009-11-2 12:1:21", FORMAT7)));
	        System.out.println("current time: " + getCurrentDateTime());
	        System.out.println("addsecond: " + addSecond(getCurrentDateTime(), -1L));
	        Date date = new Date();
	        System.out.println("current date: " + date.toString());
	 
	        System.out.println("test parseDateToString: " + parseDateToString(date, ""));
	        System.out.println("test parseStringToDate: " + parseStringToDate("1990-01-1 00:00:00", ""));
	 
	        System.out.println("test getYear: " + getYear(date));
	        System.out.println("test getMonth: " + getMonth(date));
	        System.out.println("test getDay: " + getDay(date));
	        System.out.println("test getHour: " + getHour(date));
	        System.out.println("test getMinute: " + getMinute(date));
	        System.out.println("test getSecond: " + getSecond(date));
	        System.out.println("test getMillis: " + getMillis(date));
	        System.out.println("test addDate: " + addDay(date, 2));
	        System.out.println("test betweenTwoDays: " + betweenTwoDates(date, addDay(date, 2)));
	        System.out.println("test getWeekNum: " + getWeekNum(addDay(date, -2)));
	        System.out.println("test getWeekByDate: " + getWeekByDate(parseDateToString(date, "")));
	        System.out.println("test getMonthBegin: " + getMonthBegin(parseDateToString(date, "")));
	        System.out.println("test getMonthEnd: " + getMonthEnd(parseDateToString(date, "")));
	        System.out.println("test isSameWeekDates: " + isSameWeekDates(date, addDay(date, -2)));
	 
	        System.out.println(getPreviousTwoMonth());
	        System.out.println(getPreviousMonth());
	        List<Integer> yearList = get2007ToThisYear();
	        List<String> monthList = getAllMonth();
	    }
	 
	    public static String addOneDay(String stopTime) {
	        String finishTime = stopTime;
	        Date finishDate = null;
	        if(stopTime != null && !"".equals(stopTime))
	        {
	            if(stopTime.length() < 19)
	            {
	                finishTime = stopTime.substring(0, 10) + " 00:00:00";
	            }
	            finishDate = DateUtil.parseStringToDate(finishTime, DateUtil.FORMAT7);
	            finishDate = DateUtil.addDay(finishDate, 1);
	            return DateUtil.parseDateToString(finishDate, DateUtil.FORMAT7);
	        }else{
	            return null;
	        }
	    }
	     
	     
	    /**
	     * ��date��1��
	     * @param date
	     * @return
	     */
	    public static Date addOneDay(Date date) {
	 
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        calendar.add(Calendar.DATE, 1);
	        return calendar.getTime();
	    }
}
