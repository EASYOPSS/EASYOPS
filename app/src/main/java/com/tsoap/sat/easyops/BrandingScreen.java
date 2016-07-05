package com.tsoap.sat.easyops;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tsoap.sat.businessobject.UserProfile;
import com.tsoap.sat.fragments.brand.BrandScreenFragment;
import com.tsoap.sat.fragments.brand.RegisterOrLoginFragment;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BrandingScreen extends AppCompatActivity implements BrandScreenFragment.OnBrandScreenInteractionListener,RegisterOrLoginFragment.OnBrandScreenInteractionListener{

    private static final String LOG_TAG = BrandingScreen.class.getName();
    private static final String INSTALLATION = "Installation";

   // private UserLoginTask mAuthTask = null;
    private BrandScreenFragment mBrandFragment;
    boolean alreadyRegistered;
    String sID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branding_screen_activity);
        alreadyRegistered = evaluateRegistrationStatus();
        mBrandFragment = BrandScreenFragment.newInstance(alreadyRegistered);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.frame_layout, mBrandFragment);
        ft.commit();

        if(alreadyRegistered){


                String username = sID.split("_")[0];
                String password = sID.split("_")[1];
                ParseUser.logInInBackground(username, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        ParseUser useri = ParseUser.getCurrentUser();
                        EasyOpsCache.setUser(useri);
                        try {
                            initCache();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        Intent intent = new Intent(BrandingScreen.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                });


        }




    }


    private boolean evaluateRegistrationStatus() {
        try {
            sID  = readInstallationFile();

        } catch (IOException e) {
            Log.e("LOG_TAG", "installation file not present" + e);
            e.getStackTrace();

        }
        if(sID != null && !sID.equalsIgnoreCase("EASY OPS SUPPORT")){
            return true;
        }

        return false;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_branding_screen, menu);
        return true;
    }

    @Override
    public void onClick(EasyOpsUtil.UserTypeEnum userType) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_layout, RegisterOrLoginFragment.newInstance(userType));
        ft.commit();
    }




    private  String readInstallationFile() throws IOException {
        File installation = new File(this.getFilesDir(), INSTALLATION);
        RandomAccessFile f = new RandomAccessFile(installation, "r");
        byte[] bytes = new byte[(int) f.length()];
        f.readFully(bytes);
        f.close();
        return new String(bytes);
    }

    /**
     * Write the UUID in an installation file.
     * @return
     * @throws IOException
     */
    private  String writeInstallationFile(String userId) throws IOException {
        File installation = new File(this.getFilesDir(), INSTALLATION);
        Log.d(LOG_TAG, "uuid file path" + installation.getPath());
        FileOutputStream out = new FileOutputStream(installation);
        out.write(userId.getBytes());
        out.close();
        return userId;
    }

    @Override
    public void initiateCaching(UserProfile userProfile) {

        try {
            writeInstallationFile(userProfile.getUsername()+"_"+userProfile.getVehicleNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (userProfile.getUserType()){
            case NEW_USER:
                try {
                    userProfile.getparseUser().signUp();
                    ParseUser user = ParseUser.getCurrentUser();
                    EasyOpsCache.setUser(user);
                    initCache();
                    Intent intent = new Intent(BrandingScreen.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case EXISTING_USER:

                   // userProfile.getparseUser().logIn(userProfile.getUsername(),userProfile.getVehicleNumber());
                    userProfile.getparseUser().logInInBackground(userProfile.getUsername(), userProfile.getVehicleNumber(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            ParseUser useri = ParseUser.getCurrentUser();
                            EasyOpsCache.setUser(useri);
                            try {
                                initCache();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            Intent intent = new Intent(BrandingScreen.this, Dashboard.class);
                            startActivity(intent);
                            finish();
                        }
                    });



        }

    }

    private void initCache() throws ParseException {
        EasyOpsCache.getInstance(this).insertAccountData();
        EasyOpsCache.getInstance(this).insertExpenseCategoryData();
        EasyOpsCache.getInstance(this).prepareCurrentMonthData();
    }

   /* *//**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     *//*
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String userId;
        private UserProfile profile;

        UserLoginTask(UserProfile userProfile) {
            profile = userProfile;
        }

        UserLoginTask(String sID) {
            userId = sID;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            // if already installed
            if(userId != null){
                UserProfile user = UserServiceManager.getInstance(BrandingScreen.this).getUser(userId);
                EasyOpsCache.getInstance(BrandingScreen.this).setUser(user);
                initCacheforRegister(userId);
              //  EasyOpsCache.getInstance(BrandingScreen.this).insertExpenseCategoryData();
               // EasyOpsCache.getInstance(BrandingScreen.this).insertAccountData();
                return true;
            }else {

                // Check for network connection.
                if (!isNetworkAvailable()) {


                    return false;
                }

                try {
                    userId = writeInstallationFile(profile.getVehicleNumber());
                } catch (IOException e) {
                    Log.d(LOG_TAG, "Could not able to write the file" + e.getLocalizedMessage());
                    // TBR
                    //e.printStackTrace();
                }

                if (profile.getUserType().equalsIgnoreCase(EasyOpsUtil.UserTypeEnum.EXISTING_USER.toString())) {
                    UserProfile user = UserServiceManager.getInstance(BrandingScreen.this).getUser(profile.getVehicleNumber());
                    EasyOpsCache.getInstance(BrandingScreen.this).setUser(user);
                    initCacheforRegister(profile.getVehicleNumber());

                } else {
                    // First time Registration.
                    try {
                       UserProfile user = UserServiceManager.getInstance(BrandingScreen.this).createUser(profile);
                        EasyOpsCache.getInstance(BrandingScreen.this).setUser(user);
                        initCacheforRegister(profile.getVehicleNumber());
                        //EasyOpsCache.getInstance(BrandingScreen.this).insertExpenseCategoryData();
                        //EasyOpsCache.getInstance(BrandingScreen.this).insertAccountData();

                    } catch (JSONException e) {
                        Log.e(LOG_TAG,e.getMessage());
                        return false;
                    }catch (Exception e){
                        Log.e(LOG_TAG,e.getMessage());
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                // Start DashBoard Activity
                Intent intent = new Intent(BrandingScreen.this, Dashboard.class);
                // TODO : send dashboard related data in extra
                startActivity(intent);
                finish();
            }else{
                // TODO : display server not available
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }
*/
   /* private void initCache(String username) {
        try {
            EasyOpsCache.getInstance(this).initCache(username);
        } catch (JSONException e) {
            Log.e(LOG_TAG,"INIT CACHE "+ e.getMessage());
        }
    }

    private void initCacheforRegister(String username) {
        try {
            EasyOpsCache.getInstance(this).initCacheforRegister(username);
        } catch (JSONException e) {
            Log.e(LOG_TAG,"INIT CACHE "+ e.getMessage());
        }
    }*/

    /**
     * Check the availability of the network.
     * @return boolean
     */
    private boolean isNetworkAvailable() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
