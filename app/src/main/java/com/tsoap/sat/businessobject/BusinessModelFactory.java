package com.tsoap.sat.businessobject;

import com.parse.ParseObject;
import com.tsoap.sat.utils.EasyOpsUtil;

import org.json.JSONException;

/**
 * Created by nisheeth on 06/11/15.
 */
public class BusinessModelFactory {

    private static BusinessModelFactory instance;

    private BusinessModelFactory( ){
    }


    public static  BusinessModelFactory getInstance( ){
        if(instance == null){
            synchronized (BusinessModelFactory.class){
                if(instance == null){
                    instance = new BusinessModelFactory();
                }
            }
        }
        return instance;
    }

    public BaseLogging getObjectModel(EasyOpsUtil.COLLECTION_NAME tableEnum,ParseObject obj) throws JSONException {
        BaseLogging objectModel ;
        switch (tableEnum){
            case EXPENSE_CATEGORY:
                return new ExpenseCategory(obj);
            case ACCOUNT:
                return new Account(obj);

        }
        return null;
    }

}
