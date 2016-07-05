package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by @AMANBHARDWAJ on 01/11/15.
 */
public class RouteDetails extends BaseLogging{


    private String routeNo;
    private String source;
    private String destination;
    private String avgdistance;
    private String avgTime;
    private String trafficDensity;
    private String peakhour_demand;
    private String isHotZone;

    public RouteDetails(){
        super();
    }

    RouteDetails(ParseObject obj){
        super(obj);
        this.setDestination(obj.getString("DESTINATION"));
        this.setSource(obj.getString("SOURCE"));
        this.setRouteNo(obj.getString("ROUTENO"));
        this.setAvgTime(obj.getString("AVGTIME"));
        this.setAvgdistance(obj.getString("AVGDISTANCE"));
        this.setPeakhour_demand(obj.getString("PEAKHOURS"));
        this.setIsHotZone(obj.getString("ISHOTZONE"));
        this.setIsExpensiveRoute(obj.getString("ISEXPENSIVEROUTE"));
        this.setTrafficDensity(obj.getString("TRAFFICDENSITY"));
    }

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getIsExpensiveRoute() {
        return isExpensiveRoute;
    }

    public void setIsExpensiveRoute(String isExpensiveRoute) {
        this.isExpensiveRoute = isExpensiveRoute;
    }

    public String getIsHotZone() {
        return isHotZone;
    }

    public void setIsHotZone(String isHotZone) {
        this.isHotZone = isHotZone;
    }

    public String getPeakhour_demand() {
        return peakhour_demand;
    }

    public void setPeakhour_demand(String peakhour_demand) {
        this.peakhour_demand = peakhour_demand;
    }

    public String getTrafficDensity() {
        return trafficDensity;
    }

    public void setTrafficDensity(String trafficDensity) {
        this.trafficDensity = trafficDensity;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public String getAvgdistance() {
        return avgdistance;
    }

    public void setAvgdistance(String avgdistance) {
        this.avgdistance = avgdistance;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    private String isExpensiveRoute;



    @Override
    public BaseLogging getPojoObject(ParseObject obj) {
        this.setDestination(obj.getString("DESTINATION"));
        this.setSource(obj.getString("SOURCE"));
        this.setRouteNo(obj.getString("ROUTENO"));
        this.setAvgTime(obj.getString("AVGTIME"));
        this.setAvgdistance(obj.getString("AVGDISTANCE"));
        this.setPeakhour_demand(obj.getString("PEAKHOURS"));
        this.setIsHotZone(obj.getString("ISHOTZONE"));
        this.setIsExpensiveRoute(obj.getString("ISEXPENSIVEROUTE"));
        this.setTrafficDensity(obj.getString("TRAFFICDENSITY"));
        return this;
    }

    @Override
    protected void setUpJsonData() {
        parseObject = new ParseObject(EasyOpsUtil.COLLECTION_NAME.ROUTE_DETAILS.toString());
        parseObject.put("ROUTENO", this.getRouteNo());
        parseObject.put("SOURCE", this.getSource());
        parseObject.put("DESTINATION", this.getDestination());
        parseObject.put("AVGDISTANCE", this.getAvgdistance());
        parseObject.put("AVGTIME", this.getAvgTime());
        parseObject.put("TRAFFICDENSITY", this.getTrafficDensity());
        parseObject.put("PEAKHOURS", this.getPeakhour_demand());
        parseObject.put("ISHOTZONE", this.getIsHotZone());
        parseObject.put("ISEXPENSIVEROUTE", this.getIsExpensiveRoute());
    }
}
