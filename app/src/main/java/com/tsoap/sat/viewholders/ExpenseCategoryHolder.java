package com.tsoap.sat.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsoap.sat.easyops.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nisheeth on 03/11/15.
 */
public class ExpenseCategoryHolder extends  RecyclerView.ViewHolder {

   @Bind(R.id.expense_category_title)
    public TextView category;


    @Bind(R.id.color_code)
    public LinearLayout colorNotationView;


    public ExpenseCategoryHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);


    }

    public LinearLayout getColorNotationView() {
        return colorNotationView;
    }

    public TextView getCategory() {
        return category;
    }
}
