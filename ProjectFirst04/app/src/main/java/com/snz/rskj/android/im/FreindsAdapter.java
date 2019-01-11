package com.snz.rskj.android.im;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.snz.rskj.android.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FreindsAdapter extends RecyclerView.Adapter<FreindsAdapter.ViewHolder> {

    private Context mContext;
    private List<NimUserInfo> mNimUserInfoList;

    public FreindsAdapter(List<NimUserInfo> nimUserInfoList) {
        mNimUserInfoList = nimUserInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_friends_list, viewGroup, false);
        return new ViewHolder(view);
    }

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日 HH:mm:ss");
    //获取当前时间
    Date date = new Date(System.currentTimeMillis());

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.mFriendMessage.setText("晚安" + i);
        viewHolder.mSendTime.setText(simpleDateFormat.format(date));
        viewHolder.mFriendName.setText(mNimUserInfoList.get(i).getName());
        viewHolder.mUnReadCount.setText("" + i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFriendsOnClick.friends(mNimUserInfoList.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNimUserInfoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mSendTime;
        private TextView mUnReadCount;
        private TextView mFriendMessage;
        private ImageView mFriendImage;
        private TextView mFriendName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFriendName = itemView.findViewById(R.id.friend_name);
            mFriendImage = itemView.findViewById(R.id.friends_img);
            mFriendMessage = itemView.findViewById(R.id.friend_message);
            mUnReadCount = itemView.findViewById(R.id.unread_count);
            mSendTime = itemView.findViewById(R.id.message_send_time);
        }
    }

    public interface FriendsOnClick{
        void friends(NimUserInfo nimUserInfo);
    }
    public FriendsOnClick mFriendsOnClick;

    public void setFriendsOnClick(FriendsOnClick friendsOnClick) {
        mFriendsOnClick = friendsOnClick;
    }
}
