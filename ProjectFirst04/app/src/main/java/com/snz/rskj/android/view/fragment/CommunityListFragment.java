package com.snz.rskj.android.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.bean.CommunityBean;
import com.snz.rskj.android.bean.RankingBean;
import com.snz.rskj.android.utils.CustomLinearLayoutManager;
import com.snz.rskj.android.view.adapter.CommunityItemAdater;
import com.snz.rskj.android.view.adapter.RankingItemAdater;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommunityListFragment extends BaseNetFragment<CommonPresenter, MianeModel> implements IConmmonView {


    @BindView(R.id.recyclerView)
    RecyclerView xRecyclerView;

    CommunityItemAdater adapter;
    public static CommunityListFragment getOwnTabFragment(String type){
        CommunityListFragment rankingFragment=new CommunityListFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        rankingFragment.setArguments(bundle);
        return rankingFragment;
    }
    public CommunityListFragment() {

    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_ranking;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ArrayList<CommunityBean> rankingBeans=new ArrayList<>();
        for(int i=0;i<10;i++){
            rankingBeans.add(new CommunityBean("zzy","圈子描述",""));
        }

        adapter=new CommunityItemAdater(getActivity(),rankingBeans);
        xRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        xRecyclerView.setAdapter(adapter);

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

}
