package com.tsoap.sat.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;
import com.tsoap.sat.businessobject.Account;
import com.tsoap.sat.businessobject.ExpenseCategory;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.utils.EasyOpsUtil;
import com.tsoap.sat.viewholders.ExpenseCategoryHolder;

import java.util.List;


/**
 * Created by nisheeth on 17/10/15.
 */
public class ExpenseCategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = ExpenseCategoryListAdapter.class.getName();
    Context mContext;

    EasyOpsUtil.loggingEnum loggingEnum;

    List<ExpenseCategory> expenseCategoryList;
    List<Account> accountList;

    EasyOpsCache instance;

    public ExpenseCategoryListAdapter(Context context){
        mContext = context;
    }

    public ExpenseCategoryListAdapter(Context context,EasyOpsUtil.loggingEnum loggingEnum){
        mContext = context;
        this.loggingEnum = loggingEnum;
        instance = EasyOpsCache.getInstance(mContext);
        switch (loggingEnum){
            case EXPENSE_CATEGORY:
                expenseCategoryList = instance.getExpenseCategoryList();
            case ACCOUNT:
                accountList = instance.getAccountList();
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("inside navigation","onCreateViewHolder" );
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.list_item_category, parent, false);
        RecyclerView.ViewHolder viewHolder = new ExpenseCategoryHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d("inside Expense Category", "onBindViewHolder" + position);
        ExpenseCategoryHolder holder = (ExpenseCategoryHolder)viewHolder;
        View itemView = viewHolder.itemView;

        //holder.colorNotationView.setImageResource(R.color.route_theme);
        LayoutManager.LayoutParams params = (LayoutManager.LayoutParams) itemView.getLayoutParams();
        params.setSlm(LinearSLM.ID);

        switch (loggingEnum){
            case EXPENSE_CATEGORY:
                holder.getCategory().setText(expenseCategoryList.get(position).getExpenseCategory());
                holder.colorNotationView.setBackground(mContext.getDrawable(instance.getColorCodeMap().get(expenseCategoryList.get(position).getColorcode())));
                break;
            case ACCOUNT:
                holder.getCategory().setText(accountList.get(position).getAccountName());
                holder.colorNotationView.setBackground(mContext.getDrawable(instance.getColorCodeMap().get(accountList.get(position).getColorCode())));
                break;
        }

        params.setFirstPosition(0);
        itemView.setLayoutParams(params);
    }


    @Override
    public int getItemCount() {
        Log.d(LOG_TAG,"item count");
        switch (loggingEnum){
            case EXPENSE_CATEGORY:
                return expenseCategoryList.size();
            case ACCOUNT:
                return accountList.size();
        }
        return 0;
    }
}
