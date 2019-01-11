package com.example.frame.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.frame.interfaces.ICommonModel;
import com.example.frame.interfaces.IConmmonView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseNetFragment<P extends BasePresenter,M> extends BaseFragment implements IConmmonView {
    private Unbinder mBind;
    public P mPresenter;
    public M mModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayoutId(), null);
        mBind = ButterKnife.bind(this, inflate);
        initView();
        mPresenter = getPresenter();
        mModel = getModel();
        if (mPresenter != null && mModel != null)mPresenter.attach(this, (ICommonModel) mModel);
        initData();
       return inflate;
    }

    public abstract int getLayoutId();
    public abstract void initView();
    public abstract void initData();
    public abstract P getPresenter();
    public abstract M getModel();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mBind!=null){
            mBind.unbind();
        }

        if (mPresenter != null && mModel != null)mPresenter.dettach();
    }

    public void showErrorLog(Throwable e) {
        Log.e("网络请求失败：", e.toString());
        dismissLoading();
    }
}
