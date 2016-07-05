package com.tsoap.sat.businessobject;

import com.parse.ParseObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nisheeth on 05/11/15.
 */
public class header extends BaseLogging{

    private String headerTitle;


    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    @Override
    public BaseLogging getPojoObject(ParseObject obj){
        return null;
    }

    @Override
    protected void setUpJsonData()  {

    }
}
