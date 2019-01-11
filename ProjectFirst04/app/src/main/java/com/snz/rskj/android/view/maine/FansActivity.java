package com.snz.rskj.android.view.maine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.frame.CommonPresenter;
import com.example.frame.base.BaseNetActivity;
import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;
import com.snz.rskj.android.R;
import com.snz.rskj.android.view.fragment.MianeModel;

public class FansActivity extends BaseNetActivity<CommonPresenter,MianeModel> implements IConmmonView {


    @Override
    public int getLayoutId() {
        return R.layout.activity_fans;
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

    }
}
