package com.snz.rskj.android.view.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.model.LoginModel;
import com.snz.rskj.android.view.adapter.RankingPageAdapter;
import com.snz.rskj.android.view.fragment.RarkingFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class RankingActivity extends BaseNetActivity<CommonPresenter, LoginModel> implements IConmmonView {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;

    ArrayList fragmentList;
    ArrayList<String> listTitle;
    @Override
    public int getLayoutId() {
        return R.layout.activity_ranking;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        fragmentList = new ArrayList<>();
        listTitle = new ArrayList<>();
        for(int i=0;i<5;i++){
            fragmentList.add(RarkingFragment.getRankFragment(i+""));
        }

        listTitle.add("消费榜");
        listTitle.add("收入榜");
        listTitle.add("粉丝数榜");
        listTitle.add("邀请榜");
        listTitle.add("成就榜");
        viewpager.setAdapter(new RankingPageAdapter(getSupportFragmentManager(),RankingActivity.this,fragmentList,listTitle));
        tablayout.setupWithViewPager(viewpager);//此方法就是让tablayout和ViewPager联动
    }

    @Override
    public CommonPresenter getPresenter() {
        return null;
    }

    @Override
    public LoginModel getModel() {
        return null;
    }

    @Override
    public void onRespose(int loadType, int apiType, Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }
}
