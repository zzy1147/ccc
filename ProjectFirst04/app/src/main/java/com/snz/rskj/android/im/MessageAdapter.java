package com.snz.rskj.android.im;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.chatroom.demo.DemoCache;
import com.netease.nimlib.sdk.msg.attachment.FileAttachment;
import com.netease.nimlib.sdk.msg.attachment.MsgAttachment;
import com.netease.nimlib.sdk.msg.constant.MsgTypeEnum;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.snz.rskj.android.R;

import java.io.File;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context mContext;
    private List<IMMessage> mIMMessages;

    public MessageAdapter(List<IMMessage> IMMessages) {
        mIMMessages = IMMessages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mContext = viewGroup.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_p2p_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String fromAccount = mIMMessages.get(i).getFromAccount();
        if (DemoCache.getAccount().equals(fromAccount)) {
            viewHolder.mRightGroup.setVisibility(View.VISIBLE);
            viewHolder.mLeftGroup.setVisibility(View.GONE);
            viewHolder.mRightAuthorContent.setText(mIMMessages.get(i).getContent());
            if (mIMMessages.get(i).getMsgType() == MsgTypeEnum.image) {
                FileAttachment attachment = (FileAttachment) mIMMessages.get(i).getAttachment();
                if(attachment.getThumbPath()!=null){
                    viewHolder.mRightAuthorContent.setVisibility(View.GONE);
                    Glide.with(mContext).load(new File(attachment.getThumbPath())).into(viewHolder.mRightAuthorImg);
                    viewHolder.mRightAuthorImg.setVisibility(View.VISIBLE);
                }else if(attachment.getPath()!=null){
                    viewHolder.mRightAuthorContent.setVisibility(View.GONE);
                    Glide.with(mContext).load(new File(attachment.getPath())).into(viewHolder.mRightAuthorImg);
                    viewHolder.mRightAuthorImg.setVisibility(View.VISIBLE);
                }

            }
        } else {
            viewHolder.mRightGroup.setVisibility(View.GONE);
            viewHolder.mLeftGroup.setVisibility(View.VISIBLE);
            viewHolder.mLeftAuthorContent.setText(mIMMessages.get(i).getContent());
            if (mIMMessages.get(i).getMsgType() == MsgTypeEnum.image) {
                FileAttachment attachment = (FileAttachment) mIMMessages.get(i).getAttachment();
                if(attachment.getThumbPath()!=null){
                    viewHolder.mLeftAuthorContent.setVisibility(View.GONE);
                    Glide.with(mContext).load(new File(attachment.getThumbPath())).into(viewHolder.mLeftAuthorImg);
                    viewHolder.mLeftAuthorImg.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        if (mIMMessages == null) {
            return 0;
        }
        return mIMMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mRightAuthorContent;
        private ImageView mRightAuthorImage;
        private View mRightGroup;
        private TextView mLeftAuthorContent;
        private ImageView mLeftAuthorImage;
        private ImageView mRightAuthorImg;
        private ImageView mLeftAuthorImg;
        private View mLeftGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mLeftGroup = itemView.findViewById(R.id.left_group);
            mLeftAuthorImage = itemView.findViewById(R.id.left_author_img);
            mLeftAuthorContent = itemView.findViewById(R.id.left_author_content);
            mRightGroup = itemView.findViewById(R.id.right_group);
            mRightAuthorImage = itemView.findViewById(R.id.right_author_img);
            mRightAuthorContent = itemView.findViewById(R.id.right_author_content);
            mRightAuthorImg = itemView.findViewById(R.id.right_author_image);
            mLeftAuthorImg = itemView.findViewById(R.id.left_author_image);
        }
    }
}
