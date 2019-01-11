package com.snz.rskj.android.view.fragment;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.design.CircleImageView;
import com.snz.rskj.android.view.activity.SettingActivity;
import com.snz.rskj.android.view.adapter.OwnTabPageAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchingFragment extends BaseNetFragment<CommonPresenter, MianeModel> implements IConmmonView {
    @BindView(R.id.tablayout)
    TabLayout mTabLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    ArrayList fragmentList;
    ArrayList<String> listTitle;
    @BindView(R.id.user_image)
    ImageView userImage;
    @BindView(R.id.own_header_image)
    CircleImageView ownHeaderImage;
    @BindView(R.id.own_header_name_text)
    TextView ownHeaderNameText;
    @BindView(R.id.own_header_sex_image)
    ImageView ownHeaderSexImage;
    @BindView(R.id.own_header_guanzhu_count_text)
    TextView ownHeaderGuanzhuCountText;
    @BindView(R.id.linear_own_level_container)
    LinearLayout linearOwnLevelContainer;
    @BindView(R.id.own_header_fensi_count_text)
    TextView ownHeaderFensiCountText;
    @BindView(R.id.linear_own_authorize_container)
    LinearLayout linearOwnAuthorizeContainer;
    @BindView(R.id.own_header_beizan_count_text)
    TextView ownHeaderBeizanCountText;
    @BindView(R.id.linear_own_setting_friend)
    LinearLayout linearOwnSettingFriend;
    @BindView(R.id.own_header_qianming_text)
    TextView ownHeaderQianmingText;
    @BindView(R.id.own_header_edit_qianming_image)
    ImageView ownHeaderEditQianmingImage;
    @BindView(R.id.own_header_area_text)
    TextView ownHeaderAreaText;
    @BindView(R.id.own_header_age_text)
    TextView ownHeaderAgeText;
    @BindView(R.id.own_header_job_text)
    TextView ownHeaderJobText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.nsv)
    NestedScrollView nsv;
    Unbinder unbinder;

    private ArrayList<String> mTitles = new ArrayList<>();//页卡标题集合


    @Override
    public int getLayoutId() {
        return R.layout.fragment_matching;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        setSupportActionBar(toolbar);
//
//        //去掉标题
//
//        getSupportActionBar().setDisplayShowTitleEnabled(false);


        fragmentList = new ArrayList<>();
        listTitle = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            fragmentList.add(OwnTabFragment.getOwnTabFragment(i + ""));
        }

        listTitle.add("作品");
        listTitle.add("赞过");

        OwnTabPageAdapter mAdapter = new OwnTabPageAdapter(getChildFragmentManager(), getActivity(), fragmentList, listTitle);

        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器

        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。

        ownHeaderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.lauchActivity(getActivity(),"");
            }
        });
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }





}
