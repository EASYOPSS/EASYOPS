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
import android.widget.Spinner;

import com.tsoap.sat.adapters.MySpinnerAdapter;
import com.tsoap.sat.businessobject.Account;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.tsoap.sat.utils.EasyOpsCache;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction } interface
 * to handle interaction events.
 * Use the {@link LoggingAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoggingAccountFragment extends Fragment {

    @Bind(R.id.accountName)
    EditText accountName;

    @Bind(R.id.color_code_type)
    Spinner colorCodeType;

    @Bind(R.id.work_type)
    Spinner workType;

    @Bind(R.id.organisation_type)
    Spinner organisationType;



    @Bind(R.id.submitbtn)
    Button submit;

   
    public UserLoggingInteraction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment LoggingAccountFragment.
     */
    public static LoggingAccountFragment newInstance() {
        LoggingAccountFragment fragment = new LoggingAccountFragment();

        return fragment;
    }

    public LoggingAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.logging_account_fragment, container, false);

        ButterKnife.bind(this, v);
        MySpinnerAdapter spinnerAdapter = new MySpinnerAdapter(getActivity(),R.layout.list_spinner_item,new String[10], EasyOpsUtil.loggingEnum.COLOR_CODE);
        colorCodeType.setAdapter(spinnerAdapter);

        return v;
    }

    @OnClick(R.id.submitbtn)
    public void onSubmitBtn( ) {
        Account account = validateForm();
        if (mListener != null && account!=null) {
            mListener.submit(account);
        }
    }

    private Account validateForm(){
        accountName.setError(null);

        boolean cancel = false;
        View focusView = null;

        String name = accountName.getText().toString();

        if (TextUtils.isEmpty(name)) {
            accountName.setError(getString(R.string.error_field_required));
            focusView = accountName;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            final Account data = new Account();
           // data.setUser(EasyOpsCache.getInstance(getActivity()).getUser().getVehicleNumber());

            colorCodeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    data.setColorCode(getActivity().getResources().getStringArray(R.array.colorCodeList)[i]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    data.setColorCode(getActivity().getResources().getStringArray(R.array.colorCodeList)[0]);
                }
            });

            workType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    data.setWork_type(getActivity().getResources().getStringArray(R.array.worktype)[i]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    data.setWork_type(getActivity().getResources().getStringArray(R.array.worktype)[0]);
                }
            });

            organisationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    data.setOrganisation_type(getActivity().getResources().getStringArray(R.array.oragnisationType)[i]);
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    data.setOrganisation_type(getActivity().getResources().getStringArray(R.array.oragnisationType)[0]);
                }
            });



            data.setAccountName(name);
            EasyOpsCache.getInstance(getActivity()).getAccountMap().put(name,data);
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

   /* *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnAccountDataSubmitListener {
        public void submit(Account account);
    }*/

}
