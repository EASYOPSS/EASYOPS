package com.tsoap.sat.businessobject;

import com.tsoap.sat.easyops.Dashboard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nisheeth on 04/11/15.
 */
public class Overview {

    private double income;
    private double expense;
    private int routes;
    private int distance;
    private Map<String,Double> expenseListPerCategory = new HashMap<String,Double>();

    public Overview(List<BaseLogging> expenseList,List<BaseLogging> routeList){
        initiateExpensePerCategoryList();
        initiateRoutePerAccountList();
        populateExpensesPerCategory(expenseList);
        populateRoutePerAccount(routeList);

    }

    private void initiateExpensePerCategoryList() {
        expenseListPerCategory.put("TRAFFIC RULE FINES",0.0);
        expenseListPerCategory.put("TOLL",0.0);
        expenseListPerCategory.put("PERSONAL EXPENSE",0.0);
        expenseListPerCategory.put("FUEL EXPENSE",0.0);
        expenseListPerCategory.put("VEHICLE SERVICING",0.0);
        expenseListPerCategory.put("ACCIDENTAL REPAIR",0.0);
        expenseListPerCategory.put("LATE PANELTY OR ABSENTISM",0.0);
        expenseListPerCategory.put("OTHER EXPENSES",0.0);
    }

    private void initiateRoutePerAccountList() {
        incomePerAccount.put("OLA",0.0);
        incomePerAccount.put("UBER",0.0);
        incomePerAccount.put("TAXI FOR SURE",0.0);
        incomePerAccount.put("MERU",0.0);
        incomePerAccount.put("EASY CABS",0.0);
    }

    public Map<String, Double> getIncomePerAccount() {
        return incomePerAccount;
    }

    public void setIncomePerAccount(Map<String, Double> incomePerAccount) {
        this.incomePerAccount = incomePerAccount;
    }

    public Map<String, Double> getExpenseListPerCategory() {
        return expenseListPerCategory;
    }

    public void setExpenseListPerCategory(Map<String, Double> expenseListPerCategory) {
        this.expenseListPerCategory = expenseListPerCategory;
    }

    private Map<String,Double> incomePerAccount = new HashMap<String,Double>();


    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public int getRoutes() {
        return routes;
    }

    public void setRoutes(int routes) {
        this.routes = routes;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    private void populateExpensesPerCategory(List<BaseLogging> list){
        for(BaseLogging b: list ){
            Expense e = (Expense)b;
            expense = expense + e.getExpenseAmount();
            if(getExpenseListPerCategory().containsKey(e.getExpenseCategory())){
                double d = getExpenseListPerCategory().get(e.getExpenseCategory());
                d = d+e.getExpenseAmount();
                getExpenseListPerCategory().put(e.getExpenseCategory(),d);
            }else{
                getExpenseListPerCategory().put(e.getExpenseCategory(),e.getExpenseAmount());
            }
        }
    }

    private void populateRoutePerAccount(List<BaseLogging> list){
        routes = list.size();
        for(BaseLogging b: list ){
            Route r = (Route)b;
            income = income+r.getIncome();
            if(getIncomePerAccount().containsKey(r.getAccountName())){
                double d = getIncomePerAccount().get(r.getAccountName());
                d = d+r.getIncome();
                getIncomePerAccount().put(r.getAccountName(),d);
            }else{
                getIncomePerAccount().put(r.getAccountName(),r.getIncome());
            }
        }
    }
}
