package com.snz.rskj.android.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.CommunityBean;
import com.snz.rskj.android.bean.RankingBean;

import java.util.List;


public class CommunityItemAdater extends RecyclerView.Adapter<CommunityItemAdater.ViewHolder> implements View.OnClickListener {


    private Context context;
    private List<CommunityBean> mList;
    private int positions;

    public CommunityItemAdater(Context context, List<CommunityBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itm_communit, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        positions = position;
        holder.textName.setText(position+"");
        holder.textDes.setText(mList.get(position).getName());
//        holder.textJoin.setText(mList.get(position).getMoney());
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

        TextView textName, textDes,textJoin;

        public ViewHolder(View itemView) {
            super(itemView);
            textDes = itemView.findViewById(R.id.item_community_name_text);
            textName = itemView.findViewById(R.id.item_community_des_text);
            textJoin = itemView.findViewById(R.id.item_community_status_text);
        }
    }


}

