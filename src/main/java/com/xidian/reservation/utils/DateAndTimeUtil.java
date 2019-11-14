package com.xidian.reservation.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：Maolin
 * @className ：WeekUtil
 * @date ：Created in 2019/9/21 10:53
 * @description： 星期处理工具类
 * @version: 1.0
 */
@Slf4j
public class DateAndTimeUtil {

    private static String defaultDatePattern = null;
    private static String timePattern = "HH:mm";
    private static Calendar cale = Calendar.getInstance();
    /** 日期格式yyyy-MM字符串常量 */
    private static final String MONTH_FORMAT = "yyyy-MM";
    /** 日期格式yyyy-MM-dd字符串常量 */
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    /** 日期格式HH:mm:ss字符串常量 */
    private static final String HOUR_FORMAT = "HH:mm:ss";
    /** 日期格式yyyy-MM-dd HH:mm:ss字符串常量 */
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /** 某天开始时分秒字符串常量  00:00:00 */
    private static final String DAY_BEGIN_STRING_HHMMSS = " 00:00:00";
    /**  某天结束时分秒字符串常量  23:59:59  */
    public static final String DAY_END_STRING_HHMMSS = " 23:59:59";
    private static SimpleDateFormat sdf_date_format = new SimpleDateFormat(DATE_FORMAT);
    private static SimpleDateFormat sdf_hour_format = new SimpleDateFormat(HOUR_FORMAT);
    private static SimpleDateFormat sdf_datetime_format = new SimpleDateFormat(DATETIME_FORMAT);


    /**
     * @Description: 根据日期返回当周七天日期，周一为开始
     * @Date:        11:16 2019/9/21
     * @Param:       [date]
     * @return:      java.util.List<java.lang.String>
     */
    public static List<String> getDateToWeek(Date date){
        List<String> dateWeekList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String time = "";
        //flag用来存取与当天日期的相差数
        int flag = 0;
        //i从2开始：一周从周一开始
        for(int i=2;i<9;i++){
            //新建日历
            Calendar cal = Calendar.getInstance();
            //在日历中找到当前日期
            cal.setTime(date);
            //当前日期时本周第几天，默认按照西方惯例上周星期天为第一天
            flag = -cal.get(Calendar.DAY_OF_WEEK);
            //根据循环。当天与上周星期天和本周一到周五相差的天数
            cal.add(Calendar.DATE, flag+i);
            //转化格式
            time = sdf.format(cal.getTime());
            //存入list
            dateWeekList.add(time);
        }
        return dateWeekList;
    }


    /**
     * 获得服务器当前日期及时间，以格式为：yyyy-MM-dd HH:mm:ss的日期字符串形式返回
     */
    public static String getDateTime() {
        try {
            return sdf_datetime_format.format(cale.getTime());
        } catch (Exception e) {
            log.debug("DateUtil.getDateTime():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期，以格式为：yyyy-MM-dd的日期字符串形式返回
     */
    public static String getDate() {
        try {
            return sdf_date_format.format(cale.getTime());
        } catch (Exception e) {
            log.debug("DateUtil.getDate():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前时间，以格式为：HH:mm:ss的日期字符串形式返回
     * @author dylan_xu
     * @date Mar 11, 2012
     * @return
     */
    public static String getTime() {
        String temp = " ";
        try {
            temp += sdf_hour_format.format(cale.getTime());
            return temp;
        } catch (Exception e) {
            log.debug("DateUtil.getTime():" + e.getMessage());
            return "";
        }
    }

    /**
     * 统计时开始日期的默认值
     */
    public static String getStartDate() {
        try {
            return getYear() + "-01-01";
        } catch (Exception e) {
            log.debug("DateUtil.getStartDate():" + e.getMessage());
            return "";
        }
    }

    /**
     * 统计时结束日期的默认值
     */
    public static String getEndDate() {
        try {
            return getDate();
        } catch (Exception e) {
            log.debug("DateUtil.getEndDate():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期的年份
     */
    public static String getYear() {
        try {
            return String.valueOf(cale.get(Calendar.YEAR));
        } catch (Exception e) {
            log.debug("DateUtil.getYear():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器当前日期的月份
     */
    public static String getMonth() {
        try {
            java.text.DecimalFormat df = new java.text.DecimalFormat();
            df.applyPattern("00;00");
            return df.format((cale.get(Calendar.MONTH) + 1));
        } catch (Exception e) {
            log.debug("DateUtil.getMonth():" + e.getMessage());
            return "";
        }
    }

    /**
     * 获得服务器在当前月中天数
     */
    public static String getDay() {
        try {
            return String.valueOf(cale.get(Calendar.DAY_OF_MONTH));
        } catch (Exception e) {
            log.debug("DateUtil.getDay():" + e.getMessage());
            return "";
        }
    }

    /**
     * 比较两个日期相差的天数
     */
    public static int getMargin(String date1, String date2) {
        int margin;
        try {
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date dt1 = sdf_date_format.parse(date1, pos);
            Date dt2 = sdf_date_format.parse(date2, pos1);
            long l = dt1.getTime() - dt2.getTime();
            margin = (int) (l / (24 * 60 * 60 * 1000));
            return margin;
        } catch (Exception e) {
            log.debug("DateUtil.getMargin():" + e.toString());
            return 0;
        }
    }

    /**
     * 比较两个日期相差的天数
     */
    public static double getDoubleMargin(String date1, String date2) {
        double margin;
        try {
            ParsePosition pos = new ParsePosition(0);
            ParsePosition pos1 = new ParsePosition(0);
            Date dt1 = sdf_datetime_format.parse(date1, pos);
            Date dt2 = sdf_datetime_format.parse(date2, pos1);
            long l = dt1.getTime() - dt2.getTime();
            margin = (l / (24 * 60 * 60 * 1000.00));
            return margin;
        } catch (Exception e) {
            log.debug("DateUtil.getMargin():" + e.toString());
            return 0;
        }
    }

    /**
     * 比较两个日期相差的月数
     */
    public static int getMonthMargin(String date1, String date2) {
        int margin;
        try {
            margin = (Integer.parseInt(date2.substring(0, 4)) - Integer.parseInt(date1.substring(0, 4))) * 12;
            margin += (Integer.parseInt(date2.substring(4, 7).replaceAll("-0",
                    "-")) - Integer.parseInt(date1.substring(4, 7).replaceAll("-0", "-")));
            return margin;
        } catch (Exception e) {
            log.debug("DateUtil.getMargin():" + e.toString());
            return 0;
        }
    }


    /**
     * 返回日期加X天后的日期
     */
    public static String addDay(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.DATE, i);
            return sdf_date_format.format(gCal.getTime());
        } catch (Exception e) {
            log.debug("DateUtil.addDay():" + e.toString());
            return getDate();
        }
    }
    /**
     * @Description: 返回日期加n天后的日期
     * @Date:        11:38 2019/9/21
     * @Param:       [dateTime, n]
     * @return:      java.lang.String
     */
    public static Date addDay(Date dateTime,int n){
        if (n == 0){
            return dateTime;
        }

        //日期格式
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        java.util.Calendar calstart = java.util.Calendar.getInstance();
        calstart.setTime(dateTime);

        calstart.add(java.util.Calendar.DAY_OF_WEEK, n);

        //System.out.println(df.format(calstart.getTime()));
        //System.out.println(dd.format(calstart.getTime()));
        //return df.format(calstart.getTime());返回字符串

        return calstart.getTime();

    }



    /**
     * 返回日期加X月后的日期
     */
    public static String addMonth(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.MONTH, i);
            return sdf_date_format.format(gCal.getTime());
        } catch (Exception e) {
            log.debug("DateUtil.addMonth():" + e.toString());
            return getDate();
        }
    }

    /**
     * 返回日期加X年后的日期
     */
    public static String addYear(String date, int i) {
        try {
            GregorianCalendar gCal = new GregorianCalendar(
                    Integer.parseInt(date.substring(0, 4)),
                    Integer.parseInt(date.substring(5, 7)) - 1,
                    Integer.parseInt(date.substring(8, 10)));
            gCal.add(GregorianCalendar.YEAR, i);
            return sdf_date_format.format(gCal.getTime());
        } catch (Exception e) {
            log.debug("DateUtil.addYear():" + e.toString());
            return "";
        }
    }

    /**
     * 返回某年某月中的最大天
     * @author dylan_xu
     * @date Mar 11, 2012
     * @param iyear
     * @param imonth
     * @return
     */
    public static int getMaxDay(int iyear, int imonth) {
        int day = 0;
        try {
            if (imonth == 1 || imonth == 3 || imonth == 5 || imonth == 7
                    || imonth == 8 || imonth == 10 || imonth == 12) {
                day = 31;
            } else if (imonth == 4 || imonth == 6 || imonth == 9 || imonth == 11) {
                day = 30;
            } else if ((0 == (iyear % 4)) && (0 != (iyear % 100)) || (0 == (iyear % 400))) {
                day = 29;
            } else {
                day = 28;
            }
            return day;
        } catch (Exception e) {
            log.debug("DateUtil.getMonthDay():" + e.toString());
            return 1;
        }
    }

    /**
     * 格式化日期
     */
    @SuppressWarnings("static-access")
    public String rollDate(String orgDate, int Type, int Span) {
        try {
            String temp = "";
            int iyear, imonth, iday;
            int iPos = 0;
            char seperater = '-';
            if (orgDate == null || orgDate.length() < 6) {
                return "";
            }

            iPos = orgDate.indexOf(seperater);
            if (iPos > 0) {
                iyear = Integer.parseInt(orgDate.substring(0, iPos));
                temp = orgDate.substring(iPos + 1);
            } else {
                iyear = Integer.parseInt(orgDate.substring(0, 4));
                temp = orgDate.substring(4);
            }

            iPos = temp.indexOf(seperater);
            if (iPos > 0) {
                imonth = Integer.parseInt(temp.substring(0, iPos));
                temp = temp.substring(iPos + 1);
            } else {
                imonth = Integer.parseInt(temp.substring(0, 2));
                temp = temp.substring(2);
            }

            imonth--;
            if (imonth < 0 || imonth > 11) {
                imonth = 0;
            }

            iday = Integer.parseInt(temp);
            if (iday < 1 || iday > 31)
                iday = 1;

            Calendar orgcale = Calendar.getInstance();
            orgcale.set(iyear, imonth, iday);
            temp = this.rollDate(orgcale, Type, Span);
            return temp;
        } catch (Exception e) {
            return "";
        }
    }

    public static String rollDate(Calendar cal, int Type, int Span) {
        try {
            String temp = "";
            Calendar rolcale;
            rolcale = cal;
            rolcale.add(Type, Span);
            temp = sdf_date_format.format(rolcale.getTime());
            return temp;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 返回默认的日期格式
     */
    public static synchronized String getDatePattern() {
        defaultDatePattern = "yyyy-MM-dd";
        return defaultDatePattern;
    }

    /**
     * 将指定日期按默认格式进行格式代化成字符串后输出如：yyyy-MM-dd
     */
    public static final String getDate(Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";
        if (aDate != null) {
            df = new SimpleDateFormat(getDatePattern());
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * 取得给定日期的时间字符串，格式为当前默认时间格式
     */
    public static String getTimeNow(Date theTime) {
        return getDateTime(timePattern, theTime);
    }

    /**
     * 取得当前时间的Calendar日历对象
     * @author dylan_xu
     * @date Mar 11, 2012
     * @return
     * @throws ParseException
     */
    public Calendar getToday() throws ParseException {
        Date today = new Date();
        SimpleDateFormat df = new SimpleDateFormat(getDatePattern());
        String todayAsString = df.format(today);
        Calendar cal = new GregorianCalendar();
        cal.setTime(convertStringToDate(todayAsString));
        return cal;
    }

    /**
     * 将日期类转换成指定格式的字符串形式
     * @author dylan_xu
     * @date Mar 11, 2012
     * @param aMask
     * @param aDate
     * @return
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }
        return (returnValue);
    }

    /**
     * 将指定的日期转换成默认格式的字符串形式
     * @author dylan_xu
     * @date Mar 11, 2012
     * @param aDate
     * @return
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(getDatePattern(), aDate);
    }

    /**
     * 将日期字符串按指定格式转换成日期类型
     * @author dylan_xu
     * @date Mar 11, 2012
     * @param aMask 指定的日期格式，如:yyyy-MM-dd
     * @param strDate 待转换的日期字符串
     * @return
     * @throws ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '" + aMask + "'");
        }
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            log.error("ParseException: " + pe);
            throw pe;
        }
        return (date);
    }

    /**
     * 将日期字符串按默认格式转换成日期类型
     * @author dylan_xu
     * @date Mar 11, 2012
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + getDatePattern());
            }
            aDate = convertStringToDate(getDatePattern(), strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate + "' to a date, throwing exception");
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return aDate;
    }

    /**
     * 返回一个JAVA简单类型的日期字符串
     * @author dylan_xu
     * @date Mar 11, 2012
     * @return
     */
    public static String getSimpleDateFormat() {
        SimpleDateFormat formatter = new SimpleDateFormat();
        String NDateTime = formatter.format(new Date());
        return NDateTime;
    }

    /**
     * 将指定字符串格式的日期与当前时间比较
     * @author DYLAN
     * @date Feb 17, 2012
     * @param strDate 需要比较时间
     * @return
     *      <p>
     *      int code
     *      <ul>
     *      <li>-1 当前时间 < 比较时间 </li>
     *      <li> 0 当前时间 = 比较时间 </li>
     *      <li>>=1当前时间 > 比较时间 </li>
     *      </ul>
     *      </p>
     */
    public static boolean compareToCurTime (String strDate) {
        if (StringUtils.isBlank(strDate)) {
            return false;
        }
        Date curTime = cale.getTime();
        String strCurTime = null;
        try {
            strCurTime = sdf_datetime_format.format(curTime);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("[Could not format '" + strDate + "' to a date, throwing exception:" + e.getLocalizedMessage() + "]");
            }
        }
        if (StringUtils.isNotBlank(strCurTime)) {
            return strCurTime.compareTo(strDate)>=0;
        }
        return false;
    }


    /**
     * 为查询日期添加最小时间
     */
    @SuppressWarnings("deprecation")
    public static Date addStartTime(Date param) {
        Date date = param;
        try {
            date.setHours(0);
            date.setMinutes(0);
            date.setSeconds(0);
            return date;
        } catch (Exception ex) {
            return date;
        }
    }

    /**
     * 为查询日期添加最大时间
     */
    @SuppressWarnings("deprecation")
    public static Date addEndTime(Date param) {
        Date date = param;
        try {
            date.setHours(23);
            date.setMinutes(59);
            date.setSeconds(0);
            return date;
        } catch (Exception ex) {
            return date;
        }
    }

    /**
     * 返回系统现在年份中指定月份的天数
     */
    @SuppressWarnings("deprecation")
    public static String getMonthLastDay(int month) {
        Date date = new Date();
        int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
                { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
        int year = date.getYear() + 1900;
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return day[1][month] + "";
        } else {
            return day[0][month] + "";
        }
    }

    /**
     * 返回指定年份中指定月份的天数
     * @return 指定月的总天数
     */
    public static String getMonthLastDay(int year, int month) {
        int[][] day = { { 0, 30, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 },
                { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 } };
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return day[1][month] + "";
        } else {
            return day[0][month] + "";
        }
    }

    /**
     * 判断是平年还是闰年
     * @param year
     * @return
     */
    public static boolean isLeapyear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || (year % 400) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 取得当前时间的日戳
     */
    //@SuppressWarnings("deprecation")
    public static String getTimestamp() {
        Date date = cale.getTime();
        String timestamp = "" + (date.getYear() + 1900) + date.getMonth()
                + date.getDate() + date.getMinutes() + date.getSeconds()
                + date.getTime();
        return timestamp;
    }

    /**
     * 取得指定时间的日戳
     */
    @SuppressWarnings("deprecation")
    public static String getTimestamp(Date date) {
        String timestamp = "" + (date.getYear() + 1900) + date.getMonth()
                + date.getDate() + date.getMinutes() + date.getSeconds()
                + date.getTime();
        return timestamp;
    }



    public static String getWeekdayByDate(Date date) {
        String str = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int weekDay = cal.get(Calendar.DAY_OF_WEEK);

        switch (weekDay) {
            case 1:
                str = "SUNDAY";
                break;
            case 2:
                str = "MONDAY";
                break;
            case 3:
                str = "TUESDAY";
                break;
            case 4:
                str = "WEDNESDAY";
                break;
            case 5:
                str = "THURSDAY";
                break;
            case 6:
                str = "FRIDAY";
                break;
            case 7:
                str = "SATURDAY";
                break;
            default:
                break;
        }

        return str;
    }

    /**
     * @Description: 计算分钟差
     * @Date:        22:23 2019/11/12
     * @Param:       [fromDate, toDate]
     * @return:      int
     */
    public static Long getMinuteDifference(String fromDate,String toDate) throws ParseException{
        SimpleDateFormat simpleFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long from = simpleFormat.parse(fromDate).getTime();
        long to = simpleFormat.parse(toDate).getTime();
        return ((to - from)/(1000 * 60));
    }

    /**
     * 解析日期字符串，如："发布日期：2018-05-31"、"2018-08-13 07:44 来源： 经济日报"、 "2018年08月15日 07:13:30 参考消息"	等等
     *
     * @param dateStr 返回解析好的 日期对象，解析失败时，返回 null
     * @return
     */
    public static Date parseDateStr(String dateStr) {
        if (dateStr == null || "".equals(dateStr)) {
            return null;
        }
        /**
         * dateRegexYMDHMS：针对 年月日 时分秒
         * dateRegexYMDHM：针对 年月日 时分
         * dateRegexYMDH：针对 年月日 时
         * dateRegexYMDH：针对 年月日
         */
        String dateRegexYMDHMS = "([1-2][0-9]{3})[^0-9]{1,5}?([0-1]?[0-9])[^0-9]{1,5}?([0-9]{1,2})[^0-9]{1,5}?([0-2]?[1-9])[^0-9]{1,5}?([0-9]{1,2})[^0-9]{1,5}?([0-9]{1,2})";
        String dateRegexYMDHM = "([1-2][0-9]{3})[^0-9]{1,5}?([0-1]?[0-9])[^0-9]{1,5}?([0-9]{1,2})[^0-9]{1,5}?([0-2]?[1-9])[^0-9]{1,5}?([0-9]{1,2})";
        String dateRegexYMDH = "([1-2][0-9]{3})[^0-9]{1,5}?([0-1]?[0-9])[^0-9]{1,5}?([0-9]{1,2})[^0-9]{1,5}?([0-2]?[1-9])";
        String dateRegexYMD = "([1-2][0-9]{3})[^0-9]{1,5}?([0-1]?[0-9])[^0-9]{1,5}?([0-9]{1,2})";

        Pattern pattern = Pattern.compile(dateRegexYMDHMS);
        Matcher matcher = pattern.matcher(dateStr);

        StringBuffer dateSourceBf = new StringBuffer();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            if (matcher.find()) {
                /**年月日 时分秒*/
                dateSourceBf.append(matcher.group(1)).append("-");
                dateSourceBf.append(matcher.group(2).length() == 1 ? "0" + matcher.group(2) : matcher.group(2)).append("-");
                dateSourceBf.append(matcher.group(3).length() == 1 ? "0" + matcher.group(3) : matcher.group(3)).append(" ");
                dateSourceBf.append(matcher.group(4).length() == 1 ? "0" + matcher.group(4) : matcher.group(4)).append(":");
                dateSourceBf.append(matcher.group(5).length() == 1 ? "0" + matcher.group(5) : matcher.group(5)).append(":");
                dateSourceBf.append(matcher.group(6).length() == 1 ? "0" + matcher.group(6) : matcher.group(6));
                Date date = simpleDateFormat.parse(dateSourceBf.toString());
                return date;
            }
            if (dateSourceBf.length() <= 1) {
                /**年月日 时分*/
                pattern = Pattern.compile(dateRegexYMDHM);
                matcher = pattern.matcher(dateStr);
                if (matcher.find()) {
                    dateSourceBf.append(matcher.group(1)).append("-");
                    dateSourceBf.append(matcher.group(2).length() == 1 ? "0" + matcher.group(2) : matcher.group(2)).append("-");
                    dateSourceBf.append(matcher.group(3).length() == 1 ? "0" + matcher.group(3) : matcher.group(3)).append(" ");
                    dateSourceBf.append(matcher.group(4).length() == 1 ? "0" + matcher.group(4) : matcher.group(4)).append(":");
                    dateSourceBf.append(matcher.group(5).length() == 1 ? "0" + matcher.group(5) : matcher.group(5)).append(":00");
                    Date date = simpleDateFormat.parse(dateSourceBf.toString());
                    return date;
                }
            }
            if (dateSourceBf.length() <= 1) {
                /**年月日 时*/
                pattern = Pattern.compile(dateRegexYMDH);
                matcher = pattern.matcher(dateStr);
                if (matcher.find()) {
                    dateSourceBf.append(matcher.group(1)).append("-");
                    dateSourceBf.append(matcher.group(2).length() == 1 ? "0" + matcher.group(2) : matcher.group(2)).append("-");
                    dateSourceBf.append(matcher.group(3).length() == 1 ? "0" + matcher.group(3) : matcher.group(3)).append(" ");
                    dateSourceBf.append(matcher.group(4).length() == 1 ? "0" + matcher.group(4) : matcher.group(4)).append(":00:00");
                    Date date = simpleDateFormat.parse(dateSourceBf.toString());
                    return date;
                }
            }
            if (dateSourceBf.length() <= 1) {
                /**年月日*/
                pattern = Pattern.compile(dateRegexYMD);
                matcher = pattern.matcher(dateStr);
                if (matcher.find()) {
                    dateSourceBf.append(matcher.group(1)).append("-");
                    dateSourceBf.append(matcher.group(2).length() == 1 ? "0" + matcher.group(2) : matcher.group(2)).append("-");
                    dateSourceBf.append(matcher.group(3).length() == 1 ? "0" + matcher.group(3) : matcher.group(3)).append(" 00:00:00");
                    Date date = simpleDateFormat.parse(dateSourceBf.toString());
                    return date;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: 比较时间不比较日期，time1 > time2 返回true，<=返回false
     * @Date:        15:58 2019/9/5
     * @Param:       [time1, time2]
     * @return:      boolean
     */
    public static boolean compTime(Date time1, Date time2) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String s1 = formatter.format(time1);
            String s2 = formatter.format(time2);
            if (s1.indexOf(":") < 0 || s1.indexOf(":") < 0) {
                System.out.println("格式不正确");
            } else {
                String[] array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60 + Integer.valueOf(array1[2]);
                String[] array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60 + Integer.valueOf(array2[2]);
                return total1 - total2 >= 0 ? true : false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return true;
        }
        return false;
    }

    public static boolean compTime(String s1, String s2) {
        try {
            if (s1.indexOf(":") < 0 || s1.indexOf(":") < 0) {
                System.out.println("格式不正确");
            } else {
                String[] array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60 + Integer.valueOf(array1[2]);
                String[] array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60 + Integer.valueOf(array2[2]);
                return total1 - total2 >= 0 ? true : false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return true;
        }
        return false;
    }
}
