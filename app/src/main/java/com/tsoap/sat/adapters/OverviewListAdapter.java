package com.tsoap.sat.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;
import com.tsoap.sat.businessobject.Account;
import com.tsoap.sat.businessobject.ExpenseCategory;
import com.tsoap.sat.businessobject.Overview;
import com.tsoap.sat.easyops.Dashboard;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.tsoap.sat.viewholders.ExpenseCategoryHolder;
import com.tsoap.sat.viewholders.OverviewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Created by nisheeth on 17/10/15.
 */
public class OverviewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = OverviewListAdapter.class.getName();
    Context mContext;

    EasyOpsUtil.loggingEnum loggingEnum;
    Map<String,Double> mMap = new HashMap<String,Double>();

    List<ExpenseCategory> expenseCategoryList;
    List<Account> accountList;

   Map<String,Double> sortedMap;
    List<Map.Entry<String,Double>> mList;
    EasyOpsCache instance;

    //Overview mOverview;

    public OverviewListAdapter(Context context){
        mContext = context;
    }

    public OverviewListAdapter(Context context, EasyOpsUtil.loggingEnum loggingEnum,Overview overview){
        mContext = context;
        //mOverview = overview;
        this.loggingEnum = loggingEnum;

        instance = EasyOpsCache.getInstance(mContext);
        switch (loggingEnum){
            case EXPENSE:

                sortedMap = sortByComparator(overview.getExpenseListPerCategory(),false);
                mList = new ArrayList(sortedMap.entrySet());
                mMap = overview.getExpenseListPerCategory();
                break;
            case ROUTE:
                sortedMap = sortByComparator(overview.getIncomePerAccount(),false);
                mList = new ArrayList(sortedMap.entrySet());
                mMap = overview.getIncomePerAccount();
                break;
        }
    }

    private static Map<String, Double> sortByComparator(Map<String, Double> unsortMap, final boolean order) {

        List<Map.Entry<String, Double>> list = new LinkedList<Map.Entry<String, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        Map<String, Double> sortedMap = new LinkedHashMap<String, Double>();
        for (Map.Entry<String, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("inside Overview","onCreateViewHolder" );
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_overview, parent, false);
        RecyclerView.ViewHolder viewHolder = new OverviewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("inside Overview", "onBindViewHolder" + position);
        OverviewHolder holder = (OverviewHolder)viewHolder;
        View itemView = viewHolder.itemView;

        //holder.colorNotationView.setImageResource(R.color.route_theme);
        LayoutManager.LayoutParams params = (LayoutManager.LayoutParams) itemView.getLayoutParams();
        params.setSlm(LinearSLM.ID);
        if(mList.get(position).getValue()>0) {
            holder.getCategory().setText(mList.get(position).getKey().toString());
            holder.getValue().setText("INR : " + mList.get(position).getValue().toString());
            switch (loggingEnum) {
                case EXPENSE:
                    holder.getValue().setTextColor(mContext.getResources().getColor(R.color.red500));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.colorNotationView.setBackground(mContext.getResources().getDrawable(instance.getColorCodeMap().get(instance.getExpenseCategories().get(mList.get(position).getKey()).getColorcode())));
                    }
                    break;

                case ROUTE:
                    holder.getValue().setTextColor(mContext.getResources().getColor(R.color.green700));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        holder.colorNotationView.setBackground(mContext.getResources().getDrawable(instance.getColorCodeMap().get(instance.getAccountMap().get(mList.get(position).getKey()).getColorCode())));
                    }
                    break;

            }
        }else{
            holder.getValue().setVisibility(View.GONE);
            holder.getCategory().setVisibility(View.GONE);
            holder.getColorNotationView().setVisibility(View.GONE);
        }
        params.setFirstPosition(0);
        itemView.setLayoutParams(params);
    }


    @Override
    public int getItemCount() {
        Log.d(LOG_TAG,"item count");
        return mList.size();
    }

}
