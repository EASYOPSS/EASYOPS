package com.tsoap.sat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;
import com.tsoap.sat.businessobject.BaseLogging;
import com.tsoap.sat.businessobject.Route;
import com.tsoap.sat.businessobject.header;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.viewholders.RouteLoggingViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nisheeth on 05/11/15.
 */
public class RouteLoggingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BaseLogging> mList = new ArrayList<BaseLogging>();

    Context mContext;

    private static final int VIEW_TYPE_HEADER = 0x01;
    private static final int VIEW_TYPE_CONTENT = 0x00;


    public void setData(List<BaseLogging> list) {
        mList = list;
    }

    public RouteLoggingListAdapter(Context context) {
        mContext = context;
    }


    public RouteLoggingListAdapter(Context context,List<BaseLogging> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("inside navigation", "onCreateViewHolder");

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;
        RecyclerView.ViewHolder viewHolder;
        if (viewType == VIEW_TYPE_HEADER) {
            v = inflater.inflate(R.layout.header_section, parent, false);
            viewHolder = new HeaderViewHolder(v);
        } else {
            v = inflater.inflate(R.layout.list_item_route, parent, false);
            viewHolder = new RouteLoggingViewHolder(v);

        }
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        View itemView = viewHolder.itemView;

        LayoutManager.LayoutParams params = (LayoutManager.LayoutParams) itemView.getLayoutParams();
        params.setSlm(LinearSLM.ID);

        Route data;
        if (mList.get(position).isHeader()) {
            HeaderViewHolder holder = (HeaderViewHolder) viewHolder;
            header h = (header) mList.get(position);
            holder.header.setText(h.getHeaderTitle());

        } else {
            RouteLoggingViewHolder holder = (RouteLoggingViewHolder) viewHolder;
            data = (Route) mList.get(position);
            String colorCode = EasyOpsCache.getInstance(mContext).getAccountMap().get(data.getAccountName()).getColorCode();
            holder.getColorcode().setImageResource(EasyOpsCache.getInstance(mContext).getColorCodeMap().get(colorCode));
            holder.getAccountType().setText(data.getAccountName());
            holder.getIncomeStatus().setText(data.getIncome_status());
            holder.getRouteTitle().setText(data.getRouteName());
            holder.getRevenueAmount().setText(String.valueOf(data.getRevenue()));

            StringBuilder logSummary = new StringBuilder();
            logSummary.append("Income : " + data.getIncome());
            double fines = data.getTrafficFines();

            if (fines != 0.0) {
                logSummary.append("  |  Expense : " + fines);
            }
            holder.getLogSummary().setText(logSummary);

        }

        params.setFirstPosition(mList.get(position).getSectionFirstPosition());
        itemView.setLayoutParams(params);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<BaseLogging> getList() {
        return mList;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).isHeader() ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {


        @Bind(R.id.txt_header)
        TextView header;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

}


