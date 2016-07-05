package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.tsoap.sat.utils.EasyOpsCache;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by nisheeth on 05/11/15.
 */
public abstract class BaseLogging {


    SimpleDateFormat dateformat3;
    Calendar cal;
    {
        dateformat3 = new SimpleDateFormat("yyyyMMdd");
        cal = Calendar.getInstance();
    }


    private String createdOn;
    private ParseUser user;
    private boolean isHeader;
    private int sectionFirstPosition;


    BaseLogging(){

    }


    BaseLogging(ParseObject obj){
       user = obj.getParseUser("USER");
       createdOn = obj.getInt("CREATEDON")+"";
       // isHeader = obj.getBoolean("HEADER");

    }

    protected ParseObject parseObject;

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public ParseUser getUser() {
        return user;
    }

    public void setUser(ParseUser user) {
        this.user = user;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setIsHeader(boolean isHeader) {
        this.isHeader = isHeader;
    }

    public int getSectionFirstPosition() {
        return sectionFirstPosition;
    }

    public void setSectionFirstPosition(int sectionFirstPosition) {
        this.sectionFirstPosition = sectionFirstPosition;
    }

    public ParseObject getParseObject(){
        setUpJsonData();
        user = EasyOpsCache.getUser();
        parseObject.put("USER",user);
        cal.setTime(new Date());
        String strdate = null;
        if(getCreatedOn()==null){
            strdate = dateformat3.format(cal.getTime());
            this.setCreatedOn(strdate);
        }

        parseObject.put("CREATEDON", Integer.parseInt(getCreatedOn()));

        return parseObject;
    }

    abstract public BaseLogging getPojoObject(ParseObject obj) throws JSONException;

    abstract protected void setUpJsonData();

}
