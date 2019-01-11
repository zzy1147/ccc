package com.snz.rskj.android.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseActivity;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.model.LoginModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class TitleBaseActivity<T>   extends BaseNetActivity<CommonPresenter, T> implements IConmmonView {
    @BindView(R.id.ib_back)
    ImageView ibBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_titlebar)
    RelativeLayout rlTitlebar;
    @BindView(R.id.fl_content_child)
    FrameLayout flContentChild;


    @Override
    public int getLayoutId() {
        Log.i("zzy","getLayoutId");
        return R.layout.activity_titlebase;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("zzy","baseCreate");
    }

    @Override
    public void initView() {
        Log.i("zzy","initView");
        flContentChild.addView(LayoutInflater.from(TitleBaseActivity.this).inflate(getLayoutFrameId(), null));
        tvTitle.setText(getTitle());
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public abstract int getLayoutFrameId();

    public abstract String getBaseTitle();


}
