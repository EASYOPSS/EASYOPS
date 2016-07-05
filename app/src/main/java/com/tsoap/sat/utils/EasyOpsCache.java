package com.tsoap.sat.utils;

import android.content.Context;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tsoap.sat.businessobject.Account;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.ExpenseCategory;
import com.tsoap.sat.businessobject.Overview;
import com.tsoap.sat.businessobject.UserProfile;
import com.tsoap.sat.dbinterface.base.DBManager;
import com.tsoap.sat.easyops.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nisheeth on 04/11/15.
 */
public class EasyOpsCache {

    private static EasyOpsCache instance;

    Calendar cal = Calendar.getInstance();
    int month;
    int year;

    private Context ctx;

    public Overview getCachedOverview() {
        return cachedOverview;
    }

    public void setCachedOverview(Overview cachedOverview) {
        this.cachedOverview = cachedOverview;
    }

    private Overview cachedOverview;

    private EasyOpsCache(Context context){
        ctx = context;
        populateColorCode();
    }

    private void populateColorCode() {

        colorcodeMap.put("deepOrange700", R.drawable.deeporange700);
        colorcodeMap.put("deeporange200",R.drawable.deeporange200);
        colorcodeMap.put("orange700",R.drawable.orange700);
        colorcodeMap.put("orange200",R.drawable.orange200);
        colorcodeMap.put("red900",R.drawable.red900);
        colorcodeMap.put("red500", R.drawable.red500);
        colorcodeMap.put("red300", R.drawable.red200);
        colorcodeMap.put("yellow700", R.drawable.yellow700);
        colorcodeMap.put("yellow200", R.drawable.yellow200);
        colorcodeMap.put("indigo700",R.drawable.indigo700);
        colorcodeMap.put("indigo200", R.drawable.indigo200);
        colorcodeMap.put("blue700", R.drawable.blue700);
        colorcodeMap.put("blue200", R.drawable.blue200);
        colorcodeMap.put("bluegrey700", R.drawable.bluegrey700);
        colorcodeMap.put("bluegrey200", R.drawable.bluegrey200);
        colorcodeMap.put("pink700", R.drawable.pink700);
        colorcodeMap.put("pink200", R.drawable.pink200);
        colorcodeMap.put("green700", R.drawable.green700);
        colorcodeMap.put("green200", R.drawable.green200);

    }

    private  Map<String,ExpenseCategory> expenseCategories = new HashMap<String,ExpenseCategory>(20);

    private  Map<String,Account> accountMap = new HashMap<String,Account>(10);

    private static ParseUser user = null;

    private List<String> colorCodeList = new ArrayList<String>();

    private Map<String,Integer> colorcodeMap = new HashMap<String,Integer>();

    public static EasyOpsCache getInstance(Context context){
        if(instance == null){
            synchronized (EasyOpsCache.class){
                if(instance == null){
                    instance = new EasyOpsCache(context);
                }
            }
        }
        return instance;
    }



    List<BaseLogging> mList = new ArrayList<>();

    public Map<String, ExpenseCategory> getExpenseCategories() {
        return expenseCategories;
    }

    public void setExpenseCategories(Map<String, ExpenseCategory> expenseCategories) {
        this.expenseCategories = expenseCategories;
    }

    public Map<String, Account> getAccountMap() {
        return accountMap;
    }

    public void setAccountMap(Map<String, Account> accountMap) {
        this.accountMap = accountMap;
    }

    public static ParseUser getUser() {
        return user;
    }

    public static void setUser(ParseUser User) {
        user = User;
    }

    public List<ExpenseCategory> getExpenseCategoryList(){

        List<ExpenseCategory> expensesCategoryList = new ArrayList<ExpenseCategory>(expenseCategories.values());
        Collections.sort(expensesCategoryList, new Comparator<ExpenseCategory>() {
            @Override
            public int compare(ExpenseCategory obj, ExpenseCategory obj2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(obj.getExpenseCategory(), obj2.getExpenseCategory());
                if (res == 0) {
                    res = obj.getExpenseCategory().compareTo(obj2.getExpenseCategory());
                }
                return res;
            }
        });

        return expensesCategoryList;
    }

    public List<Account> getAccountList(){
        List<Account> accountList = new ArrayList<Account>(accountMap.values());
        Collections.sort(accountList, new Comparator<Account>() {
            @Override
            public int compare(Account obj1, Account obj2) {
                int res = String.CASE_INSENSITIVE_ORDER.compare(obj1.getAccountName(), obj2.getAccountName());
                if (res == 0) {
                    res = obj1.getAccountName().compareTo(obj2.getAccountName());
                }
                return res;
            }
        });

        return accountList;
    }


    public List<String> getColorCodeList(){
        return colorCodeList;
    }

    public Map<String,Integer> getColorCodeMap(){
        return colorcodeMap;
    }


    public void insertExpenseCategoryData() {
        List<ExpenseCategory> expenseCategoryList = new ArrayList<ExpenseCategory>();

        ExpenseCategory e = new ExpenseCategory();
        e.setExpenseCategory("TRAFFIC RULE FINES");
        //e.setUser(user);
        e.setColorcode("deepOrange700");
        expenseCategoryList.add(e);

        ExpenseCategory e1 = new ExpenseCategory();
        e1.setColorcode("red900");
        //e1.setUser(user);
        e1.setExpenseCategory("TOLL");
        expenseCategoryList.add(e1);

        ExpenseCategory e2 = new ExpenseCategory();
        e2.setExpenseCategory("PERSONAL EXPENSE");
        //e2.setUser(user);
        e2.setColorcode("blue700");
        expenseCategoryList.add(e2);

        ExpenseCategory e3 = new ExpenseCategory();
        e3.setColorcode("red500");
        //e3.setUser(user);
        e3.setExpenseCategory("FUEL EXPENSE");
        expenseCategoryList.add(e3);

        ExpenseCategory e4 = new ExpenseCategory();
        e4.setExpenseCategory("VEHICLE SERVICING");
        e4.setColorcode("bluegrey700");
        //e4.setUser(user);
        expenseCategoryList.add(e4);

        ExpenseCategory e5 =new ExpenseCategory();
        e5.setExpenseCategory("ACCIDENTAL REPAIR");
        //e5.setUser(user);
        e5.setColorcode("indigo700");
        expenseCategoryList.add(e5);

        ExpenseCategory e6 =new ExpenseCategory();
        e6.setExpenseCategory("LATE PANELTY OR ABSENTISM");
        //e6.setUser(user);
        e6.setColorcode("green700");
        expenseCategoryList.add(e6);

        ExpenseCategory e7 =new ExpenseCategory();
        e7.setExpenseCategory("OTHER EXPENSES");
        //e7.setUser(user.getVehicleNumber());
        e7.setColorcode("pink700");
        expenseCategoryList.add(e7);


        for(ExpenseCategory expenseCategory : expenseCategoryList){
            expenseCategories.put(expenseCategory.getExpenseCategory(), expenseCategory);
            /*try {
                DBManager.getInstance(ctx).insertDataIntoTable(EasyOpsUtil.COLLECTION_NAME.EXPENSECATEGORY,expenseCategory.getParseObject());
            } catch (JSONException ex) {
                Log.e(DBManager.class.getName(), "inside expenseCategory insert Table" + ex.getMessage());
            }*/
        }

        ParseQuery query = new ParseQuery(EasyOpsUtil.COLLECTION_NAME.EXPENSE_CATEGORY.toString());
        query.whereEqualTo("USER", ParseUser.getCurrentUser());
        try {
            List<ParseObject> list = query.find();
            for(ParseObject obj:list){
                ExpenseCategory ec = new ExpenseCategory(obj);
                expenseCategories.put(ec.getExpenseCategory(),ec);
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }



    }

    public void insertAccountData() {
        List<Account> expenseList = new ArrayList<Account>();

        Account e = new Account();
        e.setAccountName("UBER");
        //e.setUser(user);
        e.setColorCode("indigo700");
        e.setOrganisation_type("AGGREGATORS");
        e.setWork_type("POINT TO POINT TRIPS");
        expenseList.add(e);

        Account e1 = new Account();
        e1.setAccountName("OLA");
        //e1.setUser(user);
        e1.setColorCode("green700");
        e1.setOrganisation_type("AGGREGATORS");
        e1.setWork_type("POINT TO POINT TRIPS");
        expenseList.add(e1);

        Account e2 = new Account();
        e2.setAccountName("TAXI FOR SURE");
        //e2.setUser(user.getVehicleNumber());
        e2.setColorCode("orange700");
        e2.setOrganisation_type("AGGREGATORS");
        e2.setWork_type("POINT TO POINT TRIPS");
        expenseList.add(e2);


        Account e3 = new Account();
        e3.setAccountName("MERU");
        //e3.setUser(user.getVehicleNumber());
        e3.setColorCode("pink700");
        e3.setOrganisation_type("AGGREGATORS");
        e3.setWork_type("POINT TO POINT TRIPS");
        expenseList.add(e3);

        Account e4 = new Account();
        e4.setAccountName("EASY CABS");
        //e4.setUser(user.getVehicleNumber());
        e4.setColorCode("yellow700");
        e4.setOrganisation_type("AGGREGATORS");
        e4.setWork_type("POINT TO POINT TRIPS");
        expenseList.add(e4);



        for(Account account:expenseList){
            accountMap.put(account.getAccountName(),account);
        }

        ParseQuery query = new ParseQuery(EasyOpsUtil.COLLECTION_NAME.ACCOUNT.toString());
        query.whereEqualTo("USER", ParseUser.getCurrentUser());
        try {
            List<ParseObject> list = query.find();
            for(ParseObject obj:list){
                Account acc = new Account(obj);
                accountMap.put(acc.getAccountName(),acc);
            }
        } catch (ParseException e5) {
            e5.printStackTrace();
        }


    }


    public void prepareCurrentMonthData() throws ParseException {
        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);

        List<BaseLogging> mIncomeList = DBManager.getInstance(ctx).getCurrentMonthData(EasyOpsUtil.loggingEnum.ROUTE,month,year);
        List<BaseLogging> mExpenseList = DBManager.getInstance(ctx).getCurrentMonthData(EasyOpsUtil.loggingEnum.EXPENSE,month,year);
        Overview overview = new Overview(mExpenseList,mIncomeList);
        setCachedOverview(overview);
    }

   /* public void initCache(String userId) throws JSOquNException {
        synchronized (this) {
            //ArrayList<Storage.JSONDocument> list = DBManager.getInstance(ctx).getDataByUser(EasyOpsUtil.COLLECTION_NAME.EXPENSECATEGORY, userId);
            Log.d(DBManager.class.getName(), "inside init EasyOpsCache EXPENSE DATA" + list.size());

            for(Storage.JSONDocument obj : list){
                JSONObject json = new JSONObject(obj.getJsonDoc());
                ExpenseCategory expense = (ExpenseCategory) new ExpenseCategory().getPojoObject(json);
                expenseCategories.put(expense.getExpenseCategory(), expense);
                Log.d(DBManager.class.getName(), "inside init EasyOpsCache EXPENSE DATA" + expense.getExpenseCategory());

            }




            ArrayList<Storage.JSONDocument> list2 = (DBManager.getInstance(ctx).getDataByUser(EasyOpsUtil.COLLECTION_NAME.ACCOUNT, userId));
            Log.d(DBManager.class.getName(), "inside init EasyOpsCache account DATA" + list.size());

            for(Storage.JSONDocument obj : list2){
                JSONObject json = new JSONObject(obj.getJsonDoc());
                Account account = (Account) new Account().getPojoObject(json);
                accountMap.put(account.getAccountName(), account);
                Log.d(DBManager.class.getName(), "inside init EasyOpsCache EXPENSE DATA" + account.getAccountName());

            }

            Log.d(DBManager.class.getName(), "inside init EasyOpsCache : completed");
        }

    }

    public void initCacheforRegister(String userId) throws JSONException {
        synchronized (this) {
            ArrayList<Storage.JSONDocument> list = DBManager.getInstance(ctx).getDataByUserforRegister(EasyOpsUtil.COLLECTION_NAME.EXPENSECATEGORY, userId);
            Log.d(DBManager.class.getName(), "inside init EasyOpsCache EXPENSE DATA" + list.size());

            for(Storage.JSONDocument obj : list){
                JSONObject json = new JSONObject(obj.getJsonDoc());
                ExpenseCategory expense = (ExpenseCategory) new ExpenseCategory().getPojoObject(json);
                expenseCategories.put(expense.getExpenseCategory(), expense);
                Log.d(DBManager.class.getName(), "inside init EasyOpsCache EXPENSE DATA" + expense.getExpenseCategory());

            }




            ArrayList<Storage.JSONDocument> list2 = (DBManager.getInstance(ctx).getDataByUserforRegister(EasyOpsUtil.COLLECTION_NAME.ACCOUNT, userId));
            Log.d(DBManager.class.getName(), "inside init EasyOpsCache account DATA" + list.size());

            for(Storage.JSONDocument obj : list2){
                JSONObject json = new JSONObject(obj.getJsonDoc());
                Account account = (Account) new Account().getPojoObject(json);
                accountMap.put(account.getAccountName(), account);
                Log.d(DBManager.class.getName(), "inside init EasyOpsCache EXPENSE DATA" + account.getAccountName());

            }

            Log.d(DBManager.class.getName(), "inside init EasyOpsCache : completed");
        }

    }*/




}
