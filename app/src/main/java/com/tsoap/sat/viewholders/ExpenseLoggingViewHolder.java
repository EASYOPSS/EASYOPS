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
public class ExpenseLoggingViewHolder extends RecyclerView.ViewHolder  {

    ImageView colorcode;

    TextView expenseDescription;

    TextView logSummary;

    TextView expenseAmount;


    public ImageView getColorcode() {
        return colorcode;
    }

    public TextView getExpenseDescription() {
        return expenseDescription;
    }

    public TextView getLogSummary() {
        return logSummary;
    }

    public TextView getExpenseAmount() {
        return expenseAmount;
    }



    public ExpenseLoggingViewHolder(View v) {
        super(v);
        colorcode = (ImageView)v.findViewById(R.id.color_code);
        expenseDescription = (TextView)v.findViewById(R.id.expense_description);
        logSummary = (TextView)v.findViewById(R.id.log_summary);
        expenseAmount = (TextView)v.findViewById(R.id.expense_amount);
    }



}
