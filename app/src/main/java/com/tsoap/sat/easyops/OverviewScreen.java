package com.tsoap.sat.easyops;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsoap.sat.adapters.ExpenseLoggingListAdapter;
import com.tsoap.sat.adapters.OverviewListAdapter;
import com.tsoap.sat.adapters.RouteLoggingListAdapter;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Overview;
import com.tsoap.sat.dbinterface.base.DBManager;
import com.tsoap.sat.fragments.List.BaseListFragment;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

//import com.shephertz.app42.paas.sdk.android.storage.Storage;
//import com.tsoap.sat.dbinterface.base.UserServiceManager;

public class OverviewScreen extends AppCompatActivity implements BaseListFragment.OnDataSubmitListener {

    //EasyOpsUtil.loggingEnum loggingEnum;
    BaseListFragment expenseListFragment;
    BaseListFragment incomeListFragment;

    Toolbar appBar;

    //private UserLoginTask mAuthTask = null;

    Calendar cal = Calendar.getInstance();
    int month;
    int year;

    List<BaseLogging> mList;
    LinearLayout monthView;

    @Bind(R.id.leftBtn)
    ImageView leftBtn;

    @Bind(R.id.month_label)
    TextView monthLabel;

    @Bind(R.id.rightBtn)
    ImageView rightBtn;

    RecyclerView.Adapter incomeAdapter;
    RecyclerView.Adapter expenseAdapter;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview_screen);
        ButterKnife.bind(this);

        appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);

        String strMonth = EasyOpsUtil.setMonth(month);
        monthLabel.setText(strMonth + ", " + year);

        mList = new ArrayList<BaseLogging>();
        //loggingEnum = (EasyOpsUtil.loggingEnum) getIntent().getSerializableExtra("DETAILTYPE");

        monthView = (LinearLayout) findViewById(R.id.month_navigation);
        incomeListFragment = (BaseListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_income);
        expenseListFragment = (BaseListFragment) getFragmentManager().findFragmentById(R.id.list_fragment_expense);

        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.primaryColorDark));
        }


        try {
            setDataToAdapter(month,year);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // mAuthTask = new UserLoginTask();
        // mAuthTask.execute((Void) null);

    }



    @OnClick(R.id.leftBtn)
    public void leftBtnClick(){
        month = month - 1;
        if(month < 1){
            year = year - 1;
            month = 12;
        }
        String strMonth = EasyOpsUtil.setMonth(month);
        monthLabel.setText(strMonth + ", " + year);
        try {
            setDataToAdapter(month,year);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.rightBtn)
    public void rightBtnClick(){
        month = month + 1;
        if(month > 12){
            year = year + 1;
            month = 1;
        }
        String strMonth = EasyOpsUtil.setMonth(month);
        monthLabel.setText(strMonth + ", " + year);
        try {
            setDataToAdapter(month,year);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDataToAdapter(int month,int year) throws com.parse.ParseException, ParseException {
        List<BaseLogging> mIncomeList = DBManager.getInstance(this).getCurrentMonthData(EasyOpsUtil.loggingEnum.ROUTE,month,year);
        List<BaseLogging> mExpenseList = DBManager.getInstance(this).getCurrentMonthData(EasyOpsUtil.loggingEnum.EXPENSE,month,year);
        Overview overview = new Overview(mExpenseList,mIncomeList);

        expenseAdapter = new OverviewListAdapter(this, EasyOpsUtil.loggingEnum.EXPENSE,overview);
        incomeAdapter = new OverviewListAdapter(this, EasyOpsUtil.loggingEnum.ROUTE,overview);
        incomeListFragment.setUp(incomeAdapter,false, EasyOpsUtil.loggingEnum.ROUTE);
        expenseListFragment.setUp(expenseAdapter,false, EasyOpsUtil.loggingEnum.EXPENSE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail_screen, menu);
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

    @Override
    public void submitExpenseLoggedData() {

    }

    @Override
    public void submitRouteLoggedData() {

    }

   /* public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private String userId;
        private UserProfile profile;

        @Override
        protected Boolean doInBackground(Void... params) {


           try {
                // Simulate network access.
                Thread.sleep(2000);
                switch (loggingEnum){
                    case EXPENSE:

                        List<ParseObject> list = DBManager.getInstance(DetailScreen.this).getCurrentMonthData(EasyOpsUtil.COLLECTION_NAME.EXPENSE.toString(), 0);

                        for(ParseObject obj : list){
                                mList.add(new Expense(obj));

                        }
                        ExpenseLoggingListAdapter incomeAdapter = new ExpenseLoggingListAdapter(DetailScreen.this,mList);
                        baseListFragment.setUp(incomeAdapter, false, loggingEnum);
                        break;
                    case ROUTE:
                        List<ParseObject> list1 = DBManager.getInstance(DetailScreen.this).getCurrentMonthData(EasyOpsUtil.COLLECTION_NAME.ROUTE.toString(), 0);

                        for(ParseObject obj : list1){
                                mList.add(new Route(obj));
                        }
                        break;
                }



            } catch (InterruptedException e) {
               return false;
           }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                switch (loggingEnum){
                    case EXPENSE:
                        ExpenseLoggingListAdapter incomeAdapter = new ExpenseLoggingListAdapter(DetailScreen.this,mList);
                        baseListFragment.setUp(incomeAdapter, false, loggingEnum);
                        break;
                    case ROUTE:
                        RouteLoggingListAdapter adapter1 = new RouteLoggingListAdapter(DetailScreen.this,mList);
                        baseListFragment.setUp(adapter1, false, loggingEnum);
                        break;
                }
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;

        }
    }*/
}
