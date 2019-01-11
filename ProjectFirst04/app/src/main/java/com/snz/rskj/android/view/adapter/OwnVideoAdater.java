package com.snz.rskj.android.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.RankingBean;

import java.util.List;


public class OwnVideoAdater extends RecyclerView.Adapter<OwnVideoAdater.ViewHolder> implements View.OnClickListener {


    private Context context;
    private List<RankingBean> mList;
    private int positions;

    public OwnVideoAdater(Context context, List<RankingBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_raking_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        positions = position;
        holder.textOrder.setText(position+"");
        holder.textName.setText(mList.get(position).getName());
        holder.textMoney.setText(mList.get(position).getMoney());
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textOrder, textName, textMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            textOrder = itemView.findViewById(R.id.item_raking_order_text);
            textName = itemView.findViewById(R.id.item_raking_name_text);
            textMoney = itemView.findViewById(R.id.item_raking_money_text);
        }
    }


    public void AddHeaderItem(List<RankingBean> items) {
        mList.addAll(0, items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(List<RankingBean> items) {
        mList.addAll(items);
        notifyDataSetChanged();
    }
}

