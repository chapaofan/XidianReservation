package com.xidian.reservation.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：Maolin
 * @className ：String2DateUtils
 * @date ：Created in 2019/9/1 12:34
 * @description： 时间转换工具
 * @version: 1.0
 */
public class String2DateUtils {
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
