package com.tsoap.sat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.tsoap.sat.utils.EasyOpsCache;

/**
 * Created by nisheeth on 07/11/15.
 */

public class MySpinnerAdapter extends ArrayAdapter<String> {

    Context mContext;
    String[] strArray;
    EasyOpsCache easyOpsCacheInstanse;
    EasyOpsUtil.loggingEnum loggingEnum;


    public MySpinnerAdapter(Context ctx, int txtViewResourceId, String[] objects,EasyOpsUtil.loggingEnum loggingEnum) {
        super(ctx, txtViewResourceId, objects);
        mContext = ctx;
        easyOpsCacheInstanse = EasyOpsCache.getInstance(mContext);
        strArray = objects;
        this.loggingEnum = loggingEnum;
    }

    @Override
    public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
        return getCustomView(position, cnvtView, prnt);
    }

    @Override
    public View getView(int pos, View cnvtView, ViewGroup prnt) {
        return getCustomView(pos, cnvtView, prnt);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View mySpinner = inflater.inflate(R.layout.list_spinner_item, parent, false);
        TextView main_text = (TextView) mySpinner.findViewById(R.id.expense_category_title);
        main_text.setText(strArray[position]);
        LinearLayout colorImg = (LinearLayout) mySpinner.findViewById(R.id.color_code);

        switch (loggingEnum){
            case EXPENSE_CATEGORY:
                colorImg.setBackground(mContext.getDrawable(easyOpsCacheInstanse.getColorCodeMap().get(easyOpsCacheInstanse.getExpenseCategories().get(strArray[position]).getColorcode())));
                break;
            case ACCOUNT:
                colorImg.setBackground(mContext.getDrawable(easyOpsCacheInstanse.getColorCodeMap().get(easyOpsCacheInstanse.getAccountMap().get(strArray[position]).getColorCode())));
                break;
            case COLOR_CODE:
                colorImg.setBackground(mContext.getDrawable(easyOpsCacheInstanse.getColorCodeMap().get(mContext.getResources().getStringArray(R.array.colorCodeList)[position])));
                main_text.setVisibility(View.GONE);
                break;
        }

        return mySpinner;
    }

}



