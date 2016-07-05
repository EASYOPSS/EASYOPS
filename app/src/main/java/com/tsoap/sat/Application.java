package com.tsoap.sat;

import com.parse.Parse;

import com.parse.ParseUser;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsCache;

/**
 * Created by nisheeth on 01/11/15.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "v2Oenc21C1iCrGQgRiYMUWWVpbevqNhdzmRhtMB0", "FcUs6auWJ3WCQ5d6IYW78wdkLNvM7egAaOE0ayVk");

       // initCache();
    }

    private void initCache(){
        EasyOpsCache.getInstance(this).insertExpenseCategoryData();
        EasyOpsCache.getInstance(this).insertAccountData();
    }
}
