package com.snz.rskj.android.adapter;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nimlib.sdk.avchat.constant.AVChatType;
import com.snz.rskj.android.R;
import com.snz.rskj.android.activitylive.MicStateEnum;
import com.snz.rskj.android.bean.andiencebean.Audience;

import java.util.List;

public class AudienceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private int showType;
    public static int AUDIENCE = 1;
    public static int MXU = 2;
    private Context mContext;
    private List<Audience> mAudienceList;

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public AudienceAdapter(List<Audience> audienceList) {
        mAudienceList = audienceList;
    }

    @Override
    public int getItemViewType(int position) {
        if (showType == AUDIENCE) {
            return AUDIENCE;
        } else if (showType == MXU) {
            return MXU;
        }
        return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        mContext = viewGroup.getContext();
        if (AUDIENCE == i) {
            view = LayoutInflater.from(mContext).inflate(R.layout.live_audience_item, viewGroup, false);
            return new AudienceViewHolder(view);
        } else if (MXU == i) {
            view = LayoutInflater.from(mContext).inflate(R.layout.live_microphone_item, viewGroup, false);
            return new MxuViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof AudienceViewHolder) {
            Glide.with(mContext).load(mAudienceList.get(i).getAndienceImageUrl()).into(((AudienceViewHolder) viewHolder).mAudienceImage);
            ((AudienceViewHolder) viewHolder).mAudienceName.setText(mAudienceList.get(i).getAndienceName());
            ((AudienceViewHolder) viewHolder).mAudienceMicrophone.setSelected(mAudienceList.get(i).getAndienceConnectM());
            ((AudienceViewHolder) viewHolder).mAudienceMoeny.setText("  " + mAudienceList.get(i).getAndienceGoldNum());
        } else if (viewHolder instanceof MxuViewHolder) {
            Glide.with(mContext).load(mAudienceList.get(i).getAndienceImageUrl()).into(((MxuViewHolder) viewHolder).mMicrophoneImage);
            ((MxuViewHolder) viewHolder).mMicrophoneName.setText(mAudienceList.get(i).getAndienceName());

            if (mAudienceList.get(i).getAndienceConnectM()) {
               ((MxuViewHolder) viewHolder).mMicrophoneAttention.setVisibility(View.VISIBLE);
               ((MxuViewHolder) viewHolder).mMicrophoneDelete.setVisibility(View.VISIBLE);
                if (mAudienceList.get(i).getMicStateEnum() == MicStateEnum.CONNECTING) {
                    ((MxuViewHolder) viewHolder).mMicrophoneAttention.setVisibility(View.VISIBLE);
                    ((MxuViewHolder) viewHolder).mMicrophoneDelete.setVisibility(View.VISIBLE);
                } else {
                    ((MxuViewHolder) viewHolder).mMicrophoneAttention.setVisibility(View.VISIBLE);
                    ((MxuViewHolder) viewHolder).mMicrophoneDelete.setVisibility(View.VISIBLE);
                }
            } else {
                ((MxuViewHolder) viewHolder).mMicrophoneAttention.setVisibility(View.GONE);
                ((MxuViewHolder) viewHolder).mMicrophoneDelete.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAudienceList.size();
    }

    public class AudienceViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAudienceImage;
        private TextView mAudienceName;
        private TextView mAudienceMoeny;
        private ImageView mAudienceMicrophone;

        public AudienceViewHolder(@NonNull View itemView) {
            super(itemView);
            mAudienceImage = itemView.findViewById(R.id.audience_image);
            mAudienceName = itemView.findViewById(R.id.audience_name);
            mAudienceMoeny = itemView.findViewById(R.id.audience_money);
            mAudienceMicrophone = itemView.findViewById(R.id.audience_microphone);
            mAudienceMicrophone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAudienceMicrophoneInterface.microphone(getLayoutPosition());
                    if (mAudienceMicrophone.isSelected()) {
                        mAudienceMicrophone.setSelected(false);
                    } else {
                        mAudienceMicrophone.setSelected(true);
                    }
                }
            });
        }
    }

    public class MxuViewHolder extends RecyclerView.ViewHolder {

        private TextView mMicrophoneAttention;
        private TextView mMicrophoneDelete;
        private TextView mMicrophoneName;
        private ImageView mMicrophoneImage;

        public MxuViewHolder(@NonNull View itemView) {
            super(itemView);
            mMicrophoneImage = itemView.findViewById(R.id.microphone_image);
            mMicrophoneName = itemView.findViewById(R.id.microphone_name);
            mMicrophoneDelete = itemView.findViewById(R.id.microphone_delete);
            mMicrophoneAttention = itemView.findViewById(R.id.microphone_attention);
            mMicrophoneDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMicrophoneDeleteInterface.delete(getLayoutPosition());
                }
            });
            mMicrophoneAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMicrophoneAttentionInterface.attention(getLayoutPosition());

                }
            });

        }
    }

    public interface MicrophoneDelete {
        void delete(int position);
    }

    public MicrophoneDelete mMicrophoneDeleteInterface;

    public void setMicrophoneDeleteInterface(MicrophoneDelete microphoneDeleteInterface) {
        mMicrophoneDeleteInterface = microphoneDeleteInterface;
    }

    public interface AudienceMicrophoneInterface {
        void microphone(int position);
    }

    public AudienceMicrophoneInterface mAudienceMicrophoneInterface;

    public void setAudienceMicrophoneInterface(AudienceMicrophoneInterface audienceMicrophoneInterface) {
        mAudienceMicrophoneInterface = audienceMicrophoneInterface;
    }

    public interface MicrophoneAttentionInterface {
        void attention(int position);
    }

    public MicrophoneAttentionInterface mMicrophoneAttentionInterface;

    public void setMicrophoneAttentionInterface(MicrophoneAttentionInterface microphoneAttentionInterface) {
        mMicrophoneAttentionInterface = microphoneAttentionInterface;
    }

    public interface MicrophoneApplayInterface{
        void applay(int position);
    }

    public MicrophoneApplayInterface mMicrophoneApplayInterface;

    public void setMicrophoneApplayInterface(MicrophoneApplayInterface microphoneApplayInterface) {
        mMicrophoneApplayInterface = microphoneApplayInterface;
    }
}
