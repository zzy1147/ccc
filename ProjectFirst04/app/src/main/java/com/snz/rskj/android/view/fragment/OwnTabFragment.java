package com.snz.rskj.android.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetFragment;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.adapter.MoreItemAdapter;
import com.snz.rskj.android.bean.RankingBean;
import com.snz.rskj.android.utils.CustomLinearLayoutManager;
import com.snz.rskj.android.view.adapter.RankingItemAdater;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OwnTabFragment extends BaseNetFragment<CommonPresenter, MianeModel> implements IConmmonView {


    @BindView(R.id.recyclerView)
    RecyclerView xRecyclerView;

    RankingItemAdater adapter;
    public static OwnTabFragment getOwnTabFragment(String type){
        OwnTabFragment rankingFragment=new OwnTabFragment();
        Bundle bundle=new Bundle();
        bundle.putString("type",type);
        rankingFragment.setArguments(bundle);
        return rankingFragment;
    }
    public OwnTabFragment() {

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
        ArrayList<RankingBean> rankingBeans=new ArrayList<>();
        for(int i=0;i<10;i++){
            rankingBeans.add(new RankingBean("zzy","1000"));
        }

        adapter=new RankingItemAdater(getActivity(),rankingBeans);
        CustomLinearLayoutManager linearLayoutManager = new CustomLinearLayoutManager(getActivity());
        linearLayoutManager.setScrollEnabled(false);
        xRecyclerView.setLayoutManager(linearLayoutManager);
        xRecyclerView.setAdapter(adapter);
        xRecyclerView.setNestedScrollingEnabled(false);

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
