package com.tsoap.sat.fragments.brand;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.tsoap.sat.easyops.BrandingScreen;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BrandScreenFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BrandScreenFragment extends Fragment {


    private static final String LOG_TAG = BrandingScreen.class.getName();
    private static final String INSTALLATION = "Installation";

    private static final String ALREADY_REGISTERED = "already_registered";

    ProgressBar progress;

    @Bind(R.id.existing_user)
    Button existingUser;
    @Bind(R.id.register_new)
    Button registerNew;
    @Bind(R.id.btn_layout)
    LinearLayout btnLayout;

    private boolean isAlreadyRegistered;




    public OnBrandScreenInteractionListener mListener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BrandScreenFragment.
     */
    public static BrandScreenFragment newInstance(boolean isNewUser) {
        BrandScreenFragment fragment = new BrandScreenFragment();
        Bundle args = new Bundle();
        args.putBoolean(ALREADY_REGISTERED, isNewUser);
        fragment.setArguments(args);
        return fragment;
    }

    public BrandScreenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isAlreadyRegistered = getArguments().getBoolean(ALREADY_REGISTERED);
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.brand_screen_fragment, container, false);
        ButterKnife.bind(this, view);


        progress = (ProgressBar)view.findViewById(R.id.progress);



        if (isAlreadyRegistered) {
            showProgress(true);
        }

        existingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(EasyOpsUtil.UserTypeEnum.EXISTING_USER);
            }
        });

        registerNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(EasyOpsUtil.UserTypeEnum.NEW_USER);
            }
        });


        return view;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            btnLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            progress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            btnLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public interface OnBrandScreenInteractionListener {
        void onClick(EasyOpsUtil.UserTypeEnum userTypeEnum);
    }
}
