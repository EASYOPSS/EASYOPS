package com.tsoap.sat.businessobject;

import com.parse.ParseUser;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nisheeth on 29/10/15.
 */
public class UserProfile {

    private String userId;
    private String username;
    private String phoneNumber;
    private String vehicleNumber;
    private String language;
    private EasyOpsUtil.UserTypeEnum userType;
    private String vehicleModel;
    private String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String uuid) {
        this.userId = uuid;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public EasyOpsUtil.UserTypeEnum getUserType() {
        return userType;
    }

    public void setUserType(EasyOpsUtil.UserTypeEnum userType) {
        this.userType = userType;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ParseUser getparseUser( ){

        ParseUser user = new ParseUser();
        user.setUsername(this.getUsername());
        user.setPassword(this.getVehicleNumber());
        user.setEmail(this.getEmailAddress());
        user.put("PHONENUMBER",this.getPhoneNumber());
        user.put("VEHICLENUMBER",this.getVehicleNumber());
        user.put("VEHICLEMODEL",this.getVehicleModel());
        user.put("LANGUAGE",this.getLanguage());

        return user;
    }
}
