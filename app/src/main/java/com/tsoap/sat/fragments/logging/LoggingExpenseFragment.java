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
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Expense;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link } interface
 * to handle interaction events.
 * Use the {@link LoggingExpenseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoggingExpenseFragment extends Fragment implements DatePickerDialog.OnDateSetListener {

    SimpleDateFormat dateformat3;
    Calendar cal;
    {
        dateformat3 = new SimpleDateFormat("yyyyMMdd");
        cal = Calendar.getInstance();
    }

    @Bind(R.id.expense)
    EditText expense;

    @Bind(R.id.description)
    EditText description;

    @Bind(R.id.expense_type)
    Spinner expenseType;

    @Bind(R.id.select_date_img)
    Button selectDateImg;

    @Bind(R.id.select_date)
    TextView selectDate;

    @Bind(R.id.attachment)
    Button attachment;

    @Bind(R.id.attachment_viewer)
    ImageView attachmentViewer;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.log_expense_btn)
    Button logExpenseBtn;

    String selectDateStr;

    String[] strArray;

    String expensetypestr;

    List<BaseLogging> expenseLoggingList;

    private UserLoggingInteraction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoggingExpenseFragment.
     */
    public static LoggingExpenseFragment newInstance() {
        LoggingExpenseFragment fragment = new LoggingExpenseFragment();
        return fragment;
    }

    public LoggingExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expenseLoggingList = new ArrayList<BaseLogging>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.logging_expense_fragment, container, false);
        ButterKnife.bind(this, v);
        EasyOpsCache instanse = EasyOpsCache.getInstance(getActivity());
        strArray = new String[instanse.getExpenseCategories().size()];
        strArray = EasyOpsCache.getInstance(getActivity()).getExpenseCategories().keySet().toArray(strArray);
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(getActivity(),R.layout.list_spinner_item,strArray, EasyOpsUtil.loggingEnum.EXPENSE_CATEGORY);
        expenseType.setAdapter(spinnerAdapter);

        expenseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                expensetypestr = strArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                expensetypestr = strArray[0];
            }
        });

        return v;
    }

    @OnClick(R.id.log_expense_btn)
    public void logMoreExpense() {
        Expense expenseData = validateForm();
        //expenseLoggingList.add(expenseData);
        if(expenseData != null && mListener!= null){
            mListener.submit(expenseData);
        }
    }

    private void resetForm(){
        expenseType.setSelection(0);
        description.setText(null);
        expense.setText(null);
        attachmentViewer.setVisibility(View.GONE);
    }

    private Expense validateForm() {

        expense.setError(null);
        description.setError(null);


        boolean cancel = false;
        View focusView = null;

        String expenseAmount = expense.getText().toString();
        String desc = description.getText().toString();
        //String drivingLicence = mDrivingLicence.getText().toString();

        if (TextUtils.isEmpty(expenseAmount)) {
            expense.setError(getString(R.string.error_field_required));
            focusView = expense;
            cancel = true;
        }


        if (TextUtils.isEmpty(desc)) {
            description.setError(getString(R.string.error_field_required));
            focusView = description;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            final Expense expenseData = new Expense();
            expenseData.setExpenseCategory(EasyOpsCache.getInstance(getActivity()).getExpenseCategories().get(expensetypestr).getExpenseCategory());
            expenseData.setDescription(desc);

            try{
                expenseData.setExpenseAmount(Double.parseDouble(expenseAmount));
            }catch (NumberFormatException e){
                expenseData.setExpenseAmount(0.0);
            }

            if(selectDateStr != null){
                expenseData.setCreatedOn(selectDateStr);
            }


            //todo : camera attachment functionality
            //expenseData.setAttachment();

            return expenseData;
        }

        return null;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        cal.set(year, monthOfYear, dayOfMonth);
        selectDateStr = dateformat3.format(cal.getTime());
        monthOfYear = monthOfYear + 1;
        selectDate.setText(dayOfMonth+"-"+monthOfYear+"-"+year);

    }

    @OnClick(R.id.select_date_img)
    public void setSelectDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LoggingExpenseFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setAccentColor(R.color.route_theme);


    }

}
