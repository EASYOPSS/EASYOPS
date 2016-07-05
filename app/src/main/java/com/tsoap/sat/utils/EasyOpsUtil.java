package com.tsoap.sat.utils;

import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Expense;
import com.tsoap.sat.businessobject.Overview;
import com.tsoap.sat.businessobject.Route;
import com.tsoap.sat.businessobject.header;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nisheeth on 29/10/15.
 */
public class EasyOpsUtil {

    static Calendar cal = Calendar.getInstance();
    static SimpleDateFormat dateformat3 = new SimpleDateFormat("yyyyMMdd");
    static Date date = new Date();

    public static enum UserTypeEnum
    {
        NEW_USER,EXISTING_USER
    }

    public static enum loggingEnum
    {
        ROUTE,EXPENSE,EFFICIENCY,TODOTASK,ACCOUNT,EXPENSE_CATEGORY,COLOR_CODE
    }

    public static enum MONTH
    {
        JANUARY,FEBUARY,MARCH,APRIL,MAY,JUNE,JULY,AUGUST,SEPTEMBER,OCTOBER,NOVEMBER,DECEMBER
    }


    public static enum WEEKDAY
    {
        SUNDAY,MONDAY,TUESDAY,WEDNESDAY,THURDAY,FRIDAY,SATURDAY
    }

    public static enum WEEK
    {
        FIRST_WEEK,SECOND_WEEK,THIRD_WEEK,FORTH_WEEK,FIFTH_WEEK
    }

    public static enum COLLECTION_NAME{
        USER,EXPENSE,ROUTE,ROUTE_DETAILS,EFFICIENCY,EXPENSE_CATEGORY,ACCOUNT
    }

    public static List getDataForStickyHeaderAdapter(List<BaseLogging> list) throws ParseException {
        Map<String,String> map = new HashMap<String,String>();

        List<BaseLogging> mList = new ArrayList<BaseLogging>();
        int counter = -1;
        int sectionFirstPos = 0;

        for(BaseLogging b: list){
            date = dateformat3.parse(b.getCreatedOn() + "");
            cal.setTime(date);
            String key = cal.get(Calendar.DAY_OF_MONTH)+", "+getWeekDay(cal.get(Calendar.DAY_OF_WEEK));

            if(map.containsKey(key)){
                b.setSectionFirstPosition(sectionFirstPos);
                mList.add(b);
                counter=counter+1;
            }else{
                if(counter != -1){
                    sectionFirstPos = counter;
                }
                map.put(key,"1");
                header h = new header();
                h.setHeaderTitle(key);
                h.setIsHeader(true);
                h.setSectionFirstPosition(sectionFirstPos);
                mList.add(h);
                b.setSectionFirstPosition(sectionFirstPos);
                mList.add(b);
                counter = counter+2;
            }

        }
        return mList;
    }


    private static String getWeekDay(int i){
        switch(i){
            case 1:
                return "SUNDAY";
            case 2:
                return "MONDAY";
            case 3:
                return "TUESDAY";
            case 4:
                return "WEDNESDAY";
            case 5:
                return "THURSDAY";
            case 6:
                return "FRIDAY";
            case 7:
                return "SATURDAY";
        }
        return "";
    }

    public enum NavigationEnum {
        ACCOUNT,
        ROUTE,
        EXPENSE,
        CATEGORIES,
        TASK,
        CHARTS,
        OVERVIEW,
        CALENDAR,
        SUMMARY,
        BLANK,
        PAYMENTS,
        SETTINGS,
        CONTACT
    }


    public static String setMonth(int month){
        String strMonth = null;
        switch (month){
            case 1:
                strMonth = EasyOpsUtil.MONTH.JANUARY.toString();
                break;
            case 2:
                strMonth = EasyOpsUtil.MONTH.FEBUARY.toString();
                break;
            case 3:
                strMonth = EasyOpsUtil.MONTH.MARCH.toString();
                break;
            case 4:
                strMonth = EasyOpsUtil.MONTH.APRIL.toString();
                break;
            case 5:
                strMonth = EasyOpsUtil.MONTH.MAY.toString();
                break;
            case 6:
                strMonth = EasyOpsUtil.MONTH.JUNE.toString();
                break;
            case 7:
                strMonth = EasyOpsUtil.MONTH.JULY.toString();
                break;
            case 8:
                strMonth = EasyOpsUtil.MONTH.AUGUST.toString();
                break;
            case 9:
                strMonth = EasyOpsUtil.MONTH.SEPTEMBER.toString();
                break;
            case 10:
                strMonth = EasyOpsUtil.MONTH.OCTOBER.toString();
                break;
            case 11:
                strMonth = EasyOpsUtil.MONTH.NOVEMBER.toString();
                break;
            case 12:
                strMonth = EasyOpsUtil.MONTH.DECEMBER.toString();
                break;
        }
        return strMonth;
    }


}
