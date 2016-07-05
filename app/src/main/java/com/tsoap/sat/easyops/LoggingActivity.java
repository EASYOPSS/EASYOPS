package com.tsoap.sat.easyops;

import android.annotation.TargetApi;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction;
import com.tsoap.sat.fragments.logging.LoggingAccountFragment;
import com.tsoap.sat.fragments.logging.LoggingCategoryFragment;
import com.tsoap.sat.fragments.logging.LoggingEfficiencyFragment;
import com.tsoap.sat.fragments.logging.LoggingExpenseFragment;
import com.tsoap.sat.fragments.logging.LoggingRouteFragment;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.util.List;

public class LoggingActivity extends AppCompatActivity implements UserLoggingInteraction{
    private static final String LOGGING_TYPE = "Logging_type";
    private static final String LOG_TAG = LoggingActivity.class.getName();


    Toolbar appBar;

    List<BaseLogging> mList;

    FrameLayout frameLayout;

    EasyOpsUtil.loggingEnum loggingType;

    android.app.Fragment fragment;

    Menu mMenu;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging);


        Intent intent = getIntent();
       // = intent.getExtras().getString(LOGGING_TYPE);
        loggingType = (EasyOpsUtil.loggingEnum)getIntent().getSerializableExtra("LOGGING_TYPE");


        appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.primaryColorDark));

        switch (loggingType) {
            case ROUTE:
                fragment = new LoggingRouteFragment();
                ft.add(R.id.frame_layout, fragment);
                window.setStatusBarColor(this.getResources().getColor(R.color.route_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.route_theme));
                getSupportActionBar().setTitle("New RouteDetails Income");
                break;
            case EXPENSE:
                fragment = LoggingExpenseFragment.newInstance();
                ft.add(R.id.frame_layout, fragment);
                window.setStatusBarColor(this.getResources().getColor(R.color.expense_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.expense_theme));
                getSupportActionBar().setTitle("New ExpenseCategory");
                break;
            case EFFICIENCY:
                fragment = LoggingEfficiencyFragment.newInstance();
                ft.add(R.id.frame_layout, fragment);
                window.setStatusBarColor(this.getResources().getColor(R.color.efficiency_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.efficiency_theme));
                getSupportActionBar().setTitle("Efficiency");
                break;
            case ACCOUNT:
                fragment = LoggingAccountFragment.newInstance();
                ft.add(R.id.frame_layout, fragment);
                window.setStatusBarColor(this.getResources().getColor(R.color.route_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.route_theme));
                getSupportActionBar().setTitle("Add Account");
                break;
            case EXPENSE_CATEGORY:
                fragment = LoggingCategoryFragment.newInstance();
                ft.add(R.id.frame_layout, fragment);
                window.setStatusBarColor(this.getResources().getColor(R.color.expense_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.expense_theme));
                getSupportActionBar().setTitle("Add ExpenseCategory Category");
                break;


        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logging, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void submit(final BaseLogging data) {
        data.getParseObject().saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                try {
                    Log.d(LOG_TAG,"Save Process " + e);
                    try{
                        data.getParseObject().put("image", ParseObject.createWithoutData(data.getParseObject().getClass(), ParseUser.getCurrentUser().getObjectId()));
                        ParseRelation relation = ParseUser.getCurrentUser().getRelation("Relationship");
                        relation.add(data.getParseObject());
                        ParseUser.getCurrentUser().saveInBackground();

                    }
                    catch (Exception ex){
                        Toast.makeText(getApplicationContext(), "Error with relating pic with user", Toast.LENGTH_LONG).show();
                    }
                    EasyOpsCache.getInstance(LoggingActivity.this).prepareCurrentMonthData();

                } catch (ParseException e1) {
                   // Log.e(LOG_TAG,"save failed"+e1);
                    e1.printStackTrace();
                }
            }
        });
        Intent i = new Intent(LoggingActivity.this, Dashboard.class);
        startActivity(i);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult();
        fragment.onActivityResult(requestCode, resultCode, data);
        /*switch (loggingType) {

            case EXPENSE:
                fragment.onActivityResult(requestCode, resultCode, data);
                break;
            case EFFICIENCY:
                ft.add(R.id.frame_layout, LoggingEfficiencyFragment.newInstance());
                break;
*/
    }
}
