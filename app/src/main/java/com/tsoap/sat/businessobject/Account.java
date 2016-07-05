package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nisheeth on 01/11/15.
 */
public class Account extends BaseLogging{

    private String accountId;
    private String accountName;
    private String work_type;
    private String organisation_type;
    private String colorCode;

    public Account(){
        super();

    }

    public Account(ParseObject obj){
        super(obj);
        //this.setUser(obj.getParseUser("USER"));
        this.setAccountId(obj.getString("ACCOUNTID"));
        this.setAccountName(obj.getString("ACCOUNTNAME"));
        this.setColorCode(obj.getString("COLORCODE"));
        this.setWork_type(obj.getString("WORKTYPE"));
        this.setOrganisation_type(obj.getString("ORGANISATIONTYPE"));
    }

    public String getOrganisation_type() {
        return organisation_type;
    }

    public void setOrganisation_type(String organisation_type) {
        this.organisation_type = organisation_type;
    }

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
        this.accountId = EasyOpsCache.getUser().getUsername()+accountName;
    }

    @Override
    public BaseLogging getPojoObject(ParseObject obj) throws JSONException {
        //this.setUser(obj.getParseUser("USER"));
        this.setAccountName(obj.getString("ACCOUNTNAME"));
        this.setColorCode(obj.getString("COLORCODE"));
        this.setWork_type(obj.getString("WORKTYPE"));
        this.setOrganisation_type(obj.getString("ORGANISATIONTYPE"));
        return this;
    }

    @Override
    protected void setUpJsonData() {
        parseObject = new ParseObject(EasyOpsUtil.COLLECTION_NAME.ACCOUNT.toString());
       // parseObject.put("USER", this.getUser());
        parseObject.put("ACCOUNTID",this.getAccountId());
        parseObject.put("ACCOUNTNAME", this.getAccountName());
        parseObject.put("COLORCODE", this.getColorCode());
        parseObject.put("WORKTYPE", this.getWork_type());
        parseObject.put("ORGANISATIONTYPE", this.getOrganisation_type());

    }
}
