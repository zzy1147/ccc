package com.snz.rskj.android.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseActivity;
import com.example.frame.base.BasePresenter;
import com.snz.rskj.android.R;
import com.snz.rskj.android.model.UserModel;
import com.snz.rskj.android.view.adapter.OwnTabPageAdapter;
import com.snz.rskj.android.view.fragment.CommunityListFragment;
import com.snz.rskj.android.view.fragment.OwnTabFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class CommunityActivity extends TitleBaseActivity<UserModel> {
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    ArrayList fragmentList;
    ArrayList<String> listTitle;


    public static void lauchActivity(Context context, String id){
        Intent intent =new Intent(context, CommunityActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fragmentList = new ArrayList<>();
        listTitle = new ArrayList<>();
        for(int i=0;i<2;i++){
            fragmentList.add(CommunityListFragment.getOwnTabFragment(i+""));
        }

        listTitle.add("推荐圈子");
        listTitle.add("我的圈子");

        OwnTabPageAdapter mAdapter = new OwnTabPageAdapter(getSupportFragmentManager(),CommunityActivity.this,fragmentList,listTitle);

        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器

        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。


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
