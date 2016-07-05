package com.tsoap.sat.businessobject;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nisheeth on 03/11/15.
 */
public class Efficiency extends BaseLogging {

    private UserProfile user;
    private String meterReading;
    private String workHours;
    private ParseFile meterReadingAttachment;

    public Efficiency(){
        super();
    }

    public Efficiency(ParseObject obj){
        super(obj);

        this.setMeterReading(obj.getString("METERREADING"));
        this.setWorkHours(obj.getString("WORKHOURS"));
    }

    public String getMeterReading() {
        return meterReading;
    }

    public void setMeterReading(String meterReading) {
        this.meterReading = meterReading;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public ParseFile getMeterReadingAttachment() {
        return meterReadingAttachment;
    }

    public void setMeterReadingAttachment(ParseFile meterReadingAttachment) {
        this.meterReadingAttachment = meterReadingAttachment;
    }



    @Override
    public BaseLogging getPojoObject(ParseObject obj) throws JSONException {
         this.setMeterReading(obj.getString("METERREADING"));
        this.setWorkHours(obj.getString("WORKHOURS"));
        return this;
    }

    @Override
    protected void setUpJsonData(){

         parseObject = new ParseObject(EasyOpsUtil.COLLECTION_NAME.EFFICIENCY.toString());
        parseObject.put("METERREADING", this.getMeterReading());
        parseObject.put("WORKHOURS", this.getWorkHours());
        parseObject.put("IMGFILE",this.getMeterReadingAttachment());
       // parseObject.put("METERPIC", this.getMeterReadingAttachment());

    }
}
