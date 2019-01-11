package com.snz.rskj.android.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.NewsInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 方超 on 2018/9/26.
 */

public class MoreItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int ITEM_ONE = 1;
    private final int ITEM_TWO = 2;

    private Context context;
    private List<NewsInfo.NewsDetailInfo> list;

    private OnItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(int pos, int type);
    }

    public MoreItemAdapter(Context context, List<NewsInfo.NewsDetailInfo> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setOnLongClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        NewsInfo.NewsDetailInfo importInfo = list.get(position);
        if (importInfo.layoutType.equals("1")) {
            return ITEM_ONE;
        } else {
            return ITEM_TWO;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ITEM_ONE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_item_one, parent, false);
            return new ViewHolderOne(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.more_item_two, parent, false);
            return new ViewHolderTwo(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        NewsInfo.NewsDetailInfo info = list.get(position);
        if (holder instanceof ViewHolderOne) {
            ViewHolderOne viewHolder = (ViewHolderOne) holder;
            if (!TextUtils.isEmpty(info.title)) viewHolder.mTitle.setText(info.title);
            if (info.imageListThumb != null && info.imageListThumb.size() != 0) {
                Glide.with(context).load(info.imageListThumb.get(0))
                        .into(viewHolder.mRightImage);
            } else viewHolder.mRightImage.setVisibility(View.GONE);
            viewHolder.mToTop.setVisibility(info.isTop == 1 ? View.VISIBLE : View.GONE);
            if (!TextUtils.isEmpty(info.origin)) viewHolder.mSource.setText(info.origin);
            else viewHolder.mSource.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(info.pageviews))
                viewHolder.mFollow.setText(info.pageviews + "跟帖");
            else viewHolder.mFollow.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(info.publishTime)) viewHolder.mTime.setText(info.publishTime);
            else viewHolder.mTime.setVisibility(View.GONE);
            viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) clickListener.onItemClick(position, ITEM_ONE);
                }
            });
        } else if (holder instanceof ViewHolderTwo) {
            ViewHolderTwo viewHolder = (ViewHolderTwo) holder;
            if (!TextUtils.isEmpty(info.title)) viewHolder.mTitle.setText(info.title);
            if (info.imageListThumb != null && info.imageListThumb.size() != 0) {
                Glide.with(context).load(info.imageListThumb.get(0)).into(viewHolder.mImage);
            } else viewHolder.mImage.setVisibility(View.INVISIBLE);
            viewHolder.mToTop.setVisibility(info.isTop == 1 ? View.VISIBLE : View.GONE);
            if (!TextUtils.isEmpty(info.origin)) viewHolder.mSource.setText(info.origin);
            else viewHolder.mSource.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(info.pageviews))
                viewHolder.mFollow.setText(info.pageviews + "跟帖");
            else viewHolder.mFollow.setVisibility(View.GONE);
            if (!TextUtils.isEmpty(info.publishTime)) viewHolder.mTime.setText(info.publishTime);
            else viewHolder.mTime.setVisibility(View.GONE);
            viewHolder.mItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickListener != null) clickListener.onItemClick(position, ITEM_TWO);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderOne extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.right_image)
        ImageView mRightImage;
        @BindView(R.id.to_top)
        ImageView mToTop;
        @BindView(R.id.source)
        TextView mSource;
        @BindView(R.id.follow)
        TextView mFollow;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.item)
        ConstraintLayout mItem;

        public ViewHolderOne(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView mTitle;
        @BindView(R.id.image)
        ImageView mImage;
        @BindView(R.id.to_top)
        ImageView mToTop;
        @BindView(R.id.source)
        TextView mSource;
        @BindView(R.id.follow)
        TextView mFollow;
        @BindView(R.id.time)
        TextView mTime;
        @BindView(R.id.item)
        ConstraintLayout mItem;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
