package com.tsoap.sat.fragments.dashboard;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.pkmmte.view.CircularImageView;
import com.tsoap.sat.adapters.NavigationListAdapter;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.NavigationInteraction;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationInteraction} interface
 * to handle interaction events.
 * Use the {@link NavigationDrawerFragment#} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends android.support.v4.app.Fragment {

    private static final String PREF_FILE_NAME = "prefFile";
    private static final String IS_DRAWER_OPEN = "Is Drawer Open";


    @Bind(R.id.menu_profile_photo)
    CircularImageView menuProfilePhoto;

    @Bind(R.id.menu_contact_name)
    TextView menuContactName;

    @Bind(R.id.menu_vehicle_model)
    TextView menuVehicleModel;

    @Bind(R.id.menu_vehicle_number)
    TextView menuVehicleNumber;

    @Bind(R.id.navigationRecyclerView)
    RecyclerView navigationRecyclerView;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;


    private boolean isDrawerAlreadyOpened;
    private boolean isDataFromSavedInstanceState;


    private NavigationInteraction mListener;


    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isDrawerAlreadyOpened = Boolean.valueOf(retrieveDataFromSharedPref(getActivity(), IS_DRAWER_OPEN, "false"));
        if (savedInstanceState != null) {
            isDataFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.navigation_drawer_fragment, container, false);
        ButterKnife.bind(this, view);


        NavigationListAdapter navAdapter = new NavigationListAdapter(getActivity());
        navigationRecyclerView.setHasFixedSize(true);
        navigationRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        navigationRecyclerView.setAdapter(navAdapter);
        navigationRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), navigationRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void click(View view, int position) {
                if(mListener!=null){
                    mListener.menuItemClick(view,position);
                }
            }

        }){

        });



        return view;
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

    public void setUp(DrawerLayout drawerLayout, final Toolbar toolbar, final FloatingActionsMenu floatingActionsMenu) {

        configureProfile();

        mDrawerLayout = drawerLayout;

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isDrawerAlreadyOpened) {
                    isDrawerAlreadyOpened = true;
                    savetosharedpreference(getActivity(), IS_DRAWER_OPEN, isDrawerAlreadyOpened + "");

                }
                getActivity().invalidateOptionsMenu();
                floatingActionsMenu.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
                floatingActionsMenu.setVisibility(View.VISIBLE);
            }


        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });


    }

    private void configureProfile() {

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnNavigationMenuItemClickListener {
        public void menuItemClick(View view,int pos);
    }
*/
    public static void savetosharedpreference(Context context, String key, String value) {
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String retrieveDataFromSharedPref(Context context, String key, String defaultValue) {
        String value = null;
        SharedPreferences pref = context.getSharedPreferences(PREF_FILE_NAME, context.MODE_PRIVATE);
        value = pref.getString(key, defaultValue);
        return value;
    }

    static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener{

        Context mContext;
        RecyclerView recyclerView;
        ClickListener clickListener;
        GestureDetector gestureDetector;

        RecyclerTouchListener(Context context,RecyclerView recyclerView,ClickListener clickListener){
            mContext = context;
            this.recyclerView = recyclerView;
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(mContext,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = recyclerView.findChildViewUnder(e.getX(),e.getY());
            if(child!=null && clickListener != null && gestureDetector.onTouchEvent(e)){
                clickListener.click(child,recyclerView.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }


        public static interface ClickListener{
            void click(View view,int position);
        }
    }

}
