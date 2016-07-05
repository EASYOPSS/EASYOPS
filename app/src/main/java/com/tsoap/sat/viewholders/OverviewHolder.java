package com.tsoap.sat.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsoap.sat.easyops.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nisheeth on 03/11/15.
 */
public class OverviewHolder extends  RecyclerView.ViewHolder {

    public TextView category;

    public TextView value;


    public LinearLayout colorNotationView;


    public OverviewHolder(View v) {
        super(v);
        category = (TextView)v.findViewById(R.id.overview_category_title);
        value = (TextView)v.findViewById(R.id.overview_category_value);
        colorNotationView = (LinearLayout)v.findViewById(R.id.color_code);

    }

    public LinearLayout getColorNotationView() {
        return colorNotationView;
    }

    public TextView getCategory() {
        return category;
    }

    public TextView getValue() {
        return value;
    }

}
