package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;

/**
 * Created by nisheeth on 01/11/15.
 */
public class Route extends BaseLogging{

    private String routeNo;
    private String routeName;
    private String accountName;
    private String accountId;
    private double income;
    private double trafficFines;
    private String interval_Income;
    private String income_status;

    public Route(){
        super();
    }

    public Route(ParseObject obj){
        super(obj);
        this.setRouteNo(obj.getString("ROUTENO"));
        this.setRouteName(obj.getString("ROUTENAME"));
        this.setAccountName(obj.getString("ACCOUNTNAME"));
        this.setAccountId(obj.getString("ACCOUNTID"));
        this.setUser(obj.getParseUser("USER"));
        this.setIncome_status(obj.getString("INCOMESTATUS"));
        this.setIncome(obj.getDouble("INCOME"));
        this.setTrafficFines(obj.getDouble("TRAFFIC_FINE"));
        this.setInterval_Income(obj.getString("INTERVALINCOME"));
    }


    public double getTrafficFines() {
        return trafficFines;
    }

    public void setTrafficFines(double trafficFines) {
        this.trafficFines = trafficFines;
    }

    public String getInterval_Income() {
        return interval_Income;
    }

    public void setInterval_Income(String interval_Income) {
        this.interval_Income = interval_Income;
    }

    public String getIncome_status() {
        return income_status;
    }

    public void setIncome_status(String income_status) {
        this.income_status = income_status;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public double getRevenue(){
        return getIncome() - getTrafficFines();
    }

    @Override
    public BaseLogging getPojoObject(ParseObject obj) throws JSONException {
        //this.setAccount(obj.getString("ACCOUNT"));
       // this.setRoute(obj.getString("ROUTENO"));
        //this.setUser(obj.getString("USER"));
        this.setIncome_status(obj.getString("INCOMESTATUS"));
        this.setIncome(obj.getDouble("INCOME"));
        //this.setToll(obj.getString("TOLL"));
        this.setTrafficFines(obj.getDouble("TRAFFIC_FINE"));
        //this.setLate_fines(obj.getString("LATEFINE"));
        this.setInterval_Income(obj.getString("INTERVALINCOME"));
       // this.setCreatedOn(obj.getString("CREATEDON"));
        return this;
    }

    @Override
    protected void setUpJsonData() {
        parseObject = new ParseObject(EasyOpsUtil.COLLECTION_NAME.ROUTE.toString());
        parseObject.put("ROUTENO", this.getRouteNo());
        parseObject.put("ROUTENAME",this.getRouteName());
        parseObject.put("ACCOUNTID", this.getAccountId());
        parseObject.put("ACCOUNTNAME",this.getAccountName());
        parseObject.put("INCOME", this.getIncome());
        parseObject.put("TRAFFIC_FINE", this.getTrafficFines());
        parseObject.put("INTERVALINCOME", this.getInterval_Income());
        parseObject.put("INCOMESTATUS", this.getIncome_status());
    }

}
