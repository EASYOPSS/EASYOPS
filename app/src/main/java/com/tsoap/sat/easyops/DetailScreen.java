package com.tsoap.sat.easyops;

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

import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.tsoap.sat.adapters.ExpenseLoggingListAdapter;
import com.tsoap.sat.adapters.RouteLoggingListAdapter;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Expense;
import com.tsoap.sat.businessobject.Route;
import com.tsoap.sat.dbinterface.base.DBManager;
import com.tsoap.sat.fragments.List.BaseListFragment;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//import com.shephertz.app42.paas.sdk.android.storage.Storage;
//import com.tsoap.sat.dbinterface.base.UserServiceManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailScreen extends AppCompatActivity implements BaseListFragment.OnDataSubmitListener,DatePickerDialog.OnDateSetListener {

    EasyOpsUtil.loggingEnum loggingEnum;
    BaseListFragment baseListFragment;
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

    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        ButterKnife.bind(this);

        appBar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(appBar);

        month = cal.get(Calendar.MONTH)+1;
        year = cal.get(Calendar.YEAR);

        setMonth();

        mList = new ArrayList<BaseLogging>();
        loggingEnum = (EasyOpsUtil.loggingEnum) getIntent().getSerializableExtra("DETAILTYPE");

        monthView = (LinearLayout) findViewById(R.id.month_navigation);
        baseListFragment = (BaseListFragment) getFragmentManager().findFragmentById(R.id.list_fragment);
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(R.color.primaryColorDark));

            switch (loggingEnum) {
                case EXPENSE:
                    window.setStatusBarColor(this.getResources().getColor(R.color.expense_status_bar_theme));
                    appBar.setBackgroundColor(this.getResources().getColor(R.color.expense_theme));
                    monthView.setBackgroundColor(getColor(R.color.expense_theme));
                    getSupportActionBar().setTitle("Expense Details");
                    break;
                case ROUTE:
                    window.setStatusBarColor(this.getResources().getColor(R.color.route_status_bar_theme));
                    appBar.setBackgroundColor(this.getResources().getColor(R.color.route_theme));
                    monthView.setBackgroundColor(getColor(R.color.route_theme));
                    getSupportActionBar().setTitle("Route Details");
                    break;
            }
        }

        try {
            setDataToAdapter(month,year);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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
        setMonth();
        try {
            setDataToAdapter(month,year);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setMonth(){
        String strMonth = null;
        switch (month){
            case 1:
                strMonth = EasyOpsUtil.MONTH.JANUARY.toString();
                break;
            case 2:
                strMonth = EasyOpsUtil.MONTH.FEBUARY.toString();
                break;
            case 3:
                strMonth = EasyOpsUtil.MONTH.MARCH.toString();
                break;
            case 4:
                strMonth = EasyOpsUtil.MONTH.APRIL.toString();
                break;
            case 5:
                strMonth = EasyOpsUtil.MONTH.MAY.toString();
                break;
            case 6:
                strMonth = EasyOpsUtil.MONTH.JUNE.toString();
                break;
            case 7:
                strMonth = EasyOpsUtil.MONTH.JULY.toString();
                break;
            case 8:
                strMonth = EasyOpsUtil.MONTH.AUGUST.toString();
                break;
            case 9:
                strMonth = EasyOpsUtil.MONTH.SEPTEMBER.toString();
                break;
            case 10:
                strMonth = EasyOpsUtil.MONTH.OCTOBER.toString();
                break;
            case 11:
                strMonth = EasyOpsUtil.MONTH.NOVEMBER.toString();
                break;
            case 12:
                strMonth = EasyOpsUtil.MONTH.DECEMBER.toString();
                break;
        }
        monthLabel.setText(strMonth + ", " + year);
    }

    @OnClick(R.id.rightBtn)
    public void rightBtnClick(){
        month = month + 1;
        if(month > 12){
            year = year + 1;
            month = 1;
        }
        setMonth();
        try {
            setDataToAdapter(month,year);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void setDataToAdapter(int month,int year,int day) throws com.parse.ParseException, ParseException {
        List<BaseLogging> mList;
        if(day != 0){
            mList  = DBManager.getInstance(this).getDataByDate(loggingEnum,month,year,day);
        }else{
            mList = DBManager.getInstance(this).getCurrentMonthData(loggingEnum,month,year);
        }
        if(mList == null){
            baseListFragment.showEmptyListText(loggingEnum);
            return;
        }
        List list = EasyOpsUtil.getDataForStickyHeaderAdapter(mList);
        switch(loggingEnum){
            case EXPENSE:
                adapter = new ExpenseLoggingListAdapter(DetailScreen.this,list);
                break;
            case ROUTE:
                adapter = new RouteLoggingListAdapter(DetailScreen.this,list);
                break;
        }
        baseListFragment.setUp(adapter, false, loggingEnum);
    }

    private void setDataToAdapter(int month,int year) throws com.parse.ParseException, ParseException {
        setDataToAdapter(month,year,0);
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
        if (id == R.id.date_filter) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    DetailScreen.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.show(getFragmentManager(), "Datepickerdialog");

            switch(loggingEnum){
                case EXPENSE:
                    dpd.setAccentColor(R.color.expense_theme);

                    break;
                case ROUTE:
                    dpd.setAccentColor(R.color.route_theme);
                    break;
            }
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

    @Override
    public void onDateSet(DatePickerDialog view, int y, int monthOfYear, int dayOfMonth) {
        //String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        month = monthOfYear+1;
        year = y;
        setMonth();
        try {
            setDataToAdapter(month,year,dayOfMonth);
        } catch (com.parse.ParseException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
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
