package com.tsoap.sat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tsoap.sat.easyops.R;
import com.tsoap.sat.viewholders.NavigationViewHolder;


/**
 * Created by nisheeth on 17/10/15.
 */
public class NavigationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context mContext;

    int [] navigationMenuItem = { R.string.AddAccounts,
            R.string.RoutesDetails,
            R.string.ExpenseDetails,
            R.string.Category,
            R.string.Blank,
            R.string.todotasklist,
            R.string.charts,
            R.string.Overview,
            R.string.Blank,
            R.string.payments,
            R.string.settings,
            R.string.contact_us};

    int [] navigationMenuIcons = {R.drawable.ic_account_balance_black_24dp,
            R.drawable.ic_action_icon_maps_route_03,
            R.drawable.ic_trending_down_black_24dp,
            R.drawable.ic_label_black_24dp,
            R.drawable.ic_face_black_24dp,
            R.drawable.ic_format_list_bulleted_black_24dp,
            R.drawable.ic_insert_chart_black_24dp,
            R.drawable.ic_event_note_black_24dp,
            R.drawable.ic_face_black_24dp,
            R.drawable.ic_credit_card_black_24dp,
            R.drawable.ic_settings_applications_black_24dp,
            R.drawable.ic_phone_black_24dp};

    public NavigationListAdapter(Context context){
        mContext = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("inside navigation","onCreateViewHolder" );
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.navigation_view_item, parent, false);
        RecyclerView.ViewHolder viewHolder = new NavigationViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("inside navigation", "onBindViewHolder" + position);

        NavigationViewHolder holder = (NavigationViewHolder)viewHolder;
        if(mContext.getResources().getStringArray(R.array.navigation_string)[position].equalsIgnoreCase("BLANK")){
          holder.getItemLayout().setVisibility(View.GONE);
            holder.getNavListSaperator().setVisibility(View.VISIBLE);
        }else {
            holder.getItemLayout().setVisibility(View.VISIBLE);
            holder.getNavigationImageIcon().setImageDrawable(mContext.getResources().getDrawable(navigationMenuIcons[position]));
            holder.getNevigationTextView().setText(mContext.getResources().getStringArray(R.array.navigation_string)[position]);
            holder.getNavListSaperator().setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        Log.d("Navigation List Adapter","item count" + navigationMenuIcons.length);
        return navigationMenuItem.length;
    }
}
