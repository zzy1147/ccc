package com.snz.rskj.android.view.home.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.frame.base.BaseRecAdapter;
import com.example.frame.base.BaseRecViewHolder;
import com.jaren.lib.view.LikeView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.design.musice_play.CircleMusicView;
import com.snz.rskj.android.design.musice_play.MusicalNoteLayout;
import com.snz.rskj.android.view.home.FullScreenVideoView;
import com.snz.rskj.android.view.home.bean.HomeBean;
import com.snz.rskj.android.view.home.bean.LiveBean;
import com.snz.rskj.android.view.home.viewpager.MyVideoPlayer;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import cn.jzvd.JZVideoPlayerStandard;
import de.hdodenhof.circleimageview.CircleImageView;


public class HomeViewPagerAdater extends BaseRecAdapter<HomeBean.DataBean, HomeViewPagerAdater.VideoViewHolder> implements View.OnClickListener {
    private OnItemCilckListener onItemCilckListener;
    private Context context;
    private List<HomeBean.DataBean> mList;
    private int positions;

    public HomeViewPagerAdater(List<HomeBean.DataBean> list, Context context) {
        super(list);
        this.context = context;

    }

    public HomeViewPagerAdater(List<HomeBean.DataBean> list) {
        super(list);
    }

    @Override
    public void onHolder(VideoViewHolder holder, HomeBean.DataBean dataBean, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        holder.mp_video.setUp(dataBean.getOrigaddr(), JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
        if (position == 0) {
            holder.mp_video.startVideo();
        }
        Glide.with(context).load(dataBean.getOrigaddr()).into(holder.mp_video.thumbImageView);
        positions = position;


        holder.text_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemCilckListener != null) onItemCilckListener.Call_OnItemCilck(positions);
            }
        });
        holder.text_msg.setText((dataBean.getComment_num() != 0 ? dataBean.getComment_num() : 0) + "");
        holder.text_shre.setText((dataBean.getShare_num() != 0 ? dataBean.getShare_num() : 0) + "");
        holder.text_praise.setText((dataBean.getPraise_num() != 0 ? dataBean.getPraise_num() : 0) + "");
        holder.text_praise.setText((dataBean.getPraise_num() != 0 ? dataBean.getPraise_num() : 0) + "");
        holder.text_content.setText((dataBean.getContent() != null ? dataBean.getContent() : "") + "");
        holder.text_nusic.setText((dataBean.getMusic_title() != null ? dataBean.getMusic_title() : "") + "");
        holder.text_title.setText((dataBean.getNickname() != null ? dataBean.getNickname() : "") + "");
        holder.text_praise.setOnClickListener(this);
        holder.img_heard.setOnClickListener(this);
        holder.img_rotate.setOnClickListener(this);
        holder.text_praise.setOnClickListener(this);
        holder.text_shre_linnear.setOnClickListener(this);
        holder.text_msg_linnear.setOnClickListener(this);

    }

    @Override
    public VideoViewHolder onCreateHolder() {
        return new VideoViewHolder(getViewByRes(R.layout.home_item_view_pager));
    }

    public class VideoViewHolder extends BaseRecViewHolder {
        public View rootViews;
        public MyVideoPlayer mp_video;
        public TextView tv_title;
        public ImageView img_thumb, img_heard, img_play;
        public FrameLayout rootView;
        public TextView text_praise, text_shre, text_msg, text_title, text_content, text_nusic;
        public LinearLayout text_call, text_msg_linnear, text_shre_linnear;
        public MusicalNoteLayout img_rotate;
        public LikeView img_zan;

        public VideoViewHolder(View itemView) {
            super(itemView);
            this.rootViews = itemView;
            this.mp_video = itemView.findViewById(R.id.mp_video);
            this.img_thumb = itemView.findViewById(R.id.img_thumb);
            this.img_heard = itemView.findViewById(R.id.home_item_hard);
            this.img_play = itemView.findViewById(R.id.img_play);
            this.rootView = itemView.findViewById(R.id.root_view);
            this.text_call = itemView.findViewById(R.id.home_item_call);
            this.text_shre = itemView.findViewById(R.id.home_item_text_shre);
            this.text_praise = itemView.findViewById(R.id.home_item_text_praise);
            this.text_shre_linnear = itemView.findViewById(R.id.home_item_text_shre_linnear);
            this.text_msg = itemView.findViewById(R.id.home_item_text_msg);
            this.text_nusic = itemView.findViewById(R.id.home_music_text);
            this.text_content = itemView.findViewById(R.id.home_item_txte_content);
            this.text_msg_linnear = itemView.findViewById(R.id.home_item_msg_linnear);
            this.text_title = itemView.findViewById(R.id.home_item_text_title);
            this.img_rotate = itemView.findViewById(R.id.home_item_rotate_img);
            this.img_zan = itemView.findViewById(R.id.home_item_zan);

        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_item_call:
                if (onItemCilckListener != null) onItemCilckListener.Call_OnItemCilck(positions);
                break;
            case R.id.home_item_hard:
                if (onItemCilckListener != null) onItemCilckListener.Head_OnItemCilck(positions);
                break;
            case R.id.home_item_msg_linnear:
                if (onItemCilckListener != null) onItemCilckListener.Msg_OnItemCilck(positions)
                        ;
                break;
            case R.id.home_item_text_shre_linnear:
                if (onItemCilckListener != null) onItemCilckListener.Share_OnItemCilck(positions);
                break;
            case R.id.home_item_text_praise:
                if (onItemCilckListener != null) onItemCilckListener.Praise_OnItemCilck(positions);
                break;
            case R.id.home_item_rotate_img:
                if (onItemCilckListener != null) onItemCilckListener.Music_OnItemCilck(positions);
                break;

            case R.id.home_item_zan:
                if (onItemCilckListener != null) onItemCilckListener.Zan_OnItemCilck(positions);
                break;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_thumb, img_heard, img_play;
        MyVideoPlayer videoView;
        FrameLayout rootView;
        TextView text_praise, text_shre, text_msg, text_title, text_content, text_nusic;
        LinearLayout text_call, text_msg_linnear, text_shre_linnear;
        MusicalNoteLayout img_rotate;
        LikeView img_zan;

        public ViewHolder(View itemView) {
            super(itemView);
            img_thumb = itemView.findViewById(R.id.img_thumb);
            img_heard = itemView.findViewById(R.id.home_item_hard);
            videoView = itemView.findViewById(R.id.mp_video);
            img_play = itemView.findViewById(R.id.img_play);
            rootView = itemView.findViewById(R.id.root_view);
            text_call = itemView.findViewById(R.id.home_item_call);
            text_shre = itemView.findViewById(R.id.home_item_text_shre);
            text_praise = itemView.findViewById(R.id.home_item_text_praise);
            text_shre_linnear = itemView.findViewById(R.id.home_item_text_shre_linnear);
            text_msg = itemView.findViewById(R.id.home_item_text_msg);
            text_nusic = itemView.findViewById(R.id.home_music_text);
            text_content = itemView.findViewById(R.id.home_item_txte_content);
            text_msg_linnear = itemView.findViewById(R.id.home_item_msg_linnear);
            text_title = itemView.findViewById(R.id.home_item_text_title);
            img_rotate = itemView.findViewById(R.id.home_item_rotate_img);
            img_zan = itemView.findViewById(R.id.home_item_zan);
        }
    }


    public void setOnItemCilckListener(OnItemCilckListener onItemCilckListener) {
        this.onItemCilckListener = onItemCilckListener;
    }

    public interface OnItemCilckListener {
        void Call_OnItemCilck(int position);

        void Share_OnItemCilck(int position);

        void Msg_OnItemCilck(int position);

        void Praise_OnItemCilck(int position);

        void Music_OnItemCilck(int position);

        void Head_OnItemCilck(int position);

        void Zan_OnItemCilck(int position);

        void Rotate_OnItemCilck(int position);
    }


}


