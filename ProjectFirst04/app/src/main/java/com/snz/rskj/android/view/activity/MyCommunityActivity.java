package com.snz.rskj.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BasePresenter;
import com.snz.rskj.android.R;
import com.snz.rskj.android.model.UserModel;
import com.snz.rskj.android.view.adapter.OwnTabPageAdapter;
import com.snz.rskj.android.view.fragment.CommunityListFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MyCommunityActivity extends TitleBaseActivity<UserModel> {


    public static void lauchActivity(Context context, String id){
        Intent intent =new Intent(context, MyCommunityActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @Override
    public int getLayoutFrameId() {
        return R.layout.activity_community;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public CommonPresenter getPresenter() {
        return null;
    }


    @Override
    public UserModel getModel() {
        return null;
    }

    @Override
    public String getBaseTitle() {
        return "圈子";
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
