package com.tsoap.sat.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.tsoap.sat.easyops.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nisheeth on 04/11/15.
 */
public class RouteLoggingViewHolder extends RecyclerView.ViewHolder  {

    @Bind(R.id.color_code)
    ImageView colorcode;

    @Bind(R.id.route_item_title)
    TextView routeTitle;

    @Bind(R.id.account_type)
    TextView accountType;

    @Bind(R.id.log_summary)
    TextView logSummary;

    @Bind(R.id.revenue_amount)
    TextView revenueAmount;

    @Bind(R.id.payment_status)
    TextView incomeStatus;

    public RouteLoggingViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);

    }

    public ImageView getColorcode() {
        return colorcode;
    }

    public TextView getRouteTitle() {
        return routeTitle;
    }

    public TextView getAccountType() {
        return accountType;
    }

    public TextView getLogSummary() {
        return logSummary;
    }

    public TextView getRevenueAmount() {
        return revenueAmount;
    }

    public TextView getIncomeStatus() {
        return incomeStatus;
    }


}
