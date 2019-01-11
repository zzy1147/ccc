package com.snz.rskj.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.nim.chatroom.demo.entertainment.constant.GiftType;
import com.netease.nim.chatroom.demo.entertainment.model.Gift;
import com.snz.rskj.android.R;

import java.util.List;

public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.ViewHolder> {
    private Context mContext;
    private List<Gift> mGiftList;

    public GiftAdapter(List<Gift> giftList) {
        mGiftList = giftList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View giftView = LayoutInflater.from(mContext).inflate(R.layout.live_gift_item, viewGroup, false);
        return new ViewHolder(giftView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        if(mGiftList.get(i).getGiftType() == GiftType.ROSE){
            viewHolder.mGiftImage.setImageResource(mGiftList.get(i).getImageId());
            viewHolder.mGiftName.setText(mGiftList.get(i).getTitle());
            viewHolder.mGiftCount.setText(mGiftList.get(i).getCount()+"朵");
        }else if(mGiftList.get(i).getGiftType() == GiftType.CHOCOLATE){
            viewHolder.mGiftImage.setImageResource(mGiftList.get(i).getImageId());
            viewHolder.mGiftName.setText(mGiftList.get(i).getTitle());
            viewHolder.mGiftCount.setText(mGiftList.get(i).getCount()+"盒");
        }else if(mGiftList.get(i).getGiftType() == GiftType.BEAR){
            viewHolder.mGiftImage.setImageResource(mGiftList.get(i).getImageId());
            viewHolder.mGiftName.setText(mGiftList.get(i).getTitle());
            viewHolder.mGiftCount.setText(mGiftList.get(i).getCount()+"个");
        }else if(mGiftList.get(i).getGiftType() == GiftType.ICECREAM){
            viewHolder.mGiftImage.setImageResource(mGiftList.get(i).getImageId());
            viewHolder.mGiftName.setText(mGiftList.get(i).getTitle());
            viewHolder.mGiftCount.setText(mGiftList.get(i).getCount()+"个");
        }
    }

    @Override
    public int getItemCount() {
        return mGiftList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private  TextView mGiftName;
        private  TextView mGiftCount;
        private  ImageView mGiftImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mGiftName = itemView.findViewById(R.id.gift_name);
            mGiftCount = itemView.findViewById(R.id.gift_count);
            mGiftImage = itemView.findViewById(R.id.gift_imgShow);
        }
    }
}
