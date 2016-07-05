package com.tsoap.sat.easyops;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


import com.tsoap.sat.fragments.dashboard.NavigationDrawerFragment;

import com.tsoap.sat.fragments.fragmentInteraction.NavigationInteraction;
import com.tsoap.sat.utils.EasyOpsUtil;



import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dashboard extends AppCompatActivity implements NavigationInteraction{
    private static final String LOGGING_TYPE = "LOGGING_TYPE";

    Toolbar appBar;



    @Bind(R.id.action_Efficiency)
    FloatingActionButton actionEfficiency;

    @Bind(R.id.action_Expense)
    FloatingActionButton actionExpense;

    @Bind(R.id.action_Route)
    FloatingActionButton actionRoute;

    @Bind(R.id.dashboard_scroll)
    ScrollView mScrollView;


    @Bind(R.id.multiple_actions)
    FloatingActionsMenu multipleActions;


    @Bind(R.id.drawerLayout)
    DrawerLayout drawerLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_cp_with_drawer);
        ButterKnife.bind(this);

        appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        menuMultipleActions.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                 mScrollView.setAlpha(0.15f);
            }

            @Override
            public void onMenuCollapsed() {
                mScrollView.setAlpha(1);

            }
        });





        if ((!drawerLayout.isDrawerOpen(GravityCompat.START)) && Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(this.getResources().getColor(R.color.primaryColorDark));
            }
        }else if((drawerLayout.isDrawerOpen(GravityCompat.START)) && Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.drawerNavigationFragment);
        drawerFragment.setUp(drawerLayout, appBar, menuMultipleActions);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
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


    @OnClick(R.id.action_Route)
    public void newRoute(){
        Intent intent = new Intent(this,LoggingActivity.class);
        intent.putExtra(LOGGING_TYPE, EasyOpsUtil.loggingEnum.ROUTE);
        startActivity(intent);
    }

    @OnClick(R.id.action_Expense)
    public void newExpense(){
        Intent intent = new Intent(this,LoggingActivity.class);
        intent.putExtra(LOGGING_TYPE, EasyOpsUtil.loggingEnum.EXPENSE);
        startActivity(intent);
    }

    @OnClick(R.id.action_Efficiency)
    public void newEfficiency(){
        Intent intent = new Intent(this,LoggingActivity.class);
        intent.putExtra(LOGGING_TYPE, EasyOpsUtil.loggingEnum.EFFICIENCY);
        startActivity(intent);
    }

    @Override
    public void navigate() {

    }

    @Override
    public void menuItemClick(View view, int pos) {

        Intent intent = getNavigationIntent(pos);
        startActivity(intent);

    }

    private Intent getNavigationIntent(int pos) {

        Intent intent = null;
        switch (pos){
            case 0:

                intent = new Intent(this,Categories.class);
                intent.putExtra("CATEGORYTYPE",EasyOpsUtil.loggingEnum.ACCOUNT);
                break;
            case 3:

                intent = new Intent(this,Categories.class);
                intent.putExtra("CATEGORYTYPE",EasyOpsUtil.loggingEnum.EXPENSE_CATEGORY);
                break;
            case 1:

                intent = new Intent(this,DetailScreen.class);
                intent.putExtra("DETAILTYPE",EasyOpsUtil.loggingEnum.ROUTE);
                break;
            case 2:

                intent = new Intent(this,DetailScreen.class);
                intent.putExtra("DETAILTYPE",EasyOpsUtil.loggingEnum.EXPENSE);
                break;

            case 7:

                intent = new Intent(this,OverviewScreen.class);
                //intent.putExtra("DETAILTYPE",EasyOpsUtil.loggingEnum.EXPENSE);
                break;


        }
        return intent;
    }


}
