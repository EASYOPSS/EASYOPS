package com.tsoap.sat.fragments.dashboard;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Overview;
import com.tsoap.sat.dbinterface.base.DBManager;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.NavigationInteraction;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationInteraction} interface
 * to handle interaction events.
 * Use the {@link OverviewWidgetFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewWidgetFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    @Bind(R.id.earning_title)
    LinearLayout earningTitle;

    @Bind(R.id.earnings_summary)
    LinearLayout earningsSummary;

    @Bind(R.id.overview_widget_layout)
    LinearLayout overviewWidgetLayout;

    @Bind(R.id.revenue_amount)
    TextView revenueAmount;

    @Bind(R.id.income_amount)
    TextView incomeAmount;

    @Bind(R.id.expense_amount)
    TextView expenseAmount;

    @Bind(R.id.route_count)
    TextView routeCount;

    @Bind(R.id.distance_travel)
    TextView distanceTravel;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NavigationInteraction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverviewWidgetFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OverviewWidgetFragment newInstance(String param1, String param2) {
        OverviewWidgetFragment fragment = new OverviewWidgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public OverviewWidgetFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.widget_overview, container, false);
        ButterKnife.bind(this, view);
        try {
            EasyOpsCache.getInstance(getActivity()).prepareCurrentMonthData();
            Overview overview = EasyOpsCache.getInstance(getActivity()).getCachedOverview();
            Double revenue = overview.getIncome()-overview.getExpense();

            revenueAmount.setText(revenue+"");
            incomeAmount.setText(overview.getIncome()+"");
            expenseAmount.setText(overview.getExpense()+"");
            routeCount.setText(overview.getRoutes()+"");
            distanceTravel.setText(overview.getDistance() + " KM ");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;

    }

    @OnClick(R.id.overview_widget_layout)
    public void navigateToOverviewScreen() {
        if (mListener != null) {
            mListener.navigate();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NavigationInteraction) activity;
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

  /*  *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface NavigateOverviewScreenListener {
        // TODO: Update argument type and name
        public void navigateToOverview();
    }*/

}
