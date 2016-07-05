package com.tsoap.sat.fragments.logging;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tsoap.sat.adapters.MySpinnerAdapter;
import com.tsoap.sat.businessobject.Account;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Route;
import com.tsoap.sat.businessobject.RouteDetails;
import com.tsoap.sat.easyops.LoggingActivity;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoggingRouteFragment extends Fragment implements DatePickerDialog.OnDateSetListener  {

   SimpleDateFormat dateformat3;
    Calendar cal;
    {
        dateformat3 = new SimpleDateFormat("yyyyMMdd");
        cal = Calendar.getInstance();
    }

    private static final String TO_BE_FILLED_BY_BACKEND_SUPPORT = "To Be Filled";
    @Bind(R.id.source_point)
    EditText startPoint;

    @Bind(R.id.destination_point)
    EditText destPoint;

    @Bind(R.id.earningPerRoute)
    EditText earningPerRoute;

    @Bind(R.id.select_date_img)
    Button selectDateImg;

    @Bind(R.id.select_date)
    TextView selectDate;

    @Bind(R.id.traffic_fine)
    EditText trafficFinePerRoute;



    @Bind(R.id.account_type)
    Spinner accountType;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.routes_btn)
    Button routesBtn;

   /* @Bind(R.id.preview_btn)
    Button previewBtn;
*/
    @Bind(R.id.incomeStatus)
    Spinner incomeStatus;


    String[] strArray;
    List<BaseLogging> routeLoggingList;

    String accountTypeStr;
    String incomeStatustype;

    String selectDateStr;

    public LoggingRouteFragment() {

    }


    View view;

    private UserLoggingInteraction mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        routeLoggingList = new ArrayList<BaseLogging>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.logging_route_fragment, container, false);
        ButterKnife.bind(this, view);
        EasyOpsCache instanse = EasyOpsCache.getInstance(getActivity());
        strArray = new String[instanse.getAccountMap().size()];
        strArray = EasyOpsCache.getInstance(getActivity()).getAccountMap().keySet().toArray(strArray);
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(getActivity(),R.layout.list_spinner_item,strArray, EasyOpsUtil.loggingEnum.ACCOUNT);
        accountType.setAdapter(spinnerAdapter);

        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                accountTypeStr = strArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                accountTypeStr = strArray[0];
            }
        });

        incomeStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                incomeStatustype = getActivity().getResources().getStringArray(R.array.incomeStatus)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                incomeStatustype = getActivity().getResources().getStringArray(R.array.incomeStatus)[0];
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UserLoggingInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @OnClick(R.id.select_date_img)
    public void setSelectDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LoggingRouteFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setAccentColor(R.color.route_theme);
    }

    @OnClick(R.id.routes_btn)
    public void logMoreRoutes() {
        Route routelogging = validateForm();
        //routeLoggingList.add(routelogging);
        if (routelogging != null && mListener != null) {
            mListener.submit(routelogging);
        }
    }

  /*  @OnClick(R.id.preview_btn)
    public void previewBtn() {
        if (routeLoggingList.size() > 0 && mListener != null) {
            mListener.previewRouteList(routeLoggingList);
        }
    }*/

    private Route validateForm() {

        startPoint.setError(null);
        destPoint.setError(null);
        earningPerRoute.setError(null);

        boolean cancel = false;
        View focusView = null;

        String source = startPoint.getText().toString();
        String destination = destPoint.getText().toString();
        //String drivingLicence = mDrivingLicence.getText().toString();
        String earning = earningPerRoute.getText().toString();
       // String toll = tollExpense.getText().toString();
        String trafficFines = trafficFinePerRoute.getText().toString();
        //String lateFines = otherPenalty.getText().toString();

        if (TextUtils.isEmpty(source)) {
            startPoint.setError(getString(R.string.error_field_required));
            focusView = startPoint;
            cancel = true;
        }

        if (TextUtils.isEmpty(destination)) {
            destPoint.setError(getString(R.string.error_field_required));
            focusView = destPoint;
            cancel = true;
        }

        if (TextUtils.isEmpty(earning)) {
            earningPerRoute.setError(getString(R.string.error_field_required));
            focusView = earningPerRoute;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            final Route routedata = new Route();

            final RouteDetails routemetadata = new RouteDetails();
            routemetadata.setSource(source);
            routemetadata.setDestination(destination);
            routemetadata.setRouteNo(source + "__" + destination);
            routemetadata.setAvgTime("TO BE ADDED");
            routemetadata.setAvgdistance("to be added");
            routemetadata.setTrafficDensity("to be added");
            routemetadata.setIsExpensiveRoute("to be added");
            routemetadata.setIsHotZone("to be added");
            routemetadata.setPeakhour_demand("to be added");
            routemetadata.getParseObject().saveInBackground();


            routedata.setRouteNo(source + "__" + destination);
            routedata.setRouteName(source + " - " + destination);
            try{
                routedata.setIncome(Double.parseDouble(earning));
                routedata.setTrafficFines(Double.parseDouble(trafficFines));

            }catch (NumberFormatException e){
                routedata.setIncome(0.0);
                routedata.setTrafficFines(0.0);

            }
            Account account = new Account();
            routedata.setInterval_Income(TO_BE_FILLED_BY_BACKEND_SUPPORT);
            routedata.setIncome_status(incomeStatustype);
            routedata.setAccountName(EasyOpsCache.getInstance(getActivity()).getAccountMap().get(accountTypeStr).getAccountName());
            routedata.setAccountId(EasyOpsCache.getInstance(getActivity()).getAccountMap().get(accountTypeStr).getAccountId());
            if(selectDateStr != null){
                routedata.setCreatedOn(selectDateStr);
            }

            return routedata;
        }

        return null;
    }

    private void resetForm() {

        startPoint.setText(null);
        destPoint.setText(null);
        earningPerRoute.setText(null);
       // tollExpense.setText(null);
        trafficFinePerRoute.setText(null);
        //otherPenalty.setText(null);
        accountType.setSelection(0);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        cal.set(year,monthOfYear,dayOfMonth);
        selectDateStr = dateformat3.format(cal.getTime());
        monthOfYear = monthOfYear+1;
        selectDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);
    }

  /*  public interface OnRouteDataSubmitListener {
        void previewRouteList(List<BaseLogging> routeLoggingList);
    }*/
}
