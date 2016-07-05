package com.tsoap.sat.dbinterface.base;

import android.content.Context;

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Expense;
import com.tsoap.sat.businessobject.Route;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by nisheeth on 07/12/15.
 */
public class DBManager {

    SimpleDateFormat dateformat3;
    Calendar cal;
    {
        dateformat3 = new SimpleDateFormat("yyyyMMdd");
        cal = Calendar.getInstance();
    }

    String startDate;
    String endDate;

    private static DBManager instance;

    private Context ctx;

    private DBManager(Context context){
        ctx = context;
    }


    public static DBManager getInstance(Context context){
        if(instance == null){
            synchronized (DBManager.class){
                if(instance == null){
                    instance = new DBManager(context);
                }
            }
        }
        return instance;
    }

    public List<BaseLogging> getCurrentMonthData(EasyOpsUtil.loggingEnum table, int month, int year) throws com.parse.ParseException {
        calculateInterval(month, year);
        ParseQuery<ParseObject> query = ParseQuery.getQuery(table.toString());
        query.whereGreaterThanOrEqualTo("CREATEDON", Integer.parseInt(startDate));

        //ParseQuery<ParseObject> endDateQuery = ParseQuery.getQuery(table.toString());
        query.whereLessThanOrEqualTo("CREATEDON", Integer.parseInt(endDate));

        //List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
       // queries.add(startDateQuery);
        //queries.add(endDateQuery);
        //ParseQuery<ParseObject> mainQuery = ParseQuery.or(queries);
        List<BaseLogging> mList = new ArrayList<BaseLogging>();
        List<ParseObject> list = query.find();
        for(ParseObject object: list){
            mList.add(getObj(table,object));
        }
        return mList;
    }

    private BaseLogging getObj(EasyOpsUtil.loggingEnum table, ParseObject object) {
        switch (table){
            case EXPENSE:
                return new Expense(object);
            case ROUTE:
                return new Route(object);
        }
        return null;
    }

    private void calculateInterval(int m,int year) {
        String month ;
       if(m < 9){
           month = "0" + m;
       }else{
        month = ""+ m;
       }
        startDate = year + month+"01";
        endDate = year + month + "31";

    }


    public List<BaseLogging> getDataByDate(EasyOpsUtil.loggingEnum table, int m, int year, int d) throws com.parse.ParseException {

        String month;
        String day;
        if(m < 9){
            month = "0" + m;
        }else{
            month = ""+ m;
        }
        if(d < 9){
            day = "0" + d;
        }else{
            day = ""+ d;
        }
        String date = year+month+day;
        ParseQuery<ParseObject > query = ParseQuery.getQuery(table.toString());
        query.whereEqualTo("CREATEDON", Integer.parseInt(date));
        List<BaseLogging> mList = new ArrayList<BaseLogging>();
        List<ParseObject> list = query.find();
        for(ParseObject object: list){
            mList.add(getObj(table,object));
        }
        return mList;
    }
}
