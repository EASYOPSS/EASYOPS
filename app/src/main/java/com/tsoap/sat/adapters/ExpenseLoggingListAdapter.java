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
import com.tsoap.sat.businessobject.Expense;
import com.tsoap.sat.businessobject.header;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.utils.EasyOpsCache;
import com.tsoap.sat.viewholders.ExpenseLoggingViewHolder;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nisheeth on 05/11/15.
 */
public class ExpenseLoggingListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<BaseLogging> mList;

    Context mContext;



    private static final int VIEW_TYPE_HEADER = 0x01;
    private static final int VIEW_TYPE_CONTENT = 0x00;


    public void setData(List<BaseLogging> list){
        mList = list;
    }

    public ExpenseLoggingListAdapter(Context context){
        mContext = context;
    }

    public ExpenseLoggingListAdapter(Context context,List<BaseLogging> list){
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_section, parent, false);
            viewHolder = new headerViewHolder(v);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_expense, parent, false);
             viewHolder= new ExpenseLoggingViewHolder(v);

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        View itemView = viewHolder.itemView;

        LayoutManager.LayoutParams params = (LayoutManager.LayoutParams) itemView.getLayoutParams();
        params.setSlm(LinearSLM.ID);


        if(mList.get(position).isHeader()){
            headerViewHolder holder = (headerViewHolder)viewHolder;
            header h = (header)mList.get(position);
            holder.header.setText(h.getHeaderTitle());

        }else{
            ExpenseLoggingViewHolder holder = (ExpenseLoggingViewHolder)viewHolder;
            Expense data = (Expense)mList.get(position);
            String colorCode = EasyOpsCache.getInstance(mContext).getExpenseCategories().get(data.getExpenseCategory()).getColorcode();
            holder.getColorcode().setImageResource(EasyOpsCache.getInstance(mContext).getColorCodeMap().get(colorCode));
            holder.getExpenseAmount().setText(data.getExpenseAmount()+"");
            holder.getExpenseDescription().setText(data.getDescription());
            String docAttached = "Document Not Attached";
            if(data.getAttachment()!=null){
                docAttached = "Document Attached";
            }
            String logSummary = data.getExpenseCategory() + " | " +docAttached;
            holder.getLogSummary().setText(logSummary);

        }
        params.setFirstPosition(mList.get(position).getSectionFirstPosition());
        itemView.setLayoutParams(params);
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).isHeader() ? VIEW_TYPE_HEADER : VIEW_TYPE_CONTENT;
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public List<BaseLogging> getList(){
        return mList;
    }

    class headerViewHolder extends RecyclerView.ViewHolder{


        @Bind(R.id.txt_header)
        TextView header;

        public headerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
