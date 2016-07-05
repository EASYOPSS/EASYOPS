package com.tsoap.sat.fragments.brand;


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
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.tsoap.sat.businessobject.UserProfile;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterOrLoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterOrLoginFragment extends Fragment {

    private static final String USER_TYPE = "userType";

    @Bind(R.id.language_spinner)
    Spinner languageSpinner;

    @Bind(R.id.spinner_layout)
    LinearLayout spinnerLayout;

    @Bind(R.id.userName)
    EditText userName;

    @Bind(R.id.userName_layout)
    LinearLayout userNameLayout;

    @Bind(R.id.phoneNumber)
    EditText phoneNumber;

    @Bind(R.id.phoneNumber_layout)
    LinearLayout phoneNumberLayout;

    @Bind(R.id.vehicleNumber)
    EditText vehicleNumber;

    @Bind(R.id.vehicleNumber_layout)
    LinearLayout vehicleNumberLayout;

    @Bind(R.id.registration_form)
    LinearLayout registrationForm;

    @Bind(R.id.registration_button)
    Button registrationButton;

    private EasyOpsUtil.UserTypeEnum mUserType;



    String seletedLang = "English";

    public OnBrandScreenInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userType Parameter 1.
     * @return A new instance of fragment RegisterOrLoginFragment.
     */
    public static RegisterOrLoginFragment newInstance(EasyOpsUtil.UserTypeEnum userType) {
        RegisterOrLoginFragment fragment = new RegisterOrLoginFragment();
        Bundle args = new Bundle();
        args.putSerializable(USER_TYPE, userType);
        fragment.setArguments(args);
        return fragment;
    }

    public RegisterOrLoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserType = (EasyOpsUtil.UserTypeEnum)getArguments().getSerializable(USER_TYPE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.brand_register_or_login_fragment, container, false);
        ButterKnife.bind(this, view);

        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                seletedLang = getActivity().getResources().getStringArray(R.array.language)[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        if (mUserType.toString().equalsIgnoreCase(EasyOpsUtil.UserTypeEnum.EXISTING_USER.toString())) {
            phoneNumberLayout.setVisibility(View.GONE);
            spinnerLayout.setVisibility(View.GONE);
            // mVehicleNumber.setVisibility(View.GONE);
            registrationButton.setText(getResources().getString(R.string.login));
        }

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile profile = validateLoginForm();
                if (profile != null) {
                    mListener.initiateCaching(profile);
                }
            }
        });


        return view;
    }

    private UserProfile validateLoginForm() {
        userName.setError(null);
        phoneNumber.setError(null);
        // mDrivingLicence.setError(null);
        vehicleNumber.setError(null);


        boolean cancel = false;
        View focusView = null;

        String username = userName.getText().toString();
        String phonenumber = phoneNumber.getText().toString();
        //String drivingLicence = mDrivingLicence.getText().toString();
        String vehiclenumber = vehicleNumber.getText().toString();

        if (TextUtils.isEmpty(username)) {
            userName.setError(getString(R.string.error_field_required));
            focusView = userName;
            cancel = true;
        }


        if (!TextUtils.isEmpty(phonenumber) && !isPhoneNumberValid(phonenumber)) {
            phoneNumber.setError(getString(R.string.error_invalid_phonenumber));
            focusView = phoneNumber;
            cancel = true;
        }

        if (TextUtils.isEmpty(vehiclenumber)) {
            vehicleNumber.setError(getString(R.string.error_field_required));
            focusView = vehicleNumber;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            UserProfile profile = new UserProfile();
            profile.setUsername(username);
            profile.setUserType(mUserType);
            // profile.setDrivingLicence(drivingLicence);
            profile.setPhoneNumber(phonenumber);
            profile.setVehicleNumber(vehiclenumber);
            profile.setEmailAddress(username+phonenumber+"@gmail.com");
            profile.setLanguage(seletedLang);
            profile.setVehicleModel("to be added");
            return profile;
        }

        return null;
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.length() == 10;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnBrandScreenInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public interface OnBrandScreenInteractionListener {
        void initiateCaching(UserProfile userProfile);
    }
}
