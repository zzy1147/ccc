package com.snz.rskj.android.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.flyco.tablayout.CommonTabLayout;
import com.snz.rskj.android.R;
import com.snz.rskj.android.view.maine.FansActivity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */

public class MianeFragment extends BaseNetFragment<CommonPresenter, MianeModel> implements IConmmonView {


    @BindView(R.id.common_title)
    CommonTabLayout commonTitle;
    @BindView(R.id.msg_fans)
    LinearLayout msgFans;
    @BindView(R.id.msg_zan)
    LinearLayout msgZan;
    @BindView(R.id.msg_comments)
    LinearLayout msgComments;
    @BindView(R.id.msg_my)
    LinearLayout msgMy;
    public MianeFragment() {
        // Required empty public constructor
    }

    public int getLayoutId() {
        return R.layout.fragment_miane;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public CommonPresenter getPresenter() {
        return new CommonPresenter();
    }

    @Override
    public MianeModel getModel() {
        return new MianeModel();
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {
        mPresenter.onError(e);
    }

    @OnClick({R.id.msg_fans, R.id.msg_zan, R.id.msg_comments, R.id.msg_my})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.msg_fans:
                startActivity(new Intent(getContext(),FansActivity.class));
                break;
            case R.id.msg_zan:
                break;
            case R.id.msg_comments:
                break;
            case R.id.msg_my:
                break;
        }
    }
}
