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
import android.widget.ScrollView;
import android.widget.Spinner;

import com.tsoap.sat.adapters.MySpinnerAdapter;
import com.tsoap.sat.businessobject.ExpenseCategory;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserLoggingInteraction} interface
 * to handle interaction events.
 * Use the {@link LoggingCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoggingCategoryFragment extends Fragment {

    @Bind(R.id.categoryname)
    EditText categoryname;

    @Bind(R.id.color_code_type)
    Spinner colorCodeType;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.submit)
    Button submit;


    private UserLoggingInteraction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoggingCategoryFragment.
     */
    public static LoggingCategoryFragment newInstance() {
        LoggingCategoryFragment fragment = new LoggingCategoryFragment();

        return fragment;
    }

    public LoggingCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.logging_category_fragment, container, false);
        ButterKnife.bind(this, v);
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(getActivity(),R.layout.list_spinner_item,new String[10], EasyOpsUtil.loggingEnum.COLOR_CODE);
        colorCodeType.setAdapter(spinnerAdapter);

        return v;

    }

    @OnClick(R.id.submit)
    public void onDataSubmit() {
        ExpenseCategory expenseCategoryData = validateForm();
        if (mListener != null && expenseCategoryData != null) {
            mListener.submit(expenseCategoryData);
        }
    }

    private ExpenseCategory validateForm(){
        categoryname.setError(null);
        // mDrivingLicence.setError(null);

        boolean cancel = false;
        View focusView = null;

        String name = categoryname.getText().toString();


        //String drivingLicence = mDrivingLicence.getText().toString();

        if (TextUtils.isEmpty(name)) {
            categoryname.setError(getString(R.string.error_field_required));
            focusView = categoryname;
            cancel = true;
        }



        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            final ExpenseCategory data = new ExpenseCategory();
            //data.setUser(EasyOpsCache.getInstance(getActivity()).getUser().getVehicleNumber());
            colorCodeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    data.setColorcode(getActivity().getResources().getStringArray(R.array.colorCodeList)[i]);
                    //todo: from static factory data.setColorcode();

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            data.setExpenseCategory(name);

            /*cal.setTime(new Date());
            String strdate = dateformat3.format(cal.getTime());
            data.setCreatedOn(strdate);
*/
            EasyOpsCache.getInstance(getActivity()).getExpenseCategories().put(name,data);
            return data;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnCategoryDataSubmitListener {
        public void onSubmit(ExpenseCategory expenseData);
    }*/

}
