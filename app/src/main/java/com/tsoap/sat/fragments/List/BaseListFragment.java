package com.tsoap.sat.fragments.List;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tonicartos.superslim.LayoutManager;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDataSubmitListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BaseListFragment extends Fragment {

    RecyclerView baseListRecyclerView;

    Button submitBtn;

    LinearLayout submitLayout;

    private EasyOpsUtil.loggingEnum mEnum;

    private RecyclerView.Adapter mAdapter;
    private EasyOpsUtil.loggingEnum loggingEnum;
    private TextView emptyListTextView;

    private OnDataSubmitListener mListener;

    public BaseListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.base_list_fragment, container, false);
        baseListRecyclerView = (RecyclerView)v.findViewById(R.id.baseListRecyclerView);
        emptyListTextView = (TextView)v.findViewById(R.id.empty_list_text);
        submitBtn = (Button)v.findViewById(R.id.submit);

        submitLayout = (LinearLayout)v.findViewById(R.id.submit_layout);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    switch (mEnum) {
                        case ROUTE:
                            mListener.submitRouteLoggedData();
                            break;
                        case EXPENSE:
                            mListener.submitExpenseLoggedData();
                            break;
                    }
                }
            }
        });
        return v;
    }

    public void showEmptyListText(EasyOpsUtil.loggingEnum loggingtype){
        baseListRecyclerView.setVisibility(View.GONE);
        emptyListTextView.setVisibility(View.VISIBLE);
        emptyListTextView.setText("Empty List : log " + loggingtype.toString() + " data");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDataSubmitListener) activity;
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

    public void setUp(RecyclerView.Adapter adapter,boolean isSubmitButtonNeeded,EasyOpsUtil.loggingEnum loggingEnum) {
        mEnum = loggingEnum;
        baseListRecyclerView.setLayoutManager(new LayoutManager(getActivity()));
        baseListRecyclerView.setAdapter(adapter);

        if(!isSubmitButtonNeeded){
            submitBtn.setVisibility(View.GONE);
        }

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
     */
    public interface OnDataSubmitListener {
        public void submitExpenseLoggedData();
        public void submitRouteLoggedData();



    }

}
