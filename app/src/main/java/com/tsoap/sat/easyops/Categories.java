package com.tsoap.sat.easyops;

import android.app.Fragment;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.tsoap.sat.adapters.ExpenseCategoryListAdapter;
import com.tsoap.sat.fragments.List.BaseListFragment;
import com.tsoap.sat.fragments.logging.LoggingExpenseFragment;
import com.tsoap.sat.utils.EasyOpsUtil;

public class Categories extends AppCompatActivity implements BaseListFragment.OnDataSubmitListener{

    private static final String LOGGING_TYPE = "LOGGING_TYPE";

    Toolbar appBar;
    BaseListFragment categoryFragment;
    EasyOpsUtil.loggingEnum loggingEnum;
    FloatingActionButton fab;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        loggingEnum = (EasyOpsUtil.loggingEnum)getIntent().getSerializableExtra("CATEGORYTYPE");
        frameLayout = (FrameLayout)findViewById(R.id.frame_layout);
        categoryFragment = (BaseListFragment) getFragmentManager().findFragmentById(R.id.categoryFragment);
        ExpenseCategoryListAdapter adapter = new ExpenseCategoryListAdapter(this,loggingEnum);
        categoryFragment.setUp(adapter, false,loggingEnum);

        fab = (FloatingActionButton)findViewById(R.id.addCategory);

        Window window = this.getWindow();
        window.setStatusBarColor(this.getResources().getColor(R.color.primaryColorDark));

        switch (loggingEnum){
            case EXPENSE_CATEGORY:
                window.setStatusBarColor(this.getResources().getColor(R.color.expense_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.expense_theme));
                getSupportActionBar().setTitle("ExpenseCategory Categories");
                fab.setColorNormal(getColor(R.color.expense_theme));
                fab.setColorPressed(getColor(R.color.expense_status_bar_theme));
                break;
            case ACCOUNT:
                window.setStatusBarColor(this.getResources().getColor(R.color.route_status_bar_theme));
                appBar.setBackgroundColor(getColor(R.color.route_theme));
                getSupportActionBar().setTitle("Accounts");
                fab.setColorNormal(getColor(R.color.route_theme));
                fab.setColorPressed(getColor(R.color.route_status_bar_theme));
                break;
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frameLayout.setVisibility(View.GONE);
                switch (loggingEnum){
                    case EXPENSE_CATEGORY:
                        Intent intent = new Intent(Categories.this,LoggingActivity.class);
                        intent.putExtra(LOGGING_TYPE, EasyOpsUtil.loggingEnum.EXPENSE_CATEGORY);
                        startActivity(intent);
                        break;


                    case ACCOUNT:
                        intent = new Intent(Categories.this,LoggingActivity.class);
                        intent.putExtra(LOGGING_TYPE, EasyOpsUtil.loggingEnum.ACCOUNT);
                        startActivity(intent);
                        break;


                }

            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_categories, menu);
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

        if(id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void submitExpenseLoggedData() {

    }

    @Override
    public void submitRouteLoggedData() {

    }
}
