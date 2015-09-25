package utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author liuzy
 *
 */
public class DateUtil      
{      
	public static void main(String[] args){
		
		System.out.println(getCurDate("yyyyMMdd").substring(2));
	}
	
    //Ĭ����ʾ���ڵĸ�ʽ      
    public static final String DATAFORMAT_STR = "yyyy-MM-dd";      
          
    //Ĭ����ʾ���ڵĸ�ʽ      
    public static final String YYYY_MM_DATAFORMAT_STR = "yyyy-MM";      
          
    //Ĭ����ʾ����ʱ��ĸ�ʽ      
    public static final String DATATIMEF_STR = "yyyy-MM-dd HH:mm:ss";      
          
    //Ĭ����ʾ�����������ڵĸ�ʽ      
    public static final String ZHCN_DATAFORMAT_STR = "yyyy��MM��dd��";      
          
    //Ĭ����ʾ������������ʱ��ĸ�ʽ      
    public static final String ZHCN_DATATIMEF_STR = "yyyy��MM��dd��HHʱmm��ss��";      
          
    //Ĭ����ʾ������������ʱ��ĸ�ʽ      
    public static final String ZHCN_DATATIMEF_STR_4yMMddHHmm = "yyyy��MM��dd��HHʱmm��";      
          
    private static DateFormat dateFormat = null;      
          
    private static DateFormat dateTimeFormat = null;      
          
    private static DateFormat zhcnDateFormat = null;      
          
    private static DateFormat zhcnDateTimeFormat = null;      
    static     
    {      
        dateFormat = new SimpleDateFormat(DATAFORMAT_STR);      
        dateTimeFormat = new SimpleDateFormat(DATATIMEF_STR);      
        zhcnDateFormat = new SimpleDateFormat(ZHCN_DATAFORMAT_STR);      
        zhcnDateTimeFormat = new SimpleDateFormat(ZHCN_DATATIMEF_STR);      
    }      
          
    private static DateFormat getDateFormat(String formatStr)      
    {      
        if (formatStr.equalsIgnoreCase(DATAFORMAT_STR))      
        {      
            return dateFormat;      
        }      
        else     
            if (formatStr.equalsIgnoreCase(DATATIMEF_STR))      
            {      
                return dateTimeFormat;      
            }      
            else     
                if (formatStr.equalsIgnoreCase(ZHCN_DATAFORMAT_STR))      
                {      
                    return zhcnDateFormat;      
                }      
                else     
                    if (formatStr.equalsIgnoreCase(ZHCN_DATATIMEF_STR))      
                    {      
                        return zhcnDateTimeFormat;      
                    }      
                    else     
                    {      
                        return new SimpleDateFormat(formatStr);      
                    }      
    }      
          
    /**    
     * ����Ĭ����ʾ����ʱ��ĸ�ʽ"yyyy-MM-dd HH:mm:ss"��ת��dateTimeStrΪDate����   
     * dateTimeStr������"yyyy-MM-dd HH:mm:ss"����ʽ    
     * @param dateTimeStr    
     * @return    
     */     
    public static Date getDate(String dateTimeStr)      
    {      
        return getDate(dateTimeStr, DATATIMEF_STR);      
    }      
    
    /**
     * �ַ���ת����ʱ���
     * @param str
     * @return Timestamp
     */
    public static Timestamp getStrTimestamp(String str){
    	return Timestamp.valueOf(str);
    }
          
    /**    
     * ����Ĭ��formatStr�ĸ�ʽ��ת��dateTimeStrΪDate����    
     * dateTimeStr������formatStr����ʽ    
     * @param dateTimeStr    
     * @param formatStr    
     * @return    
     */     
    public static Date getDate(String dateTimeStr, String formatStr)      
    {      
        try     
        {      
            if (dateTimeStr == null || dateTimeStr.equals(""))      
            {      
                return null;      
            }      
            DateFormat sdf = getDateFormat(formatStr);      
            java.util.Date d = sdf.parse(dateTimeStr);      
            return d;      
        }      
        catch (ParseException e)      
        {      
            throw new RuntimeException(e);      
        }      
    }      
          
    /**    
     * ��YYYYMMDDת����Date����    
     * @param date    
     * @return    
     * @throws BusinessException    
     */     
    public static Date transferDate(String date) throws Exception      
    {      
        if (date == null || date.length() < 1)      
            return null;      
              
        if (date.length() != 8)      
            throw new Exception("���ڸ�ʽ����");      
        String con = "-";      
              
        String yyyy = date.substring(0, 4);      
        String mm = date.substring(4, 6);      
        String dd = date.substring(6, 8);      
              
        int month = Integer.parseInt(mm);      
        int day = Integer.parseInt(dd);      
        if (month < 1 || month > 12 || day < 1 || day > 31)      
            throw new Exception("���ڸ�ʽ����");      
              
        String str = yyyy + con + mm + con + dd;      
        return DateUtil.getDate(str, DateUtil.DATAFORMAT_STR);      
    }      
          
//    /**    
//     * ��YYYY��MM��DD����ת����yyyymmdd��ʽ�ַ���    
//     * @param date    
//     * @return    
//     */     
//    public static String getYYYYMMDDDate(Date date)      
//    {      
//        if (date == null)      
//            return null;      
//        String yyyy = getYear(date) + "";      
//        String mm = getMonth(date) + "";      
//        String dd = getDay(date) + "";      
//              
//        mm = StringUtil.rightAlign(mm, 2, "0");      
//        dd = StringUtil.rightAlign(dd, 2, "0");      
//        return yyyy + mm + dd;      
//    }      
          
//    /**    
//     * ��YYYY��MM��DD����ת����YYYYMMDDHHMMSS��ʽ�ַ���    
//     * @param date    
//     * @return    
//     */     
//    public static String getYYYYMMDDHHMMSSDate(Date date)      
//    {      
//        if (date == null)      
//            return null;      
//        String yyyy = getYear(date) + "";      
//        String mm = getMonth(date) + "";      
//        String dd = getDay(date) + "";      
//        String hh = getHour(date) + "";      
//        String min = getMin(date) + "";      
//        String ss = getSecond(date) + "";      
//              
//        mm = StringUtil.rightAlign(mm, 2, "0");      
//        dd = StringUtil.rightAlign(dd, 2, "0");      
//        hh = StringUtil.rightAlign(hh, 2, "0");      
//        min = StringUtil.rightAlign(min, 2, "0");      
//        ss = StringUtil.rightAlign(ss, 2, "0");      
//              
//        return yyyy + mm + dd + hh + min + ss;      
//    }      
//          
//    /**    
//     * ��YYYY��MM��DD����ת����yyyymmdd��ʽ�ַ���    
//     * @param date    
//     * @return    
//     */     
//    public static String getYYYYMMDDDate(String date)      
//    {      
//        return getYYYYMMDDDate(getDate(date, DATAFORMAT_STR));      
//    }      
          
    /**    
     * ��Dateת�����ַ�����yyyy-mm-dd hh:mm:ss�����ַ���    
     * @param date    
     * @return    
     */     
    public static String dateToDateString(Date date)      
    {      
        return dateToDateString(date, DATATIMEF_STR);      
    }      
          
    /**    
     * ��Dateת����formatStr��ʽ���ַ���    
     * @param date    
     * @param formatStr    
     * @return    
     */     
    public static String dateToDateString(Date date, String formatStr)      
    {      
        DateFormat df = getDateFormat(formatStr);      
        return df.format(date);      
    }      
          
    /**    
     * ����һ��yyyy-MM-dd HH:mm:ss ��ʽ������ʱ���ַ����е�HH:mm:ss   
     * @param dateTime    
     * @return    
     */     
    public static String getTimeString(String dateTime)      
    {      
        return getTimeString(dateTime, DATATIMEF_STR);      
    }      
          
    /**    
     * ����һ��formatStr��ʽ������ʱ���ַ����е�HH:mm:ss    
     * @param dateTime    
     * @param formatStr    
     * @return    
     */     
    public static String getTimeString(String dateTime, String formatStr)      
    {      
        Date d = getDate(dateTime, formatStr);      
        String s = dateToDateString(d);      
        return s.substring(DATATIMEF_STR.indexOf('H'));      
    }      
          
    /**    
     * ��ȡ��ǰ����yyyy-MM-dd����ʽ    
     * @return    
     */     
    public static String getCurDate()      
    {      
        //return dateToDateString(new Date(),DATAFORMAT_STR);     
        return dateToDateString(Calendar.getInstance().getTime(), DATAFORMAT_STR);      
    }      
          
    /**    
     * ��ȡ��ǰ����yyyy-MM-dd����ʽ    
     * @return    
     */     
    public static String getCurDate(String format)      
    { 
        //return dateToDateString(new Date(),DATAFORMAT_STR);     
        return dateToDateString(Calendar.getInstance().getTime(), format);      
    }   
    /**    
     * ��ȡ��ǰ����yyyy��MM��dd�յ���ʽ    
     * @return    
     */     
    public static String getCurZhCNDate()      
    {      
        return dateToDateString(new Date(), ZHCN_DATAFORMAT_STR);      
    }      
          
    /**    
     * ��ȡ��ǰ����ʱ��yyyy-MM-dd HH:mm:ss����ʽ    
     * @return    
     */     
    public static String getCurDateTime()      
    {      
        return dateToDateString(new Date(), DATATIMEF_STR);      
    }      
          
    /**    
     * ��ȡ��ǰ����ʱ��yyyy��MM��dd��HHʱmm��ss�����ʽ    
     * @return    
     */     
    public static String getCurZhCNDateTime()      
    {      
        return dateToDateString(new Date(), ZHCN_DATATIMEF_STR);      
    }      
    
    /**
     * 
     * @param time
     * @param style  yyyy-MM-dd   yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDate(long time, String style) {
		Date date = new Date(time);
		SimpleDateFormat outFormat = new SimpleDateFormat(style);
		return outFormat.format(date);
	}
    
    /**
     * 
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
		if (date == null) return "";
		SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");
		return outFormat.format(date);
	}
          
    /**    
     * ��ȡ����d��days����һ��Date    
     * @param d    
     * @param days    
     * @return    
     */     
    public static Date getInternalDateByDay(Date d, int days)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        now.add(Calendar.DATE, days);      
        return now.getTime();      
    }      
          
    public static Date getInternalDateByMon(Date d, int months)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        now.add(Calendar.MONTH, months);      
        return now.getTime();      
    }      
          
    public static Date getInternalDateByYear(Date d, int years)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        now.add(Calendar.YEAR, years);      
        return now.getTime();      
    }      
          
    public static Date getInternalDateBySec(Date d, int sec)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        now.add(Calendar.SECOND, sec);      
        return now.getTime();      
    }      
          
    public static Date getInternalDateByMin(Date d, int min)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        now.add(Calendar.MINUTE, min);      
        return now.getTime();      
    }      
          
    public static Date getInternalDateByHour(Date d, int hours)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        now.add(Calendar.HOUR_OF_DAY, hours);      
        return now.getTime();      
    }      
          
    /**    
     * ����һ�������ַ������������ڸ�ʽ��Ŀǰ֧��4��    
     * ��������ǣ��򷵻�null    
     * @param DateString    
     * @return    
     */     
    public static String getFormateStr(String DateString)      
    {      
        String patternStr1 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}"; //"yyyy-MM-dd"     
        String patternStr2 = "[0-9]{4}-[0-9]{1,2}-[0-9]{1,2}\\s[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}"; //"yyyy-MM-dd HH:mm:ss";     
        String patternStr3 = "[0-9]{4}��[0-9]{1,2}��[0-9]{1,2}��";//"yyyy��MM��dd��"     
        String patternStr4 = "[0-9]{4}��[0-9]{1,2}��[0-9]{1,2}��[0-9]{1,2}ʱ[0-9]{1,2}��[0-9]{1,2}��";//"yyyy��MM��dd��HHʱmm��ss��"     
              
        Pattern p = Pattern.compile(patternStr1);      
        Matcher m = p.matcher(DateString);      
        boolean b = m.matches();      
        if (b)      
            return DATAFORMAT_STR;      
        p = Pattern.compile(patternStr2);      
        m = p.matcher(DateString);      
        b = m.matches();      
        if (b)      
            return DATATIMEF_STR;      
              
        p = Pattern.compile(patternStr3);      
        m = p.matcher(DateString);      
        b = m.matches();      
        if (b)      
            return ZHCN_DATAFORMAT_STR;      
              
        p = Pattern.compile(patternStr4);      
        m = p.matcher(DateString);      
        b = m.matches();      
        if (b)      
            return ZHCN_DATATIMEF_STR;      
        return null;      
    }      
          
    /**    
     * ��һ��"yyyy-MM-dd HH:mm:ss"�ַ�����ת����"yyyy��MM��dd��HHʱmm��ss��"���ַ���   
     * @param dateStr    
     * @return    
     */     
    public static String getZhCNDateTime(String dateStr)      
    {      
        Date d = getDate(dateStr);      
        return dateToDateString(d, ZHCN_DATATIMEF_STR);      
    }      
          
    /**    
     * ��һ��"yyyy-MM-dd"�ַ�����ת����"yyyy��MM��dd��"���ַ���    
     * @param dateStr    
     * @return    
     */     
    public static String getZhCNDate(String dateStr)      
    {      
        Date d = getDate(dateStr, DATAFORMAT_STR);      
        return dateToDateString(d, ZHCN_DATAFORMAT_STR);      
    }      
          
    /**    
     * ��dateStr��fmtFromת����fmtTo�ĸ�ʽ    
     * @param dateStr    
     * @param fmtFrom    
     * @param fmtTo    
     * @return    
     */     
    public static String getDateStr(String dateStr, String fmtFrom, String fmtTo)      
    {      
        Date d = getDate(dateStr, fmtFrom);      
        return dateToDateString(d, fmtTo);      
    }      
          
    /**    
     * �Ƚ�����"yyyy-MM-dd HH:mm:ss"��ʽ�����ڣ�֮�������ٺ���,time2-time1   
     * @param time1    
     * @param time2    
     * @return    
     */     
    public static long compareDateStr(String time1, String time2)      
    {      
        Date d1 = getDate(time1);      
        Date d2 = getDate(time2);      
        return d2.getTime() - d1.getTime();      
    }      
          
    /**    
     * ��Сʱ������ɷ����Ժ���Ϊ��λ��ʱ��    
     * @param hours    
     * @return    
     */     
    public static long getMicroSec(BigDecimal hours)      
    {      
        BigDecimal bd;      
        bd = hours.multiply(new BigDecimal(3600 * 1000));      
        return bd.longValue();      
    }      
          
    /**    
     * ��ȡDate�еķ���    
     * @param d    
     * @return    
     */     
    public static int getMin(Date d)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        return now.get(Calendar.MINUTE);      
    }      
          
    /**    
     * ��ȡDate�е�Сʱ(24Сʱ)    
     * @param d    
     * @return    
     */     
    public static int getHour(Date d)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        return now.get(Calendar.HOUR_OF_DAY);      
    }      
          
    /**    
     * ��ȡDate�е���    
     * @param d    
     * @return    
     */     
    public static int getSecond(Date d)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        return now.get(Calendar.SECOND);      
    }      
          
    /**    
     * ��ȡxxxx-xx-xx����    
     * @param d    
     * @return    
     */     
    public static int getDay(Date d)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        return now.get(Calendar.DAY_OF_MONTH);      
    }      
          
    /**    
     * ��ȡ�·ݣ�1-12��    
     * @param d    
     * @return    
     */     
    public static int getMonth(Date d)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        return now.get(Calendar.MONTH) + 1;      
    }      
          
    /**    
     * ��ȡ19xx,20xx��ʽ����    
     * @param d    
     * @return    
     */     
    public static int getYear(Date d)      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        now.setTime(d);      
        return now.get(Calendar.YEAR);      
    }      
          
    /**    
     * �õ�d���ϸ��µ����+�·�,��200505    
     * @return    
     */     
    public static String getYearMonthOfLastMon(Date d)      
    {      
        Date newdate = getInternalDateByMon(d, -1);      
        String year = String.valueOf(getYear(newdate));      
        String month = String.valueOf(getMonth(newdate));      
        return year + month;      
    }      
          
    /**    
     * �õ���ǰ���ڵ��������200509    
     * @return String    
     */     
    public static String getCurYearMonth()      
    {      
        Calendar now = Calendar.getInstance(TimeZone.getDefault());      
        String DATE_FORMAT = "yyyyMM";      
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);      
        sdf.setTimeZone(TimeZone.getDefault());      
        return (sdf.format(now.getTime()));      
    }      
          
    public static Date getNextMonth(String year, String month)      
    {      
        String datestr = year + "-" + month + "-01";      
        Date date = getDate(datestr, DATAFORMAT_STR);      
        return getInternalDateByMon(date, 1);      
    }      
          
    public static Date getLastMonth(String year, String month)      
    {      
        String datestr = year + "-" + month + "-01";      
        Date date = getDate(datestr, DATAFORMAT_STR);      
        return getInternalDateByMon(date, -1);      
    }      
          
    /**    
     * �õ�����d������ҳ�����ڿؼ���ʽ����"2001-3-16"    
     * @param d    
     * @return    
     */     
    public static String getSingleNumDate(Date d)      
    {      
        return dateToDateString(d, DATAFORMAT_STR);      
    }      
          
    /**    
     * �õ�d����ǰ������,"yyyy-MM-dd"    
     * @param d    
     * @return    
     */     
    public static String getHalfYearBeforeStr(Date d)      
    {      
        return dateToDateString(getInternalDateByMon(d, -6), DATAFORMAT_STR);      
    }      
          
    /**    
     * �õ���ǰ����D���µ׵�ǰ/���������ʱ��,<0��ʾ֮ǰ��>0��ʾ֮��    
     * @param d    
     * @param days    
     * @return    
     */     
    public static String getInternalDateByLastDay(Date d, int days)      
    {      
              
        return dateToDateString(getInternalDateByDay(d, days), DATAFORMAT_STR);      
    }      
          
    /**    
     * �����е����������    
     *  @param field int  ��Ҫ�ӵ��ֶ�  �� �� ��    
     * @param amount int �Ӷ���    
     * @return String    
     */     
    public static String addDate(int field, int amount)      
    {      
        int temp = 0;      
        if (field == 1)      
        {      
            temp = Calendar.YEAR;      
        }      
        if (field == 2)      
        {      
            temp = Calendar.MONTH;      
        }      
        if (field == 3)      
        {      
            temp = Calendar.DATE;      
        }      
              
        String Time = "";      
        try     
        {      
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");      
            Calendar cal = Calendar.getInstance(TimeZone.getDefault());      
            cal.add(temp, amount);      
            Time = sdf.format(cal.getTime());      
            return Time;      
        }      
        catch (Exception e)      
        {      
            e.printStackTrace();      
            return null;      
        }      
              
    }      
          
    /**    
     * ���ϵͳ��ǰ�·ݵ�����    
     * @return    
     */     
    public static int getCurentMonthDay()      
    {      
        Date date = Calendar.getInstance().getTime();      
        return getMonthDay(date);      
    }      
          
    /**    
     * ���ָ�������·ݵ�����    
     * @return    
     */     
    public static int getMonthDay(Date date)      
    {      
        Calendar c = Calendar.getInstance();      
        c.setTime(date);      
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);      
              
    }      
          
    /**    
     * ���ָ�������·ݵ�����  yyyy-mm-dd    
     * @return    
     */     
    public static int getMonthDay(String date)      
    {      
        Date strDate = getDate(date, DATAFORMAT_STR);      
        return getMonthDay(strDate);      
              
    }      
          
    public static String getStringDate(Calendar cal)      
    {      
              
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");      
        return format.format(cal.getTime());      
    }      
     /**
      *      
     * @Description: ��ȡ�������ڸ�ʽΪ��yyyy-MM-dd�����ڵ�����֮��
     * @param @param cal
     * @param @return
     * @return String
      */
    public static int reductDate(String time_1,String time_2){      
              
    	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Long reductTime = null;
		try {
			reductTime = sf.parse(time_2).getTime()-sf.parse(time_1).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  (int)(reductTime/1000/60/60/24);
    }      
}     