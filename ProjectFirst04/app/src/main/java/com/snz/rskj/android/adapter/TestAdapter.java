package com.snz.rskj.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.frame.interfaces.IConmmonClick;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.DataInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private Context mContext;
    private List<Object> mData;

    public TestAdapter(Context context, List<Object> data) {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new TestHolder(LayoutInflater.from(mContext).inflate(R.layout.test_adapter_layout, null));
    }

    private IConmmonClick mIConmmonClick;

    public void setItemClickLitener(IConmmonClick clickLitener) {
        this.mIConmmonClick = clickLitener;
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder testHolder, final int i) {
        DataInfo.SonBean info = (DataInfo.SonBean) mData.get(i);
        Glide.with(mContext).load(info.imgsrc).into(testHolder.leftImage);
        testHolder.title.setText(info.title);
        testHolder.source.setText(info.source);
        testHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIConmmonClick.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }

    public class TestHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left_image)
        ImageView leftImage;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.source)
        TextView source;
        @BindView(R.id.item)
        RelativeLayout item;

        public TestHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
